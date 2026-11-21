package com.mt8163tools.modules.events

import com.mt8163tools.R
import com.mt8163tools.core.module.Module

class EventsModule : Module {
    override val id = "events"
    override val title = "Events"
    override val iconRes = R.drawable.ic_events
    override val order = 50
    override val defaultEnabled = true

    override fun createFragment() = EventsFragment()
}
