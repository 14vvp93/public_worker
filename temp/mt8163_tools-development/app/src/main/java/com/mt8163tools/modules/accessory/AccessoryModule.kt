package com.mt8163tools.modules.accessory

import com.mt8163tools.R
import com.mt8163tools.core.module.Module

class AccessoryModule : Module {
    override val id = "accessory"
    override val title = "Accessory"
    override val iconRes = R.drawable.ic_accessory
    override val order = 10
    override val defaultEnabled = false

    override fun createFragment() = AccessoryFragment()
}
