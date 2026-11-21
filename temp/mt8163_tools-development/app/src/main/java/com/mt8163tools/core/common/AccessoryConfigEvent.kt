package com.mt8163tools.core.common

import android.content.Context
import android.content.Intent

object AccessoryConfigEvent {
    const val ACTION = "com.mt8163tools.action.ACCESSORY_CONFIG_CHANGED"
    const val EXTRA_MODULE_ENABLED = "module_enabled"
    const val MODULE_ID = "accessory"

    private const val TARGET_PACKAGE = "android"
    private const val PREFERENCES_NAME = "accessory_hook_config"
    private const val KEY_MODULE_ENABLED = "module_enabled"

    @Suppress("DEPRECATION")
    fun sendConfigurationChanged(context: Context, enabled: Boolean) {
        val appContext = context.applicationContext
        try {
            appContext.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_WORLD_READABLE
            ).edit().putBoolean(KEY_MODULE_ENABLED, enabled).apply()
        } catch (_: SecurityException) {
            appContext.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
            ).edit().putBoolean(KEY_MODULE_ENABLED, enabled).apply()
        }

        appContext.sendBroadcast(
            Intent(ACTION)
                .setPackage(TARGET_PACKAGE)
                .putExtra(EXTRA_MODULE_ENABLED, enabled)
        )
    }
}
