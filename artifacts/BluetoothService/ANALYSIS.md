# BluetoothService — service-side analysis

**Repository:** `14vvp93/public_worker`  
**APK:** `artifacts/BluetoothService.apk`  
**Decompilation:** `artifacts/BluetoothService/jadx/`  
**JADX:** v1.5.7; 2,532 processing units; 68 decompiler errors. The output is partial, but all six critical files below were generated and inspected. Do not treat unrelated decompiler failures as proof that these classes are incorrect.

## 1. Artifact identity

| Property | Value |
|---|---|
| Package | `com.hcn.bluetoothservice` |
| Version name | `202601221437592` |
| Version code | `2` |
| minSdk | 28 |
| targetSdk | 31 |
| sharedUserId | `android.uid.system` |
| Application | `com.hcn.bluetooth.MyApplication` |
| Persistent | `true` |

The APK manifest declares the Bluetooth music service:

- Class: `com.hcn.bluetooth.service.BluetoothMusicService`
- `android:exported="true"`
- Intent action: `com.hcn.bluetooth.service.BluetoothMusicService`
- No `android:permission` attribute is declared on this service.
- The application uses the system shared UID.

The service's `onBind()` returns a `BluetoothMusicBinder` implementing `IBluetoothMusicService.Stub`. It does not inspect the incoming Intent in the decompiled method.

**Access-policy finding:** the manifest exposes the service and does not attach a service-level permission. A scan of the decompiled `BluetoothMusicService.java` found no calls to `Binder.getCallingUid/Pid`, calling-permission enforcement, or signature/package checks. This is evidence that this class does not implement those checks; it is not proof that every OS/vendor policy permits a third-party client to bind. The system shared UID and actual installed APK/signing identity still need runtime verification on the target.

## 2. IBluetoothMusicService — exact Binder contract

Descriptor: `com.hcn.bluetooth.service.IBluetoothMusicService`

| Transaction | Method | Request payload | Reply |
|---:|---|---|---|
| 1 | `isA2dpConnected()` | none | boolean as int |
| 2 | `isAvrcpConnected()` | none | boolean as int |
| 3 | `isA2dpPlaying()` | none | boolean as int |
| 4 | `getConnectDevice()` | none | nullable `BluetoothDeviceInfo` |
| 5 | `send_Avrcp_Cmd(int)` | int command | void |
| 6 | `requestA2dp()` | none | void |
| 7 | `releaseA2dp()` | none | void |
| 8 | `regMusicClientBinder(IBinder)` | strong Binder | void |
| 9 | `unRegMusicClientBinder()` | none | void |
| 10 | `registerBTMusicCallback(IMusicCallback)` | callback Binder | void |
| 11 | `unregisterBTMusicCallback(IMusicCallback)` | callback Binder | void |
| 12 | `getID3Info()` | none | String[3] |

All methods use interface token `com.hcn.bluetooth.service.IBluetoothMusicService`. Parcelable transaction 4 uses an integer presence marker followed by `BluetoothDeviceInfo.writeToParcel()`. The generated Proxy recycles request/reply Parcels in finally blocks.

### AVRCP command mapping — confirmed from service implementation

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

### Important side effects

- `requestA2dp()` through the Binder first calls `mSourceInfo.onRequestPlayAudio("com.autochips.bluetooth/.BtMusicActivity")`, then schedules the A2DP request.
- `releaseA2dp()` schedules release work through the main Handler.
- `unRegMusicClientBinder()` unlinks the registered client death recipient, stops playback if the cached state is PLAYING, and calls `releaseA2dp()`. It is not a harmless callback-unregister operation.
- `regMusicClientBinder(IBinder)` stores one client Binder and links a death recipient. A client death invokes `unRegMusicClientBinder()`.

Do not call transaction 9 as generic disconnect cleanup unless the module deliberately wants the service's stop-and-release behavior.

## 3. IMusicCallback — exact callback contract

Descriptor: `com.hcn.bluetooth.service.IMusicCallback`

| Transaction | Method | Payload |
|---:|---|---|
| 1 | `onA2dpConnectStateChanged(int)` | raw A2DP profile state |
| 2 | `onAvrcpConnectStateChanged(int)` | raw AVRCP profile state |
| 3 | `onMetadataChanged(String, String, String)` | title, artist, album |
| 4 | `onPlayStatusChanged(MusicPlayState)` | nullable Parcelable |

The first three Proxy calls use one-way Binder transactions (`FLAG_ONEWAY`). Transaction 4 uses a synchronous transaction and reads the reply exception. The callback Stub enforces the interface descriptor.

The service stores callbacks in `RemoteCallbackList<IMusicCallback>`. Registration immediately sends current metadata and play state; it does not immediately send A2DP/AVRCP connection state. Later notifications are dispatched through `callListener`:

- 0 → metadata
- 1 → playback state
- 2 → AVRCP state
- 3 → A2DP state

