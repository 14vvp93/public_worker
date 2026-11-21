# Accessory Module — Technical Analysis

**Status:** Initial ACC hook implementation added; runtime verification pending  
**Last updated:** 2026-11-20  
**Target:** Android 9 / API 28, MediaTek MT8163, ARM32

## 1. Purpose

The Accessory module coordinates Bluetooth audio state, the active media source, and playback behavior around vehicle ACC power events.

The module must work with any compatible Android media player. A media player is an interchangeable playback source, not a fixed application dependency. Bluetooth remains an independent system-level connection and audio transport.

The implementation must not identify or control a player by a hard-coded package name.

## 2. Architectural boundaries

The module observes and combines three independent state domains:

| Domain | Responsibility | Primary source |
|---|---|---|
| Bluetooth | Bluetooth connection and A2DP audio state | System Bluetooth service |
| Media source | Which source the head unit currently selects | `android.sourceservice.SourceService` |
| Playback | Whether the selected media session is actually playing | Android `MediaSession` / `MediaController` |

These domains must not be treated as interchangeable:

- A Bluetooth connection does not prove that media is playing.
- A selected source does not prove that its player is in the playing state.
- Audio Focus alone does not reliably identify the currently playing player.
- A media player's package name identifies the application owning a session; it must not be used as a fixed allowlist.

## 3. Bluetooth service findings

The system image contains the Bluetooth service package:

- `com.hcn.bluetoothservice`
- `BluetoothMusicService`
- `IBluetoothMusicService`
- Bluetooth A2DP / AVRCP callbacks
- Media metadata and playback status

The inspected service exposes an A2DP playing-state check through `isA2dpPlaying()`. This represents Bluetooth A2DP state and is not a player-specific playback API.

AVRCP command handling includes PLAY and PAUSE. These commands belong to the Bluetooth control path and must not be confused with direct control of a specific Android media session.

