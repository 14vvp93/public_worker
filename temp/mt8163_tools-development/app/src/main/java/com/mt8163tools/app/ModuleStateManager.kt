package com.mt8163tools.app

import android.content.Context
import android.content.SharedPreferences
import com.mt8163tools.core.module.Module

class ModuleStateManager(context: Context) {
    private val preferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun initialize(modules: List<Module>) {
        modules.forEach { module ->
            val key = keyFor(module)
            if (!preferences.contains(key)) {
                preferences.edit().putBoolean(key, module.defaultEnabled).apply()
            }
        }
    }

    fun isEnabled(module: Module): Boolean =
        preferences.getBoolean(keyFor(module), module.defaultEnabled)

    fun setEnabled(module: Module, enabled: Boolean) {
        preferences.edit().putBoolean(keyFor(module), enabled).apply()
    }

    private fun keyFor(module: Module) = "module_${module.id}"

    private companion object {
        const val PREFERENCES_NAME = "module_states"
    }
}
