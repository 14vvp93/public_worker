# BluetoothMusicService Binder Integration

## Scope

This document defines the Accessory module integration with the vendor service shipped in `com.hcn.bluetoothservice`. It is based on the decompiled APK analysis in [BluetoothService analysis](../../../artifacts/BluetoothService/ANALYSIS.md).

Target baseline: Android 9 / API 28, MediaTek MT8163, ARM32.

## Service endpoint

| Property | Value |
|---|---|
| Package | `com.hcn.bluetoothservice` |
| Class | `com.hcn.bluetooth.service.BluetoothMusicService` |
| Action | `com.hcn.bluetooth.service.BluetoothMusicService` |
| Exported | `true` |
| Service-level manifest permission | None declared |
| Binder descriptor | `com.hcn.bluetooth.service.IBluetoothMusicService` |
| Callback descriptor | `com.hcn.bluetooth.service.IMusicCallback` |

The exported manifest declaration does not prove that binding will succeed on the target firmware. Verify the installed APK identity, process UID, SELinux policy and actual bind result on the MT8163 device.

## Binder contract

### IBluetoothMusicService

| Transaction | Method | Result |
|---:|---|---|
| 1 | `isA2dpConnected()` | Boolean |
| 2 | `isAvrcpConnected()` | Boolean |
| 3 | `isA2dpPlaying()` | Boolean |
| 4 | `getConnectDevice()` | Nullable `BluetoothDeviceInfo` |
| 5 | `send_Avrcp_Cmd(int)` | Void |
| 6 | `requestA2dp()` | Void |
| 7 | `releaseA2dp()` | Void |
| 8 | `regMusicClientBinder(IBinder)` | Void |
| 9 | `unRegMusicClientBinder()` | Void |
| 10 | `registerBTMusicCallback(IMusicCallback)` | Void |
| 11 | `unregisterBTMusicCallback(IMusicCallback)` | Void |
| 12 | `getID3Info()` | String array (title, artist, album) |

AVRCP command values: 0 PLAY, 1 PAUSE, 2 PLAY/PAUSE, 3 STOP, 4 NEXT, 5 PREVIOUS, 6 FAST FORWARD, 7 REWIND.

The service dispatches AVRCP commands only when its selected MediaController is non-null and A2DP state is CONNECTED (2). Some commands are rate-limited by the service.

### IMusicCallback

| Transaction | Callback | Payload |
|---:|---|---|
| 1 | `onA2dpConnectStateChanged(int)` | Raw A2DP profile state |
| 2 | `onAvrcpConnectStateChanged(int)` | Raw AVRCP profile state |
| 3 | `onMetadataChanged(String, String, String)` | Title, artist, album |
| 4 | `onPlayStatusChanged(MusicPlayState)` | Nullable Parcelable |

The first three callback transactions are one-way Binder calls; playback-state delivery is synchronous. Callback methods can execute on Binder threads and must not access Android Views directly.

On callback registration, the service immediately sends metadata and playback state, but does not send initial A2DP/AVRCP connection states. Query those states after registration. Subsequent state changes arrive through callbacks.

## Parcelable data

### MusicPlayState

Parcel order: `state:int`, `position:long`, `duration:long`, `speed:float`, `updateTime:long`, `bufferedPosition:long`.

Playback state values follow Android PlaybackState constants. Time values are milliseconds; `updateTime` is based on `SystemClock.elapsedRealtime()`.

### BluetoothDeviceInfo

Parcel order: `mDeviceIndex:int`, `mDeviceStatus:int`, `mDeviceName:String`, `mDeviceAddr:String`.

Important: `getConnectDevice()` populates `mDeviceStatus` from Android `BluetoothDevice.getBondState()`, not the vendor's custom DeviceStatus enum. Normalize the value instead of assuming custom enum values.

## Accessory integration boundary

Implement a small vendor Binder adapter owned by the Accessory module. Keep the generic Android MediaSession integration as the primary/fallback path.

The adapter should:

1. Bind explicitly using the package and service class above.
2. Convert the service Binder with `IBluetoothMusicService.Stub.asInterface()`.
3. Create one stable `IMusicCallback.Stub` instance per connection.
4. Register the callback, then query A2DP, AVRCP and playback state to create the initial snapshot.
5. Convert raw Binder values into immutable module models.
6. Publish normalized state through the existing Accessory state/event boundary. Events must consume the state Accessory actually observes.
7. On module disable, service disconnect or Binder death, unregister the same callback instance when possible, unbind once, and clear stale connection state.
8. Handle `RemoteException`, null Binder, failed binding and repeated teardown safely.

Do not register `regMusicClientBinder()` without a demonstrated requirement. It is separate from callback registration and stores one client Binder.

### Teardown safety

Use `unregisterBTMusicCallback(callback)` for normal callback cleanup.

Do not use `unRegMusicClientBinder()` as routine teardown: the service may send STOP when its cached state is PLAYING and calls `releaseA2dp()`. This is an active audio-routing side effect.

### Audio routing safety

Do not call `requestA2dp()` automatically after binding. It calls the vendor source manager with `com.autochips.bluetooth/.BtMusicActivity` before scheduling the A2DP request and can change audio routing.

Do not issue AVRCP PLAY merely because ACC became active or the service connected. Inspect actual playback state first and preserve an already-playing media session, regardless of player brand.

## ACC and Events coordination

- Determine ACC state after Accessory is enabled.
- Publish the ACC state observed by Accessory to Events; do not independently infer it in Events.
- Bluetooth restoration after ACC must wait for its required readiness/connection condition.
- Do not unnecessarily apply the Bluetooth restoration wait to media-player handling.
- Prefer Binder callbacks for ongoing updates. Do not add polling.

## Target-device verification checklist

On the MT8163 device:

```sh
pm path com.hcn.bluetoothservice
dumpsys package com.hcn.bluetoothservice
dumpsys activity services com.hcn.bluetoothservice
```

Then verify:

- Installed APK SHA-256 and signing certificate match the analyzed artifact.
- Accessory process can bind under its actual UID and SELinux domain.
- Service connection and Binder death handling work.
- Initial snapshot queries return valid values.
- Metadata, playback and connection callbacks are delivered.
- Module disable unregisters the callback and unbinds without stopping music or releasing A2DP.
- ACC transitions do not trigger unintended PLAY or A2DP requests.

Static decompilation is not a substitute for this runtime test.

## Implementation status

- [x] Static service and Binder contract analysis.
- [x] Callback transaction and registration behavior analysis.
- [x] Parcelable layouts and AVRCP command mapping analysis.
- [x] Accessory integration and teardown rules documented.
- [ ] Target-device bind and callback verification.
- [ ] Accessory Binder adapter implementation.
- [ ] ACC/Events integration and regression tests.
