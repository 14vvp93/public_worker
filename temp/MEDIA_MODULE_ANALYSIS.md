# Media Module — Repository Audit and Technical Findings

**Status:** Static repository audit completed for the Media-related implementation paths.  
**Reviewed repository:** `14vvp93/public_worker`  
**Branch:** `main`  
**Reviewed commit:** `216694116bbcaeb0f516dc02d626a7e054d10df5`  
**Date:** 2026-11-22  
**Scope:** Repository inventory, project documentation, firmware SourceService/SourceInfo implementation, Bluetooth service and client APKs, current Accessory source mirror, and relevant GitHub Actions.  
**Implementation status:** No application code, workflow, or existing documentation was changed as part of this audit. This report is the only intended addition.

---

## 1. Executive findings

The repository contains the necessary static evidence to define the Media implementation more precisely. The most important result is that the firmware already provides a source-selection service and callback API. Media does not need to invent its own source detector.

The following state domains must remain separate:

| Domain | Meaning | Evidence/source |
|---|---|---|
| ACC | Vehicle accessory power state | Existing MCU receiver and `McuService.AccStatus()` |
| Selected audio source | Source currently selected by the head-unit audio-routing service | `SourceInfo.getSourcePackage()`, backed by `sys.sourcing.package` |
| Source playback information | Firmware-reported state and track information for the selected source | `SourceInfo.getMusicInfo()`, `onMusicEvent(MusicInfo)` |
| Android media player | Playback state and commands for an Android MediaSession | `MediaSessionManager`, `MediaController` |
| Bluetooth transport | A2DP/AVRCP connection state | Vendor `IBluetoothMusicService` |
| Bluetooth playback | Bluetooth music playback state | `IBluetoothMusicService.isA2dpPlaying()` and `IMusicCallback` |

These values are related but not interchangeable. A selected source does not prove that audio is currently playing. A connected A2DP profile does not prove that the Bluetooth player is ready. An active MediaSession does not by itself prove that it is the source selected by the head unit.

### Main conclusions

1. **Audio Source mechanism is identified.** The firmware service is `android.sourceservice.SourceService`; the client facade is `android.sourceservice.SourceInfo`. The source property has explicit update sites in the inspected SourceService implementation, and callback events are available.
2. **SourceInfo callbacks are not currently used by the Accessory implementation.** Current code reads the source property at ACC OFF only. The firmware also exposes source-change and MusicInfo callbacks that can provide event-driven updates.
3. **Bluetooth package roles are not a real identity conflict.** `com.autochips.bluetooth` is the Bluetooth UI/source application. `com.hcn.bluetoothservice` is the separate APK that provides the Bluetooth music Binder service.
4. **The vendor Bluetooth Binder contract is known.** PLAY is command `0`; PAUSE is command `1`. The service has internal prerequisites before dispatching either command.
5. **Bluetooth readiness is not fully observable through the current Binder API.** A2DP/AVRCP connection flags are necessary observations, but the service also requires an internal Bluetooth MediaController. The Binder interface exposes no explicit getter for that controller's readiness.
6. **Current Bluetooth restoration code is incomplete relative to the Media concept.** It sends PLAY on ACC ON but does not send PAUSE on ACC OFF. Its class comment incorrectly describes the client as read-only.
7. **Current MediaSession selection is not tied to SourceInfo.** It selects the first playing non-Bluetooth session returned by MediaSessionManager, which can be different from the selected audio source.
8. **The current restoration paths are coupled.** The `mediaSessionOwnsRestore` flag can suppress Bluetooth restoration, even though the two paths represent distinct source types.
9. **Vendor Bluetooth service behavior can itself trigger source selection and PLAY.** A2DP reconnect and the vendor auto-play setting must be considered to prevent duplicate or premature playback commands.
10. **All findings are static.** The repository does not provide proof of successful runtime behavior on the installed MT8163 head unit. Device verification remains necessary.

---

## 2. Repository coverage and evidence quality

### 2.1 Repository inventory

The recursive Git tree was inspected at the reviewed commit. It contains 46,828 entries, including 41,375 files and 5,453 directories.

The main areas relevant to this audit are:

- `docs/` — architecture, firmware reverse-engineering notes, Accessory analysis, Bluetooth Binder contracts, and Events analysis.
- `artifacts/CarServises/` — extracted and decompiled vendor framework code, including SourceService and SourceInfo.
- `artifacts/BluetoothService/` — Bluetooth music service APK, manifest, JADX output, Binder interfaces, and service implementation.
- `artifacts/AutoBtClient/` — separate Bluetooth UI/client APK, manifest, and LocalBTMusicManager implementation.
- `rom/system/framework/` — original framework inputs, including the CarServices VDEX/JAR files.
- `temp/mt8163_tools-development/` — a source snapshot of MT8163 Tools, including the Accessory hook and playback controllers.
- `.github/workflows/` — extraction and decompilation workflows.

