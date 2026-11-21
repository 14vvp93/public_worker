# Voltage Module — Technical Analysis

**Document status:** Current implementation analysis  
**Version:** 2.0  
**Project:** MT8163 Tools  
**Target platform:** Android 9 (API 28), MediaTek MT8163 (ARM32)  
**Integration:** LSPosed 1.9.2, Zygisk mode

## 1. Purpose and boundaries

The Voltage module applies a configurable software offset to the battery-voltage value maintained by the MCU service.

- Supported offset: `-2.0..+2.0 V`
- Adjustment step: `0.1 V`
- Default offset: `0.0 V`
- Persisted UI value: `Float`, in volts

This changes the value exposed by the Android MCU service. It does not change the physical battery voltage, MCU measurement, charging behavior, or hardware configuration.

The active implementation is an LSPosed after-hook in `system_server`. The former SystemUI `McuManager.getBattery()F` hook is obsolete and is not part of the current design.

## 2. Active hook target

| Property | Value |
|---|---|
| Package/process | `android` (`system_server`) |
| Service class | `android.carsource.McuService` |
| Constructor | `McuService(Context)` |
| Hooked method | `onBattery(McuPacket)` |
| Method descriptor | `(Landroid/carsource/McuService$McuPacket;)V` |
| Hook type | LSPosed after-hook |
| Destination field | `McuService.mBatteryVoltage` |

The hook is installed only when LSPosed loads package `android`. The constructor hook attaches the configuration receiver to the service context after construction. The battery hook resolves the exact nested `McuPacket` class through the target class loader.

The exact target firmware remains the runtime authority: constructor resolution, method resolution, packet method return types, and field access must be confirmed on-device.

## 3. Battery packet decoding

Only MCU battery packets are processed.

1. Read `subCmd()`; accept only value `3`.
2. Read `paramLen()`; require at least one parameter.
3. Read `param(0)` as a byte.
4. Interpret the byte as unsigned and convert tenths of a volt to volts:

```kotlin
val rawVoltage = (parameter.toInt() and 0xFF) / 10.0f
```

Example: byte `0x78` is unsigned decimal `120`, therefore the decoded voltage is `12.0 V`.

Unexpected/null packet data and invalid values must not be used to update the service field. The implementation uses guarded type checks; a firmware API type mismatch causes the packet to be ignored and must be diagnosed during device verification.

## 4. Correction and low-voltage protection

The hook runs **after** the original `McuService.onBattery()` method.

The original method therefore processes the real MCU packet and performs its original low-voltage/reboot checks before the hook changes `mBatteryVoltage`.

The hook keeps the decoded value separately as `rawBatteryVoltage` and applies:

```text
effectiveVoltage = rawBatteryVoltage + voltageOffset  when enabled
effectiveVoltage = rawBatteryVoltage                  when disabled
```

The offset is never accumulated. A configuration change recalculates the field from the retained raw value, not from the previously corrected field.

This ordering is intentional: a synthetic negative offset must not be fed into the original low-voltage protection. The original protection continues to evaluate the real packet. Device testing must confirm this behavior on the target firmware.

## 5. Configuration and synchronization

The app publishes the complete runtime configuration using:

| Item | Value |
|---|---|
| Action | `com.mt8163tools.action.VOLTAGE_CONFIG_CHANGED` |
| Target package | `android` |
| Enabled extra | `module_enabled` |
| Offset extra | `voltage_offset` |

Publication points:

- App startup: publish enabled state and current offset.
- Voltage module switch change: publish the complete configuration.
- Offset change or Reset: persist the offset and publish the updated configuration.

The hook registers a dynamic receiver after the `McuService` constructor completes. Packet updates and configuration updates share a lock so the raw value, enabled state, offset, and service field are applied consistently.

The hook configuration is stored separately in `voltage_hook_config`. The app attempts Android 9 `MODE_WORLD_READABLE` and falls back to `MODE_PRIVATE` if access is denied. `XSharedPreferences` initial cross-process loading must be verified on the target device.

There is a startup-order race to verify: the app's startup broadcast can be sent before the `McuService` constructor registers the receiver, in which case that broadcast is lost. If `MODE_WORLD_READABLE` is available, initial preference loading can provide the configuration. If the private fallback is used and the startup broadcast was missed, the hook can retain its default/stale configuration until another app-side configuration event is published. Device verification must explicitly test this case; startup publication alone does not guarantee delivery.