Reference artifacts: [BluetoothService analysis](https://github.com/14vvp93/public_worker/tree/main/artifacts/BluetoothService).

### 3.1 Verified Bluetooth music AIDL contract

The decompiled `IBluetoothMusicService` interface exposes separate A2DP and AVRCP state:

- `isA2dpConnected()`
- `isAvrcpConnected()`
- `isA2dpPlaying()`
- `registerBTMusicCallback(IMusicCallback)`
- `unregisterBTMusicCallback(IMusicCallback)`
- `send_Avrcp_Cmd(int)`

The callback interface `IMusicCallback` exposes event-driven notifications:

- `onA2dpConnectStateChanged(int)`
- `onAvrcpConnectStateChanged(int)`
- `onMetadataChanged(String, String, String)`
- `onPlayStatusChanged(MusicPlayState)`

The inspected client wrapper defines AVRCP command values: PLAY = `0`, PAUSE = `1`, PLAY_PAUSE = `2`, STOP = `3`, NEXT = `4`, PREVIOUS = `5`.

This confirms that the vendor service has an explicit AVRCP-connected state and callback; ACL/A2DP connection must not be used as a substitute for AVRCP readiness. The service wrapper binds to an explicit component and registers the callback after binding. Whether MT8163 Tools can safely bind to this service from its LSPosed process, and the actual installed service package/component and access permissions, remain unverified on the target device.

**Artifact consistency warning:** the inspected `LocalBTMusicManager` binds to `com.hcn.bluetoothservice/com.hcn.bluetooth.service.BluetoothMusicService`, while the separately inspected APK manifest reports package `com.autochips.bluetooth`. Do not hard-code either package/component until the installed device package and service declaration are confirmed.

Reference source files:
- [IBluetoothMusicService](https://github.com/14vvp93/public_worker/blob/main/artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/service/IBluetoothMusicService.java)
- [IMusicCallback](https://github.com/14vvp93/public_worker/blob/main/artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/service/IMusicCallback.java)
- [LocalBTMusicManager](https://github.com/14vvp93/public_worker/blob/main/artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/api/LocalBTMusicManager.java)


## 4. SourceService findings

The system framework contains:

`android.sourceservice.SourceService`

The inspected implementation includes the following relevant members:

| Member | Observed responsibility |
|---|---|
| `muxMediaPlayer(String, int)` | Source switching, including CurrentSource and source policy handling |
| `onActivityStatus(String, String, int, String)` | Activity/source status updates |
| `onFocusStateChange(String, int, int, int, int)` | Audio Focus state changes |

The service also references state such as:

- `muxMediaPlayer`
- `CurrentSource`
- `SourcePolicy`
- `mAudioFocusPackage`
- `mBtMusicSession`

These findings make SourceService a useful source-selection signal. They do not establish that every source transition corresponds to active playback.

**Hook implementation is not yet verified.** Before adding a hook, confirm the exact runtime class, method descriptors, class loader, access level, and callback behavior against the target device's framework. The signatures above are the observed decompiled declarations, not a guarantee that the same overloads are available at runtime.

Reference artifacts: [Framework analysis](https://github.com/14vvp93/public_worker/tree/main/artifacts/framework). The framework report was updated in the auxiliary repository in commit `bf2fcfea7cb0c74537b9e19a9884aa9a57b7d70f`.

## 5. Source transition observation

Logcat showed source transitions containing labels similar to:

```text
[A2DP, BT_UI, BT_CONNTECTED]
[MUSIC, BT_CONNTECTED]
```

The observed `BT_CONNTECTED` spelling is preserved from the log output; it may be a vendor-side spelling rather than a public API constant.

This observation indicates that the head unit distinguishes a Bluetooth UI/source state from a MUSIC source state while retaining a Bluetooth connection state. It is evidence of separate source and connection dimensions, not proof of the exact internal meaning of every label.

The module should record source transitions as events and correlate them with Bluetooth and MediaSession callbacks.

## 6. ACC event flow

The inspected ACC handling path includes:

```text
McuService$1.onReceive
        |
        v
    AccStatus
        |
        v
 Handler (what = 5)
        |
        v
 Accessory module
```

The Bluetooth media session may disappear when ACC turns OFF and become available again after ACC turns ON. The implementation must therefore tolerate session destruction and recreation.

The ACC event must be intercepted in `system_server` by hooking the existing `android.carsource.McuService$1.onReceive` path. Use only the broadcast action `android.mcu.device.action.ACC` and its `AccStatus` extra. Maintain a private in-memory `accState` for the hook's internal state. When the module becomes enabled, immediately synchronize this value by calling the existing `McuService.AccStatus()` method; it reads `CarStatusService.getAccStatus() == 1`, where `1` means ACC ON. Do not wait for another broadcast to discover the current state.

Do not add an ACC receiver to `AccessoryFragment`. The fragment is UI only and must not own ACC state, playback state, MediaSession tracking, or playback commands. The former fragment-level receiver has been removed.

Do not use `AccessoryApi`, `CarStatusService`, polling, or timers. The ACC event is delivered by the existing system receiver; hook that path rather than registering a second application receiver.

## 7. MediaSession-based playback tracking

Use Android media-session APIs to determine actual playback state and control the owning player:

- Obtain active sessions through the supported session access available to the hook/process.
- Identify each session by its owning package and session/controller identity.
- Read `PlaybackState` and treat `STATE_PLAYING` as the playing condition.
- Retain the identity of the session that was playing before ACC OFF.
- Use that session's `MediaController.TransportControls` for targeted PLAY or PAUSE commands.

Do not infer playback from Audio Focus, source selection, metadata presence, or A2DP connection alone.

The exact mechanism for obtaining active sessions in the target process still requires implementation-level verification. The inspected framework includes `MediaSessionService.getActiveSessionsLocked` and `MediaSessionRecord`; these are framework internals, not stable public application APIs. Prefer the supported controller/session path available in the actual hook context and avoid depending on private internals unless target-device verification proves it necessary.

## 8. ACC behavior

### ACC OFF

1. Receive the ACC OFF event.
2. Inspect the currently available media sessions.
3. Select the session that is actually in `STATE_PLAYING`.
4. Retain its package/session identity and the pre-ACC playback state in memory.
5. Send PAUSE only to the session that was playing.

If no session is playing, do not issue PAUSE and do not create a future playback request.

### ACC ON

The media-player and Bluetooth restoration paths are independent and must start from the same ACC ON event.

**Media-player path:**

1. Receive the ACC ON event.
2. Immediately check whether the previously identified player's MediaSession is already available and controllable.
3. If available, match it against the retained player/session identity and restore playback only if the pre-ACC state was `STATE_PLAYING`.
4. If it is not yet available, wait for the MediaSession availability/change callback and perform the same identity check when that event arrives.
5. Clear the pending restoration state after it is handled or invalidated.

Do not wait for Bluetooth connection or AVRCP readiness before attempting MediaSession restoration. If the previous session does not return, do not start an unrelated player. A new player must not inherit another player's restoration request.

**Bluetooth path:**

1. Independently observe Bluetooth connection state after ACC ON.
2. Wait for the relevant Bluetooth control channel (AVRCP) to become ready; an ACL connection alone must not be treated as proof that AVRCP is ready.
3. Only then consider sending an AVRCP PLAY command, and only if target-device testing proves that the head unit requires this command for its Bluetooth source behavior.
4. Do not send AVRCP PLAY concurrently with MediaSession PLAY for the same restoration event.

The Bluetooth PLAY step is currently a conditional design option, not confirmed required behavior. Do not issue it merely because Bluetooth connected.

**Important:** session recreation, Bluetooth/AVRCP readiness callbacks, and callback ordering must be verified on the target head unit. Do not add timers, polling, retry loops, or recurring callbacks. Each path may proceed only when its relevant system callback or current state check makes the operation valid.

## 9. Event-driven and failure-handling requirements

The module must follow the project-wide event-driven execution rules:

- No worker threads, executors, thread pools, timers, polling, or recurring callbacks.
- React only to ACC broadcasts, Bluetooth callbacks, SourceService hook callbacks, MediaSession callbacks, and explicit user actions.
- Keep hook callbacks short and non-blocking.
- Do not perform disk I/O or blocking IPC in a hooked-method hot path.
- Treat missing services, null controllers, destroyed sessions, unsupported commands, and stale session identities as normal failure cases.
- Do not send PLAY or PAUSE when the target session cannot be identified confidently.
- Keep Bluetooth ACL connection, AVRCP control readiness, media-source selection, and player/session state as separate dimensions.
- Start MediaSession restoration independently of Bluetooth connection; do not gate player PLAY on Bluetooth readiness.
- Gate any optional AVRCP PLAY command on confirmed AVRCP readiness and target-device evidence that the command is required.
- Never issue MediaSession PLAY and AVRCP PLAY concurrently for the same ACC restoration event.

The module must honor its persisted enabled state. When disabled, it must not issue playback commands or perform module-specific actions.

## 10. Current implementation status

| Item | Status |
|---|---|
| BluetoothService structure and behavior inspected | Research complete |
| A2DP / AVRCP behavior inspected | Research complete |
| Vendor Bluetooth AIDL observer | Integrated in `AccessoryHook` through `VendorBluetoothMusicClient`; target bind/callback verification pending |
| SourceService decompiled and relevant members identified | Research complete |
| Source transition observed in Logcat | Observed |
| ACC broadcast path identified | Research complete |
| Existing AccessoryFragment ACC receiver | Removed; Fragment is UI-only |
| ACC handling | Hook added; runtime verification pending |\n| Current ACC state synchronization on module enable | Implemented via `McuService.AccStatus()`; runtime verification pending |
| MediaSession control approach | Implemented in `AccessoryPlaybackController`; target runtime verification pending |
| AccessoryHook | Implemented; runtime verification pending |
| SourceService runtime hook verification | Pending |
| Active MediaSession tracking | Implemented with `MediaSessionManager` callbacks; target runtime verification pending |
| ACC pause/restore controller | Implemented with exact session-token matching; target runtime verification pending |
| Multi-player device testing | Not performed |

`AccessoryFragment` is UI-only. `AccessoryHook` is registered in `system_server`, attaches to the `McuService` instance, and synchronizes the current ACC state through `McuService.AccStatus()` when the module is enabled. Subsequent ACC transitions are observed through the existing `McuService$1.onReceive` callback. `AccessoryPlaybackController` tracks active MediaSessions, pauses only the session that was playing, and restores only the exact saved session token. It waits for the PAUSED callback before issuing PLAY, preventing an ACC ON event from racing an in-flight PAUSE request. `VendorBluetoothMusicClient` is independently enabled with the module and observes vendor A2DP/AVRCP state through Binder callbacks; its events are forwarded to the app-side Events receiver. It does not issue AVRCP commands or call `requestA2dp()`. Build verification and all target-device binding, callback, and playback behavior remain pending.

## 11. Implementation plan

1. Verify the SourceService runtime class and exact hook descriptors on the target device.
2. Verify the installed Bluetooth service package/component, binding permissions, and availability of the inspected AIDL interface from the intended LSPosed process.
3. Hook the existing `android.carsource.McuService$1.onReceive` in system_server and verify the action plus `AccStatus` extra at runtime. (Code added; device verification pending.)
4. Keep `AccessoryFragment` UI-only and publish the persisted module-enabled state through `accessory_hook_config` and a package-scoped broadcast. (Code added.)
5. Implement callback-driven MediaSession state tracking and targeted pause/restore, independent of Bluetooth readiness.
6. Verify Bluetooth callbacks and implement optional AVRCP PLAY only if target-device evidence requires it.
7. Add cross-process diagnostics only if required; do not write app storage from the system_server hook process.
8. Verify behavior with at least two compatible media players, including ACC OFF/ON, Bluetooth reconnect, player switching, and session destruction.
9. Record build verification and real-device runtime verification separately.

## 12. Related project constraints

- One APK and one Gradle application module.
- Kotlin and Android XML Views.
- Android 9 / API 28, MT8163 ARM32, 1280×720 at 160 dpi.
- LSPosed hooks live in the existing `com.mt8163tools.hooks` package.
- The Accessory feature is disabled by default and must honor the shared module-enabled state.
- Keep the implementation minimal and event-driven; do not introduce a service layer, polling mechanism, or generic hook framework without a demonstrated requirement.


## 13. SourceInfo and state preservation

**Project clarification (2026-11-20):** the vendor `android.sourceservice.SourceInfo` API is used for preserving/restoring the selected source state. This is a separate responsibility from tracking whether a player was actively playing before ACC OFF.

Relevant SourceInfo methods:

| Method | Role |
|---|---|
| `getSourcePackage()` | Read the reported active source package. |
| `getMusicInfo()` | Retrieve vendor music information. Exact playback-state semantics remain under analysis. |
| `onRequestPlayAudio(String)` | Request audio/source activation for a package. |
| `onRequestPlayAudio(Context)` | Request audio/source activation for a context package using the vendor `/background` convention. |
| `onRequestPlayAudio(ComponentName)` | Request audio/source activation for a component. |
| `setOnEventListener(...)` | Receive SourceInfo events, including source-change notifications. |

### 13.1 Do not merge the state domains

- **Source selection state:** the source/package selected by the head unit. SourceInfo is the vendor API for source-state preservation and source activation requests.
- **Player playback state:** whether a particular Android media player is playing, paused, or stopped. This is a different state from source selection.
- **Bluetooth state:** A2DP/AVRCP connection, readiness, and playback state. This belongs to the vendor Bluetooth service path.

A source can remain selected while playback is paused or stopped. A MediaSession can identify a player session but must not be used as a replacement for the vendor SourceInfo source-state mechanism. Bluetooth playback must not be controlled through MediaSession.

### 13.2 ACC integration constraints

Keep the responsibilities separate:

1. Use the SourceInfo contract for source-state preservation/restoration.
2. Use the vendor Bluetooth music service to obtain and control Bluetooth playback state.
3. Use MediaSession only for non-Bluetooth Android media-player playback where that path is applicable.
4. Do not send AVRCP PLAY merely because a source package identifies Bluetooth; confirm the Bluetooth service's current state and the target-device behavior.
5. Do not call `onRequestPlayAudio(...)` as though it were a read-only state getter. It is a request to the vendor source service and may change routing/source state.
6. Do not assume that `getMusicInfo()` is equivalent to MediaSession `PlaybackState`; inspect its fields and update semantics first.
7. Do not assume `getSourcePackage()` is always a fresh snapshot until all property writers and callback behavior are verified.

The exact sequence of SourceInfo calls at ACC OFF/ON is not yet specified here. It must follow the verified vendor contract and target-device tests; this documentation does not claim that SourceInfo integration has already been implemented.

### 13.3 Related analysis

See [Firmware SourceInfo Analysis](../../SOURCE_INFO_FIRMWARE_ANALYSIS.md) for Binder registration, client/server methods, the `sys.sourcing.package` implementation, callback dispatch, known consumers, and unresolved reverse-engineering work.


## 14. Runtime SourceInfo check at ACC OFF

The current hook implementation reads the vendor SourceInfo package when processing ACC OFF:

- Resolve `android.sourceservice.SourceInfo` from the boot class loader.
- Call `SourceInfo.getInstance().getSourcePackage()`.
- Emit a `source_snapshot` event with the package and the Bluetooth-source classification.
- Pass the package to `VendorBluetoothMusicClient`.

The Bluetooth client queries `IBluetoothMusicService.isA2dpPlaying()` and arms Bluetooth restoration only when the source package exactly equals:

```text
com.autochips.bluetooth
```

For a different, empty, or unavailable source package, the Bluetooth snapshot is skipped and Bluetooth restoration remains disabled for that ACC cycle. This is fail-closed behavior: an unknown source must not cause Bluetooth PLAY.

The SourceInfo read is independent of MediaSession. The MediaSession controller continues to handle non-Bluetooth Android players by exact session identity. The SourceInfo check does not issue `onRequestPlayAudio(...)` and does not change source routing.

**Status:** code integrated in `AccessoryHook.kt` and `VendorBluetoothMusicClient.kt`; Kotlin compilation and target-device runtime verification are pending.


## 15. Implemented SourceInfo check

**Code commits:**
- `ef56c5b6e872a504bdc0b677b407137eb57c5d4e` — ACC OFF SourceInfo read in `AccessoryHook.kt`.
- `88f3ccaf61bf5a9da5b97584c7be3cd5e9e1d410` — Bluetooth source gate in `VendorBluetoothMusicClient.kt`.

At the ACC OFF transition, `AccessoryHook` calls:

```kotlin
SourceInfo.getInstance().getSourcePackage()
```

The implementation uses Xposed reflection to avoid a compile-time dependency on the vendor framework class. It trims the returned package and treats null, empty results, missing class/service, and reflection failures as unknown. Failures are logged through Xposed.

The source package is compared with the vendor-observed Bluetooth source package:

```text
com.autochips.bluetooth
```

The package and classification are published as the `source_snapshot` event.

### 15.1 Bluetooth ACC OFF gate

`VendorBluetoothMusicClient.onAccStateChanged(false, ...)` receives the captured source package.

- Exact Bluetooth package match: query `IBluetoothMusicService.isA2dpPlaying()` at ACC OFF.
- Any other or unknown package: skip the Bluetooth playback query and set `wasPlayingBeforeAccOff = false`.
- Bluetooth service unavailable or query failure: report UNKNOWN and do not arm restoration.

The result is reported as `bluetooth_acc_off_snapshot`. This implementation is event-driven and takes one snapshot at ACC OFF; it does not poll SourceInfo or maintain a continuously updated playback cache.

### 15.2 Scope and non-effects

- The source check is read-only.
- It does not call `onRequestPlayAudio(...)`.
- It does not switch the selected source or issue AVRCP commands.
- It does not use MediaSession to identify the Bluetooth source.
- The existing MediaSession controller remains a separate path for Android media players.

### 15.3 Verification and known discrepancy

Implementation is committed, but Kotlin compilation and target-device ACC OFF/ON behavior are not yet verified. The next device test must confirm the `source_snapshot` package and corresponding Bluetooth snapshot result for Bluetooth and at least one non-Bluetooth source.

**Known discrepancy:** the current Bluetooth ACC ON implementation still receives `mediaSessionOwnsRestore` and may skip Bluetooth restoration when it is true. This means the current code is not yet fully aligned with the requirement that Bluetooth restoration be independent of MediaSession. Track and fix this separately; do not mistake the ACC OFF SourceInfo gate for a completed end-to-end Bluetooth restoration implementation.

## 16. Standalone SourceChecker utility

This standalone Kotlin class reads only the currently selected source package through the vendor `SourceInfo` API and classifies whether it is the Bluetooth source. It is documentation-only and is not part of the application implementation.

```kotlin
package com.mt8163tools.media

import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

object SourceChecker {

    private const val TAG = "MT8163-SourceChecker"
    private const val SOURCE_INFO_CLASS = "android.sourceservice.SourceInfo"

    data class Result(
        val packageName: String?,
        val isBluetooth: Boolean
    )

    fun check(): Result {
        return try {
            val sourceInfoClass = XposedHelpers.findClass(
                SOURCE_INFO_CLASS,
                null
            )

            val sourceInfo = XposedHelpers.callStaticMethod(
                sourceInfoClass,
                "getInstance"
            ) ?: return Result(null, false)

            val packageName = (
                XposedHelpers.callMethod(
                    sourceInfo,
                    "getSourcePackage"
                ) as? String
            )?.trim()?.takeIf { it.isNotEmpty() }

            Result(
                packageName = packageName,
                isBluetooth = packageName == "com.autochips.bluetooth"
            )
        } catch (exception: Exception) {
            XposedBridge.log("$TAG: Source check failed: $exception")
            Result(null, false)
        }
    }
}
```

### Usage

```kotlin
val source = SourceChecker.check()

XposedBridge.log(
    "Current source=${source.packageName}, " +
        "Bluetooth=${source.isBluetooth}"
)
```

The result has three practical outcomes:

- `packageName` is `com.autochips.bluetooth`: Bluetooth source.
- `packageName` is another non-empty package: a different source.
- `packageName` is `null`: SourceInfo could not provide a package.

The class performs a read-only check. It does not request playback, change the selected source, query MediaSession, or send Bluetooth/AVRCP commands.

**Integration status:** standalone documentation example only; not added to the project source set and not build- or device-verified. The `null` class loader used by `findClass` must be validated in the actual Xposed hook context on the target firmware.