The audit concentrated on every identified Media/Accessory/SourceInfo/Bluetooth code path and its supporting documentation. The large generated resource trees were inventoried; unrelated generated resources were not individually reviewed because they do not define the Media behavior.

### 2.2 Principal evidence files

| Area | Repository path |
|---|---|
| Media concept | `temp/MEDIA_MODULE.md` |
| Main architecture | `docs/ARCHITECTURE.md` |
| Firmware SourceInfo analysis | `docs/SOURCE_INFO_FIRMWARE_ANALYSIS.md` |
| Accessory analysis | `docs/modules/accessory/ANALYSIS.md` |
| Bluetooth service analysis | `docs/modules/accessory/BLUETOOTH_SERVICE.md` |
| Bluetooth Binder contracts | `docs/BLUETOOTH_BINDER_CONTRACTS.md` |
| Events analysis | `docs/modules/events/ANALYSIS.md` |
| Firmware SourceService | `artifacts/CarServises/jadx/boot-carservices_classes/sources/android/sourceservice/SourceService.java` |
| Firmware SourceInfo | `artifacts/CarServises/jadx/boot-carservices_classes/sources/android/sourceservice/SourceInfo.java` |
| Firmware MusicInfo | `artifacts/CarServises/jadx/boot-carservices_classes/sources/android/sourceservice/MusicInfo.java` |
| Firmware Source Binder interface | `artifacts/CarServises/jadx/boot-carservices_classes/sources/android/sourceservice/ISourceService.java` |
| Bluetooth service manifest | `artifacts/BluetoothService/jadx/resources/AndroidManifest.xml` |
| Bluetooth service implementation | `artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/service/BluetoothMusicService.java` |
| Bluetooth Binder interface | `artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/service/IBluetoothMusicService.java` |
| Bluetooth callback interface | `artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/service/IMusicCallback.java` |
| Bluetooth playback parcel | `artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/api/MusicPlayState.java` |
| Bluetooth UI/client manifest | `artifacts/AutoBtClient/jadx/resources/AndroidManifest.xml` |
| Bluetooth UI Binder client | `artifacts/AutoBtClient/jadx/sources/com/hcn/bluetooth/api/LocalBTMusicManager.java` |
| Current ACC integration | `temp/mt8163_tools-development/app/src/main/java/com/mt8163tools/hooks/AccessoryHook.kt` |
| Current MediaSession controller | `temp/mt8163_tools-development/app/src/main/java/com/mt8163tools/hooks/AccessoryPlaybackController.kt` |
| Current Bluetooth client | `temp/mt8163_tools-development/app/src/main/java/com/mt8163tools/hooks/VendorBluetoothMusicClient.kt` |

The `temp/mt8163_tools-development` tree is an auxiliary source snapshot inside `public_worker`. It must not be assumed to be the current canonical state of the separate `mt8163_tools` repository without comparing its commit and files.

---

## 3. ACC event and synchronization mechanism

The current Accessory design uses the existing MCU service rather than introducing a second ACC observer.

The inspected implementation identifies:

- MCU class: `android.carsource.McuService`
- Constructor: `McuService(Context)`
- Existing receiver: `android.carsource.McuService$1`
- Receiver method: `onReceive(Context, Intent)`
- Broadcast action: `android.mcu.device.action.ACC`
- State extra: `AccStatus`
- Current-state getter: `McuService.AccStatus()`

The current `AccessoryHook.kt` hooks the MCU service constructor, retains the service/context, and hooks the existing ACC receiver after the original receiver handles the broadcast. When the module becomes enabled, it also reads the current ACC state through `McuService.AccStatus()`. This avoids waiting for a future transition after module enablement.

**Design implication:** ACC remains the initiating event for the Media coordinator. The UI must not become the owner of ACC state, and Media should not create a separate ACC polling mechanism.

Static source inspection does not prove that LSPosed resolves the exact classes/methods on the installed firmware. Runtime hook verification is still required.

---

## 4. Firmware Audio Source mechanism

### 4.1 SourceInfo is a client facade over the sourceinfo Binder service

The firmware client class is:

`android.sourceservice.SourceInfo`

Its constructor resolves the Binder service named:

`sourceinfo`

through:

`ServiceManager.getService("sourceinfo")`

