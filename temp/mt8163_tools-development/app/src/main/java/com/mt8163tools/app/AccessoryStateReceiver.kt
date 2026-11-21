package com.mt8163tools.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mt8163tools.core.common.AccessoryConfigEvent
import com.mt8163tools.core.common.AccessoryStateEvent
import com.mt8163tools.core.common.EventsLog

class AccessoryStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != AccessoryStateEvent.ACTION) return

        val appContext = context.applicationContext
        EventsLog.initialize(appContext)

        if (intent.hasExtra(AccessoryStateEvent.EXTRA_ACC_STATE)) {
            val state = intent.getBooleanExtra(AccessoryStateEvent.EXTRA_ACC_STATE, false)
            val synchronized = intent.getBooleanExtra(
                AccessoryStateEvent.EXTRA_SYNCHRONIZED,
                false
            )
            EventsLog.record(
                appContext,
                AccessoryConfigEvent.MODULE_ID,
                if (synchronized) "acc_state_sync" else "acc_state_changed",
                "ACC " + if (state) "ON" else "OFF"
            )
            return
        }

        val type = intent.getStringExtra(AccessoryStateEvent.EXTRA_EVENT_TYPE) ?: return
        val message = intent.getStringExtra(AccessoryStateEvent.EXTRA_EVENT_MESSAGE) ?: return
        EventsLog.record(appContext, AccessoryConfigEvent.MODULE_ID, type, message)
    }
}
