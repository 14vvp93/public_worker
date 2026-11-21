# BluetoothMusicService — Binder Integration Analysis

**Project:** MT8163 Tools  
**Related module:** Accessory  
**Status:** Static APK/JADX analysis complete; target-device binding and callback verification pending  
**Analysis date:** 2026-11-20  
**Target platform:** Android 9 / API 28, MediaTek MT8163, ARM32

## 1. Scope and source artifact

This document records the service-side analysis of the vendor Bluetooth music service used by the Accessory module. It is based on the checked-in APK and JADX output in the auxiliary artifact repository:

- [BluetoothService APK and JADX artifacts](https://github.com/14vvp93/public_worker/tree/main/artifacts/BluetoothService)
- [Service-side analysis report](https://github.com/14vvp93/public_worker/blob/main/artifacts/BluetoothService/ANALYSIS.md)

JADX v1.5.7 processed 2,532 units and reported 68 decompiler errors. The critical manifest and service/AIDL/Parcelable files listed below were generated and inspected. The decompilation is partial; unrelated decompiler errors do not prove that these inspected classes are incorrect.

## 2. Service identity and manifest

The inspected APK declares:

| Property | Value |
|---|---|
| Package | `com.hcn.bluetoothservice` |
| Version name | `202601221437592` |
| Version code | `2` |
| minSdk | 28 |
| targetSdk | 31 |
| sharedUserId | `android.uid.system` |
| Application class | `com.hcn.bluetooth.MyApplication` |
| Persistent | `true` |

Service declaration:

- Class: `com.hcn.bluetooth.service.BluetoothMusicService`
- `android:exported="true"`
- Intent action: `com.hcn.bluetooth.service.BluetoothMusicService`
- No service-level `android:permission` is declared.

The decompiled `onBind()` returns a `BluetoothMusicBinder` implementing `IBluetoothMusicService.Stub`. The inspected method does not branch on the incoming Intent.

### Access-policy limitation

The manifest exposes the service and does not declare a service-level permission. The inspected `BluetoothMusicService.java` contains no calls to `Binder.getCallingUid/Pid`, calling-permission enforcement, or signature/package checks. This is evidence about the inspected class only; it does not prove that a third-party process can bind on the target firmware. The system shared UID, APK signing identity, SELinux policy, installed package state, and runtime service manager policy still require device verification.

**Package-name discrepancy to resolve:** earlier client-side research referenced a binding to `com.hcn.bluetoothservice/com.hcn.bluetooth.service.BluetoothMusicService`, while a separately inspected artifact/manifest observation reported `com.autochips.bluetooth`. The APK analyzed in this report declares `com.hcn.bluetoothservice`. Use the exact installed package and service declaration from the target MT8163 device as the final authority; do not silently substitute one package name for the other.

## 3. IBluetoothMusicService Binder contract

Interface descriptor:

```text
com.hcn.bluetooth.service.IBluetoothMusicService
```

The inspected AIDL-generated Stub/Proxy defines these transactions:

| Transaction | Method | Request | Reply |
|---:|---|---|---|
| 1 | `isA2dpConnected()` | none | boolean encoded as int |
| 2 | `isAvrcpConnected()` | none | boolean encoded as int |
| 3 | `isA2dpPlaying()` | none | boolean encoded as int |
| 4 | `getConnectDevice()` | none | nullable `BluetoothDeviceInfo` |
| 5 | `send_Avrcp_Cmd(int)` | int command | void |
| 6 | `requestA2dp()` | none | void |
| 7 | `releaseA2dp()` | none | void |
| 8 | `regMusicClientBinder(IBinder)` | strong Binder | void |
| 9 | `unRegMusicClientBinder()` | none | void |
| 10 | `registerBTMusicCallback(IMusicCallback)` | callback Binder | void |
| 11 | `unregisterBTMusicCallback(IMusicCallback)` | callback Binder | void |
| 12 | `getID3Info()` | none | String array of three values |

The interface token is `com.hcn.bluetooth.service.IBluetoothMusicService`. Transaction 4 uses a presence marker followed by `BluetoothDeviceInfo.writeToParcel()`. The generated Proxy recycles request and reply Parcels in finally blocks.

### 3.1 AVRCP command mapping

The service implementation maps integer commands as follows:

| Value | Action |
|---:|---|
| 0 | PLAY |
| 1 | PAUSE |
| 2 | PLAY/PAUSE toggle |
| 3 | STOP |
| 4 | NEXT |
| 5 | PREVIOUS |
| 6 | FAST FORWARD |
| 7 | REWIND |

The service requires a non-null active `MediaController` and A2DP state `2` before dispatching a command. NEXT/PREVIOUS and FAST FORWARD/REWIND are rate-limited by 400 ms Handler messages.

Do not issue PLAY simply because ACC becomes active, the service binds, or Bluetooth connects. Command dispatch must be governed by the Accessory playback arbitration rules in section 9.

### 3.2 Side effects and lifecycle-sensitive methods

- `requestA2dp()` first calls `mSourceInfo.onRequestPlayAudio("com.autochips.bluetooth/.BtMusicActivity")`, then schedules the A2DP request. This is an active source/routing operation, not a passive status query; do not call it automatically on bind.
- `releaseA2dp()` schedules release work through the main Handler.
- `unRegMusicClientBinder()` unlinks the registered client death recipient, stops playback if the cached state is PLAYING, and calls `releaseA2dp()`. It is **not** ordinary callback cleanup.
- `regMusicClientBinder(IBinder)` stores one client Binder and links a death recipient. Client death invokes `unRegMusicClientBinder()`.

The Accessory client should not register this separate client Binder unless a concrete requirement for the vendor-side death-recipient mechanism is established. Do not call transaction 9 during normal teardown.

## 4. IMusicCallback contract and delivery

Interface descriptor:

```text
com.hcn.bluetooth.service.IMusicCallback
```

The inspected callback Stub defines four transactions:

| Transaction | Callback | Payload | Binder delivery |
|---:|---|---|---|
| 1 | `onA2dpConnectStateChanged(int)` | raw A2DP profile state | one-way |
| 2 | `onAvrcpConnectStateChanged(int)` | raw AVRCP profile state | one-way |
| 3 | `onMetadataChanged(String, String, String)` | title, artist, album | one-way |
| 4 | `onPlayStatusChanged(MusicPlayState)` | nullable Parcelable | synchronous |

The service stores callback instances in `RemoteCallbackList<IMusicCallback>`.

### 4.1 Registration behavior

On callback registration, the service immediately sends:

1. `onMetadataChanged(title, artist, album)`
2. `onPlayStatusChanged(currentState)`

It does **not** immediately send A2DP or AVRCP connection-state callbacks. The client must query `isA2dpConnected()` and `isAvrcpConnected()` after registration to establish an initial connection snapshot. Later changes are delivered through callbacks.

The service dispatches callback events through its listener path using event selectors:

- 0 — metadata
- 1 — playback state
- 2 — AVRCP connection state
- 3 — A2DP connection state

A callback implementation must tolerate calls arriving on Binder threads, nullable/incomplete metadata, and unknown initial connection state. Copy incoming values into an immutable application snapshot and publish them through the Accessory module's established state/event boundary.

### 4.2 Correct unregister semantics

Use:

```kotlin
service.unregisterBTMusicCallback(callback)
```

to remove the exact callback instance from the remote callback list during normal teardown.

Do not use:

```kotlin
service.unRegMusicClientBinder()
```

as a synonym. That method operates on a separate client Binder registration and can stop playback and release A2DP.

## 5. Parcelable data structures

### 5.1 MusicPlayState

Parcel field order:

1. `state: int`
2. `position: long`
3. `duration: long`
4. `speed: float`
5. `updateTime: long`
6. `bufferedPosition: long`

The state values match Android `PlaybackState` constants:

| Value | State |
|---:|---|
| 0 | NONE |
| 1 | STOPPED |
| 2 | PAUSED |
| 3 | PLAYING |
| 4 | FAST_FORWARDING |
| 5 | REWINDING |
| 6 | BUFFERING |
| 7 | ERROR |
| 8 | CONNECTING |
| 9 | SKIPPING_TO_PREVIOUS |
| 10 | SKIPPING_TO_NEXT |
| 11 | SKIPPING_TO_QUEUE_ITEM |

Position, duration, and buffered position are millisecond values from the Android media-session model. `updateTime` is based on `SystemClock.elapsedRealtime()`.

### 5.2 BluetoothDeviceInfo

Parcel field order:

1. `mDeviceIndex: int`
2. `mDeviceStatus: int`
3. `mDeviceName: String`
4. `mDeviceAddr: String`

The class defines custom status constants: PAIRED=0, CONNECTED=1, CONNECTEDPHONE=2, CONNECTEDMEDIA=3, CONNECTING=4, DISCONNECTING=5, UNKNOWN=-1.

**Important caveat:** the constructor used by `getConnectDevice()` assigns `mDeviceStatus` directly from `BluetoothDevice.getBondState()`, not from the custom status constants. Android bond-state values (for example, BOND_BONDED=12) may therefore appear in this field. Do not interpret it as the custom enum without normalizing it. `mDeviceIndex` remains -1 in this constructor.

## 6. BluetoothMusicService implementation behavior

The inspected service implementation:

- Initializes a `RemoteCallbackList`, main-thread Handler, Bluetooth profile proxies for A2DP Sink (profile 11) and AVRCP Controller (profile 12), broadcast receivers, and `MediaSessionManager`.
- Tracks active media sessions whose tags contain `A2DP_MBS_TAG` or `A2DP_MBS_TAG_Q`.
- Registers a `MediaController.Callback` and reads playback state and metadata from the selected controller.
- Exposes metadata in title, artist, album order.
- Reports raw Bluetooth profile state integers; CONNECTED is `2`.
- Returns true from `isA2dpPlaying()` only when cached playback state is PLAYING (`3`).
- Returns the first connected A2DP Sink device from `getConnectDevice()`, or null.
- On A2DP disconnect, resets playback state, clears all three metadata strings, and notifies clients.
- May trigger `requestA2dp()` on A2DP connection when system property `sys.sourcing.package` equals `com.autochips.bluetooth`.

This is a vendor media-session bridge, not a generic Bluetooth adapter API. Playback commands are forwarded to the selected vendor-tagged `MediaController` via `TransportControls`.

## 7. Accessory integration design

The service-side contract is sufficiently identified to implement a small vendor Binder adapter, subject to target-device verification.

### 7.1 Connection sequence

1. When the Accessory module is enabled, bind explicitly to the verified installed service component. The inspected APK component is `com.hcn.bluetoothservice/com.hcn.bluetooth.service.BluetoothMusicService`; confirm it against the target device before relying on it.
2. Handle bind failure, null Binder, service disconnect, Binder death, and `RemoteException`.
3. Convert the Binder using `IBluetoothMusicService.Stub.asInterface()`.
4. Create and register exactly one `IMusicCallback.Stub` instance for this connection.
5. Query initial A2DP/AVRCP connection state after callback registration. Query playback/device/metadata only when required.
6. Normalize raw vendor values at the Accessory boundary and publish the resulting snapshot through the module's existing state/event mechanism.
7. On module disable or service disconnect, unregister the exact callback instance if the remote interface remains usable, unbind, and clear stale state. Cleanup must be idempotent.

### 7.2 State ownership and Events

Accessory owns the observation of this vendor service. Events must receive the state that Accessory actually observes and publishes; Events must not independently bind to this vendor service or re-derive Accessory's Bluetooth state through a different API.

Keep these state dimensions separate:

- A2DP connection
- AVRCP connection/readiness
- A2DP playback state
- active media source
- Android MediaSession playback state
- metadata/device information

A Bluetooth connection does not prove that a media player is playing. A2DP playback state must not be treated as a substitute for identifying which Android media session is active.

### 7.3 Threading and lifecycle

- Binder callbacks can arrive off the main thread.
- Copy Parcelable/string values into immutable app-owned models before publishing them.
- Keep UI updates on the main thread.
- Do not block Binder callback threads with disk I/O or unrelated IPC.
- Handle nullable callback payloads and stale/disconnected Binder references.
- Do not add polling for ongoing state; use callbacks and explicit initial queries.
- Do not create extra service layers or files unless the existing Accessory architecture requires them.

## 8. Relationship to generic Android MediaSession control

Keep generic Android MediaSession tracking as the player-independent source of truth for which media player is actually playing. The vendor Bluetooth Binder interface provides Bluetooth-side A2DP/AVRCP state and the head unit's vendor playback bridge.

Do not hard-code AIMP or any other player package. Any compatible media player may be active.

The two mechanisms serve different purposes:

- MediaSession identifies the active player and its real playback state.
- BluetoothMusicService reports vendor Bluetooth connection/playback state and accepts AVRCP commands.

Do not infer a particular player's state solely from vendor metadata or `isA2dpPlaying()`.

## 9. ACC playback arbitration requirements

The Accessory module must preserve the established ACC policy:

### ACC state

- When the module becomes enabled, synchronize the current ACC state immediately; do not wait for another broadcast.
- Accessory owns the ACC state it observes.
- Publish that observed state to Events through the existing module boundary.
- The Fragment is UI-only and must not own ACC state, MediaSession tracking, or playback commands.

### ACC OFF

1. Observe ACC OFF through the existing system hook path.
2. Inspect active media sessions and identify the session that is actually in `STATE_PLAYING`.
3. Retain the identity of that player/session and its pre-ACC playback state.
4. Pause only the session that was playing.
5. If no session is playing, do not send PAUSE and do not create a future playback request.

### ACC ON

Media-player restoration and Bluetooth restoration are independent paths from the ACC ON event.

**Media-player path:**

1. Immediately check whether the previously identified session is available and controllable.
2. If available, match it against the retained identity and restore only if it was playing before ACC OFF.
3. If unavailable, wait for the MediaSession availability/change callback and repeat the identity check when notified.
4. Clear pending restoration state after handling or invalidation.
5. Do not wait for Bluetooth/AVRCP readiness before attempting MediaSession restoration.
6. Never start an unrelated player if the original session does not return.

**Bluetooth path:**

1. Observe Bluetooth connection state independently.
2. Wait for AVRCP readiness where a Bluetooth control command is actually required; ACL/A2DP alone does not prove AVRCP readiness.
3. Do not send AVRCP PLAY merely because ACC became active or Bluetooth reconnected.
4. Only consider AVRCP PLAY if target-device testing demonstrates that it is required for the head unit's Bluetooth-source behavior.
5. Do not send AVRCP PLAY concurrently with MediaSession PLAY for the same restoration event.

AIMP is only an example of a media player. The logic must remain player-independent.

## 10. Target-device verification checklist

The APK/JADX inspection is static analysis, not proof of runtime access. Verify on the MT8163 device:

```sh
pm path com.hcn.bluetoothservice
dumpsys package com.hcn.bluetoothservice
dumpsys activity services com.hcn.bluetoothservice
```

Then:

1. Compare the installed APK SHA-256 and signing certificate with the analyzed artifact.
2. Confirm the installed package name and exact service component.
3. Confirm that the Accessory process can bind under the actual UID, package, signature, and SELinux policy.
4. Confirm that the callback is registered and receives metadata/playback state.
5. Confirm that initial A2DP/AVRCP state queries return valid values after registration.
6. Confirm callback behavior across A2DP/AVRCP connect and disconnect.
7. Confirm normal callback unregister/unbind does not stop playback or release A2DP.
8. Verify AVRCP command behavior only in a controlled test, including the service's active MediaController/A2DP prerequisites.
9. Test ACC OFF/ON with at least two different media players and verify that no unrelated player is started.
10. Record build/static verification separately from real-device runtime verification.

## 11. Inspected source files

Within the auxiliary artifact repository:

- [Decoded AndroidManifest.xml](https://github.com/14vvp93/public_worker/blob/main/artifacts/BluetoothService/jadx/resources/AndroidManifest.xml)
- [IBluetoothMusicService.java](https://github.com/14vvp93/public_worker/blob/main/artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/service/IBluetoothMusicService.java)
- [IMusicCallback.java](https://github.com/14vvp93/public_worker/blob/main/artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/service/IMusicCallback.java)
- [BluetoothMusicService.java](https://github.com/14vvp93/public_worker/blob/main/artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/service/BluetoothMusicService.java)
- [MusicPlayState.java](https://github.com/14vvp93/public_worker/blob/main/artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/api/MusicPlayState.java)
- [BluetoothDeviceInfo.java](https://github.com/14vvp93/public_worker/blob/main/artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/api/BluetoothDeviceInfo.java)

## 12. Conclusion

The inspected APK establishes the vendor Binder transaction contract, callback protocol and delivery behavior, Parcelable layouts, AVRCP command mapping, service side effects, and manifest export configuration. This is enough to design the Accessory-side adapter without guessing the AIDL protocol.

Implementation must remain gated on a successful bind/callback test against the APK actually installed on the MT8163 firmware. Until that test passes, service availability and runtime access must be treated as unverified.

## 13. Accessory-side Binder observer implementation (2026-11-20)

The first read-only integration slice is now present in the application source:

- `app/src/main/java/com/mt8163tools/hooks/VendorBluetoothMusicClient.kt`
- `app/src/main/java/com/mt8163tools/hooks/AccessoryHook.kt`

### Implemented behavior

- The observer is attached from the existing `AccessoryConfiguration` using the same system-server context used by the ACC hook.
- It binds explicitly to `com.hcn.bluetoothservice/com.hcn.bluetooth.service.BluetoothMusicService` with `BIND_AUTO_CREATE`.
- It uses the verified Binder descriptor and transaction IDs directly, avoiding an extra generated-AIDL module or new Gradle dependency.
- It registers one callback Binder and queries the initial A2DP, AVRCP, and A2DP playback booleans after registration.
- It consumes the four callback transactions and publishes Bluetooth service, connection, playback, and metadata observations through the existing Accessory event sink.
- All binding lifecycle and state transitions run on the system_server main looper. Binder callback payloads are copied on the Binder thread and event publication is posted to the main looper. No HandlerThread, worker thread, executor, timer, or polling loop is created. Initial synchronous Binder registration and state queries still execute on the main looper; their latency must be measured on the target firmware because a stalled vendor service could block system_server.
- It handles bind failure, null binding, binding death, Binder death, service disconnect, remote exceptions, callback unregister, and unbind.
- The client follows the Accessory module enabled/disabled lifecycle. Disabling the module unregisters the callback when possible and unbinds.

### Deliberate safety boundary

This implementation is an **observer only**. It does not call:

- `send_Avrcp_Cmd()`
- `requestA2dp()`
- `releaseA2dp()`
- `regMusicClientBinder()`
- `unRegMusicClientBinder()`

Therefore this first slice cannot initiate Bluetooth PLAY/PAUSE, switch the head-unit audio source, release A2DP, or invoke the vendor client-Binder stop/release path. Generic Android MediaSession playback control remains in `AccessoryPlaybackController`; the two paths are not coupled.

### Verification status

Source integration is committed, but no Gradle build or MT8163 runtime test has been run in this step. In particular, the following remain unverified:

1. Kotlin compilation against the repository's Android/Gradle toolchain.
2. Whether the system-server process can bind to the installed vendor service on the target firmware.
3. Whether the runtime Binder transaction behavior matches the checked-in APK artifact.
4. Callback delivery and clean unregister/unbind on the actual device.

Do not mark the vendor Binder integration as runtime-verified until those checks pass. The observer's state events are diagnostic observations; they do not yet trigger AVRCP playback commands or alter the existing ACC playback arbitration.


## 14. Event-driven threading correction (2026-11-20)

The initial observer implementation used a dedicated `HandlerThread`. This contradicted the repository-wide execution rule that forbids worker threads and polling. It has been removed.

Current behavior:

- `VendorBluetoothMusicClient` uses the existing system_server main looper for binding lifecycle, enable/disable transitions, Binder death cleanup, and state publication.
- Binder callback transactions only decode the primitive/String payloads and enqueue publication to the main looper. They do not perform disk I/O or call the vendor service.
- `setEnabled()`, `ServiceConnection` callbacks, unbind, and rebind are event-driven; no worker thread or retry loop is created.
- Normal teardown unregisters the exact callback Binder and unbinds. It never calls `unRegMusicClientBinder()`, `releaseA2dp()`, `requestA2dp()`, or AVRCP commands.
- The callback's synchronous playback-state transaction writes the required Binder reply exception status after consuming the complete `MusicPlayState` parcel layout.

### Main-looper IPC constraint

The vendor AIDL exposes synchronous registration, unregistration, and state-query transactions. With the project's no-worker-thread rule, the initial registration and three state queries execute on the system_server main looper. This is an explicit trade-off, not a claim that synchronous IPC is inherently non-blocking. Measure connection and query latency on the target firmware and inspect system_server responsiveness. If the vendor service can stall these calls, the architecture decision must be revisited rather than introducing an unapproved worker thread.

### Verification status

- Static source inspection: updated implementation uses no `HandlerThread`, executor, timer, or polling.
- Build verification: not run in this environment.
- Target-device Binder bind/callback and system_server latency verification: pending.
