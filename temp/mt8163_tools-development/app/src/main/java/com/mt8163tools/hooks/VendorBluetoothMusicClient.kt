package com.mt8163tools.hooks

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Parcel
import android.os.RemoteException
import de.robv.android.xposed.XposedBridge

/**
 * Read-only observer for the vendor BluetoothMusicService.
 *
 * This client never issues AVRCP commands or changes A2DP routing.
 */
internal class VendorBluetoothMusicClient(
    private val eventSink: (String, String) -> Unit
) {
    private val mainHandler = Handler(Looper.getMainLooper())

    @Volatile
    private var enabled = false

    @Volatile
    private var serviceBinder: IBinder? = null

    @Volatile
    private var service: VendorMusicServiceProxy? = null

    private var callbackRegistered = false
    private var appContext: Context? = null
    private var bound = false
    private var deathRecipient: IBinder.DeathRecipient? = null
    private var a2dpConnected: Boolean? = null
    private var avrcpConnected: Boolean? = null
    private var a2dpPlaying: Boolean? = null
    private var accState: Boolean? = null
    private var wasPlayingBeforeAccOff = false
    private var restoreRequested = false
    private var restoreCommandSent = false
    private var restorePendingLogged = false

    private val callback = MusicCallbackBinder { type, message, state ->
        mainHandler.post {
            when (type) {
                "bluetooth_a2dp_state" -> a2dpConnected = state
                "bluetooth_avrcp_state" -> avrcpConnected = state
                "bluetooth_playback_state" -> {
                    a2dpPlaying = state
                    if (state == true && restoreCommandSent) {
                        publish("bluetooth_playback_restored", "Bluetooth playback resumed")
                        clearRestoreRequest()
                    }
                }
            }
            publish(type, message)
            maybeRestoreBluetooth()
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            if (!enabled) return

            serviceBinder = binder
            val recipient = IBinder.DeathRecipient {
                mainHandler.post { handleBinderDeath(binder) }
            }
            deathRecipient = recipient

            try {
                binder.linkToDeath(recipient, 0)
            } catch (exception: RemoteException) {
                log("linkToDeath failed: $exception")
                handleBinderDeath(binder)
                return
            }

            if (!enabled || serviceBinder !== binder) return

            val proxy = VendorMusicServiceProxy(binder)
            service = proxy
            try {
                proxy.registerCallback(callback)
                callbackRegistered = true

                publish(
                    "bluetooth_service_connected",
                    "Vendor Bluetooth music service connected"
                )

                // The service does not send initial A2DP/AVRCP states on registration.
                val a2dpConnected = proxy.isA2dpConnected()
                val avrcpConnected = proxy.isAvrcpConnected()
                val a2dpPlaying = proxy.isA2dpPlaying()
                this@VendorBluetoothMusicClient.a2dpConnected = a2dpConnected
                this@VendorBluetoothMusicClient.avrcpConnected = avrcpConnected
                this@VendorBluetoothMusicClient.a2dpPlaying = a2dpPlaying
                publish(
                    "bluetooth_state_snapshot",
                    "A2DP connected=$a2dpConnected, AVRCP connected=$avrcpConnected, playing=$a2dpPlaying"
                )
                maybeRestoreBluetooth()
            } catch (exception: Exception) {
                log("Binder initialization failed: $exception")
                publish(
                    "bluetooth_service_error",
                    "Vendor Bluetooth Binder initialization failed: $exception"
                )
                if (binder.isBinderAlive) {
                    try {
                        proxy.unregisterCallback(callback)
                    } catch (unregisterError: Exception) {
                        log("callback cleanup failed: $unregisterError")
                    }
                }
                clearRemote(binder)
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            clearRemote(serviceBinder)
            publish(
                "bluetooth_service_disconnected",
                "Vendor Bluetooth music service disconnected"
            )
        }

        override fun onBindingDied(name: ComponentName) {
            val binder = serviceBinder
            clearRemote(binder)
            publish(
                "bluetooth_service_binding_died",
                "Vendor Bluetooth service binding died"
            )
            unbindIfNeeded()
            if (enabled) bindIfNeeded()
        }

        override fun onNullBinding(name: ComponentName) {
            clearRemote(serviceBinder)
            publish(
                "bluetooth_service_error",
                "Vendor Bluetooth service returned a null Binder"
            )
            unbindIfNeeded()
        }
    }

    fun attach(context: Context) {
        if (appContext != null) return
        appContext = context.applicationContext ?: context
    }

    fun onAccStateChanged(
        state: Boolean,
        mediaSessionOwnsRestore: Boolean,
        sourcePackage: String? = null
    ) {
        if (!enabled) return

        mainHandler.post {
            if (!enabled) return@post

            if (!state) {
                if (accState != false) {
                    wasPlayingBeforeAccOff = if (
                        sourcePackage != BLUETOOTH_SOURCE_PACKAGE
                    ) {
                        publish(
                            "bluetooth_acc_off_snapshot",
                            "Bluetooth playback query skipped: SourceInfo source=${sourcePackage ?: "unknown"}"
                        )
                        false
                    } else {
                        try {
                            val currentService = service
                            if (serviceBinder?.isBinderAlive == true && currentService != null) {
                                currentService.isA2dpPlaying().also { playing ->
                                    publish(
                                        "bluetooth_acc_off_snapshot",
                                        "SourceInfo source=$sourcePackage; A2DP playing=$playing"
                                    )
                                }
                            } else {
                                publish(
                                    "bluetooth_acc_off_snapshot",
                                    "SourceInfo source=$sourcePackage; A2DP state UNKNOWN: Bluetooth service unavailable"
                                )
                                false
                            }
                        } catch (exception: Exception) {
                            publish(
                                "bluetooth_acc_off_snapshot",
                                "SourceInfo source=$sourcePackage; A2DP state UNKNOWN: $exception"
                            )
                            log("failed to query A2DP playback state at ACC OFF: $exception")
                            false
                        }
                    }
                    restoreRequested = false
                    restoreCommandSent = false
                    restorePendingLogged = false
                }
                accState = false
                return@post
            }

            val previousState = accState
            accState = true
            if (previousState != false) return@post

            if (!wasPlayingBeforeAccOff) {
                publish(
                    "bluetooth_restore_skipped",
                    "Bluetooth PLAY skipped: Bluetooth was not playing before ACC OFF"
                )
                clearRestoreRequest()
                return@post
            }

            if (mediaSessionOwnsRestore) {
                publish(
                    "bluetooth_restore_skipped",
                    "Bluetooth PLAY skipped: an active MediaSession owns playback restoration"
                )
                clearRestoreRequest()
                wasPlayingBeforeAccOff = false
                return@post
            }

            restoreRequested = true
            maybeRestoreBluetooth()
        }
    }

    fun setEnabled(value: Boolean) {
        if (enabled == value) return
        enabled = value
        if (value) {
            bindIfNeeded()
        } else {
            disconnect()
        }
    }

    private fun bindIfNeeded() {
        if (!enabled || bound) return
        val context = appContext ?: return

        val intent = Intent(SERVICE_ACTION).setComponent(
            ComponentName(SERVICE_PACKAGE, SERVICE_CLASS)
        )
        try {
            bound = context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            if (!bound) {
                publish(
                    "bluetooth_service_bind_failed",
                    "Could not bind to vendor Bluetooth music service"
                )
            }
        } catch (exception: Exception) {
            bound = false
            publish(
                "bluetooth_service_bind_failed",
                "Vendor Bluetooth service bind failed: $exception"
            )
            log("bindService failed: $exception")
        }
    }

    private fun disconnect() {
        val binder = serviceBinder
        val proxy = service

        if (proxy != null && callbackRegistered && binder?.isBinderAlive == true) {
            try {
                proxy.unregisterCallback(callback)
            } catch (exception: Exception) {
                log("callback unregister failed: $exception")
            }
        }

        callbackRegistered = false
        clearRemote(binder)
        accState = null
        wasPlayingBeforeAccOff = false
        clearRestoreRequest()
        unbindIfNeeded()
        publish(
            "bluetooth_service_disabled",
            "Vendor Bluetooth observer disabled"
        )
    }

    private fun unbindIfNeeded() {
        if (!bound) return
        val context = appContext ?: return
        try {
            context.unbindService(serviceConnection)
        } catch (exception: IllegalArgumentException) {
            log("service was already unbound: $exception")
        } catch (exception: Exception) {
            log("unbindService failed: $exception")
        } finally {
            bound = false
        }
    }

    private fun handleBinderDeath(deadBinder: IBinder) {
        if (serviceBinder !== deadBinder) return
        clearRemote(deadBinder)
        publish(
            "bluetooth_service_binder_died",
            "Vendor Bluetooth Binder died"
        )
        unbindIfNeeded()
        if (enabled) bindIfNeeded()
    }

    private fun clearRemote(expectedBinder: IBinder?) {
        if (expectedBinder == null || serviceBinder !== expectedBinder) return

        val recipient = deathRecipient
        if (recipient != null) {
            try {
                expectedBinder.unlinkToDeath(recipient, 0)
            } catch (_: NoSuchElementException) {
                // The Binder death recipient was already removed.
            } catch (_: Exception) {
                // The remote process may already be gone.
            }
        }

        serviceBinder = null
        service = null
        deathRecipient = null
        callbackRegistered = false
        a2dpConnected = null
        avrcpConnected = null
        a2dpPlaying = null
    }

    private fun maybeRestoreBluetooth() {
        if (!enabled || accState != true || !restoreRequested || restoreCommandSent) return

        if (a2dpConnected != true || avrcpConnected != true || a2dpPlaying == null) {
            if (!restorePendingLogged) {
                restorePendingLogged = true
                publish(
                    "bluetooth_restore_pending",
                    "Waiting for A2DP, AVRCP and playback state"
                )
            }
            return
        }

        if (a2dpPlaying == true) {
            publish(
                "bluetooth_restore_already_playing",
                "Bluetooth playback is already active; PLAY was not sent"
            )
            clearRestoreRequest()
            return
        }

        val proxy = service ?: return
        try {
            restoreCommandSent = true
            proxy.sendAvrcpCommand(AVRCP_PLAY)
            publish(
                "bluetooth_restore_requested",
                "AVRCP PLAY sent after A2DP and AVRCP became ready"
            )
        } catch (exception: Exception) {
            restoreCommandSent = false
            restoreRequested = false
            publish(
                "bluetooth_restore_error",
                "AVRCP PLAY failed: $exception"
            )
            log("AVRCP PLAY failed: $exception")
        }
    }

    private fun clearRestoreRequest() {
        restoreRequested = false
        restoreCommandSent = false
        restorePendingLogged = false
    }

    private fun publish(type: String, message: String) {
        mainHandler.post {
            try {
                eventSink(type, message)
            } catch (exception: Exception) {
                log("event publication failed ($type): $exception")
            }
        }
    }

    private fun log(message: String) {
        XposedBridge.log("$LOG_TAG: $message")
    }

    private class VendorMusicServiceProxy(
        private val remote: IBinder
    ) {
        fun isA2dpConnected(): Boolean = transactBoolean(TRANSACTION_IS_A2DP_CONNECTED)
        fun isAvrcpConnected(): Boolean = transactBoolean(TRANSACTION_IS_AVRCP_CONNECTED)
        fun isA2dpPlaying(): Boolean = transactBoolean(TRANSACTION_IS_A2DP_PLAYING)

        fun registerCallback(callback: IBinder) {
            transactCallback(TRANSACTION_REGISTER_CALLBACK, callback)
        }

        fun sendAvrcpCommand(command: Int) {
            transactInt(TRANSACTION_SEND_AVRCP_COMMAND, command)
        }

        fun unregisterCallback(callback: IBinder) {
            transactCallback(TRANSACTION_UNREGISTER_CALLBACK, callback)
        }

        private fun transactBoolean(code: Int): Boolean {
            val data = Parcel.obtain()
            val reply = Parcel.obtain()
            try {
                data.writeInterfaceToken(SERVICE_DESCRIPTOR)
                if (!remote.transact(code, data, reply, 0)) {
                    throw RemoteException("Unsupported Bluetooth service transaction $code")
                }
                reply.readException()
                return reply.readInt() != 0
            } finally {
                reply.recycle()
                data.recycle()
            }
        }

        private fun transactInt(code: Int, value: Int) {
            val data = Parcel.obtain()
            val reply = Parcel.obtain()
            try {
                data.writeInterfaceToken(SERVICE_DESCRIPTOR)
                data.writeInt(value)
                if (!remote.transact(code, data, reply, 0)) {
                    throw RemoteException("Unsupported Bluetooth service transaction $code")
                }
                reply.readException()
            } finally {
                reply.recycle()
                data.recycle()
            }
        }

        private fun transactCallback(code: Int, callback: IBinder) {
            val data = Parcel.obtain()
            val reply = Parcel.obtain()
            try {
                data.writeInterfaceToken(SERVICE_DESCRIPTOR)
                data.writeStrongBinder(callback)
                if (!remote.transact(code, data, reply, 0)) {
                    throw RemoteException("Unsupported Bluetooth service transaction $code")
                }
                reply.readException()
            } finally {
                reply.recycle()
                data.recycle()
            }
        }

        private companion object {
            const val SERVICE_DESCRIPTOR =
                "com.hcn.bluetooth.service.IBluetoothMusicService"
            const val TRANSACTION_IS_A2DP_CONNECTED = 1
            const val TRANSACTION_IS_AVRCP_CONNECTED = 2
            const val TRANSACTION_IS_A2DP_PLAYING = 3
            const val TRANSACTION_REGISTER_CALLBACK = 10
            const val TRANSACTION_UNREGISTER_CALLBACK = 11
            const val TRANSACTION_SEND_AVRCP_COMMAND = 5
        }
    }

    private class MusicCallbackBinder(
        private val callbackSink: (String, String, Boolean?) -> Unit
    ) : Binder() {
        init {
            attachInterface(null, CALLBACK_DESCRIPTOR)
        }

        override fun onTransact(
            code: Int,
            data: Parcel,
            reply: Parcel?,
            flags: Int
        ): Boolean {
            if (code == INTERFACE_TRANSACTION) {
                reply?.writeString(CALLBACK_DESCRIPTOR)
                return true
            }

            data.enforceInterface(CALLBACK_DESCRIPTOR)
            when (code) {
                TRANSACTION_A2DP_STATE -> {
                    val state = data.readInt()
                    callbackSink(
                        "bluetooth_a2dp_state",
                        "A2DP connected=${state == PROFILE_CONNECTED} (raw=$state)",
                        state == PROFILE_CONNECTED
                    )
                }

                TRANSACTION_AVRCP_STATE -> {
                    val state = data.readInt()
                    callbackSink(
                        "bluetooth_avrcp_state",
                        "AVRCP connected=${state == PROFILE_CONNECTED} (raw=$state)",
                        state == PROFILE_CONNECTED
                    )
                }

                TRANSACTION_METADATA -> {
                    val title = data.readString().orEmpty()
                    val artist = data.readString().orEmpty()
                    val album = data.readString().orEmpty()
                    callbackSink(
                        "bluetooth_metadata",
                        "title=$title; artist=$artist; album=$album",
                        null
                    )
                }

                TRANSACTION_PLAY_STATUS -> {
                    val hasState = data.readInt() != 0
                    val state = if (hasState) data.readInt() else null
                    if (hasState) {
                        data.readLong()
                        data.readLong()
                        data.readFloat()
                        data.readLong()
                        data.readLong()
                    }
                    callbackSink(
                        "bluetooth_playback_state",
                        "Playback state=${state ?: "unknown"}; playing=${state == PLAYBACK_STATE_PLAYING}",
                        if (hasState) state == PLAYBACK_STATE_PLAYING else null
                    )
                    reply?.writeNoException()
                }

                else -> return super.onTransact(code, data, reply, flags)
            }
            return true
        }

        private companion object {
            const val CALLBACK_DESCRIPTOR = "com.hcn.bluetooth.service.IMusicCallback"
            const val TRANSACTION_A2DP_STATE = 1
            const val TRANSACTION_AVRCP_STATE = 2
            const val TRANSACTION_METADATA = 3
            const val TRANSACTION_PLAY_STATUS = 4
            const val PROFILE_CONNECTED = 2
            const val PLAYBACK_STATE_PLAYING = 3
        }
    }

    private companion object {
        const val LOG_TAG = "MT8163Tools"
        const val BLUETOOTH_SOURCE_PACKAGE = "com.autochips.bluetooth"
        const val SERVICE_PACKAGE = "com.hcn.bluetoothservice"
        const val SERVICE_CLASS = "com.hcn.bluetooth.service.BluetoothMusicService"
        const val SERVICE_ACTION = "com.hcn.bluetooth.service.BluetoothMusicService"
        const val AVRCP_PLAY = 0
    }
}
