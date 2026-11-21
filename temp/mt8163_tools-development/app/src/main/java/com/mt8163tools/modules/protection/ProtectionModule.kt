package com.mt8163tools.modules.protection

import com.mt8163tools.R
import com.mt8163tools.core.module.Module

class ProtectionModule : Module {
    override val id = "protection"
    override val title = "Protection"
    override val iconRes = R.drawable.ic_protection
    override val order = 20
    override val defaultEnabled = false

    override fun createFragment() = ProtectionFragment()
}