No polling, timers, worker threads, executors, recurring callbacks, or periodic refresh are used.

## 6. Settings and module state

- UI offset: `voltage_settings/voltage_offset`, stored as `Float`.
- Hook configuration: `voltage_hook_config`, containing only `module_enabled` and `voltage_offset`.
- General module enablement remains owned by the shared module-state mechanism.
- The Voltage module defaults to disabled according to the project-wide module defaults.
- When disabled, the service field is restored to the latest raw voltage.
- Offset values must be finite and within `-2.0..+2.0 V`; invalid values are rejected or safely ignored.

The settings layer owns persistence and event publication. The hook callback uses in-memory state and does not perform disk I/O.

## 7. Source ownership

Current implementation files:

```text
app/src/main/java/com/mt8163tools/
├── app/
│   └── MainActivity.kt
├── core/common/
│   └── VoltageConfigEvent.kt
├── hooks/
│   ├── XposedEntry.kt
│   └── VoltageHook.kt
└── modules/voltage/
    └── VoltageSettings.kt
```

Responsibilities:

- `XposedEntry.kt`: accepts only package `android` and installs the Voltage hook.
- `VoltageHook.kt`: hooks the service constructor and battery method, decodes packets, manages the receiver and in-memory configuration, and updates `mBatteryVoltage`.
- `VoltageConfigEvent.kt`: defines the broadcast contract and publishes the hook preferences/configuration.
- `VoltageSettings.kt`: validates and persists offset changes and requests live configuration publication.
- `MainActivity.kt`: publishes the complete Voltage configuration at startup and after the module switch changes.

The feature UI/settings and hook remain in the single app module. No generic hook registry or separate Gradle module is introduced.

## 8. LSPosed scope and manifest

The LSPosed scope is `android` only. The former `com.android.systemui` scope is no longer required for Voltage.

The confirmed device baseline is LSPosed 1.9.2 in Zygisk mode. The manifest declares Xposed module metadata and shared-preference support. The relationship between `xposedminversion=93` and the project's compile-only Legacy Xposed API 82 dependency remains an explicit compatibility check; do not infer runtime compatibility from either version alone.

## 9. Verification status

**Implemented in the repository:** system_server hook migration, packet decoding path, raw-value retention, offset application, configuration receiver/broadcast contract, and updated LSPosed scope.

**Not yet verified:** APK compilation and target-device runtime behavior.

Required verification:

1. Build the APK manually using the repository's dedicated Android build workflow.
2. Confirm LSPosed loads the module in `android` and resolves `McuService(Context)`, `onBattery(McuPacket)`, and `mBatteryVoltage`.
3. Confirm runtime return types for `subCmd()`, `paramLen()`, and `param(0)` match the guarded casts in the implementation.
4. Confirm the battery packet filter and decoded voltage against live MCU data.
5. Confirm receiver registration and configuration delivery at startup and after each setting change, including the startup-order race described above.
6. Test disabled state, zero offset, positive and negative offsets, limits, Reset, and changes after a battery packet has arrived.
7. Confirm changing the displayed/service voltage does not alter the original low-voltage protection or charging behavior.
8. Verify preference access when `MODE_WORLD_READABLE` succeeds and when the private fallback is taken.
9. Resolve the Xposed metadata/API dependency compatibility check.

A successful build does not prove runtime correctness. No build or device test is claimed by this document.

## 10. Superseded design notes

The earlier implementation hooked `McuManager.getBattery()F` in the SystemUI process. That approach changed the getter result for all its callers in that process and was later replaced by the system_server service hook.

The following former assumptions are no longer current:

- SystemUI as the LSPosed target process.
- `McuManager.getBattery()F` as the hook target.
- A three-second StatusBar refresh as the mechanism for applying the correction.
- Getter-result modification as the active correction point.
- A SystemUI-only runtime scope.

The firmware observation that StatusBar formats the voltage as `%.1fV` and refreshes it periodically may remain useful historical context, but it does not define the current hook architecture.

For authoritative hook and runtime details, see [SYSTEM_SERVER.md](SYSTEM_SERVER.md). For LSPosed scope details, see [LSPOSED.md](LSPOSED.md).
