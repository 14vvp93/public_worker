# Bluetooth Binder Contracts

**Status:** analysis documented; integration implementation is a separate step  
**Target platform:** Android 9 (API 28), MediaTek MT8163  
**Source service package:** `com.hcn.bluetoothservice`

## 1. Scope and service discovery

The target firmware exposes the Bluetooth service package `com.hcn.bluetoothservice` under UID 1000. The package registers these services:

- `com.hcn.bluetooth.service.BluetoothMusicService`
- `com.hcn.bluetooth.service.BluetoothHfpclientService`
- `com.hcn.bluetooth.service.BluetoothAdapterManagerService`

This document focuses on the music Binder contract used by the Accessory module. HFP-client and adapter-manager contracts are identified as adjacent services, but are not specified here.

## 2. BluetoothMusicService

The service is declared with `exported=true` and no separate manifest permission was identified on its service declaration.

The Binder interface is `IBluetoothMusicService`. Its API surface covers:

- A2DP and AVRCP connection/state queries;
- Bluetooth connection and device information;
- playback control and playback-state information;
- current device / device identifier and ID3 metadata access;
- AVRCP command handling;
- registration and unregistration of music-client callbacks.

The service is therefore the firmware-facing source of Bluetooth music state and commands. The Accessory module should use this service contract rather than assuming a particular media-player application (for example, AIMP). The Bluetooth service remains the same even when the active media player changes.

### Callback contract: IMusicCallback

`IMusicCallback` exposes four callback methods. Registering a callback immediately provides metadata and playback-state information. It does **not** provide the initial A2DP/AVRCP connection states.

Consequently, after binding and registering the callback, the client must explicitly query the current A2DP and AVRCP states. Callback delivery should then be used for ongoing changes instead of periodic polling.

### Unregistration caveat

The method `unRegMusicClientBinder()` may have side effects, including stopping music and/or releasing A2DP. Treat it as potentially state-changing. Do not call it as routine cleanup until its implementation and lifecycle semantics have been verified on the target firmware.

## 3. Accessory module integration contract

The intended integration sequence is:

1. Bind to `BluetoothMusicService`.
2. Obtain the `IBluetoothMusicService` interface.
3. Register an `IMusicCallback` implementation.
4. Explicitly query initial A2DP and AVRCP states because registration does not report them.
5. Publish the observed Bluetooth and ACC-related state through the project's Events module contract.
6. Use Binder callbacks as the primary mechanism for subsequent state changes.
7. Handle Binder disconnection and rebind/re-register, then query the initial states again.

The Accessory module is responsible for observing the ACC state and the Bluetooth service state. Events must receive the state actually observed by Accessory; Events should not independently guess or reconstruct the ACC state.

## 4. Playback safety rule

**Do not send a Bluetooth PLAY command merely because ACC becomes active or Bluetooth reconnects.**

If another media player was already active, issuing PLAY through the Bluetooth service can cause unintended playback behavior. The integration must distinguish restoration of the Bluetooth connection from a deliberate request to start playback.

In particular:

- connection restoration is not itself a playback request;
- do not send PLAY when a media player was already active before the ACC transition;
- keep connection-state recovery and playback-control decisions as separate flows.

## 5. Implementation implications

- Prefer Binder callbacks over polling for ongoing metadata and playback updates.
- Query initial A2DP/AVRCP state after callback registration.
- Re-register callbacks after Binder reconnection.
- Keep the Bluetooth service integration independent of any specific player application.
- Forward Accessory's observed ACC/Bluetooth state to Events using the established module event contract.
- Treat callback unregistration as potentially destructive until verified.

## 6. Verification still required

The following details must be checked against the decompiled service implementation and on-device behavior before relying on them:

- exact parameter and return types for every `IBluetoothMusicService` method;
- exact names and semantics of all four `IMusicCallback` methods;
- whether `unRegMusicClientBinder()` always stops playback/releases A2DP or does so only under particular conditions;
- Binder death/reconnect behavior and required binding flags;
- whether service calls require privileged caller identity or additional runtime checks.

This document records the currently established contract and integration constraints; it does not claim that every method's implementation semantics have been fully verified.