and wraps it as `ISourceService`.

The singleton provides these relevant methods:

- `getSourcePackage()`
- `getMusicInfo()`
- `setOnEventListener(OnEventListener)`
- `onRequestPlayAudio(...)`
- `onRequestAuxAudio(...)`

The exact implementation of `getSourcePackage()` is:

`SystemProperties.get("sys.sourcing.package")`

This getter is a direct system-property read. It does not perform a new Binder query to determine the source.

### 4.2 SourceService owns selected-source state

The server implementation is:

`android.sourceservice.SourceService`

It tracks internal fields including:

- `CurrentSource`
- `mCurrentClass`
- `mAudioPathStatus`
- `mMusicInfo`
- `mBtMusicSession`

The service changes the current source in response to activity and media-routing events. Relevant source categories include radio, DAB/SXM, MPEG, AUX/video input, built-in Bluetooth, third-party Bluetooth-related applications, ordinary media applications, and selected CarPlay/focus cases.

Examples from the inspected code:

- Resuming `com.autochips.bluetooth/.BtMusicActivity` selects the Bluetooth source `com.autochips.bluetooth` and marks the BT UI/A2DP paths.
- Packages in the vendor `BT_MUSIC` list also select the Bluetooth source.
- Recognized media packages and HCN media-player classes select a music path and set `CurrentSource` to the corresponding package.
- A long-duration media-start event can invoke `muxMediaPlayer(pkg, session)`, which changes the routing path to MUSIC and selects that package.

This is the firmware's source-selection/routing model. It is more authoritative for the selected head-unit source than foreground-activity detection alone.

### 4.3 Source property writes are traceable in the decompiled SourceService

A complete search of the inspected `SourceService.java` found these four write sites for `sys.sourcing.package`:

| SourceService location | Behavior |
|---|---|
| Constructor, line 197 | Initializes the property to an empty string |
| `onActivityStatus()`, line 585 | Writes the new `CurrentSource` when source/path/class state changes |
| `muxMediaPlayer()`, line 808 | Writes the selected media-player package when the media source changes |
| Media stop handling, line 1134 | Writes the current source while resetting the corresponding music state |

This corrects the older documentation statement that only one confirmed write path had been found. Within the checked-in decompiled `SourceService.java`, the source-selection write paths are now statically traceable.

This does not prove that no other system component can write the system property. It does establish the update paths in the inspected SourceService implementation.

### 4.4 SourceInfo provides event-driven source and playback callbacks

The `SourceInfo.OnEventListener` interface exposes:

- `onSourceChanged(String)`
- `onMusicEvent(MusicInfo)`

The client callback Binder receives these events and posts them to a Handler before dispatching them to the registered listener.

The registration path is:

- `setOnEventListener(listener)` stores the listener and calls `ISourceService.setcallback(token)`.
- Passing `null` removes the callback through `ISourceService.removecallback(token)`.

The firmware service maintains a callback list and invokes:

- `onSourceChanged(pkg)` when the selected source changes.
- `onMusicEvent(info)` when media status for the current source changes.

The current Accessory implementation does **not** register this listener. It performs only a one-time reflective read of `getSourcePackage()` at ACC OFF.

**Recommended use:** on Media initialization, obtain the current source and current MusicInfo snapshot, then register/use the callback stream for subsequent changes. The callback should be the ongoing event mechanism; do not add repeated property reads or periodic polling. The exact initial-snapshot versus callback-registration ordering should be designed to avoid missing a source change during initialization.

### 4.5 MusicInfo is a separate firmware playback observation

The firmware `MusicInfo` Parcelable contains:

| Field | Type |
|---|---|
| `className` | String |
| `fileName` | String |
| `state` | String |
| `duration` | int |
| `position` | int |

The parcel order is the same as the listed order.

SourceService updates this object from media-status events when the event belongs to the currently selected source. The observed state values in the decompiled paths include values such as `start`, `paused`, `stopped`, and `stop`; source-switch/reset paths may clear the state and metadata.

`getMusicInfo()` performs a Binder call and returns a nullable `MusicInfo`. SourceInfo callback events carry the same type.

**Important distinction:** MusicInfo is firmware-reported source/media information. It is not the same API as Android `PlaybackState`, and it does not itself provide a transport-control object for an arbitrary player. It should be treated as source-side evidence and correlated with the relevant control API.

### 4.6 Source requests are active operations, not readiness checks

The SourceInfo overloads of `onRequestPlayAudio()` call the SourceService Binder method. The String and Context overloads append `/background`; the ComponentName overload passes the flattened component name.