A callback client should tolerate initial connection state being unknown until a later state notification or an explicit query.

## 4. Parcelable layouts

### MusicPlayState

Parcel order and types:

1. `state: int`
2. `position: long`
3. `duration: long`
4. `speed: float`
5. `updateTime: long`
6. `bufferedPosition: long`

Playback states match Android `PlaybackState` constants: NONE=0, STOPPED=1, PAUSED=2, PLAYING=3, FAST_FORWARDING=4, REWINDING=5, BUFFERING=6, ERROR=7, CONNECTING=8, SKIPPING_TO_PREVIOUS=9, SKIPPING_TO_NEXT=10, SKIPPING_TO_QUEUE_ITEM=11. Position, duration and buffered position are millisecond values from the Android media-session model; update time is based on `SystemClock.elapsedRealtime()`.

### BluetoothDeviceInfo

Parcel order:

1. `mDeviceIndex: int`
2. `mDeviceStatus: int`
3. `mDeviceName: String`
4. `mDeviceAddr: String`

Custom `DeviceStatus` constants are PAIRED=0, CONNECTED=1, CONNECTEDPHONE=2, CONNECTEDMEDIA=3, CONNECTING=4, DISCONNECTING=5, UNKNOWN=-1.

**Caveat:** the constructor used by `getConnectDevice()` sets `mDeviceStatus` directly from `BluetoothDevice.getBondState()`, not from those custom constants. Android bond-state values (for example BOND_BONDED=12) can therefore appear in this field. Do not interpret it as the custom status enum without normalizing it. `mDeviceIndex` remains -1 in this constructor.

## 5. BluetoothMusicService behavior

- On create, initializes a `RemoteCallbackList`, main-thread Handler, Bluetooth profile proxies for A2DP Sink (profile 11) and AVRCP Controller (profile 12), receiver events, and MediaSessionManager.
- Tracks active media sessions whose tags contain `A2DP_MBS_TAG` or `A2DP_MBS_TAG_Q`.
- Registers a `MediaController.Callback` and reads playback state/metadata from that controller.
- Metadata order exposed to clients is title, artist, album.
- A2DP/AVRCP connection values are profile state integers; CONNECTED is 2.
- `isA2dpPlaying()` is true only when cached state equals PLAYING (3).
- `getConnectDevice()` returns the first connected A2DP Sink device or null.
- A2DP disconnect resets playback state, clears all three metadata strings, and notifies clients.
- A2DP connect can trigger `requestA2dp()` when system property `sys.sourcing.package` equals `com.autochips.bluetooth`.

The service is a vendor media-session bridge, not a generic Bluetooth adapter API. Its playback commands are forwarded to the selected vendor-tagged `MediaController` via `TransportControls`.

## 6. Accessory Module integration decision

The service-side contract is now sufficiently identified to implement a small vendor Binder adapter, subject to one target-device bind test. Recommended boundary:

- Keep the generic Android MediaSession path as the primary/fallback controller.
- Put vendor binding and callback handling behind the Accessory module's existing enable/disable lifecycle.
- Bind explicitly to `ComponentName("com.hcn.bluetoothservice", "com.hcn.bluetooth.service.BluetoothMusicService")`.
- Handle bind failure, null Binder, `RemoteException`, disconnect and Binder death.
- Register exactly one `IMusicCallback` per connection; unregister it before unbinding.
- Do not call `unRegMusicClientBinder()` for ordinary teardown unless stop-and-release is intended.
- Do not call `requestA2dp()` automatically on connection; it can launch the vendor Bluetooth activity and change audio routing.
- Treat callback state integers as raw profile states; normalize them at the module boundary.
- Copy incoming `MusicPlayState` into an immutable app model before posting UI/events.
- Keep all UI work on the main thread and make cleanup idempotent.
- No polling.

## 7. Remaining target verification

The decompiled APK is now available in the repository, but it must be compared with the APK installed on the MT8163 device. Verify:

```sh
pm path com.hcn.bluetoothservice
dumpsys package com.hcn.bluetoothservice
dumpsys activity services com.hcn.bluetoothservice
```

Also compare the installed APK SHA-256 and signing certificate with the repository artifact. Confirm that the Accessory process can bind and receive callbacks under the actual device UID/SELinux/package policy. The manifest's exported flag alone does not prove runtime access.

## 8. Decompilation quality

JADX completed with exit code 3 and reported 68 errors. The output contains `sources/` and `resources/`; the manifest and the five required Java classes were generated and inspected. The decompiler log and input hash are preserved alongside the output. For any class whose implementation matters but is missing or malformed, inspect its DEX/smali with Apktool or another DEX tool rather than guessing.

## 9. Source locations

