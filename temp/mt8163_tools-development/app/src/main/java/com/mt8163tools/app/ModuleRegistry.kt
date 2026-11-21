package com.mt8163tools.app

import com.mt8163tools.core.module.Module
import com.mt8163tools.modules.accessory.AccessoryModule
import com.mt8163tools.modules.autorun.AutorunModule
import com.mt8163tools.modules.events.EventsModule
import com.mt8163tools.modules.protection.ProtectionModule
import com.mt8163tools.modules.voltage.VoltageModule

object ModuleRegistry {
    val modules: List<Module> by lazy {
        listOf(
            AccessoryModule(),
            ProtectionModule(),
            VoltageModule(),
            AutorunModule(),
            EventsModule()
        ).sortedBy(Module::order)
    }
}
