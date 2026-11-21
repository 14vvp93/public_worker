# MT8163 Tools Architecture

## 1. Purpose

MT8163 Tools is one Android application for MT8163-based head units. It produces one APK and contains independent feature packages. Features are modular at the code and behavior level, not separate Gradle projects or installable plugins.

## 2. Platform and technology

- Android 9 / API 28; minSdk 28 and targetSdk 28.
- Kotlin, Android XML Views, Fragments, and View Binding.
- One Gradle application module: `:app`.
- JDK 17 and Android SDK Platform 37.
- Target display: 1280×720 at 160 dpi.
- Dark Graphite + Neon Red visual theme.
- LSPosed hooks use the Legacy Xposed-compatible API as a compile-only dependency.
- Jetpack Compose and WebView are not used.

## 3. Repository structure

All app-owned source and resources are inside `app/`:

```text
app/
├── build.gradle.kts
├── proguard-rules.pro
└── src/main/
    ├── AndroidManifest.xml
    ├── assets/
    │   └── xposed_init
    ├── java/com/mt8163tools/
    │   ├── app/
    │   │   ├── MainActivity.kt
    │   │   ├── ModuleRegistry.kt
    │   │   ├── ModuleStateManager.kt
    │   │   └── ModuleContentHost.kt
    │   ├── core/
    │   │   ├── common/
    │   │   │   ├── EventsLog.kt
    │   │   │   └── VoltageConfigEvent.kt
    │   │   └── module/
    │   ├── modules/
    │   │   ├── accessory/
    │   │   ├── protection/
    │   │   ├── voltage/
    │   │   ├── autorun/
    │   │   └── events/
    │   └── hooks/
    │       ├── XposedEntry.kt
    │       └── VoltageHook.kt
    └── res/
        ├── drawable/
        ├── layout/
        └── values/
```

### Package ownership

| Package | Responsibility |
|---|---|
| `app` | Activity, navigation, feature registry, private persisted feature states, and APK entry point |
| `core/module` | Shared `Module` contract |
| `core/common` | Small shared contracts/utilities, including the Events log and Voltage configuration events |
| `modules/*` | Independent feature implementation, Fragment, and feature-specific resources |
| `hooks` | LSPosed package-load entry point and feature-specific system hooks |

The Gradle project root contains only the application module. Do not create `core`, `modules`, `hooks`, or `integration` Gradle subprojects.

## 4. Feature modularity

Each feature is a normal Kotlin package under `modules/`. A feature owns its implementation and UI resources and must not depend on another feature.

The shared `Module` contract defines:
- Stable unique ID.
- Display title and icon resource.
- Display order.
- Default enabled state.
- Fragment factory.

`ModuleRegistry` is the single static list of features. `MainActivity` builds the navigation and opens the selected Fragment directly through `FragmentManager`. Do not add a navigation wrapper for a single Fragment transaction.

### Adding a feature

1. Create `modules/<feature>/` under `app/src/main/java/com/mt8163tools/modules/`.
2. Add its Kotlin implementation and XML resources under the existing app source/resource roots.
3. Implement the shared `Module` contract.
4. Register it in `ModuleRegistry`.
5. Set a stable ID, display order, and default enabled state.
6. Update the documentation.

No Gradle settings, project includes, library modules, dynamic loading, service locator, dependency injection, or plugin framework are required.

## 5. Shared code and resources

- Keep only genuinely shared contracts and utilities in `core/`.
- Shared colors, dimensions, styles, and reusable resources live in the app's `res/` tree.
- Feature-specific layouts and drawables use unique resource names and remain in the same app resource tree.
- Do not create extra layers such as `data`, `domain`, repositories, or use cases without a concrete requirement.
- Keep dependencies explicit in the single `app/build.gradle.kts`.

## 6. Navigation and feature state

- `ModuleRegistry` owns the available feature definitions.
- `MainActivity` owns navigation and renders the feature list.
- `ModuleStateManager` owns independent persistent enabled states in the private `module_states` SharedPreferences file. It has no Voltage-specific behavior or cross-process access.
- Defaults apply only when a key is absent. Existing user choices must survive restarts and app updates.
- All features are disabled on a fresh installation except Events.

| Feature | Default |
|---|---|
| Accessory | Disabled |
| Protection | Disabled |
| Voltage | Disabled |
| Autorun | Disabled |
| Events | Enabled |

A disabled feature remains viewable, but its content cannot receive touch, key, or accessibility input. The shared content host blocks descendant focus and accessibility traversal and displays the inactive overlay. The feature switch remains outside the blocked content and available to re-enable the feature.

Feature implementations must also check their enabled state before performing operations. UI input blocking alone is not an operational safety boundary.

## 7. Centralized Events log

