package com.mt8163tools.modules.autorun

import com.mt8163tools.R
import com.mt8163tools.core.module.Module

class AutorunModule : Module {
    override val id = "autorun"
    override val title = "Autorun"
    override val iconRes = R.drawable.ic_autorun
    override val order = 40
    override val defaultEnabled = false

    override fun createFragment() = AutorunFragment()
}