SourceService posts this request into its activity/source-selection path as an `OnResume` event. This can change the selected source, audio routing, MCU source state, and notifications.

The Bluetooth service's `requestA2dp()` Binder wrapper specifically calls:

`onRequestPlayAudio("com.autochips.bluetooth/.BtMusicActivity")`

before scheduling the A2DP request.

Therefore:

- Do not call `onRequestPlayAudio()` just to ask whether a source is ready.
- Do not treat it as a passive getter.
- Do not use it automatically for every ACC ON transition without an explicit source-restoration decision.
- Treat `onRequestAuxAudio()` as an audio-routing operation as well.

### 4.7 SourcePolicy is routing/mix control, not player transport control

`SourcePolicy` and `ExtAudioMuxer` apply normal source muxing, navigation/music mixing, volume changes, mute/fade behavior, and related audio-path policy.

They do not expose a generic Pause/Play operation for an arbitrary Android player. Player transport must continue to use the correct player-specific control plane:

- Android MediaSession for ordinary Android media players.
- Vendor Bluetooth Binder for the built-in Bluetooth player.

---

## 5. Bluetooth service and Bluetooth player

### 5.1 Two APKs with distinct roles

The manifests and client source establish the following roles:

| Role | Package | Relevant component |
|---|---|---|
| Bluetooth UI/source application | `com.autochips.bluetooth` | `com.autochips.bluetooth.BtMusicActivity` |
| Bluetooth music Binder service provider | `com.hcn.bluetoothservice` | `com.hcn.bluetooth.service.BluetoothMusicService` |

The Bluetooth UI's `LocalBTMusicManager` binds to the service using the explicit component:

`com.hcn.bluetoothservice/com.hcn.bluetooth.service.BluetoothMusicService`

This is a correct package-role separation, not a genuine identity mismatch. SourceInfo should compare the selected source against `com.autochips.bluetooth`; Binder binding should target `com.hcn.bluetoothservice`.

The inspected BluetoothService manifest marks the service as exported and does not declare a service-level permission. The APK uses `android.uid.system`, is persistent, and declares minSdk 28 / targetSdk 31. Manifest metadata alone does not prove that the actual system_server caller can bind on the installed firmware; package, signature, UID, and SELinux behavior still require device confirmation.

### 5.2 Binder contract

Binder descriptor:

`com.hcn.bluetooth.service.IBluetoothMusicService`

Relevant transactions:

| Transaction | Method | Purpose |
|---:|---|---|
| 1 | `isA2dpConnected()` | A2DP connection snapshot |
| 2 | `isAvrcpConnected()` | AVRCP connection snapshot |
| 3 | `isA2dpPlaying()` | Current Bluetooth playback snapshot |
| 5 | `send_Avrcp_Cmd(int)` | Bluetooth playback command |
| 6 | `requestA2dp()` | Active A2DP/source request |
| 7 | `releaseA2dp()` | Release A2DP |
| 8 | `regMusicClientBinder(IBinder)` | Separate client-death registration |
| 9 | `unRegMusicClientBinder()` | Destructive client-Binder teardown |
| 10 | `registerBTMusicCallback(IMusicCallback)` | Register state callback |
| 11 | `unregisterBTMusicCallback(IMusicCallback)` | Unregister this callback |
| 12 | `getID3Info()` | Current metadata strings |

AVRCP command mapping:

| Value | Command |
|---:|---|
| 0 | PLAY |
| 1 | PAUSE |
| 2 | PLAY/PAUSE toggle |
| 3 | STOP |
| 4 | NEXT |
| 5 | PREVIOUS |
| 6 | FAST FORWARD |
| 7 | REWIND |

For Media restoration, use explicit PLAY and PAUSE commands, not PLAY/PAUSE toggle. A toggle depends on a sufficiently fresh state snapshot and can invert the intended action if the state changed between observation and command.

### 5.3 Callback contract and initial state

Callback descriptor:

`com.hcn.bluetooth.service.IMusicCallback`

Callbacks:

| Transaction | Callback | Payload | Delivery |
|---:|---|---|---|
| 1 | `onA2dpConnectStateChanged(int)` | Raw A2DP profile state | One-way |
| 2 | `onAvrcpConnectStateChanged(int)` | Raw AVRCP profile state | One-way |
| 3 | `onMetadataChanged(String,String,String)` | Title, artist, album | One-way |
| 4 | `onPlayStatusChanged(MusicPlayState)` | Playback Parcelable | Synchronous |

Connection state `2` represents CONNECTED. Playback state `3` represents PLAYING.

