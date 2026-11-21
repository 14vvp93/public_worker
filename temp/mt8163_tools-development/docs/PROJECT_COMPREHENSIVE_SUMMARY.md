# MT8163 Tools — Comprehensive Project Summary

**Snapshot date:** 2026-11-20  
**Repository:** [14vvp93/mt8163_tools](https://github.com/14vvp93/mt8163_tools)  
**Branch:** `development`  
**Snapshot HEAD:** `857fe9db150bfa2c218036c0f7b930e30f48e472`  
**Application version:** `1.0.0` (`versionCode = 10000`)  
**Document type:** consolidated project status, architecture, implementation record, and verification plan

> This document describes the repository state observed at the snapshot above. It distinguishes implemented source code, static firmware/APK findings, documented design, CI/build evidence, and target-device verification. A code path being present does not mean that it has been compiled or verified on the head unit.

---

## 1. Project purpose

MT8163 Tools is a single-APK Android application for a MediaTek MT8163-based automotive head unit. The application groups independent device features behind one UI and one Gradle application module. System-level behavior is implemented through LSPosed hooks running in `system_server`, while the app process owns settings, module navigation, and the persistent Events journal.

The project is intended to provide focused tools for the target head unit rather than a generic Android utility suite. The implementation must remain compatible with the actual firmware and its vendor services.

### Primary objectives

1. Provide one maintainable APK with independently enabled modules.
2. Integrate with the target MCU/framework only at verified hook points.
3. Handle ACC transitions through existing system events, not polling.
4. Keep Bluetooth transport/playback control separate from Android media-player control.
5. Preserve source-selection, player-playback, and Bluetooth state as distinct concepts.
6. Record meaningful module events in one bounded journal.
7. Keep the implementation small, event-driven, and appropriate for Android 9 / MT8163 / ARM32.

## 2. Fixed target platform and engineering constraints

| Item | Project target |
|---|---|
| Hardware | MediaTek MT8163 head unit |
| CPU ABI | ARM32 |
| Android | Android 9 |
| API level | minSdk 28 / targetSdk 28 |
| Compile SDK | 37 |
| JDK | 17 |
| Language | Kotlin |
| UI | Android XML Views, Fragments, View Binding |
| Gradle modules | One application module: `:app` |
| Display | 1280 × 720, 160 dpi |
| Theme | Dark Graphite with Neon Red accents |
| Hook framework | LSPosed, Legacy Xposed-compatible API as compile-only |
| Application package | `com.mt8163tools` |

### Mandatory implementation rules

- Event-driven execution only: user actions, broadcasts, hooked methods, Android callbacks, and Binder callbacks.
- No polling, timers, periodic refresh, worker loops, recurring retries, or always-running background tasks.
- Do not add threads, executors, pools, services, or architectural layers without a demonstrated need.
- Hook callbacks must remain short and avoid disk I/O or blocking work in hot paths.
- Preserve null-safety, validate external values, handle Binder/service death, and release registered callbacks/listeners.
- Do not hard-code a particular third-party media player. The player must be discovered from the active MediaSession.
- Do not use MediaSession as the Bluetooth playback control channel.
- Keep the repository boundary strict: product code and maintained documentation belong in `mt8163_tools`. The `public_worker` repository is an analysis/artifact source only.
- Update documentation when behavior or contracts change. Never report a build or device test as successful unless it was actually run.

## 3. Repository architecture

The project is intentionally a single Gradle application module. Features are Kotlin packages, not separate Gradle modules, APKs, dynamically loaded plugins, or runtime extensions.

### Main source layout

```text
app/
└── src/main/
    ├── AndroidManifest.xml
    ├── assets/
    │   └── xposed_init
    ├── java/com/mt8163tools/
    │   ├── app/
    │   │   ├── MainActivity.kt
    │   │   ├── ModuleRegistry.kt
    │   │   ├── ModuleStateManager.kt
    │   │   ├── ModuleContentHost.kt
    │   │   └── AccessoryStateReceiver.kt
    │   ├── core/
    │   │   ├── common/
    │   │   │   ├── EventsLog.kt
    │   │   │   ├── AccessoryConfigEvent.kt
    │   │   │   ├── AccessoryStateEvent.kt
    │   │   │   └── VoltageConfigEvent.kt
    │   │   └── module/
    │   │       └── Module.kt
    │   ├── modules/
    │   │   ├── accessory/
    │   │   ├── autorun/
    │   │   ├── events/
    │   │   ├── protection/
    │   │   └── voltage/
    │   └── hooks/
    │       ├── XposedEntry.kt
    │       ├── AccessoryHook.kt
    │       ├── AccessoryPlaybackController.kt
    │       ├── VendorBluetoothMusicClient.kt
    │       └── VoltageHook.kt
    └── res/
```

### Ownership boundaries

| Layer | Responsibility |
|---|---|
| `app` | Activity, module navigation, persisted module states, app-side receivers |
| `core/module` | Small shared feature contract |
| `core/common` | Broadcast contracts and shared Events storage |
| `modules/*` | Feature UI, settings, and feature-level behavior |
| `hooks` | LSPosed entry point and vendor/system hooks |
| `res` | Shared and feature-specific Android XML resources |

Do not introduce a generic hook framework, service locator, dependency-injection container, feature plugin system, or additional Gradle modules for the current scope.

## 4. Application shell and module system

### 4.1 Module registry

`ModuleRegistry` contains the feature definitions and sorts them by each module's declared order. The current registry contains:

1. Accessory
2. Protection
3. Voltage
4. Autorun
5. Events

The application opens the first registered module at startup. `MainActivity` owns navigation and replaces the current Fragment directly through `FragmentManager`.

### 4.2 Persistent module state

`ModuleStateManager` stores feature enablement in app-private SharedPreferences named `module_states`. Each feature uses the key `module_<module-id>`.

Defaults are written only when a key does not yet exist. Existing user choices therefore survive application restarts and updates.

| Module | Default state |
|---|---|
| Accessory | Disabled |
| Protection | Disabled |
| Voltage | Disabled |
| Autorun | Disabled |
| Events | Enabled |

A disabled module's UI content is blocked by the shared content host, but UI blocking is not treated as the operational safety boundary. Each module/hook must independently respect its persisted enabled state.

### 4.3 UI implementation

The UI uses XML layouts, Android Views, Fragments, and View Binding. Jetpack Compose and WebView are not used. The target layout is 1280×720 at 160 dpi.

The module Fragment is a presentation/settings surface. Hardware event ownership and system playback control must not be placed in a Fragment lifecycle.

## 5. LSPosed integration and process model

### 5.1 Entry point

`XposedEntry.kt` implements `IXposedHookLoadPackage`. It accepts only package `android` (the `system_server` process) and installs the feature hooks:

- `VoltageHook`
- `AccessoryHook`

The LSPosed entry class is declared in `app/src/main/assets/xposed_init`. The manifest contains Xposed module metadata, shared-preference metadata, and the configured scope resource.

The Xposed API is declared as `compileOnly`; it must not be packaged into the APK as an application dependency.

### 5.2 System/app process separation

There are two relevant processes:

- **system_server:** receives MCU/ACC events, owns the hooks, talks to framework/vendor services, and maintains in-memory operation state.
- **MT8163 Tools app process:** owns UI, private SharedPreferences, and the Events journal.

System hooks must not write directly to app-private storage. The established bridge is an explicit package-scoped broadcast. The app-side manifest receiver validates the action and forwards the event to the app's shared logger.

### 5.3 Hook safety

Hook target class names and method signatures originate from firmware inspection. Their actual availability, class loader, return types, and side effects still require runtime verification on the target firmware. Static decompilation alone is not proof of runtime compatibility.

## 6. Accessory module — overall responsibility

Accessory coordinates ACC transitions, Android media-player playback, vendor Bluetooth playback, and selected source information.

It must work with arbitrary compatible Android media-player applications. No player package such as AIMP is a special case or allowlist entry.

The central design rule is to keep these state domains independent:

| State domain | What it answers | Current source/API |
|---|---|---|
| ACC | Is vehicle accessory power ON or OFF? | MCU service / existing ACC receiver |
| Selected source | Which head-unit source/package is selected? | Vendor SourceInfo / SourceService |
| Android player playback | Which MediaSession is playing, paused, or stopped? | MediaSessionManager / MediaController |
| Bluetooth transport | Are A2DP and AVRCP connected? | Vendor Bluetooth music Binder |
| Bluetooth playback | Is A2DP music currently playing? | `IBluetoothMusicService.isA2dpPlaying()` |

None of these values is a substitute for another. Bluetooth connected does not mean Bluetooth music is playing. A selected source does not prove active playback. A MediaSession does not establish which vendor source is selected.

### 6.1 ACC hook target

The current Accessory hook is installed in `system_server`:

| Item | Value |
|---|---|
| MCU service | `android.carsource.McuService` |
| Constructor | `McuService(Context)` |
| ACC receiver | `android.carsource.McuService$1` |
| Method | `onReceive(Context, Intent)` |
| Broadcast action | `android.mcu.device.action.ACC` |
| State extra | `AccStatus` |
| Current state getter | `McuService.AccStatus()` |

The constructor hook retains the service and application context. The existing MCU receiver is hooked after its original method runs. Duplicate ACC transitions are filtered in memory.

When Accessory becomes enabled, the hook synchronizes the current hardware state through `McuService.AccStatus()`; it does not wait for the next ACC broadcast. The UI Fragment is not responsible for ACC listening or state ownership.

### 6.2 Accessory configuration

The app publishes module enablement using the Accessory configuration broadcast. The system-side receiver updates the in-memory enabled state and enables/disables both playback controllers.

Disabling Accessory must stop module-specific actions and release callbacks/listeners/bindings where applicable. Enabling it synchronizes current ACC state.

## 7. Android MediaSession playback path

### 7.1 Purpose

`AccessoryPlaybackController` controls ordinary Android media-player sessions. It does not use a fixed player package. It identifies the actual session using its package name and `MediaSession.Token`.

Bluetooth-owned MediaSessions are explicitly ignored when their package is `com.hcn.bluetoothservice`; Bluetooth is managed through the vendor Bluetooth Binder path.

### 7.2 ACC OFF behavior

When ACC transitions to OFF:

1. Obtain active MediaSession controllers through `MediaSessionManager`.
2. Ignore sessions owned by the vendor Bluetooth service package.
3. Find an active non-Bluetooth session with `PlaybackState.STATE_PLAYING`.
4. Retain the exact session token and owning package.
5. Confirm that the session supports PAUSE or PLAY_PAUSE.
6. Register a callback for that exact controller.
7. Request PAUSE only for that controller.

If there is no eligible playing session, no MediaSession restoration request is created. If the controller cannot be controlled, the hook does not invent a replacement player.

### 7.3 ACC ON behavior

When ACC transitions back to ON:

1. Look for the exact saved token and package.
2. If it is not active yet, retain the request and wait for active-session/playback callbacks.
3. If the session reports PLAYING, do not send another PLAY.
4. If it reports STOPPED or ERROR, cancel the restoration.
5. If it supports PLAY or PLAY_PAUSE and is not playing, request PLAY.
6. Confirm PAUSED/PLAYING from the controller callback before recording the completed transition.

A different session from the same package must not inherit the request. A destroyed session cancels restoration. Disabling the module clears saved state and unregisters callbacks/listeners.

### 7.4 Event-driven restoration

The controller uses the active-session listener and the saved controller's `MediaController.Callback`. It does not poll or use a timer to wait for session recreation.

The callback handler uses the system main looper to receive framework callbacks. It is not a worker pool or periodic task.

## 8. Vendor Bluetooth Binder integration

### 8.1 Service identity and contract

The inspected firmware exposes a vendor music Binder service:

- Package identified by client binding contract: `com.hcn.bluetoothservice`
- Component: `com.hcn.bluetooth.service.BluetoothMusicService`
- Binder descriptor: `com.hcn.bluetooth.service.IBluetoothMusicService`
- Callback: `IMusicCallback`

The service provides separate A2DP and AVRCP state, A2DP playback state, metadata, and playback callbacks.

Relevant methods identified from the decompiled contract:

- `isA2dpConnected()`
- `isAvrcpConnected()`
- `isA2dpPlaying()`
- `registerBTMusicCallback(IMusicCallback)`
- `unregisterBTMusicCallback(IMusicCallback)`
- `send_Avrcp_Cmd(int)`

Relevant callback methods:

- `onA2dpConnectStateChanged(int)`
- `onAvrcpConnectStateChanged(int)`
- `onMetadataChanged(String, String, String)`
- `onPlayStatusChanged(MusicPlayState)`

The service does not send initial A2DP/AVRCP state values as part of callback registration. The client explicitly queries the initial states after registration and then relies on callbacks for subsequent changes.

### 8.2 Current client implementation

`VendorBluetoothMusicClient` is integrated into `AccessoryHook`. It:

- Binds to the explicit vendor service component.
- Wraps Binder transactions through a local proxy.
- Registers an `IMusicCallback`-compatible Binder callback.
- Queries initial A2DP connected, AVRCP connected, and A2DP playing values.
- Tracks callback updates.
- Links to Binder death and handles service disconnect, binding death, null binding, and rebind paths.
- Unregisters its callback and unbinds when disabled.
- Publishes service/state events to the app process through the Accessory event bridge.

A2DP connection, AVRCP connection, and A2DP playback are kept as separate values. A2DP/ACL connection alone is not proof that AVRCP is ready for control commands.

### 8.3 Bluetooth snapshot at ACC OFF

At the ACC OFF transition, the current code reads SourceInfo and compares the returned package with the exact Bluetooth source package:

`com.autochips.bluetooth`

- If SourceInfo reports that exact package, the client queries `isA2dpPlaying()` at ACC OFF.
- If SourceInfo reports another package, the Bluetooth playback query is skipped.
- If SourceInfo is unavailable, returns null, or the Binder service is unavailable, the snapshot is treated as unknown/not playing for restoration purposes.
- The decision is based on the ACC OFF moment; the code does not maintain a continuously refreshed playback-state cache as the source of the ACC OFF decision.

The source check is therefore a gate for deciding whether Bluetooth playback should be considered for restoration. It is not itself a Bluetooth playback-state query.

### 8.4 Bluetooth restoration path present in current source

The current `VendorBluetoothMusicClient` contains an ACC ON restoration path:

1. It requires that Bluetooth had been observed playing at ACC OFF.
2. It requires A2DP connected and AVRCP connected.
3. It waits for the required state through Binder callback updates / initial state snapshot, without periodic polling.
4. If A2DP is already playing, it does not send PLAY.
5. Otherwise it sends AVRCP PLAY (command value `0`).
6. It waits for the playback callback to confirm resumed playback and publishes the corresponding event.

The source contains a `mediaSessionOwnsRestore` guard on ACC ON. This guard can skip Bluetooth PLAY when the MediaSession controller reports that it owns a restoration cycle. **This is a known design conflict with the requirement that Bluetooth restoration be independent of MediaSession.** The guard and its ownership semantics must be reviewed before declaring the two restoration paths fully independent.

### 8.5 Important service identity discrepancy

The analysis artifacts contain an inconsistency that must not be silently normalized:

- The inspected client wrapper binds to `com.hcn.bluetoothservice/com.hcn.bluetooth.service.BluetoothMusicService`.
- A separately inspected Bluetooth APK manifest reports package `com.autochips.bluetooth`.
- SourceInfo's Bluetooth source comparison also uses `com.autochips.bluetooth`.

These may represent distinct package roles or different artifacts/firmware versions. The installed package and exported service component must be confirmed on the actual MT8163 before treating the hard-coded binding component as universally correct.

### 8.6 Callback unregistration risk

The vendor contract analysis warns that `unRegMusicClientBinder()` may have side effects, potentially stopping music or releasing A2DP. The current local proxy calls the callback-unregistration transaction when cleaning up. Its exact behavior must be validated against the service implementation and device behavior; do not assume it is a harmless listener-only operation.

## 9. SourceInfo and selected-source state

### 9.1 Firmware service discovery

The firmware analysis identified:

- Binder service name: `sourceinfo`
- Server implementation: `android.sourceservice.SourceService`
- Client singleton: `android.sourceservice.SourceInfo`
- Client API: `getInstance()`, `getSourcePackage()`, `getMusicInfo()`, `setOnEventListener(...)`, and `onRequestPlayAudio(...)` overloads.

The service registration was found in the vendor `boot-carservices` path. The client resolves the Binder through `ServiceManager.getService("sourceinfo")`.

### 9.2 getSourcePackage implementation

The decompiled getter is:

```java
public String getSourcePackage() {
    return SystemProperties.get("sys.sourcing.package");
}
```

This establishes that the getter reads the system property `sys.sourcing.package`.

A confirmed write to the same property was found in one `SourceService.onTransact()` stop-status branch. That branch writes `CurrentSource` when handling a matching stopped status and also resets relevant MusicInfo state.

This does **not** prove that the identified branch is the only writer, nor that every source switch refreshes the property. Although source changes were observed on the target device during manual and automatic switching, a complete trace of all `CurrentSource` assignments and property writes remains necessary before treating the property as an authoritative snapshot for every timing condition.

### 9.3 SourceInfo callback and request methods

The client contains a callback/handler path that dispatches source-change notifications to `SourceInfo.OnEventListener`. The complete event mapping, payload semantics, registration lifecycle, and ordering remain incompletely documented.

The `onRequestPlayAudio(...)` overloads are active source/audio requests, not passive getters:

- String overload appends `/background`.
- Context overload uses the context package and appends `/background`.
- ComponentName overload flattens the component to a short string.

Do not call these methods as a generic ACC restore operation without proving the intended source restoration behavior and side effects. The project clarification is that SourceInfo is relevant to preserving/restoring selected source state; it is not a substitute for MediaSession playback state or Bluetooth transport state.

### 9.4 Current integrated read

The current Accessory ACC OFF path reflectively calls:

- `android.sourceservice.SourceInfo`
- `getInstance()`
- `getSourcePackage()`

It trims the returned package, treats empty/null/exception as unknown, logs a `source_snapshot` event, and passes the package to the Bluetooth client for source gating.

This is a one-time read at ACC OFF. The current integrated call uses `XposedHelpers.findClass(SOURCE_INFO_CLASS, null)`. Whether a null/bootstrap class loader resolves this vendor framework class in the actual hook context remains unverified.

### 9.5 Standalone SourceChecker example

A standalone `SourceChecker` example is documented in `docs/modules/accessory/ANALYSIS.md`. It is documentation-only and is not part of the application source set. It performs a read-only SourceInfo lookup and classifies `com.autochips.bluetooth` as Bluetooth. It does not request playback, switch source, access MediaSession, or send AVRCP commands.

The standalone example's class-loader assumption is also pending device validation.

## 10. Events module and event pipeline

### 10.1 Purpose

Events is a bounded, persistent journal of meaningful events explicitly emitted by the application modules and supported system hooks. It is not a Logcat collector and does not promise to capture every Android/vendor event.

### 10.2 Record structure and storage

Each record contains:

| Field | Meaning |
|---|---|
| `timestamp` | Unix timestamp in milliseconds |
| `module_id` | Stable module identifier |
| `type` | Machine-readable event type |
| `message` | Short human-readable detail |

Storage details:

- App-private SharedPreferences: `events_log`
- Key: `entries`
- JSON representation: `org.json.JSONArray` / `JSONObject`
- Maximum history: 300 records
- Newest record first
- Module ID and event type limited to 64 characters
- Message limited to 500 characters
- Invalid JSON is handled as an empty log
- Writes use `SharedPreferences.apply()`

### 10.3 Collection enablement

The Events module state controls collection, not just screen visibility.

- Events defaults to enabled.
- `MainActivity` initializes the in-memory `AtomicBoolean` gate from persisted module state.
- When disabled, new events are discarded and are not queued for later.
- Existing history remains stored.
- The disable action is not recorded after collection has stopped.
- Enabling collection resumes recording and records `collection_enabled`.

The gate is checked before and inside the synchronized write section.

### 10.4 System-to-app bridge

System hooks call `AccessoryStateEvent.send()` / `sendPlaybackEvent()` to send a package-scoped broadcast. The manifest-registered `AccessoryStateReceiver` receives it in the app process, initializes the Events gate from persisted module state, and writes the record through `EventsLog`.

The Events Fragment registers for the logger's refresh broadcast only while started, reloads the stored history, and unregisters when stopped.

### 10.5 Current event coverage

Current documented event producers include:

- MainActivity: module enable/disable and Events collection enable.
- VoltageSettings: voltage offset changes.
- AccessoryHook: ACC synchronization/transition and source snapshot.
- AccessoryPlaybackController: player pause request/confirmation, pending restoration, PLAY request/confirmation, cancellation, and errors.
- VendorBluetoothMusicClient: service bind/connect/disconnect/death/error, initial Bluetooth snapshot, A2DP/AVRCP state, metadata, playback state, ACC OFF snapshot, and restoration events.

Events does not currently capture all SourceService transitions, all framework/vendor Logcat messages, or all unrelated MediaSession state changes. Source selection callbacks are not yet comprehensively forwarded.

## 11. Voltage module

### 11.1 Purpose and scope

Voltage applies a configurable software offset to the battery voltage value maintained by the MCU service. It changes the value exposed by the Android MCU service; it does not change the physical battery voltage, MCU measurement, charging behavior, or hardware configuration.

Settings:

| Setting | Value |
|---|---|
| Offset range | `-2.0..+2.0 V` |
| Step | `0.1 V` |
| Default | `0.0 V` |
| Stored value | `Float`, volts |

### 11.2 Current hook target

The current design hooks in `system_server`, not SystemUI:

| Item | Value |
|---|---|
| Service | `android.carsource.McuService` |
| Constructor | `McuService(Context)` |
| Method | `onBattery(McuPacket)` |
| Descriptor | `(Landroid/carsource/McuService$McuPacket;)V` |
| Hook | After-hook |
| Field updated | `McuService.mBatteryVoltage` |

Only battery packets are processed:

1. `subCmd()` must equal `3`.
2. `paramLen()` must be at least `1`.
3. `param(0)` is interpreted as an unsigned byte.
4. Raw voltage is decoded as byte / 10.0 volts.

The raw value is retained separately. The effective field is recalculated from raw voltage plus the configured offset; the offset is never accumulated on a previously corrected value.

The hook runs after the original MCU method. The original method therefore performs its own packet processing and low-voltage/reboot checks before the field is modified. This ordering is intentional but must be verified on the target device.

### 11.3 Configuration

The app persists the user setting in `voltage_settings/voltage_offset`. A separate hook configuration file `voltage_hook_config` carries `module_enabled` and `voltage_offset` for cross-process use.

The app publishes the complete configuration at startup, on module enablement changes, and on offset changes. The hook updates in-memory configuration through a broadcast receiver registered after the MCU service constructor.

A startup-order risk remains: if the app sends the configuration broadcast before the system receiver is registered, that broadcast can be missed. Initial XSharedPreferences loading and the Android 9 world-readable/private fallback behavior must be verified on the target firmware. Do not assume that the startup broadcast always arrives.

The former SystemUI `McuManager.getBattery()F` hook is obsolete and is not part of the current implementation.

## 12. Protection and Autorun modules

Both modules are present in the module registry and have module/Fragment scaffolding. The repository snapshot does not establish completed hardware behavior for either module.

Do not infer functionality from the module name or registry entry. Any implementation work must first define the exact requirement, target firmware API/event, enable/disable semantics, and verification method. No unverified behavior should be represented as complete.

## 13. Build configuration and GitHub Actions

### 13.1 Gradle configuration

- One application module: `:app`
- Android Gradle Plugin version declared in version catalog: `9.5.1`
- AndroidX Core KTX: `1.19.1`
- AppCompat: `1.8.0`
- Fragment: `1.9.1`
- Xposed API: `82` as compile-only
- Java source/target compatibility: 17
- minSdk/targetSdk: 28
- compileSdk: 37
- Debug variant configured; no release build variant is documented.
- Debug APK filename: `MT8163-Tools-v<version>-debug.apk`

The README documents Gradle 9.6.0 and JDK 17 as the manual build environment.

### 13.2 Android CI workflow

`.github/workflows/android-ci.yml` runs on pushes and pull requests targeting `main` and `development`. It checks out the code, installs JDK 17, configures Gradle, and runs:

```bash
./gradlew --no-daemon --stacktrace --build-cache :app:assembleDebug
```

This is a real Android compile/assemble workflow, not merely a Gradle Wrapper validation.

### 13.3 Manual APK workflow

`.github/workflows/android-build.yml` is manually dispatched. It:

- Sets up JDK 17 and Gradle caching.
- Builds `:app:assembleDebug` with build cache and parallel execution.
- Reads `appVersionName` from `app/build.gradle.kts`.
- Uploads the generated debug APK as a versioned artifact.
- Retains the artifact for 14 days.

Gradle Configuration Cache is not enabled. The workflow contains a one-time cache-clear condition for run number 6.

### 13.4 CI status at this snapshot

For HEAD `857fe9db150bfa2c218036c0f7b930e30f48e472`, the GitHub status query returned no status entries and the commit workflow-run query returned no associated workflow runs. Therefore this snapshot has **no confirmed CI build result** in the checked data.

Do not state that Kotlin compilation or APK assembly passed. The latest repository changes in this snapshot are documentation commits; the last source implementation commits precede them.

## 14. Documentation map

The repository contains the following principal technical documents:

| Document | Purpose |
|---|---|
| `README.md` | Project overview, platform, build instructions, documentation links |
| `docs/ARCHITECTURE.md` | Single-module architecture, module state, Events, LSPosed, event-driven rules |
| `docs/BLUETOOTH_BINDER_CONTRACTS.md` | Vendor Bluetooth Binder and callback contracts |
| `docs/SOURCE_INFO_FIRMWARE_ANALYSIS.md` | SourceInfo/SourceService firmware reverse engineering |
| `docs/modules/accessory/ANALYSIS.md` | ACC, MediaSession, Bluetooth, SourceInfo design and status; standalone SourceChecker |
| `docs/modules/accessory/BLUETOOTH_SERVICE.md` | Detailed BluetoothMusicService APK/JADX analysis |
| `docs/modules/events/ANALYSIS.md` | Events storage, bridge, coverage, and verification |
| `docs/modules/voltage/ANALYSIS.md` | Voltage hook, packet decoding, offset and verification |
| `docs/modules/voltage/LSPOSED.md` | Voltage/LSPosed notes |
| `docs/modules/voltage/SYSTEM_SERVER.md` | System-server integration notes |

The `public_worker` repository is referenced only for analysis artifacts, such as JADX output and firmware disassembly. The maintained project source and this consolidated report remain in `mt8163_tools`.

## 15. Confirmed findings, implementation, and unresolved items

### 15.1 Confirmed from repository source / static analysis

- The application is structured as one APK and one Gradle app module.
- The module registry contains Accessory, Protection, Voltage, Autorun, and Events.
- LSPosed entry filters package `android` and installs Voltage and Accessory hooks.
- Accessory hooks the existing MCU ACC receiver and synchronizes the current ACC state through the MCU service method.
- MediaSession controller code targets the exact saved non-Bluetooth session and uses callbacks for confirmation/restoration.
- The Bluetooth client implements a Binder proxy, callback registration, initial state queries, Binder death handling, ACC OFF playback snapshot, and AVRCP PLAY restoration logic.
- SourceInfo's `getSourcePackage()` implementation reads `sys.sourcing.package`.
- The vendor Bluetooth APK code checks the Bluetooth source package string `com.autochips.bluetooth` before its own AVRCP PLAY action.
- Events uses app-private storage, a 300-record limit, and a package-scoped system-to-app event bridge.
- Voltage uses the MCU battery packet path in `system_server` and applies a bounded offset after the original handler.

### 15.2 Implemented in repository source but not yet proven on hardware

- ACC receiver hook and current-state synchronization.
- SourceInfo read on ACC OFF and Bluetooth source gating.
- Vendor Bluetooth service binding, Binder transactions, callback handling, and restoration command path.
- MediaSession pause/restore path.
- Events forwarding from system_server to the app process.
- Voltage packet decoding and offset application.
- Runtime enable/disable propagation for Accessory and Voltage.

### 15.3 Important unresolved issues

1. **No confirmed build for current HEAD.** Latest HEAD has no associated workflow run/status in the queried GitHub data.
2. **No confirmed end-to-end MT8163 runtime test.** Hook resolution, Binder access, callback behavior, and real ACC transitions remain to be demonstrated.
3. **SourceInfo class loader.** Current integration uses `findClass("android.sourceservice.SourceInfo", null)`; the correct loader must be proven on the target firmware.
4. **Source property freshness.** All writers to `sys.sourcing.package` and all `CurrentSource` assignments have not been traced. A confirmed stop-path writer alone does not establish freshness at every ACC OFF.
5. **Bluetooth service identity discrepancy.** The client component package and the APK manifest package differ in the inspected artifacts. Confirm the installed service component/package on the target device.
6. **Bluetooth/MediaSession ownership coupling.** The current Bluetooth ACC ON path still accepts `mediaSessionOwnsRestore` and can skip Bluetooth PLAY based on the MediaSession path. This conflicts with the architectural requirement that Bluetooth restoration be independent of MediaSession.
7. **Bluetooth callback unregistration side effects.** Verify that the vendor unregister transaction does not stop playback or release A2DP unexpectedly.
8. **ACC OFF timing.** Verify that the SourceInfo package and A2DP playing query are sampled at the correct moment relative to the original MCU ACC handler and vendor source transition.
9. **Events coverage.** SourceInfo source-change callbacks and arbitrary framework/vendor events are not comprehensively forwarded; Events is not a Logcat collector.
10. **Voltage startup configuration race.** Verify initial cross-process preferences and configuration delivery if the first startup broadcast occurs before receiver registration.
11. **Voltage low-voltage protection ordering.** Verify on the real MCU firmware that the original safety logic always consumes the raw value before the after-hook modifies `mBatteryVoltage`.
12. **Protection and Autorun.** Module scaffolding exists; completed feature behavior is not established by this snapshot.

## 16. Recommended next work — ordered by risk

### Priority 1: establish a reproducible build baseline

1. Run the Android CI workflow for the current `development` HEAD.
2. Confirm `:app:assembleDebug` completes with JDK 17 and the declared Android SDK.
3. Preserve the workflow URL, commit SHA, result, and artifact identifier in the project status record.
4. Do not begin unrelated behavior changes until the compile baseline is known.

### Priority 2: validate ACC and SourceInfo on the real MT8163

1. Confirm the hook is loaded in `system_server`.
2. Confirm the exact class loader required for `android.sourceservice.SourceInfo`.
3. Record SourceInfo package for each relevant state: selected Bluetooth source, another source, active playback, pause, stop, manual source switch, automatic source switch, ACC OFF.
4. Confirm whether `sys.sourcing.package` is current at each transition.
5. Check the event order between ACC broadcast, SourceInfo read, and vendor Bluetooth state query.

### Priority 3: remove ambiguity from Bluetooth restoration ownership

1. Confirm the actual installed Bluetooth service package/component.
2. Confirm Binder transaction codes, interface descriptor, callback transaction payloads, and access permissions against the installed service.
3. Verify A2DP and AVRCP callback values on the target device.
4. Review the `mediaSessionOwnsRestore` parameter/branch. Bluetooth restoration must not be suppressed merely because an Android MediaSession owns a separate player restoration operation.
5. Confirm whether AVRCP PLAY is required by actual head-unit behavior and whether the vendor unregister transaction is safe.
6. Verify that a third-party media player active before ACC OFF never causes Bluetooth PLAY.

### Priority 4: verify MediaSession independently

1. Test at least two unrelated Android media players; no app-specific assumptions should be needed.
2. Confirm the exact playing session is paused on ACC OFF.
3. Confirm only the same token/package is restored on ACC ON.
4. Test session disappearance/recreation, pause confirmation, already-playing state, stopped/error state, and destruction.
5. Confirm Bluetooth MediaSessions remain excluded from this controller.

### Priority 5: validate Events and Voltage

1. Confirm system_server-to-app broadcasts arrive and are persisted.
2. Confirm Events disabled state discards new entries without deleting history.
3. Confirm the 300-entry bound and newest-first display.
4. Verify Voltage packet decoding against real MCU packets.
5. Verify startup settings delivery, enable/disable, positive/negative limits, Reset, and low-voltage protection ordering.

### Priority 6: define Protection and Autorun requirements

Do not implement speculative behavior. First define each feature's exact target system API, expected events, state model, user configuration, failure behavior, and hardware test.

## 17. Key architectural decisions to preserve

- One APK, one Gradle application module.
- Feature packages, not separate installable plugins.
- LSPosed system hooks are explicit and package-guarded.
- Accessory owns ACC observation and playback decisions; Events only records what Accessory reports.
- SourceInfo is selected-source state, not player playback state.
- MediaSession is for Android player sessions, not Bluetooth restoration.
- Bluetooth is handled through the vendor Bluetooth music service and its Binder callbacks/state.
- A2DP connected, AVRCP connected, and A2DP playing are distinct states.
- A Bluetooth PLAY command requires a valid pre-ACC Bluetooth playback snapshot and appropriate readiness; it must never be sent merely because ACC became ON.
- Third-party players are identified by their actual MediaSession token/package, not a fixed app name.
- No polling or timer-based restoration. Use system/Binder/session callbacks.
- Events is a bounded explicit event journal, not Logcat.
- Voltage offset is applied to the exposed MCU service value after original packet handling; it does not change physical voltage.
- Documentation, source implementation, CI evidence, and target-device verification must be reported as separate evidence categories.

---

## 18. Snapshot commit history relevant to current status

The recent history on `development` records the progression of the Accessory/SourceInfo work:

| Commit | Change |
|---|---|
| `8096b7ae` | Initial Bluetooth playback restoration implementation |
| `aedb7482` | AVRCP PLAY command definition |
| `b6950aaa` | Clarified Bluetooth restore skip reason |
| `1530af07` | Query Bluetooth playback state on ACC OFF |
| `2663d814` | MediaSession/Bluetooth restoration ownership change |
| `63974673` | Document firmware SourceInfo analysis |
| `bbeb6f32` | Separate SourceInfo and playback state responsibilities |
| `ef56c5b6` | Read SourceInfo package on ACC OFF |
| `88f3ccaf` | Gate Bluetooth restore by selected source |
| `ac9f5327` | Document source-gated Bluetooth snapshot |
| `857fe9db` | Add standalone SourceChecker documentation example |

Full commit history and diffs are available from the repository. The table is a concise milestone list, not a substitute for inspecting the exact commit diff.

---

**End of snapshot report.** Update this document when implementation or verification status changes; do not rewrite pending items as completed without new evidence.
