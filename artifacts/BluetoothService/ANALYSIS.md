# BluetoothService — static analysis

**Repository:** `14vvp93/public_worker`  
**Target directory:** `artifacts/BluetoothService/`  
**Analysis status:** partial; Binder client contract is identified, system-service implementation and access policy are not yet verified.

## 1. Artifact inventory

The repository currently contains:

- `artifacts/BluetoothService.apk` — 9,282,308 bytes. This is the APK intended for analysis as the device-side Bluetooth service.
- `artifacts/AutoBtClient/` — a separate decompilation of the client application, package `com.autochips.bluetooth`.
- `artifacts/BluetoothService/` — was cleared in the previous cleanup and is being repopulated with analysis output.

Important distinction: `AutoBtClient` is not the system Bluetooth service. Its manifest and decompiled classes describe the client application. They must not be treated as the manifest or implementation of `com.hcn.bluetoothservice`.

## 2. Identified packages and components

| Component | Identity | Evidence / status |
|---|---|---|
| Auto Bluetooth client | `com.autochips.bluetooth` | Decompiled in `artifacts/AutoBtClient` |
| Music service package | `com.hcn.bluetoothservice` | Referenced by the client Binder manager |
| Music service component | `com.hcn.bluetoothservice/com.hcn.bluetooth.service.BluetoothMusicService` | Explicit component used by `LocalBTMusicManager`; export/access policy still unverified |
| System APK path on target | `/system/app/BluetoothService/BluetoothService.apk` | Reported device path; package UID 1000 was observed on the target |

The APK at `artifacts/BluetoothService.apk` is the service-side artifact to decompile. The client APK and its manifest are not substitutes for it.

## 3. Binder API discovered in the client

The decompiled client contains two Binder contracts:

### `IBluetoothMusicService`

- 12 Binder transactions are defined.
- The client uses this interface to control and observe the Bluetooth music service.
- The identified control domain includes AVRCP playback commands.
- The transaction order, exact argument types, return types, parcel layout, and exception behavior must be taken from the actual generated Stub/Proxy source before implementing a compatible client. Do not infer these from method names alone.

### `IMusicCallback`

- 4 Binder transactions are defined.
- It is the callback contract used by the music service to report state to clients.
- The exact callback signatures and Parcelable ordering must be copied from the generated interface source before implementation.

### Data models

- `MusicPlayState` contains six Parcelable fields.
- `BluetoothDeviceInfo` contains device index, status, name, and MAC address.
- Exact field types, nullability, Parcelable read/write order, and transaction-level use must be confirmed against source from the target service/client artifact before relying on them.

## 4. AVRCP command mapping

The client-side mapping identified for AVRCP is:

| Value | Command |
|---:|---|
| 0 | PLAY |
| 1 | PAUSE |
| 2 | NEXT |
| 3 | PREVIOUS |
| 4–5 | Additional commands exist in the client mapping; exact symbolic names and semantics must be verified from the source before use |

Do not send PLAY automatically when a media session is already active. The Accessory module must avoid duplicate playback starts.

## 5. Accessory Module integration design

The integration should be event-driven and must not use polling.

Recommended responsibilities:

1. **BluetoothMusicController** — owns the service binding lifecycle, Binder proxy, callback registration, Binder death handling, and disconnect cleanup. It should only be enabled after service access has been verified on the target.
2. **MediaSessionController** — provides the generic selected-player path for Android media sessions. This remains the fallback/universal control path and avoids coupling all media playback to a vendor-specific Binder API.
3. **MediaPlaybackSnapshot** — immutable normalized playback state consumed by the Accessory UI and Events module.
4. **Events integration** — forward A2DP/AVRCP connection and playback state changes through callbacks/broadcasts; do not add periodic polling.
5. **ACC OFF/ON behavior** — remember whether playback was actually PLAYING before ACC OFF and restore only that state. Do not start playback if it was paused, stopped, or unknown.
6. **Player identity** — do not identify the active player by foreground process. Use MediaSession/active controller identity and observed callbacks.