On callback registration, the vendor service immediately sends current metadata and current playback state. It does **not** initially send A2DP or AVRCP connection state callbacks. A client must query `isA2dpConnected()` and `isAvrcpConnected()` after registration to establish the initial connection snapshot, then rely on callback events for later changes.

The `MusicPlayState` parcel layout is:

1. state: int
2. position: long
3. duration: long
4. speed: float
5. updateTime: long
6. bufferedPosition: long

### 5.4 Service-side command prerequisites

The vendor implementation's `send_Avrcp_Cmd()` checks:

1. `mMediaController` is not null.
2. A2DP connection state equals `2`.
3. `MediaController.getTransportControls()` is not null.

It then dispatches PLAY/PAUSE/other commands through that controller's `TransportControls`.

The service obtains this internal controller through `MediaSessionManager`. It scans active sessions for a tag containing either:

- `A2dpMediaBrowserService`
- `BluetoothMediaBrowserService`

When such a session appears, it stores the corresponding MediaController and registers a callback.

**Readiness consequence:** A2DP connected and AVRCP connected are not a complete readiness guarantee. The vendor service also requires its internal tagged MediaController. The inspected Binder API does not provide a method that directly reports whether this internal controller has been acquired. The current caller can receive a successful Binder transaction even when the service method returns early because `mMediaController` is null; the method returns void and does not return a command-accepted status.

Consequently, “Binder call returned without RemoteException” is not proof that PLAY or PAUSE was delivered to the phone/player.

### 5.5 Vendor service can initiate its own source and playback actions

Two vendor behaviors matter for Media:

- When A2DP connects and `sys.sourcing.package` equals `com.autochips.bluetooth`, the Bluetooth service calls `requestA2dp()`.
- When its internal delayed handler receives the A2DP/AVRCP readiness message, it can send PLAY if `Utils.isBTMusicAutoPlay()` is enabled and SourceInfo still reports `com.autochips.bluetooth`.

This means the vendor service may perform source activation or autoplay independently of the custom Media controller. The exact device preference value and timing must be tested. A custom PLAY command can otherwise duplicate a vendor-generated PLAY.

The Binder `requestA2dp()` operation is not passive: its wrapper first asks SourceInfo to resume/select `com.autochips.bluetooth/.BtMusicActivity`, then schedules audio-focus/A2DP work. It must not be used as a readiness probe.

### 5.6 Safe callback teardown

Two methods have materially different behavior:

- `unregisterBTMusicCallback(callback)` removes the specified callback from the RemoteCallbackList.
- `unRegMusicClientBinder()` unlinks the separate client Binder, sends STOP if the cached state is PLAYING, and calls `releaseA2dp()`.

Normal listener cleanup must use callback unregistration and service unbinding. Do not call `unRegMusicClientBinder()` as generic cleanup.

The current Accessory-side client uses callback transaction 11 and does not invoke the destructive method 9. This is the correct teardown direction.

---

## 6. Android MediaSession path

The current `AccessoryPlaybackController.kt` uses the platform `MediaSessionManager` and `MediaController` APIs.

### 6.1 Existing behavior

On ACC OFF, it:

1. Queries active MediaSessions.
2. Excludes sessions whose package is `com.hcn.bluetoothservice`.
3. Selects the first remaining controller with `PlaybackState.STATE_PLAYING`.
4. Saves that controller's session token and package.
5. Checks for PAUSE or PLAY_PAUSE support.
6. Registers a callback on the controller.
7. Requests PAUSE.

On ACC ON, it searches for the saved token and package. It waits for active-session/playback callbacks if the exact session is not currently available, avoids sending PLAY when the session is already PLAYING, cancels restoration for STOPPED/ERROR or a destroyed session, and otherwise requests PLAY when the action is available.

This is a useful foundation: it uses MediaSession callbacks, identifies a session by token/package, and does not hard-code AIMP or another specific media player.

### 6.2 Mismatch with the Media concept

The conceptual requirement is to act on the player that corresponds to the selected Audio Source. The current code does not use SourceInfo to choose the MediaSession. It selects the first playing non-Bluetooth session returned by MediaSessionManager.

Potential result: a non-selected or unrelated active session could be paused while another source is actually selected.

The implementation also does not launch a player's main activity when the app process/session is absent. It waits for a session callback if a saved token exists; if the original session never returns, the controller cannot currently perform the concept's proposed process/activity recovery.

### 6.3 Restoration ownership bug

The current controller sets `mediaSessionWasPlaying = true` as soon as it finds a playing non-Bluetooth session, before confirming that the session supports PAUSE and before successfully requesting PAUSE.

