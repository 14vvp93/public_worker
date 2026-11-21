package com.mt8163tools.hooks

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.mt8163tools.core.common.AccessoryConfigEvent
import com.mt8163tools.core.common.AccessoryStateEvent
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XSharedPreferences
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

class AccessoryHook {

    private val configuration = AccessoryConfiguration()

    @Volatile
    private var accReceiverHookInstalled = false

    fun install(classLoader: ClassLoader) {
        configuration.loadInitial()
        installServiceConstructorHook(classLoader)
    }

    private fun installServiceConstructorHook(classLoader: ClassLoader) {
        try {
            XposedHelpers.findAndHookConstructor(
                MCU_SERVICE_CLASS,
                classLoader,
                Context::class.java,
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val context = param.args.firstOrNull() as? Context ?: return
                        configuration.attach(param.thisObject, context)
                        installAccReceiverHook(classLoader)
                    }
                }
            )
            XposedBridge.log("$LOG_TAG: hooked $MCU_SERVICE_CLASS constructor")
        } catch (throwable: Throwable) {
            XposedBridge.log(
                "$LOG_TAG: failed to hook $MCU_SERVICE_CLASS constructor: $throwable"
            )
        }
    }

    private fun installAccReceiverHook(classLoader: ClassLoader) {
        if (accReceiverHookInstalled) return

        synchronized(this) {
            if (accReceiverHookInstalled) return

            try {
                XposedHelpers.findAndHookMethod(
                    ACC_RECEIVER_CLASS,
                    classLoader,
                    ON_RECEIVE_METHOD,
                    Context::class.java,
                    Intent::class.java,
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val intent = param.args.getOrNull(1) as? Intent ?: return
                            if (intent.action != ACC_ACTION) return
                            configuration.onAccChanged(
                                intent.getBooleanExtra(ACC_STATUS_EXTRA, false)
                            )
                        }
                    }
                )
                accReceiverHookInstalled = true
                XposedBridge.log(
                    "$LOG_TAG: hooked $ACC_RECEIVER_CLASS#$ON_RECEIVE_METHOD"
                )
            } catch (throwable: Throwable) {
                XposedBridge.log(
                    "$LOG_TAG: failed to hook $ACC_RECEIVER_CLASS#$ON_RECEIVE_METHOD: $throwable"
                )
            }
        }
    }

    private class AccessoryConfiguration {
        private val playbackController = AccessoryPlaybackController { type, message ->
            publishPlaybackEvent(type, message)
        }
        private val bluetoothMusicClient = VendorBluetoothMusicClient { type, message ->
            publishPlaybackEvent(type, message)
        }
        private val hookPreferences =
            XSharedPreferences(APP_PACKAGE, HOOK_PREFERENCES)

        @Volatile
        private var enabled = false

        @Volatile
        private var accState = false

        @Volatile
        private var hasAccState = false

        @Volatile
        private var service: Any? = null

        @Volatile
        private var appContext: Context? = null

        @Volatile
        private var receiver: BroadcastReceiver? = null

        fun loadInitial() {
            try {
                hookPreferences.reload()
                enabled = hookPreferences.getBoolean(KEY_MODULE_ENABLED, false)
            } catch (throwable: Throwable) {
                XposedBridge.log(
                    "$LOG_TAG: unable to load initial Accessory settings: $throwable"
                )
            }
        }

        fun attach(service: Any, context: Context) {
            this.service = service
            appContext = context.applicationContext
            playbackController.attach(context)
            playbackController.setEnabled(enabled)
            bluetoothMusicClient.attach(context)
            bluetoothMusicClient.setEnabled(enabled)
            if (enabled) syncAccState(service)

            if (receiver != null) return

            synchronized(this) {
                if (receiver != null) return

                val configurationReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        if (intent.action != AccessoryConfigEvent.ACTION) return

                        val wasEnabled = enabled
                        val nextEnabled = intent.getBooleanExtra(
                            AccessoryConfigEvent.EXTRA_MODULE_ENABLED,
                            wasEnabled
                        )
                        enabled = nextEnabled
                        playbackController.setEnabled(nextEnabled)
                        bluetoothMusicClient.setEnabled(nextEnabled)

                        if (!wasEnabled && nextEnabled) {
                            this@AccessoryConfiguration.service?.let(::syncAccState)
                        }

                        XposedBridge.log(
                            "$LOG_TAG: Accessory module enabled=$enabled"
                        )
                    }
                }

                try {
                    context.registerReceiver(
                        configurationReceiver,
                        IntentFilter(AccessoryConfigEvent.ACTION)
                    )
                    receiver = configurationReceiver
                    XposedBridge.log(
                        "$LOG_TAG: registered system_server Accessory receiver"
                    )
                } catch (throwable: Throwable) {
                    XposedBridge.log(
                        "$LOG_TAG: failed to register Accessory config receiver: $throwable"
                    )
                }
            }
        }

        fun onAccChanged(nextState: Boolean) {
            if (!enabled || (hasAccState && accState == nextState)) return

            accState = nextState
            hasAccState = true
            applyAccState(nextState, synchronized = false)
            publishState(nextState, false)
            XposedBridge.log("$LOG_TAG: ACC state=$accState")
        }

        private fun syncAccState(service: Any) {
            try {
                val currentState = XposedHelpers.callMethod(
                    service,
                    MCU_ACC_STATUS_METHOD
                ) as? Boolean ?: return

                accState = currentState
                hasAccState = true
                applyAccState(currentState, synchronized = true)
                publishState(currentState, true)
                XposedBridge.log(
                    "$LOG_TAG: synchronized ACC state=$accState"
                )
            } catch (throwable: Throwable) {
                XposedBridge.log(
                    "$LOG_TAG: failed to read current ACC state: $throwable"
                )
            }
        }

        private fun applyAccState(state: Boolean, synchronized: Boolean) {
            if (!state) {
                val sourcePackage = readSourcePackage()
                val isBluetoothSource = sourcePackage == BLUETOOTH_SOURCE_PACKAGE

                publishPlaybackEvent(
                    "source_snapshot",
                    "ACC OFF SourceInfo package=${sourcePackage ?: "unknown"}; bluetoothSource=$isBluetoothSource"
                )
                bluetoothMusicClient.onAccStateChanged(
                    false,
                    mediaSessionOwnsRestore = false,
                    sourcePackage = sourcePackage
                )
                playbackController.onAccStateChanged(false, synchronized)
                return
            }

            val mediaSessionOwnsRestore = playbackController.hasMediaSessionForCurrentAccCycle()
            playbackController.onAccStateChanged(true, synchronized)
            bluetoothMusicClient.onAccStateChanged(true, mediaSessionOwnsRestore)
        }

        private fun readSourcePackage(): String? {
            return try {
                val sourceInfoClass = XposedHelpers.findClass(SOURCE_INFO_CLASS, null)
                val sourceInfo = XposedHelpers.callStaticMethod(
                    sourceInfoClass,
                    SOURCE_INFO_GET_INSTANCE
                ) ?: return null

                (XposedHelpers.callMethod(
                    sourceInfo,
                    SOURCE_INFO_GET_SOURCE_PACKAGE
                ) as? String)?.trim()?.takeIf(String::isNotEmpty)
            } catch (throwable: Throwable) {
                XposedBridge.log(
                    "$LOG_TAG: failed to read SourceInfo source package: $throwable"
                )
                null
            }
        }

        private fun publishState(state: Boolean, synchronized: Boolean) {
            val context = appContext ?: return
            try {
                AccessoryStateEvent.send(context, state, synchronized)
            } catch (throwable: Throwable) {
                XposedBridge.log(
                    "$LOG_TAG: failed to publish ACC state: $throwable"
                )
            }
        }

        private fun publishPlaybackEvent(type: String, message: String) {
            val context = appContext ?: return
            try {
                AccessoryStateEvent.sendPlaybackEvent(context, type, message)
            } catch (throwable: Throwable) {
                XposedBridge.log(
                    "$LOG_TAG: failed to publish playback event: $throwable"
                )
            }
        }
    }

    private companion object {
        const val APP_PACKAGE = "com.mt8163tools"
        const val HOOK_PREFERENCES = "accessory_hook_config"
        const val KEY_MODULE_ENABLED = "module_enabled"

        const val MCU_SERVICE_CLASS = "android.carsource.McuService"
        const val MCU_ACC_STATUS_METHOD = "AccStatus"
        const val SOURCE_INFO_CLASS = "android.sourceservice.SourceInfo"
        const val SOURCE_INFO_GET_INSTANCE = "getInstance"
        const val SOURCE_INFO_GET_SOURCE_PACKAGE = "getSourcePackage"
        const val BLUETOOTH_SOURCE_PACKAGE = "com.autochips.bluetooth"
        const val ACC_RECEIVER_CLASS = "android.carsource.McuService" + "$" + "1"
        const val ON_RECEIVE_METHOD = "onReceive"
        const val ACC_ACTION = "android.mcu.device.action.ACC"
        const val ACC_STATUS_EXTRA = "AccStatus"

        const val LOG_TAG = "MT8163Tools"
    }
}