## 6. Binder lifecycle and safety requirements

A production client must:

- Bind with an explicit `ComponentName` and handle `bindService` returning false.
- Avoid assuming that the service is exported or bindable by a normal application.
- Handle null Binder, failed `Stub.asInterface`, `RemoteException`, service disconnect, and Binder death.
- Link a `DeathRecipient` while connected and unlink it during teardown.
- Register the callback once per connection and unregister it before unbinding when the service contract permits.
- Avoid retaining an Activity or View from the Binder callback.
- Marshal UI updates onto the main thread.
- Make disconnect and cleanup idempotent.
- Never silently fall back to a guessed transaction code or guessed Parcelable layout.

## 7. Access and permission verification — BLOCKING

The following facts are not established by the client decompilation:

- Whether `BluetoothMusicService` is exported.
- Whether it requires a signature/system permission or a custom permission.
- Whether it checks calling UID, package name, signature, or privileged permissions.
- Whether binding is restricted to a privileged/system UID.
- Whether the target APK is the exact version installed on the device.
- Whether callback registration is permission-gated.
- Whether the service is started/bound by another system component and whether it survives client unbind.

The client APK's `AndroidManifest.xml` only describes `com.autochips.bluetooth`. It cannot prove the service-side export or permission configuration.

The target device observation that the service package has UID 1000 suggests a system-privileged installation, but does not prove that MT8163 Tools can bind to it. Verify the actual service manifest and runtime behavior on the target before enabling the Binder path.

## 8. Required verification on the target

Collect the following from the target device:

```sh
adb shell pm path com.hcn.bluetoothservice
adb shell dumpsys package com.hcn.bluetoothservice
adb shell dumpsys activity services com.hcn.bluetoothservice
adb shell service list
```

If using Termux directly, run the equivalent commands with the available Android shell access. Also capture the actual installed APK and decompile that exact file:

```sh
pm path com.hcn.bluetoothservice
```

Pull the returned APK path to the analysis workstation, then run JADX and Apktool against that APK. Preserve:

- Original APK and SHA-256.
- Decoded `AndroidManifest.xml`.
- JADX sources and decompiler log.
- Apktool resources/smali output.
- Signing certificate report.
- Package metadata and service declarations.

## 9. Required next analysis pass

The service APK `artifacts/BluetoothService.apk` must be decoded independently. The next pass must inspect:

1. Service package metadata, version, SDK levels, signing certificate, and APK hash.
2. Decoded service manifest: application UID assumptions, exported services, intent filters, permissions, process names, and provider/receiver declarations.
3. `BluetoothMusicService` implementation and its Binder Stub.
4. Exact `IBluetoothMusicService` transaction codes, signatures, parcel order, and return values.
5. Exact `IMusicCallback` callback transaction codes and payloads.
6. `MusicPlayState` and `BluetoothDeviceInfo` Parcelable serialization.
7. Permission/UID/signature checks and service-side caller validation.
8. Service start/bind lifecycle, callback fan-out, threading, and failure behavior.
9. Compatibility of the service's Binder protocol with Android 9 / API 28 and the target MT8163 firmware.

## 10. Current implementation decision

**Do not implement or enable the vendor Binder integration yet.**

The client-side protocol is sufficiently identified to guide the next inspection, but the service-side contract and access policy remain unverified. Proceed with the generic MediaSession-based controller design; enable the vendor Binder adapter only after the service APK and target-device access checks confirm the exact contract.

## 11. Source locations

- Service APK: [artifacts/BluetoothService.apk](../BluetoothService.apk)
- Client decompilation: [artifacts/AutoBtClient](../AutoBtClient)
- Client analysis report: [artifacts/AutoBtClient/REPORT.md](../AutoBtClient/REPORT.md)

---

**Conclusion:** the current evidence identifies a vendor music Binder service and a client-side control/callback protocol, but does not yet prove that a third-party application can bind to the service. The service APK is present in the repository root artifacts directory; its full decompilation and service-side analysis are the remaining required work.