Every feature records meaningful state changes and completed actions through `core/common/EventsLog.record()`. The event entry contains a timestamp, stable module ID, event type, and short human-readable message. The Events feature reads and displays this shared log; features do not write to the Events UI or depend on the Events Fragment.

- Event records are stored in the app-private `events_log` SharedPreferences file.
- The log is bounded to the newest 300 records; newest entries appear first.
- A package-scoped in-app broadcast refreshes the visible Events screen when a record is added.
- Hook-originated ACC and MediaSession events cross the process boundary through the package-scoped `ACCESSORY_STATE_CHANGED` broadcast and the manifest-registered `AccessoryStateReceiver`; the receiver initializes the Events gate from persisted `module_states` before recording.
- Accessory playback command requests, confirmed PAUSED/PLAYING transitions, pending restoration, cancellation, and command errors are recorded under the Accessory module ID. A request is not reported as a completed playback transition until the matching MediaController callback confirms it.
- No background service, thread, timer, or polling is used.
- Event messages must not contain passwords, tokens, personal data, or unnecessary raw payloads.

### Events enable/disable contract

The Events module switch controls collection itself, not only the screen:

- On app startup, the in-memory collection gate is initialized from the persisted Events module state.
- When Events is disabled, `EventsLog.record()` returns without writing, broadcasting, or buffering anything.
- Events generated while collection is disabled are permanently omitted; there is no queue or replay.
- Enabling Events resumes collection for subsequent events.
- Existing stored records remain available when collection is disabled; disabling does not clear the history.
- The event gate is checked before and inside the synchronized write section to prevent a concurrent disable from allowing a pending write.
- The Events module's own enable action is recorded only after collection is enabled. Its disable action is intentionally not recorded because collection has already stopped.

Current event sources include module enable/disable actions and Voltage offset changes. Each feature must call the shared API for its own meaningful events as its behavior is implemented.

**Process boundary:** `EventsLog` runs only in the MT8163 Tools application process and writes app-private storage. LSPosed hooks execute in `system_server` and must not call this logger directly. Accessory is the sole ACC observer: after receiving an ACC broadcast or successfully synchronizing the current state, it sends a package-scoped `ACCESSORY_STATE_CHANGED` broadcast to the app. The manifest-registered `AccessoryStateReceiver` initializes the Events collection gate from the persisted `module_states/module_events` value, then records the event through `EventsLog`. Events does not install an ACC hook or independently read ACC. The hook callback performs no disk I/O, blocking wait, or scheduling; the broadcast is the explicit process boundary. The receiver is exported because the sender is the separate `android` system process; it accepts only the app-specific action and required state extra. High-frequency getter invocations must not be logged.

## 8. LSPosed hooks

All LSPosed code is in the `com.mt8163tools.hooks` package. It is not a separate Gradle module.

- `XposedEntry.kt` implements the LSPosed package-load callback, filters the `android` package (system_server), and installs the feature hooks directly.
- `VoltageHook.kt` owns the Voltage hook and its runtime configuration.
- `AccessoryHook.kt` owns ACC observation and publishes the observed state to the app process.
- `AccessoryPlaybackController.kt` pauses and restores the exact MediaSession associated with the ACC transition.
- `app/src/main/assets/xposed_init` contains the fully qualified entry point class.
- The main app manifest contains the Xposed module metadata and recommended scope.
- Xposed API is declared as `compileOnly`; it must not be packaged into the APK.

Do not add a generic hook registry, hook interface, dependency-injection layer, dynamic plugin mechanism, or placeholder hook. When a feature genuinely needs an LSPosed hook, add its implementation and one explicit package-guarded call in `XposedEntry.kt`. A UI feature does not automatically require a system hook.

Voltage intentionally hooks the shared `McuManager.getBattery()F` getter in SystemUI. The corrected value is visible to every caller in that process. The hook remains installed while Voltage is disabled; when disabled, it returns the original value unchanged.

### Accessory ACC hook

- Process/package: `android` (`system_server`).
- Service constructor: `android.carsource.McuService(Context)`; the hook retains the service instance and application context.
- ACC receiver: `android.carsource.McuService$1.onReceive(Context, Intent)`.
- ACC broadcast action: `android.mcu.device.action.ACC`.
- State extra: `AccStatus` (`boolean`).
- Current-state getter: `android.carsource.McuService.AccStatus()`; the decompiled implementation delegates to `CarStatusService.getAccStatus() == 1` and returns a boolean.
- On ACC broadcasts, the hook reads the broadcast extra after the original receiver handles the event. When Accessory is enabled, it publishes the resulting state to the app process.
- When Accessory is enabled after startup, the hook calls `McuService.AccStatus()` to synchronize the current hardware state; it does not wait for another ACC transition.
- The app-side `AccessoryStateReceiver` forwards ACC transitions, synchronization results, and playback-controller events to `EventsLog`. Playback PAUSED/PLAYING records are emitted only after MediaController confirms the state; command requests and failures use separate event types. Events does not install a second ACC hook or read the hardware state independently.

