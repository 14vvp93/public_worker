package com.mt8163tools.core.common

import android.content.Context
import android.content.Intent

object VoltageConfigEvent {
    const val ACTION = "com.mt8163tools.action.VOLTAGE_CONFIG_CHANGED"
    const val EXTRA_VOLTAGE_OFFSET = "voltage_offset"
    const val EXTRA_MODULE_ENABLED = "module_enabled"
    const val MODULE_ID = "voltage"

    private const val TARGET_PACKAGE = "android"
    private const val PREFERENCES_NAME = "voltage_hook_config"
    private const val KEY_VOLTAGE_OFFSET = "voltage_offset"
    private const val KEY_MODULE_ENABLED = "module_enabled"

    fun sendOffsetChanged(context: Context, voltageOffset: Float) {
        val appContext = context.applicationContext
        val preferences = getHookPreferences(appContext)
        val enabled = preferences.getBoolean(KEY_MODULE_ENABLED, false)
        publish(appContext, enabled, voltageOffset)
    }

    fun sendConfigurationChanged(
        context: Context,
        enabled: Boolean,
        voltageOffset: Float
    ) {
        publish(context.applicationContext, enabled, voltageOffset)
    }

    @Suppress("DEPRECATION")
    private fun getHookPreferences(context: Context) = try {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_WORLD_READABLE)
    } catch (_: SecurityException) {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private fun publish(context: Context, enabled: Boolean, voltageOffset: Float) {
        val safeVoltageOffset = voltageOffset.takeIf { it.isFinite() }
            ?.coerceIn(-2.0f, 2.0f)
            ?: 0.0f
        getHookPreferences(context).edit()
            .putBoolean(KEY_MODULE_ENABLED, enabled)
            .putFloat(KEY_VOLTAGE_OFFSET, safeVoltageOffset)
            .apply()

        context.sendBroadcast(
            Intent(ACTION)
                .setPackage(TARGET_PACKAGE)
                .putExtra(EXTRA_MODULE_ENABLED, enabled)
                .putExtra(EXTRA_VOLTAGE_OFFSET, safeVoltageOffset)
        )
    }
}
