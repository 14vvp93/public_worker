# LSPosed Integration

**Target device:** MediaTek MT8163, ARM32
**Android:** 9 / API 28
**Known-good LSPosed baseline:** 1.9.2, Zygisk mode
**Compile-time API:** Legacy Xposed API 82

## Current hook

Voltage is the only implemented LSPosed hook.

| Property | Value |
|---|---|
| Package/process | android (system_server) |
| Service | android.carsource.McuService |
| Method | onBattery(McuPacket) |
| Descriptor | (Landroid/carsource/McuService$McuPacket;)V |
| Hook type | After-hook |
| Packet selector | subCmd() == 3 |
| Voltage | unsigned param(0) / 10.0 |
| Target field | mBatteryVoltage |

XposedEntry.kt dispatches only for package android. The recommended LSPosed scope in arrays.xml is also android. No other feature currently requires a SystemUI hook.

## Runtime behavior

The original McuService.onBattery() executes first, including its original low-voltage protection. The after-hook then reads the battery packet and writes the raw voltage plus the configured offset to mBatteryVoltage. The raw voltage is retained separately so configuration changes always recalculate from the real packet value and never accumulate offsets.

The service constructor hook registers a dynamic receiver with the service context. The receiver accepts the existing VOLTAGE_CONFIG_CHANGED action targeted to package android. The in-memory state and raw voltage are synchronized; no polling, timers, threads, executors, or background services are used.

## Configuration

- Preferences: voltage_hook_config
- Keys: module_enabled, voltage_offset
- Broadcast action: com.mt8163tools.action.VOLTAGE_CONFIG_CHANGED
- Broadcast target: android
- Offset range: -2.0..+2.0 V
- Default offset: 0.0 V

The app publishes configuration on startup and when the module switch changes. VoltageSettings publishes offset changes. The app UI and settings persistence remain unchanged.

## Verification status

The source migration is separate from build and device verification. Confirm APK compilation, LSPosed package loading, receiver delivery, packet handling, field updates, and preservation of the original low-voltage behavior on the target device.

Do not reinstall the known-good LSPosed 1.9.2 installation as part of routine development.