The method and receiver signatures are based on firmware decompilation. LSPosed class resolution, method invocation, and live ACC behavior still require device runtime verification.

### Accessory MediaSession behavior

- The controller uses the system `MediaSessionManager` from `system_server`; no player package is hardcoded.
- On ACC OFF, it examines the active sessions and selects the first session whose `PlaybackState` is `STATE_PLAYING`. It stores that session's `MediaSession.Token` and package name, registers a playback callback, and sends PAUSE only to that controller.
- If no session is playing, no restoration request is created.
- On ACC ON, it immediately searches for the exact saved token and package. It sends PLAY only when that same session is available and advertises `ACTION_PLAY` or `ACTION_PLAY_PAUSE`.
- If the session is not currently active or does not yet advertise a play action, the saved request remains pending. `OnActiveSessionsChangedListener` and the saved controller's `MediaController.Callback` retry restoration on session/playback callbacks; there is no polling or timed retry.
- A different session from the same package is never substituted. A session already playing is not sent another PLAY command. If the saved session reports `STATE_STOPPED` or `STATE_ERROR`, or the session is destroyed, the restoration request is discarded.
- Disabling Accessory unregisters the active-session listener, unregisters the controller callback, and clears any pending restoration.
- The callback handler is used only to receive framework callbacks on the system main looper; it does not run a worker, timer, or polling loop.
- The controller sends playback lifecycle events through the same package-scoped process bridge used for ACC state. The app-side receiver records them with the Accessory module ID; the hook never writes app-private preferences directly.
- PAUSED and PLAYING are logged only after the saved controller reports those states through `MediaController.Callback`. Command requests, pending restoration, cancellation, and exceptions have distinct event types.
- Bluetooth/AVRCP reconnection is a separate process and is not part of this MediaSession controller.

MediaSession permission availability, callback delivery, and transport-control behavior under this firmware's `system_server` require runtime verification on the head unit.

## 9. Event-driven execution — mandatory

The application, all features, and LSPosed hooks are strictly event-driven.

**Forbidden:**
- Background/worker threads, executors, scheduled executors, and thread pools.
- Handler polling loops, timers, recurring callbacks, polling, and periodic refresh.
- WorkManager, always-running services, or tasks that monitor state while idle.
- Continuous preference, file, or network checks.

Work may run only in response to a discrete event:
- Explicit user interaction.
- Android lifecycle or system callback.
- Incoming event or broadcast.
- Invocation of an LSPosed hooked method.

An event handler performs only the work required for that event and returns. It must not schedule recurring or deferred work.

### Hook hot path

A hook callback may read only already-available in-memory configuration. It must not perform disk I/O, IPC, blocking waits, or schedule work.

### Cross-process configuration

The app stores module states and UI settings privately. A separate minimal `voltage_hook_config` preference file exposes only `module_enabled` and `voltage_offset` to LSPosed (Android 9 `MODE_WORLD_READABLE`, with a private fallback). The app updates this file and sends explicit broadcasts when Voltage settings or enablement change, and sends the complete configuration on app startup. After SystemUI's Application completes `onCreate()`, the hook registers a dynamic receiver by hooking `Instrumentation.callApplicationOnCreate(Application)`. The receiver validates each event and updates an atomic in-memory configuration. The hook reads only `voltage_hook_config` initially; it never reads `module_states` or `voltage_settings`. The getter hook reads only that in-memory state.

The hook stays installed while the feature is disabled to support live enable/disable without restarting SystemUI. Disabled behavior must preserve the original getter result.

## 10. Android and device constraints

- Target Android 9 / API 28.
- Target hardware: MediaTek MT8163, ARM32.
- Target display: 1280×720 at 160 dpi.
- Use XML Views and Fragments.
- Keep the APK single and self-contained.
- Do not use Compose, WebView, Google Play dependencies, or separate feature APKs.
- Preserve LSPosed metadata, entry-point asset, and compile-only API dependency.

## 11. Documentation and verification

Documentation is part of implementation. Update affected architecture, feature, and hook documentation in the same change.

Record:
- Component ownership and event flow.
- Preference names, keys, defaults, and validation.
- Hook target package, class, method descriptor, class loader, and side effects.
- Enable/disable behavior and failure handling.
- Dependencies, permissions, and device assumptions.
- Actual build/test results and unresolved device checks.

Distinguish repository inspection, build verification, and device runtime verification. A successful build does not prove LSPosed recognition or runtime hook behavior. Never claim a build or device test that was not run.
