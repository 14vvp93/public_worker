package com.mt8163tools.hooks

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.mt8163tools.core.common.VoltageConfigEvent
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XSharedPreferences
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

class VoltageHook {

    private val configuration = VoltageConfiguration()

    fun install(classLoader: ClassLoader) {
        configuration.loadInitial()
        installServiceConstructorHook(classLoader)
        installBatteryPacketHook(classLoader)
    }

    private fun installServiceConstructorHook(classLoader: ClassLoader) {
        try {
            XposedHelpers.findAndHookConstructor(
                MCU_SERVICE_CLASS,
                classLoader,
                Context::class.java,
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val context = param.args.firstOrNull() as? Context ?: return
                        configuration.attach(param.thisObject, context)
                    }
                }
            )
            XposedBridge.log("$LOG_TAG: hooked $MCU_SERVICE_CLASS constructor")
        } catch (throwable: Throwable) {
            XposedBridge.log(
                "$LOG_TAG: failed to hook $MCU_SERVICE_CLASS constructor: $throwable"
            )
        }
    }

    private fun installBatteryPacketHook(classLoader: ClassLoader) {
        try {
            val packetClass = XposedHelpers.findClass(MCU_PACKET_CLASS, classLoader)
            XposedHelpers.findAndHookMethod(
                MCU_SERVICE_CLASS,
                classLoader,
                ON_BATTERY_METHOD,
                packetClass,
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val packet = param.args.firstOrNull() ?: return
                        configuration.onBatteryPacket(param.thisObject, packet)
                    }
                }
            )
            XposedBridge.log(
                "$LOG_TAG: hooked $MCU_SERVICE_CLASS#$ON_BATTERY_METHOD"
            )
        } catch (throwable: Throwable) {
            XposedBridge.log(
                "$LOG_TAG: failed to hook $MCU_SERVICE_CLASS#$ON_BATTERY_METHOD: $throwable"
            )
        }
    }

    private class VoltageConfiguration {
        private val lock = Any()
        private val hookPreferences =
            XSharedPreferences(APP_PACKAGE, HOOK_PREFERENCES)

        private var enabled = false
        private var voltageOffset = DEFAULT_VOLTAGE_OFFSET
        private var rawVoltage: Float? = null
        private var service: Any? = null
        private var receiver: BroadcastReceiver? = null

        fun loadInitial() {
            try {
                hookPreferences.reload()
                update(
                    hookPreferences.getBoolean(KEY_MODULE_ENABLED, false),
                    hookPreferences.getFloat(
                        KEY_VOLTAGE_OFFSET,
                        DEFAULT_VOLTAGE_OFFSET
                    )
                )
            } catch (throwable: Throwable) {
                XposedBridge.log(
                    "$LOG_TAG: unable to load initial settings: $throwable"
                )
            }
        }

        fun attach(service: Any, context: Context) {
            synchronized(lock) {
                this.service = service
                applyLocked()
                if (receiver != null) return

                val configurationReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        if (intent.action != VoltageConfigEvent.ACTION) return

                        val current = snapshot()
                        val nextEnabled = intent.getBooleanExtra(
                            VoltageConfigEvent.EXTRA_MODULE_ENABLED,
                            current.enabled
                        )
                        val nextOffset = intent.getFloatExtra(
                            VoltageConfigEvent.EXTRA_VOLTAGE_OFFSET,
                            current.voltageOffset
                        )
                        update(nextEnabled, nextOffset)
                    }
                }

                try {
                    context.registerReceiver(
                        configurationReceiver,
                        IntentFilter(VoltageConfigEvent.ACTION)
                    )
                    receiver = configurationReceiver
                    XposedBridge.log(
                        "$LOG_TAG: registered system_server voltage receiver"
                    )
                } catch (throwable: Throwable) {
                    XposedBridge.log(
                        "$LOG_TAG: failed to register voltage receiver: $throwable"
                    )
                }
            }
        }

        fun onBatteryPacket(service: Any, packet: Any) {
            try {
                val subCommand =
                    XposedHelpers.callMethod(packet, PACKET_SUB_COMMAND_METHOD) as? Byte
                        ?: return
                if (subCommand.toInt() != BATTERY_SUB_COMMAND) return

                val parameterCount =
                    XposedHelpers.callMethod(packet, PACKET_PARAMETER_COUNT_METHOD) as? Int
                        ?: return
                if (parameterCount < 1) return

                val rawByte =
                    XposedHelpers.callMethod(
                        packet,
                        PACKET_PARAMETER_METHOD,
                        BATTERY_VOLTAGE_PARAMETER
                    ) as? Byte ?: return

                val voltage = (rawByte.toInt() and 0xFF) / VOLTAGE_SCALE
                if (!voltage.isFinite()) return

                synchronized(lock) {
                    this.service = service
                    rawVoltage = voltage
                    applyLocked()
                }
            } catch (throwable: Throwable) {
                XposedBridge.log(
                    "$LOG_TAG: failed to process battery packet: $throwable"
                )
            }
        }

        private fun update(enabled: Boolean, voltageOffset: Float) {
            if (
                !voltageOffset.isFinite() ||
                voltageOffset !in MIN_VOLTAGE_OFFSET..MAX_VOLTAGE_OFFSET
            ) {
                XposedBridge.log(
                    "$LOG_TAG: rejected invalid voltage offset: $voltageOffset"
                )
                return
            }

            synchronized(lock) {
                this.enabled = enabled
                this.voltageOffset = voltageOffset
                applyLocked()
            }
        }

        private fun snapshot(): Configuration = synchronized(lock) {
            Configuration(enabled, voltageOffset)
        }

        private fun applyLocked() {
            val target = service ?: return
            val raw = rawVoltage ?: return
            val corrected = if (enabled) raw + voltageOffset else raw
            if (!corrected.isFinite()) return

            try {
                XposedHelpers.setFloatField(
                    target,
                    BATTERY_VOLTAGE_FIELD,
                    corrected
                )
            } catch (throwable: Throwable) {
                XposedBridge.log(
                    "$LOG_TAG: failed to update $BATTERY_VOLTAGE_FIELD: $throwable"
                )
            }
        }
    }

    private data class Configuration(
        val enabled: Boolean,
        val voltageOffset: Float
    )

    private companion object {
        const val APP_PACKAGE = "com.mt8163tools"
        const val HOOK_PREFERENCES = "voltage_hook_config"
        const val KEY_MODULE_ENABLED = "module_enabled"
        const val KEY_VOLTAGE_OFFSET = "voltage_offset"

        const val MCU_SERVICE_CLASS = "android.carsource.McuService"
        const val MCU_PACKET_CLASS = "android.carsource.McuService\u0024McuPacket"
        const val ON_BATTERY_METHOD = "onBattery"
        const val BATTERY_VOLTAGE_FIELD = "mBatteryVoltage"

        const val PACKET_SUB_COMMAND_METHOD = "subCmd"
        const val PACKET_PARAMETER_COUNT_METHOD = "paramLen"
        const val PACKET_PARAMETER_METHOD = "param"
        const val BATTERY_SUB_COMMAND = 3
        const val BATTERY_VOLTAGE_PARAMETER = 0
        const val VOLTAGE_SCALE = 10.0f

        const val DEFAULT_VOLTAGE_OFFSET = 0.0f
        const val MIN_VOLTAGE_OFFSET = -2.0f
        const val MAX_VOLTAGE_OFFSET = 2.0f
        const val LOG_TAG = "MT8163Tools"
    }
}
