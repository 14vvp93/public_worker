package com.mt8163tools.modules.voltage

import android.content.Context
import android.content.SharedPreferences
import com.mt8163tools.core.common.EventsLog
import com.mt8163tools.core.common.VoltageConfigEvent

class VoltageSettings(context: Context) {
    private val appContext = context.applicationContext
    private val preferences: SharedPreferences =
        appContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getVoltageOffset(): Float {
        val voltageOffset = preferences.getFloat(
            KEY_VOLTAGE_OFFSET,
            DEFAULT_VOLTAGE_OFFSET
        )
        return voltageOffset.takeIf { it.isFinite() }
            ?.coerceIn(MIN_VOLTAGE_OFFSET, MAX_VOLTAGE_OFFSET)
            ?: DEFAULT_VOLTAGE_OFFSET
    }

    fun setVoltageOffset(value: Float) {
        val voltageOffset = value.takeIf { it.isFinite() }
            ?.coerceIn(MIN_VOLTAGE_OFFSET, MAX_VOLTAGE_OFFSET)
            ?: DEFAULT_VOLTAGE_OFFSET
        val storedOffset = preferences.getFloat(KEY_VOLTAGE_OFFSET, Float.NaN)
        if (storedOffset == voltageOffset) return

        preferences.edit()
            .putFloat(KEY_VOLTAGE_OFFSET, voltageOffset)
            .apply()
        EventsLog.record(
            appContext,
            MODULE_ID,
            "voltage_offset_changed",
            "Voltage offset changed to $voltageOffset V"
        )
        VoltageConfigEvent.sendOffsetChanged(appContext, voltageOffset)
    }

    companion object {
        const val MIN_VOLTAGE_OFFSET = -2.0f
        const val MAX_VOLTAGE_OFFSET = 2.0f
        const val DEFAULT_VOLTAGE_OFFSET = 0.0f

        private const val MODULE_ID = "voltage"
        private const val PREFERENCES_NAME = "voltage_settings"
        private const val KEY_VOLTAGE_OFFSET = "voltage_offset"
    }
}
