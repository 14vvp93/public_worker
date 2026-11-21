package com.mt8163tools.core.common

import android.content.Context
import android.content.Intent

object AccessoryStateEvent {
    const val ACTION = "com.mt8163tools.action.ACCESSORY_STATE_CHANGED"
    const val EXTRA_ACC_STATE = "acc_state"
    const val EXTRA_SYNCHRONIZED = "synchronized"
    const val EXTRA_EVENT_TYPE = "event_type"
    const val EXTRA_EVENT_MESSAGE = "event_message"

    fun send(context: Context, state: Boolean, synchronized: Boolean) {
        context.applicationContext.sendBroadcast(
            Intent(ACTION)
                .setPackage(APP_PACKAGE)
                .putExtra(EXTRA_ACC_STATE, state)
                .putExtra(EXTRA_SYNCHRONIZED, synchronized)
        )
    }

    fun sendPlaybackEvent(context: Context, type: String, message: String) {
        if (type.isBlank() || message.isBlank()) return

        context.applicationContext.sendBroadcast(
            Intent(ACTION)
                .setPackage(APP_PACKAGE)
                .putExtra(EXTRA_EVENT_TYPE, type)
                .putExtra(EXTRA_EVENT_MESSAGE, message)
        )
    }

    private const val APP_PACKAGE = "com.mt8163tools"
}