If PAUSE is unsupported or the command fails, the flag can remain true even though no restorable token exists or no pause occurred.

The public query `hasMediaSessionForCurrentAccCycle()` returns true when this flag is true or a token was saved. AccessoryHook passes that value to the Bluetooth controller as `mediaSessionOwnsRestore`. The Bluetooth path can then skip PLAY and clear its own restoration request.

This creates two problems:

- An unrelated MediaSession can suppress a Bluetooth restore.
- A MediaSession that was not actually paused can still be treated as the owner of restoration.

The source-selection decision should determine which playback domain owns the ACC cycle. Ownership should not be inferred merely from the presence of some playing non-Bluetooth MediaSession.

---

## 7. Current Accessory integration and concrete gaps

The inspected Kotlin source is an auxiliary snapshot under `temp/mt8163_tools-development`. It currently contains three main components.

### 7.1 AccessoryHook.kt

Responsibilities:

- Hooks the existing MCU ACC receiver.
- Synchronizes the current ACC state when the module is enabled.
- Controls MediaSession and Bluetooth clients.
- Reads SourceInfo at ACC OFF.
- Publishes state and diagnostic events.

Current limitations:

- It reads SourceInfo only once at ACC OFF.
- It does not register `SourceInfo.OnEventListener`.
- It uses reflective `findClass("android.sourceservice.SourceInfo", null)`; the appropriate class-loader behavior must be verified on the actual system_server/firmware environment.
- It determines MediaSession restoration ownership before invoking the MediaSession ACC ON callback, then passes that ownership flag into Bluetooth restoration.

### 7.2 AccessoryPlaybackController.kt

Current gaps:

- Selects the first playing non-Bluetooth session, not a session matched to the selected SourceInfo package.
- Marks `mediaSessionWasPlaying` before validating PAUSE support and successful command delivery.
- The flag can remain true when no token was saved or PAUSE failed.
- Does not implement the concept's proposed app/process launch recovery.
- Keeps the saved token/package in memory only; process/module lifecycle loss clears that state.
- The Bluetooth exclusion is based on the service package, which is appropriate for the vendor service's own session but should be retained alongside source-domain selection rather than used as the sole source classifier.

### 7.3 VendorBluetoothMusicClient.kt

Current gaps and contradictions:

- The class comment says “read-only observer” and says it never issues AVRCP commands. The implementation does issue AVRCP PLAY during ACC ON restoration.
- It snapshots Bluetooth playback at ACC OFF only if SourceInfo says `com.autochips.bluetooth` and the Binder service is already available.
- If the asynchronous service bind has not completed at that moment, it treats Bluetooth as not playing for restoration. There is no later ACC OFF snapshot retry.
- It does not issue AVRCP PAUSE at ACC OFF.
- It requires A2DP connected, AVRCP connected, and a known playback state before sending PLAY, but cannot verify the vendor service's internal `mMediaController` readiness.
- It marks the command as sent after calling the void Binder method; the service may have silently returned without issuing the command if its internal controller is unavailable.
- It can skip Bluetooth restoration based on `mediaSessionOwnsRestore`, coupling two playback domains that should be classified independently.
- The existing service connection/death/callback lifecycle is event-driven, and normal teardown uses the exact callback-unregister transaction.

### 7.4 Main-looper synchronous Binder constraint

The Bluetooth client performs service registration, state queries, unregistration, and command transactions from the system_server main looper in the current implementation. These are synchronous Binder calls. A slow or blocked vendor service could therefore affect system_server responsiveness.

This is a known architectural trade-off. Measure transaction latency on the target head unit. Do not add a worker thread, executor, timer, or polling loop without explicitly changing the project's event-driven constraint.

---

## 8. Recommended Media behavior model

The following is a technical recommendation based on the inspected firmware and current source. It is not an implementation change.

### 8.1 Determine the source domain before selecting a playback controller

Use SourceInfo/SourceService to determine the currently selected source. Classify the source into one of the two Media playback domains:

- **Bluetooth source:** selected source is `com.autochips.bluetooth`.
- **Android Media Player source:** selected source identifies an ordinary media application that has a corresponding active MediaSession.

Do not select a controller based only on “some active MediaSession exists.” Do not infer Bluetooth playback from A2DP connection alone.

If the selected source or its playback state is unknown, do not issue an unrelated Pause/Play command. Retain an explicit UNKNOWN state rather than converting missing information into “not playing.”

### 8.2 Establish a source snapshot at ACC OFF

At the ACC OFF transition, capture the state before sending a transport command.

For a Bluetooth source:

