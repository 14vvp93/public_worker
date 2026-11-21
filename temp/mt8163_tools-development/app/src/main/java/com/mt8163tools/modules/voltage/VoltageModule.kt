package com.mt8163tools.modules.voltage

import com.mt8163tools.R
import com.mt8163tools.core.module.Module

class VoltageModule : Module {
    override val id = "voltage"
    override val title = "Voltage"
    override val iconRes = R.drawable.ic_voltage
    override val order = 30
    override val defaultEnabled = false

    override fun createFragment() = VoltageFragment()
}
