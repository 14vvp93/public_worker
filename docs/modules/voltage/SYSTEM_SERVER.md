# Voltage — system_server implementation

**Status:** Source migration applied by GitHub Actions; build and device verification pending.

## Hook target

- Process/package: android (system_server)
- Service: android.carsource.McuService
- Method: onBattery(McuPacket)
- Descriptor: (Landroid/carsource/McuService$McuPacket;)V
- Hook: LSPosed after-hook
- Packet filter: subCmd() == 3
- Voltage byte: param(0), unsigned, divided by 10.0
- Destination field: McuService.mBatteryVoltage

The packet structure was verified in the framework disassembly. McuPacket.paramLen() must be at least one before reading param(0).

## Runtime sequence

1. XposedEntry ignores every package except android.
2. VoltageHook hooks the McuService(Context) constructor and the exact onBattery(McuPacket) overload.
3. After the constructor completes, the hook registers the configuration receiver using the service context.
4. After the original onBattery() completes, the hook extracts the raw voltage only from battery packets.
5. The original method therefore performs its low-voltage/reboot checks against the real packet before the correction is applied.
6. The hook stores the raw value and writes raw + offset when enabled, otherwise the raw value, to mBatteryVoltage.
7. A configuration broadcast reapplies the current offset to the last raw value. It never adds an offset to an already corrected value.

## Synchronization and safety

- Packet updates and configuration updates share one lock.
- Raw voltage is retained separately from the corrected field value.
- Null packets, unexpected packet types, short packets, non-finite offsets, and out-of-range offsets are ignored safely.
- Offset range is -2.0..+2.0 V.
- No polling, timers, worker threads, executors, or recurring callbacks are used.
- The original onBattery() implementation is not replaced or bypassed.

## Broadcast contract

- Action: com.mt8163tools.action.VOLTAGE_CONFIG_CHANGED
- Target package: android
- Extras: module_enabled, voltage_offset

The app continues to publish the complete configuration on startup and when the Voltage module switch changes. VoltageSettings publishes offset changes. No changes to MainActivity or VoltageSettings are required.

## Verification still required

1. Build the APK and confirm the hook source compiles against the project's Legacy Xposed API 82 dependency.
2. Confirm LSPosed loads the module in android and resolves the framework classes on the target firmware.
3. Confirm receiver registration and configuration broadcasts.
4. Confirm mBatteryVoltage changes while the original low-voltage protection remains active.
5. Test enabled/disabled state, positive/negative offsets, reset, and configuration changes after a battery packet.
6. Confirm no reboot occurs from a synthetic offset because the original low-voltage check runs before the after-hook correction.

Build success alone does not establish device runtime correctness.