- Capture the selected source.
- Query `isA2dpPlaying()` from the vendor service if it is bound and responsive.
- Record A2DP and AVRCP connection state separately.
- If playback is confirmed, use explicit AVRCP PAUSE command 1.
- Preserve the distinction between command requested and PAUSED state confirmed by the callback.

For an Android Media Player source:

- Match the selected package to its MediaSession controller.
- Confirm `STATE_PLAYING`.
- Save the exact token/package identity.
- Check PAUSE support.
- Request PAUSE and wait for the callback confirmation.

Do not mark a playback domain as owning restoration merely because a player was discovered. The snapshot must distinguish at least: source identity, observed playing state, command requested, and resulting state confirmation.

### 8.3 Restore only the source that was playing before ACC OFF

On ACC ON, use the saved source snapshot:

- If the source was not playing before ACC OFF, do not send PLAY.
- If the source was Bluetooth, restore through the Bluetooth service path only.
- If the source was an Android Media Player, restore the matching MediaSession only.
- Do not wait for Bluetooth readiness before attempting MediaSession restoration.
- Do not let an unrelated MediaSession suppress Bluetooth restoration.
- If the original controller/session is unavailable, retain a pending state only while a defined event can resolve it. Do not substitute a different player or source.

### 8.4 Bluetooth readiness requires a further decision

The current Binder surface exposes A2DP connection, AVRCP connection, and playback state, but not the readiness of the vendor service's internal Bluetooth MediaController.

Before finalizing Bluetooth restoration behavior, determine whether the required readiness can be established using:

- the vendor callbacks and initial snapshot;
- the Bluetooth-tagged MediaSession becoming active;
- a source/player readiness callback from the vendor service; or
- a target-device test that proves the exact safe command timing.

Do not assume that A2DP + AVRCP connected means the internal MediaController is ready. Do not use `requestA2dp()` as a probe because it actively selects/requests the Bluetooth source.

Also inspect the head unit's BT auto-play setting. The vendor service can send PLAY itself after reconnect when Bluetooth is still the selected source. Media must avoid issuing a duplicate PLAY or fighting the vendor policy.

### 8.5 Process readiness for Android media players

A running process is not equivalent to an active and controllable MediaSession. The actual readiness condition should be the return of the correct session/controller and its advertised transport actions.

The existing controller can wait for MediaSession callbacks but does not implement application launch/recovery. Before adding that behavior, define:

- when launching the app is allowed;
- which activity is the appropriate entry point;
- how a launched app is matched to the original saved source;
- which event proves that its MediaSession is ready;
- what happens if the app was removed, disabled, or cannot create a session.

Never send PLAY to a different package/session simply because the original is absent.

### 8.6 One coordinator, independent transport adapters

A single Media coordinator can own the ACC-cycle snapshot and source decision while delegating transport operations to separate adapters:

- Android MediaSession adapter.
- Vendor Bluetooth Binder adapter.

The adapters must not decide source ownership independently. The coordinator should select one source domain for the ACC cycle and must not treat a connection state, a source package, or a MediaSession as substitutes for one another.

This preserves the original requirement of one shared Media Controller without merging the two transport protocols.

---

## 9. Documentation reconciliation

The following older statements should be corrected when the concept is updated:

| Existing document/claim | Audit finding |
|---|---|
| SourceInfo property freshness and writer mapping are still largely unknown | The checked-in SourceService has four statically identifiable property write sites. Runtime timing is still unverified. |
| Bluetooth package identity is a discrepancy | The two names have distinct, confirmed roles: source/UI APK versus Binder service APK. |
| VendorBluetoothMusicClient is read-only | Current Kotlin source sends AVRCP PLAY. It does not yet send PAUSE. |
| Bluetooth is an independent read-only observer | It is not read-only in the current source; ACC ON can issue PLAY. |
| A2DP/AVRCP readiness is sufficient to issue PLAY | Vendor service also requires its internal tagged MediaController. |
| MediaSession owns restoration when a non-Bluetooth playing session exists | Current flag can be set without successful PAUSE and can incorrectly suppress Bluetooth restoration. |
| SourceInfo is only read once | That is current Accessory integration behavior, not a limitation of the firmware API; SourceInfo callbacks are available but unused. |

`docs/PROJECT_COMPREHENSIVE_SUMMARY.md` is a dated project snapshot (2026-11-20), not a current source of truth. It contains some of the older statements above. `reports/analysis_report.md` also records unresolved SourceInfo freshness and package-role ambiguity that this source-level audit has refined. These older reports should not be silently treated as current conclusions.

---

## 10. GitHub Actions and artifact verification

