package com.mt8163tools.hooks

import android.content.Context
import android.media.session.MediaController
import android.media.session.MediaSession
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.os.Handler
import android.os.Looper
import de.robv.android.xposed.XposedBridge

internal class AccessoryPlaybackController(
    private val eventSink: (String, String) -> Unit
) {

    private var manager: MediaSessionManager? = null
    private var handler: Handler? = null
    private var enabled = false
    private var accState: Boolean? = null
    private var savedToken: MediaSession.Token? = null
    private var savedPackage: String? = null
    private var mediaSessionWasPlaying = false
    private var observedController: MediaController? = null
    private var playbackCallback: MediaController.Callback? = null
    private var sessionsListener: MediaSessionManager.OnActiveSessionsChangedListener? = null
    private var pauseConfirmationPending = false
    private var resumeConfirmationPending = false
    private var restorePendingLogged = false

    fun attach(context: Context) {
        if (manager != null) return

        manager = context.getSystemService(Context.MEDIA_SESSION_SERVICE)
            as? MediaSessionManager
        handler = Handler(Looper.getMainLooper())

        if (enabled) registerSessionsListener()
    }

    fun setEnabled(value: Boolean) {
        if (enabled == value) return

        enabled = value
        if (value) {
            registerSessionsListener()
        } else {
            clearRestoration()
            mediaSessionWasPlaying = false
            unregisterSessionsListener()
            accState = null
        }
    }

    fun onAccStateChanged(state: Boolean, synchronized: Boolean = false) {
        if (!enabled) return

        val previousState = accState
        accState = state

        if (!state && (previousState == true || (synchronized && previousState == null))) {
            pauseCurrentSession()
            return
        }

        if (state && previousState == false) {
            restorePreviousSession()
        }
    }

    fun hasMediaSessionForCurrentAccCycle(): Boolean =
        mediaSessionWasPlaying || savedToken != null

    private fun pauseCurrentSession() {
        if (!enabled || accState != false) return
        if (savedToken != null) {
            mediaSessionWasPlaying = true
            return
        }

        mediaSessionWasPlaying = false
        val controllers = getActiveSessions() ?: return
        val bluetoothSessions = controllers.filter {
            it.packageName == BLUETOOTH_SERVICE_PACKAGE
        }
        if (bluetoothSessions.isNotEmpty()) {
            emit(
                "playback_session_ignored",
                "Ignored ${bluetoothSessions.size} Bluetooth MediaSession(s); Bluetooth state is managed through BluetoothMusicService"
            )
        }
        val controller = controllers.firstOrNull {
            it.packageName != BLUETOOTH_SERVICE_PACKAGE &&
                it.playbackState?.state == PlaybackState.STATE_PLAYING
        } ?: return
        mediaSessionWasPlaying = true
        val playbackState = controller.playbackState ?: return
        if ((playbackState.actions and PlaybackState.ACTION_PAUSE) == 0L &&
            (playbackState.actions and PlaybackState.ACTION_PLAY_PAUSE) == 0L
        ) {
            return
        }

        savedToken = controller.sessionToken
        savedPackage = controller.packageName
        pauseConfirmationPending = true
        observeController(controller)

        try {
            controller.transportControls.pause()
            emit(
                "playback_pause_requested",
                "Pause requested for ${controller.packageName}"
            )
            log("pause requested package=${controller.packageName}")
        } catch (throwable: Throwable) {
            emit("playback_error", "Pause failed for ${controller.packageName}: $throwable")
            clearRestoration()
            log("failed to pause active session: $throwable")
        }
    }

    private fun restorePreviousSession() {
        restorePreviousSession(getActiveSessions())
    }

    private fun registerSessionsListener() {
        if (!enabled || sessionsListener != null) return

        val mediaSessionManager = manager ?: return
        val callbackHandler = handler ?: return
        val listener = MediaSessionManager.OnActiveSessionsChangedListener { controllers ->
            if (enabled && accState == true) {
                restorePreviousSession(controllers)
            }
        }

        try {
            mediaSessionManager.addOnActiveSessionsChangedListener(
                listener,
                null,
                callbackHandler
            )
            sessionsListener = listener
            log("registered active MediaSession listener")
        } catch (throwable: Throwable) {
            log("failed to register MediaSession listener: $throwable")
        }
    }

    private fun unregisterSessionsListener() {
        val listener = sessionsListener ?: return
        try {
            manager?.removeOnActiveSessionsChangedListener(listener)
        } catch (throwable: Throwable) {
            log("failed to unregister MediaSession listener: $throwable")
        } finally {
            sessionsListener = null
        }
    }

    private fun restorePreviousSession(controllers: List<MediaController>?) {
        if (!enabled || accState != true) return
        val token = savedToken ?: return
        val controller = controllers?.firstOrNull {
            it.sessionToken == token && it.packageName == savedPackage
        }

        if (controller == null) {
            logRestorePending()
            return
        }

        observeController(controller)
        val state = controller.playbackState

        if (pauseConfirmationPending) {
            logRestorePending()
            return
        }

        if (state?.state == PlaybackState.STATE_PLAYING) {
            if (resumeConfirmationPending) {
                emit(
                    "playback_resumed",
                    "Playback resumed for ${controller.packageName}"
                )
            }
            clearRestoration()
            return
        }
        if (state?.state == PlaybackState.STATE_STOPPED ||
            state?.state == PlaybackState.STATE_ERROR
        ) {
            emit(
                "playback_restore_cancelled",
                "Restore cancelled for ${controller.packageName}: session stopped or failed"
            )
            clearRestoration()
            return
        }

        if (resumeConfirmationPending) return

        val actions = state?.actions ?: 0L
        if ((actions and PlaybackState.ACTION_PLAY) == 0L &&
            (actions and PlaybackState.ACTION_PLAY_PAUSE) == 0L
        ) {
            logRestorePending()
            return
        }

        try {
            resumeConfirmationPending = true
            controller.transportControls.play()
            emit(
                "playback_restore_requested",
                "Play requested for ${controller.packageName}"
            )
            log("play requested package=${controller.packageName}")
        } catch (throwable: Throwable) {
            emit(
                "playback_error",
                "Restore failed for ${controller.packageName}: $throwable"
            )
            clearRestoration()
            log("failed to restore session: $throwable")
        }
    }

    private fun observeController(controller: MediaController) {
        if (observedController?.sessionToken == controller.sessionToken) return

        unregisterPlaybackCallback()
        val callbackHandler = handler ?: return
        val callback = object : MediaController.Callback() {
            override fun onPlaybackStateChanged(state: PlaybackState?) {
                if (!enabled || savedToken == null) return

                if (state?.state == PlaybackState.STATE_PAUSED && pauseConfirmationPending) {
                    pauseConfirmationPending = false
                    emit(
                        "playback_paused",
                        "Playback paused for ${savedPackage ?: "unknown player"}"
                    )
                    if (accState == true) restorePreviousSession()
                    return
                }

                if (state?.state == PlaybackState.STATE_STOPPED ||
                    state?.state == PlaybackState.STATE_ERROR
                ) {
                    emit(
                        "playback_restore_cancelled",
                        "Restore cancelled for ${savedPackage ?: "unknown player"}: session stopped or failed"
                    )
                    clearRestoration()
                } else if (state?.state == PlaybackState.STATE_PLAYING &&
                    resumeConfirmationPending
                ) {
                    emit(
                        "playback_resumed",
                        "Playback resumed for ${savedPackage ?: "unknown player"}"
                    )
                    clearRestoration()
                } else if (accState == true) {
                    restorePreviousSession()
                }
            }

            override fun onSessionDestroyed() {
                if (savedToken != null) {
                    emit(
                        "playback_restore_cancelled",
                        "Restore cancelled: media session destroyed"
                    )
                }
                clearRestoration()
            }
        }

        try {
            controller.registerCallback(callback, callbackHandler)
            observedController = controller
            playbackCallback = callback
        } catch (throwable: Throwable) {
            log("failed to observe MediaSession: $throwable")
        }
    }

    private fun logRestorePending() {
        if (restorePendingLogged) return
        restorePendingLogged = true
        emit(
            "playback_restore_pending",
            "Waiting for the original media session to become available"
        )
    }

    private fun unregisterPlaybackCallback() {
        val controller = observedController
        val callback = playbackCallback
        if (controller != null && callback != null) {
            try {
                controller.unregisterCallback(callback)
            } catch (throwable: Throwable) {
                log("failed to unregister MediaSession callback: $throwable")
            }
        }
        observedController = null
        playbackCallback = null
    }

    private fun clearRestoration() {
        savedToken = null
        savedPackage = null
        pauseConfirmationPending = false
        resumeConfirmationPending = false
        restorePendingLogged = false
        unregisterPlaybackCallback()
    }

    private fun getActiveSessions(): List<MediaController>? {
        return try {
            manager?.getActiveSessions(null)
        } catch (throwable: Throwable) {
            log("failed to query active MediaSessions: $throwable")
            null
        }
    }

    private fun emit(type: String, message: String) {
        try {
            eventSink(type, message)
        } catch (throwable: Throwable) {
            log("failed to publish event $type: $throwable")
        }
    }

    private fun log(message: String) {
        XposedBridge.log("$LOG_TAG: $message")
    }

    private companion object {
        const val LOG_TAG = "MT8163Tools"
        const val BLUETOOTH_SERVICE_PACKAGE = "com.hcn.bluetoothservice"
    }
}