- [Service APK](../BluetoothService.apk)
- [JADX output](./jadx)
- [Decoded service manifest](./jadx/resources/AndroidManifest.xml)
- [IBluetoothMusicService](./jadx/sources/com/hcn/bluetooth/service/IBluetoothMusicService.java)
- [IMusicCallback](./jadx/sources/com/hcn/bluetooth/service/IMusicCallback.java)
- [BluetoothMusicService](./jadx/sources/com/hcn/bluetooth/service/BluetoothMusicService.java)
- [MusicPlayState](./jadx/sources/com/hcn/bluetooth/api/MusicPlayState.java)
- [BluetoothDeviceInfo](./jadx/sources/com/hcn/bluetooth/api/BluetoothDeviceInfo.java)

---

**Conclusion:** the actual service implementation, Binder transaction contract, callback protocol, Parcelable layouts, AVRCP command mapping and manifest export configuration have been inspected. A vendor Binder adapter can now be implemented without guessing the protocol. The only blocking verification before enabling it on the target is a real bind/callback test against the installed firmware APK and its runtime access policy.


## 10. Callback and Accessory integration notes (2026-11-20)

This section records the focused follow-up inspection of `IMusicCallback.java` and the corresponding service implementation.

### Callback delivery details

The callback Stub uses descriptor `com.hcn.bluetooth.service.IMusicCallback` and transaction IDs 1–4:

| ID | Callback | Binder delivery |
|---:|---|---|
| 1 | `onA2dpConnectStateChanged(int)` | one-way |
| 2 | `onAvrcpConnectStateChanged(int)` | one-way |
| 3 | `onMetadataChanged(String, String, String)` | one-way |
| 4 | `onPlayStatusChanged(MusicPlayState)` | synchronous |

The service uses `RemoteCallbackList<IMusicCallback>`. On registration it adds the callback and immediately invokes, in order, `onMetadataChanged(title, artist, album)` and `onPlayStatusChanged(currentState)`. It does **not** immediately invoke either connection-state callback. The client must therefore query `isA2dpConnected()` and `isAvrcpConnected()` after registration to establish its initial connection snapshot; subsequent changes arrive through callbacks.

The callback implementation must be safe to receive Binder calls off the UI thread. Copy callback values into an immutable module-level snapshot and dispatch state publication to the appropriate application thread. Treat metadata strings and the Parcelable as nullable/possibly incomplete input.

### Teardown distinction

There are two different operations and they must not be confused:

- `unregisterBTMusicCallback(callback)`: removes only this callback from the service's `RemoteCallbackList`; use this during normal client teardown.
- `unRegMusicClientBinder()`: tears down the separately registered music-client Binder, sends STOP if cached playback state is PLAYING, and calls `releaseA2dp()`; do not use as routine callback cleanup.

The service stores a single `mMusicBinder` for `regMusicClientBinder(IBinder)`; this is separate from the callback list. The Accessory integration should not register this client Binder unless a concrete requirement for that vendor-side client-death mechanism is established.

### Safe initial connection sequence

1. Bind explicitly to package `com.hcn.bluetoothservice`, class `com.hcn.bluetooth.service.BluetoothMusicService`, using the service action already declared in the manifest.
2. Convert the returned Binder with `IBluetoothMusicService.Stub.asInterface()`.
3. Register one `IMusicCallback.Stub` instance.
4. Query `isA2dpConnected()`, `isAvrcpConnected()`, `isA2dpPlaying()`, and, if needed, `getConnectDevice()` / `getID3Info()` to establish the initial snapshot.
5. Publish the normalized snapshot through the Accessory module's existing state/event boundary. Events must consume the state observed by Accessory rather than independently re-deriving it.
6. On module disable or service disconnect, unregister the exact callback instance when the remote interface is still usable, then unbind and clear stale connection/playback state. Make cleanup idempotent and handle `RemoteException` and Binder death.

Do not call `requestA2dp()` merely because binding succeeded: the service implementation calls `mSourceInfo.onRequestPlayAudio("com.autochips.bluetooth/.BtMusicActivity")` before scheduling the request, so this is an active routing/playback operation, not a passive status query.

### Playback arbitration requirements

The Accessory coordinator must distinguish the Bluetooth sink state from the active media player's state. In particular:

- Do not send AVRCP PLAY merely because ACC became active or the service connected.
- Before issuing PLAY, inspect the actual playback state and preserve an already-playing media session; this rule is independent of the player brand (AIMP is only an example).
- Keep the existing ACC policy: determine ACC state after the module is enabled, and publish the state that Accessory actually observes to Events.
- Bluetooth restoration after ACC must wait for the required readiness/connection condition; media-player handling must not inherit that wait unnecessarily.
- Prefer callbacks for ongoing state changes; no polling loop is required for this Binder interface.

### Verification status

Static inspection of the checked-in JADX output confirms the callback transaction behavior and the service-side registration/dispatch sequence described above. This is not yet a successful runtime integration test. Binding permission/policy, actual callback delivery, and behavior on the installed MT8163 firmware APK remain to be verified on-device.