The relevant workflow runs were inspected:

| Workflow | Run | Result | Relevant details |
|---|---:|---|---|
| Analyze MT8163 framework MCU services | [Run #11](https://github.com/14vvp93/public_worker/actions/runs/44771655098) | Success | VDEX extraction, JADX analysis, report publication, and artifact upload all completed |
| Decompile BluetoothService APK | [Run #2](https://github.com/14vvp93/public_worker/actions/runs/44747195113) | Success | APK verification, JADX decompilation, build metadata, file index, and output commit completed |
| Decompile CarServices VDEX | [Run #15](https://github.com/14vvp93/public_worker/actions/runs/44900833624) | Partial failure | Input inspection, VDEX extraction, CompactDEX conversion, JADX decompilation, and artifact upload succeeded; the final pull-request creation step failed |

The latest CarServices run's overall failure must not be interpreted as a failed decompilation. Its decompile job completed the extraction/conversion/JADX and artifact-upload steps; only the PR creation step failed.

The checked-in BluetoothService analysis records JADX exit code 3 and 68 decompiler errors. Its relevant service, Binder, callback, Parcelable, and manifest files are present and were inspected. This is usable static evidence for those classes, but not proof that every decompiled class is complete or that the artifact exactly matches the APK installed on the head unit.

No new GitHub Action was started because the necessary decompiled inputs and outputs already exist. No workflow was modified.

---

## 11. Verification still required on the MT8163 head unit

Static reverse engineering is not runtime validation. The following checks remain necessary:

1. Confirm installed package names, APK hashes, signing certificates, and the exact exported Bluetooth service component.
2. Confirm the SourceInfo singleton and `sourceinfo` Binder service resolve from the actual system_server class-loader context.
3. Register SourceInfo callbacks and verify source-change and MusicInfo event ordering during manual switching, automatic switching, playback, pause, stop, and ACC transitions.
4. Verify that `sys.sourcing.package` and `getMusicInfo()` reflect the expected source/state at the exact ACC OFF boundary.
5. Verify A2DP and AVRCP callback values and the initial snapshot after Bluetooth service binding.
6. Determine when the vendor internal Bluetooth MediaController becomes available and how that readiness can be observed safely.
7. Verify explicit AVRCP PAUSE and PLAY on the installed firmware, including vendor auto-play behavior and duplicate-command risk.
8. Verify that ordinary MediaSession pause/restore targets only the session associated with the selected source.
9. Test a missing/destroyed MediaSession and define the allowed application-launch recovery behavior.
10. Measure synchronous Binder transaction latency from system_server.
11. Verify all transitions with more than one Android media player and with Bluetooth selected.
12. Record firmware build identity and compare installed APK hashes against the checked-in artifacts.

Useful on-device inspection commands already identified in the Bluetooth analysis include:

`pm path com.hcn.bluetoothservice`

`dumpsys package com.hcn.bluetoothservice`

`dumpsys activity services com.hcn.bluetoothservice`

---

## 12. Final assessment and next documentation decisions

The repository provides a concrete firmware source-selection mechanism, a separate Bluetooth playback Binder mechanism, and an Android MediaSession mechanism. The conceptual Media design is technically achievable without inventing a new source detector.

Before updating `MEDIA_MODULE.md` with implementation details, the following decisions should be made explicit:

1. **Source authority:** SourceInfo selected source is the primary source-domain selector; MediaSession and Bluetooth APIs provide playback/control state for their respective domains.
2. **SourceInfo event model:** use source-change and MusicInfo callbacks for ongoing updates, with a defined initial-snapshot sequence.
3. **ACC OFF snapshot:** record the selected source and its actual playback state before issuing Pause.
4. **Command confirmation:** distinguish a requested Pause/Play from a state transition confirmed by the relevant callback.
5. **MediaSession matching:** bind the selected source package to the correct session; do not use the first arbitrary playing session.
6. **Bluetooth readiness:** resolve the missing internal MediaController-ready signal or define a proven event-driven readiness condition.
7. **Independent restoration:** remove the conceptual dependency where a MediaSession can suppress Bluetooth restoration. The selected source, not an unrelated active session, determines which playback path owns restoration.
8. **Vendor auto-play:** define how the custom controller coexists with the Bluetooth service's built-in auto-play policy.
9. **Missing player behavior:** define whether and how an Android player's activity may be relaunched when its original session is absent.
10. **Unknown state:** if source or playback state cannot be established, do not issue a speculative transport command.

**No implementation change is authorized by this report.** These findings are the technical basis for the next revision of the Media concept. Code and existing workflows remain unchanged.
