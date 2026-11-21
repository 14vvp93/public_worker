package com.mt8163tools.app

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mt8163tools.R
import com.mt8163tools.core.common.AccessoryConfigEvent
import com.mt8163tools.core.common.EventsLog
import com.mt8163tools.core.common.VoltageConfigEvent
import com.mt8163tools.core.module.Module
import com.mt8163tools.databinding.ActivityMainBinding
import com.mt8163tools.modules.voltage.VoltageSettings

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var moduleStateManager: ModuleStateManager
    private var selectedModule: Module? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moduleStateManager = ModuleStateManager(applicationContext)
        moduleStateManager.initialize(ModuleRegistry.modules)
        initializeEventsCollection()
        publishVoltageConfiguration()
        publishAccessoryConfiguration()

        renderModules()
        ModuleRegistry.modules.firstOrNull()?.let(::openModule)
    }

    private fun initializeEventsCollection() {
        val eventsModule = ModuleRegistry.modules.firstOrNull {
            it.id == EventsLog.MODULE_ID
        }
        EventsLog.setEnabled(eventsModule?.let(moduleStateManager::isEnabled) ?: false)
    }

    private fun publishAccessoryConfiguration() {
        val accessoryModule = ModuleRegistry.modules.firstOrNull {
            it.id == AccessoryConfigEvent.MODULE_ID
        }
        val enabled = accessoryModule?.let(moduleStateManager::isEnabled) ?: false
        AccessoryConfigEvent.sendConfigurationChanged(applicationContext, enabled)
    }

    private fun publishVoltageConfiguration() {
        val voltageModule = ModuleRegistry.modules.firstOrNull {
            it.id == VoltageConfigEvent.MODULE_ID
        }
        val enabled = voltageModule?.let(moduleStateManager::isEnabled) ?: false
        val voltageOffset = VoltageSettings(applicationContext).getVoltageOffset()
        VoltageConfigEvent.sendConfigurationChanged(
            applicationContext,
            enabled,
            voltageOffset
        )
    }

    private fun renderModules() {
        binding.moduleList.removeAllViews()
        ModuleRegistry.modules.forEach { module ->
            val item = LayoutInflater.from(this)
                .inflate(R.layout.item_module_navigation, binding.moduleList, false) as TextView
            item.text = module.title
            item.setCompoundDrawablesRelativeWithIntrinsicBounds(module.iconRes, 0, 0, 0)
            item.isSelected = module === selectedModule
            item.setOnClickListener { openModule(module) }
            binding.moduleList.addView(item)
        }
    }

    private fun openModule(module: Module) {
        if (selectedModule?.id == module.id) return

        selectedModule = module
        binding.moduleTitle.text = module.title

        val enabled = moduleStateManager.isEnabled(module)
        binding.moduleEnabledSwitch.setOnCheckedChangeListener(null)
        binding.moduleEnabledSwitch.isChecked = enabled
        binding.moduleEnabledSwitch.contentDescription =
            getString(if (enabled) R.string.module_enabled else R.string.module_disabled)
        binding.moduleEnabledSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (module.id == EventsLog.MODULE_ID && !isChecked) {
                EventsLog.setEnabled(false)
            }
            moduleStateManager.setEnabled(module, isChecked)
            if (module.id == EventsLog.MODULE_ID) {
                if (isChecked) {
                    EventsLog.setEnabled(true)
                    EventsLog.record(
                        applicationContext,
                        EventsLog.MODULE_ID,
                        "collection_enabled",
                        "Event collection enabled"
                    )
                }
            } else {
                EventsLog.record(
                    applicationContext,
                    module.id,
                    if (isChecked) "module_enabled" else "module_disabled",
                    if (isChecked) "Module enabled" else "Module disabled"
                )
            }
            if (module.id == VoltageConfigEvent.MODULE_ID) {
                publishVoltageConfiguration()
            }
            if (module.id == AccessoryConfigEvent.MODULE_ID) {
                publishAccessoryConfiguration()
            }
            binding.moduleContainer.setContentEnabled(isChecked)
            binding.moduleEnabledSwitch.contentDescription =
                getString(if (isChecked) R.string.module_enabled else R.string.module_disabled)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.module_container, module.createFragment(), module.id)
            .commitNow()
        binding.moduleContainer.setContentEnabled(enabled)

        for (index in 0 until binding.moduleList.childCount) {
            binding.moduleList.getChildAt(index).isSelected =
                ModuleRegistry.modules.getOrNull(index)?.id == module.id
        }
    }
}
