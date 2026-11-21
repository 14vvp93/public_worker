package com.mt8163tools.core.common

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.atomic.AtomicBoolean

data class ModuleEvent(
    val timestamp: Long,
    val moduleId: String,
    val type: String,
    val message: String
)

object EventsLog {
    const val MODULE_ID = "events"
    const val ACTION_EVENT_RECORDED = "com.mt8163tools.action.EVENT_RECORDED"

    private const val PREFERENCES_NAME = "events_log"
    private const val MODULE_PREFERENCES_NAME = "module_states"
    private const val MODULE_ENABLED_KEY = "module_events"
    private const val KEY_ENTRIES = "entries"
    private const val MAX_ENTRIES = 300
    private const val MAX_MODULE_ID_LENGTH = 64
    private const val MAX_TYPE_LENGTH = 64
    private const val MAX_MESSAGE_LENGTH = 500

    private val enabled = AtomicBoolean(false)
    private val lock = Any()

    private fun preferences(context: Context): SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun initialize(context: Context) {
        val modulePreferences = context.applicationContext.getSharedPreferences(
            MODULE_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        setEnabled(modulePreferences.getBoolean(MODULE_ENABLED_KEY, true))
    }

    fun setEnabled(value: Boolean) {
        synchronized(lock) {
            enabled.set(value)
        }
    }

    fun isEnabled(): Boolean = enabled.get()

    fun record(context: Context, moduleId: String, type: String, message: String) {
        if (!enabled.get()) return

        val safeModuleId = moduleId.trim().take(MAX_MODULE_ID_LENGTH)
        val safeType = type.trim().take(MAX_TYPE_LENGTH)
        val safeMessage = message.trim().take(MAX_MESSAGE_LENGTH)
        if (safeModuleId.isEmpty() || safeType.isEmpty() || safeMessage.isEmpty()) return

        val appContext = context.applicationContext
        synchronized(lock) {
            if (!enabled.get()) return
            val entries = readArray(preferences(appContext).getString(KEY_ENTRIES, null))
            val updated = JSONArray().put(
                JSONObject()
                    .put("timestamp", System.currentTimeMillis())
                    .put("module_id", safeModuleId)
                    .put("type", safeType)
                    .put("message", safeMessage)
            )
            for (index in 0 until minOf(entries.length(), MAX_ENTRIES - 1)) {
                entries.optJSONObject(index)?.let(updated::put)
            }
            preferences(appContext).edit().putString(KEY_ENTRIES, updated.toString()).apply()
            appContext.sendBroadcast(
                Intent(ACTION_EVENT_RECORDED).setPackage(appContext.packageName)
            )
        }
    }

    fun getEvents(context: Context): List<ModuleEvent> = synchronized(lock) {
        val entries = readArray(preferences(context).getString(KEY_ENTRIES, null))
        buildList(entries.length()) {
            for (index in 0 until entries.length()) {
                val item = entries.optJSONObject(index) ?: continue
                val timestamp = item.optLong("timestamp", 0L)
                val moduleId = item.optString("module_id")
                val type = item.optString("type")
                val message = item.optString("message")
                if (timestamp > 0L && moduleId.isNotBlank() && type.isNotBlank()) {
                    add(ModuleEvent(timestamp, moduleId, type, message))
                }
            }
        }
    }

    private fun readArray(value: String?): JSONArray {
        if (value.isNullOrBlank()) return JSONArray()
        return try {
            JSONArray(value)
        } catch (_: JSONException) {
            JSONArray()
        }
    }
}
