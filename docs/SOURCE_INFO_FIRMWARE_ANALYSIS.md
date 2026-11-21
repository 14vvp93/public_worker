# Firmware SourceInfo Analysis

**Status:** initial reverse-engineering findings documented; source-selection semantics and property freshness are not yet fully verified  
**Target:** Android 9 (API 28), MediaTek MT8163  
**Repository:** `14vvp93/mt8163_tools`  
**Analysis-only source:** `14vvp93/public_worker`  
**Firmware analysis workflow run:** [44771655098](https://github.com/14vvp93/public_worker/actions/runs/44771655098)  
**Analysis commit:** [1b0651063da1c0fa5ec866c6ecf279d79b5fb551](https://github.com/14vvp93/public_worker/commit/1b0651063da1c0fa5ec866c6ecf279d79b5fb551)

## 1. Purpose

This document records the currently established firmware API for identifying the active audio/source package through the vendor `android.sourceservice` implementation.

The immediate motivation is the Accessory module: before restoring Bluetooth playback after ACC ON, it must distinguish Bluetooth music from playback owned by another media player. The vendor SourceInfo API may provide a more direct source identity than Android MediaSession package inspection.

This document records firmware analysis and the initial read-only SourceInfo integration in the Accessory ACC OFF path. Runtime verification on the target device remains pending.

## 2. Evidence and source boundaries

The findings below come from firmware framework/service disassembly produced by the analysis workflow and from the decompiled Bluetooth vendor APK.

Relevant analysis artifacts:

- `boot-carservices-disassembly.txt`
- `boot-framework-disassembly.txt`
- `boot-mediatek-framework-disassembly.txt`
- `oat_arm_services-disassembly.txt`
- `oat_arm_mediatek-services-disassembly.txt`
- `MCU-REPORT.md`
- `raw-strings.txt`

The workflow artifact is named `mt8163-framework-mcu-analysis` and belongs to run [44771655098](https://github.com/14vvp93/public_worker/actions/runs/44771655098).

The source files in `public_worker` are analysis inputs and outputs only. Product code and maintained project documentation belong in `mt8163_tools`.

## 3. Service registration

The firmware's `android.carserver.carservices` startup code constructs:

```java
new SourceService(context);
ServiceManager.addService("sourceinfo", srcService);
```

Therefore:

- Binder service name: `sourceinfo`
- Server implementation: `android.sourceservice.SourceService`
- Implementation location: `boot-carservices`
- This is not a service implemented by the ordinary `services.jar` code path.

## 4. SourceInfo client API

Class: `android.sourceservice.SourceInfo`

The class is a client-side singleton. Its `getInstance()` method initializes a callback token and handler, resolves the Binder named `sourceinfo` through `ServiceManager.getService("sourceinfo")`, and converts it to `ISourceService`.

If the Binder service cannot be found, initialization throws:

```text
RuntimeException("can not find sourceinfo serivce")
```

### 4.1 Public methods identified

```java
getInstance()
getMusicInfo()
getSourcePackage()
onRequestAuxAudio(String, boolean, boolean)
onRequestPlayAudio(ComponentName)
onRequestPlayAudio(Context)
onRequestPlayAudio(String)
setInputPathMute(int, boolean)
setOnEventListener(SourceInfo.OnEventListener)
```

### 4.2 getSourcePackage()

The decompiled implementation is:

```java
public String getSourcePackage() {
    return SystemProperties.get("sys.sourcing.package");
}
```

This is a direct read of the system property `sys.sourcing.package`; it does not perform a Binder request.

**Important limitation:** the getter's implementation is established, but the property is not yet proven to be refreshed on every source transition. A confirmed write exists in one stop-status branch (see section 6). Do not assume that a read at ACC OFF always represents the currently active source until all property writers and source-transition paths have been traced and verified on the device.

### 4.3 getMusicInfo()

`getMusicInfo()` calls `mService.getMusicInfo()` through Binder and returns a `MusicInfo` value. The client returns null when a `RemoteException` is caught.

This is a second potential source of playback/source metadata. Its exact fields, update semantics, and relationship to the active source still require full analysis.

### 4.4 Playback request methods

The client exposes several `onRequestPlayAudio` overloads:

- `onRequestPlayAudio(ComponentName)` flattens the component using `flattenToShortString()` and forwards the resulting string to Binder.
- `onRequestPlayAudio(Context)` obtains the context package name, appends `/background`, and forwards it.
- `onRequestPlayAudio(String)` appends `/background` and forwards it.

These are source/audio routing requests, not evidence that the client should use them to restore Bluetooth playback. Their server-side policy and side effects must be understood before any use.

## 5. Binder interface: ISourceService

Interface: `android.sourceservice.ISourceService`

Methods identified from the Binder interface:

```java
doExtAudioEffect(int, int[])
getMusicInfo()
onRequestAuxAudio(String, boolean, boolean)
onRequestPlayAudio(String)
removecallback(ISourceCallback)
setInputPathMute(int, boolean)
setcallback(ISourceCallback)
```

The server implementation `SourceService` extends `ISourceService.Stub`.

The callback list is held by the service in `mCallBacks`; callback registration/removal synchronizes access to the list.

## 6. SourceService server implementation

Class: `android.sourceservice.SourceService`

The constructor initializes, among other fields:

- `TOP_ACTIVITY_PROPERTY = "sys.top.activity"`
- `mAudioFocusPackage` (`AtomicReference`)
- `mAudioMixPackage` (`AtomicReference`)
- `mAudioVoicePackage` (`AtomicReference`)
- `CurrentSource` (initially empty string)
- `mCurrentClass` (initially empty string)
- `mMusicInfo`
- `mPerpareToKill` (`AtomicReference`)
- `mTopPackage`
- `mAudioPathStatus` (`EnumSet`)
- `mPolicy` (`SourcePolicy`)
- fade-related handlers and observers.

Methods identified include:

- `getMusicInfo()`
- `onRequestPlayAudio(String)`
- `onTransact(int, Parcel, Parcel, int)`
- `setcallback(ISourceCallback)`
- `removecallback(ISourceCallback)`
- `onRequestAuxAudio(String, boolean, boolean)`
- `setInputPathMute(int, boolean)`
- `doExtAudioEffect(int, int[])`
- `dump(...)`

Private methods identified include:

- `IsSourceActivity(String)`
- `abortMix(String, int)`
- `audioFadein()`, `audioFadeout()`
- `callManager(MusicInfo)`, `callManager(String, int)`
- `forceStopPackage(String)`
- `isAuxAudioEnable()`
- `muxMediaPlayer(String, int)`
- `onActivityStatus(String, String, int, String)`
- `onMediaStatus(String, int, String, int)`
- `onFocusStateChange(String, int, int, int, int)`
- `setOnActivityResume(String)`
- `setSourceChanged4Camerad(String)`
- `setTopActivity4Camerad(String)`
- `voiceFocus(boolean, String, boolean)`

`onRequestPlayAudio(String)` posts work to `mFadeHandler`; it is processed asynchronously.

### 6.1 Confirmed system-property write

The disassembly contains this write:

```java
SystemProperties.set("sys.sourcing.package", CurrentSource);
```

The confirmed occurrence is in an `onTransact()` branch handling a `"stopped"` status for a source matching `CurrentSource` by `startsWith`.

That branch also:

- resets duration and position;
- clears `MusicInfo.fileName` and `MusicInfo.className`;
- sets `MusicInfo.state = "stop"`;
- calls `SourcePolicy.onSessionChanged(...)`;
- calls `callManager(CurrentSource, 102)`.

This proves that the property is written in this stop-handling path. It does **not** prove that this is the only writer, nor that all source changes update the property.

### 6.2 Unresolved property freshness

Before using `getSourcePackage()` as the authoritative ACC OFF snapshot, trace every assignment to `CurrentSource` and every write to `sys.sourcing.package`.

In particular, determine whether source transitions update the property in:

- `onTransact()` branches other than the confirmed stop branch;
- `onActivityStatus(...)`;
- `onMediaStatus(...)`;
- `setOnActivityResume(...)`;
- `setSourceChanged4Camerad(...)`;
- callback/lambda paths and observers.

Do not infer property freshness from the getter or from the existence of a single setter.

## 7. SourceInfo callback path

The client owns:

- `mToken: ISourceCallback`
- `mHandler: Handler`
- `mService: ISourceService`
- singleton `sSourceInfo`
- `mOnEventListener`.

The Binder callback implementation (`SourceInfo$1`) receives service callbacks and forwards messages to the handler. The handler implementation (`SourceInfo$2.handleMessage(Message)`) dispatches callbacks to the registered `OnEventListener`; a source-change callback invokes `onSourceChanged(String)`.

This establishes an event-driven callback path in the API. The full callback message mapping, event payload semantics, registration lifecycle, and exact set of events still need to be extracted before integration.

## 8. ISourceInfo transaction token is distinct from ISourceService

The framework disassembly also contains the interface token:

```text
android.sourceservice.ISourceInfo
```

This token appears in framework-originated Binder transactions handled by `SourceService.onTransact()`. It is not the same interface as `android.sourceservice.ISourceService`.

The currently identified special transaction codes handled by `SourceService.onTransact()` are:

```text
101, 201, 301, 401, 501, 601, 701
```

Prior inspection associated transaction 101 with `DESTROY` and 501 with `HIDE`, but the complete transaction payloads and sender-side methods have not yet been documented here. Treat those labels as provisional until the full switch and corresponding framework callers are cross-checked.

Do not conflate the `ISourceInfo` transaction token with the generated `ISourceService` Binder contract.

## 9. Bluetooth vendor APK usage

The decompiled vendor APK contains a direct consumer of this API:

File: `artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/service/BluetoothMusicService.java`

The service obtains:

```java
private SourceInfo mSourceInfo = SourceInfo.getInstance();
```

Before issuing its AVRCP PLAY command, it checks:

```java
String sourcePackage = mSourceInfo.getSourcePackage();
if ("com.autochips.bluetooth".equals(sourcePackage)) {
    BluetoothMusicService.this.send_Avrcp_Cmd(0);
}
```

This establishes that the vendor Bluetooth music service itself reads `SourceInfo.getSourcePackage()` and compares the value with:

```text
com.autochips.bluetooth
```

It is strong evidence that this package string is meaningful to the firmware's Bluetooth source-selection logic. It does not establish the complete set of valid source values or guarantee that the property is current at every point in time.

A second consumer, `artifacts/BluetoothService/jadx/sources/com/hcn/auto_compat/PlatformUtils.java`, uses reflection to resolve:

- `SourceInfo.getInstance()`
- `onRequestPlayAudio(Context)`
- `onRequestPlayAudio(String)`.

Analysis-only source links:

- [BluetoothMusicService.java](https://github.com/14vvp93/public_worker/blob/main/artifacts/BluetoothService/jadx/sources/com/hcn/bluetooth/service/BluetoothMusicService.java)
- [PlatformUtils.java](https://github.com/14vvp93/public_worker/blob/main/artifacts/BluetoothService/jadx/sources/com/hcn/auto_compat/PlatformUtils.java)

## 10. Accessory integration constraints

The intended Accessory behavior remains:

1. At the ACC OFF event, obtain the Bluetooth music playback state from the vendor Bluetooth Binder service at that moment; do not maintain a continuously refreshed playback-state cache.
2. Determine whether Bluetooth music or a third-party media player owned playback before ACC OFF.
3. Restore Bluetooth playback only when Bluetooth music itself was playing.
4. If a third-party player was active, do not send Bluetooth PLAY/AVRCP PLAY.
5. Keep Events as an observer/logger. Accessory owns decisions and reports observed state/events to Events.
6. Keep the implementation event-driven; do not poll SourceInfo or the Bluetooth service.

An initial SourceInfo source check is now integrated into the Accessory ACC OFF path. The hook calls `SourceInfo.getInstance().getSourcePackage()` and treats only the exact package `com.autochips.bluetooth` as the Bluetooth source. The Bluetooth Binder playback snapshot is skipped for every other or unknown source. This is an initial implementation; runtime behavior and source freshness still require verification on the target device. Existing MediaSession and Bluetooth Binder responsibilities remain separate.

## 11. Remaining reverse-engineering work

The next analysis pass must extract complete method bodies and cross-references for:

1. `SourceService.onActivityStatus(...)`.
2. `SourceService.onMediaStatus(...)`.
3. All branches of `SourceService.onTransact(...)`, including transaction codes, interface tokens, parcel argument order, and return behavior.
4. `SourceService.setOnActivityResume(...)`.
5. `SourceService.setSourceChanged4Camerad(...)`.
6. Every assignment to `CurrentSource`.
7. Every write to `sys.sourcing.package`.
8. `SourceInfo$2.handleMessage(Message)` and the complete `SourceInfo$1` callback implementation.
9. `SourcePolicy` behavior and `MusicInfo` fields/state semantics.
10. The source values and transitions as proven by assignments and call sites, not strings alone.

Then verify on the MT8163 device:

- whether `getSourcePackage()` changes on every relevant source switch;
- what it returns during active playback, pause, stop, source handoff, and ACC OFF;
- whether the callback reports source changes reliably and in what order;
- whether `getMusicInfo()` is consistent with the property and callback state.

## 12. Current conclusions

| Finding | Status |
|---|---|
| System Binder service is registered as `sourceinfo` | Confirmed in disassembly |
| Server implementation is `android.sourceservice.SourceService` | Confirmed in disassembly |
| Client getter reads `sys.sourcing.package` | Confirmed in disassembly |
| Property is written during one matching source-stop path | Confirmed in disassembly |
| Property changes on manual and automatic source switches | Observed on the target device; complete writer tracing remains pending |
| `SourceInfo` exposes a source-change callback path | Confirmed at API/dispatch level; full semantics pending |
| Bluetooth vendor service checks for `com.autochips.bluetooth` before AVRCP PLAY | Confirmed in APK decompilation |
| Complete list of source package values | Not established |
| SourceInfo is safe as the sole ACC OFF source snapshot | Not established |
| ACC OFF SourceInfo package check | Integrated; target-device runtime verification pending |

The initial ACC OFF SourceInfo check is integrated in the product code. Kotlin compilation and target-device runtime verification are not yet confirmed.


## 13. SourceInfo state-preservation role

**Project clarification (2026-11-20):** the SourceInfo API is used for preserving/restoring the selected source state. This role must be kept distinct from the Accessory module's MediaSession playback-state tracking and the vendor Bluetooth playback state.

The relevant client API surface is:

| Method | Known purpose / behavior |
|---|---|
| `getSourcePackage()` | Reads the currently reported source package from `sys.sourcing.package`. |
| `getMusicInfo()` | Retrieves the vendor `MusicInfo` object through Binder. Its exact state fields and semantics still require analysis. |
| `onRequestPlayAudio(String)` | Requests audio/source activation for the supplied package; the client appends `/background` before forwarding. |
| `onRequestPlayAudio(Context)` | Requests audio/source activation for the context package with `/background` appended. |
| `onRequestPlayAudio(ComponentName)` | Requests audio/source activation for the supplied component after flattening it to a short component string. |
| `setOnEventListener(...)` | Registers the client listener used to receive SourceInfo events, including the source-change callback path. |

The methods above are part of the vendor SourceInfo contract. Their presence alone does not establish that every method must be called during every ACC transition. In particular, `onRequestPlayAudio(...)` is a request to the vendor source service; do not treat it as a passive getter or call it without confirming the desired source-restoration behavior.

### 13.1 State domains

Keep these state domains separate:

1. **Source selection state** — which source/package is selected by the head unit. SourceInfo is the vendor API associated with preserving and requesting this state.
2. **Player playback state** — whether an individual player is playing, paused, stopped, or has lost its session. MediaSession state applies to Android media players and is not a substitute for SourceInfo source selection.
3. **Bluetooth transport/control state** — A2DP connection/playback and AVRCP readiness/state, obtained from the vendor Bluetooth service. Bluetooth must not be routed through MediaSession restoration.

A selected source does not prove that its audio is currently playing. Likewise, a playing MediaSession does not by itself prove that the head unit's selected source should be changed.

### 13.2 Integration boundary

For Accessory design, treat SourceInfo as the source-state preservation/request mechanism and keep Bluetooth playback restoration on the vendor Bluetooth service path. Do not conflate SourceInfo source restoration with sending AVRCP PLAY or with restoring an Android MediaSession.

The exact ACC OFF/ON call sequence, whether the SourceInfo singleton internally retains state, and which of its methods are required to restore a previously selected source must be established from the complete implementation and target-device behavior before changing production code. The existing analysis of property freshness and callback semantics remains applicable.

No SourceInfo integration or behavior change is claimed by this documentation update.


## 14. Initial Accessory source check implementation

The Accessory hook now reads the source package at the ACC OFF handling point through the vendor client API:

```kotlin
val sourceInfoClass = XposedHelpers.findClass(
    "android.sourceservice.SourceInfo",
    null
)
val sourceInfo = XposedHelpers.callStaticMethod(sourceInfoClass, "getInstance")
val sourcePackage = XposedHelpers.callMethod(
    sourceInfo,
    "getSourcePackage"
) as? String
```

The result is compared against the exact Bluetooth source package observed in the vendor Bluetooth service:

```text
com.autochips.bluetooth
```

Behavior:

- Exact match: query the vendor Bluetooth music service for `isA2dpPlaying()` and allow Bluetooth restoration to be considered.
- Any other package: do not query the Bluetooth playback state for this ACC cycle and do not arm Bluetooth restoration.
- Null, empty value, missing SourceInfo class/service, or reflection failure: treat source as unknown and fail closed; Bluetooth restoration is not armed.
- The source snapshot is emitted as an Accessory playback event for diagnostics.
- This check does not use MediaSession to identify Bluetooth and does not change source routing.
- The non-Bluetooth MediaSession restoration path remains independent.

The check is deliberately read-only. It does not call `onRequestPlayAudio(...)`, because those methods request source/audio activation and are not passive source getters.

**Verification boundary:** the user has observed `sys.sourcing.package` changing on both manual and automatic source switches on the target head unit. The new hook path has not yet been confirmed by a Kotlin build or a real-device ACC OFF/ON test. Also, all firmware writers of the property have not yet been exhaustively traced.


## 15. Implementation record — ACC OFF source classification

**Implementation commit:** `ef56c5b6e872a504bdc0b677b407137eb57c5d4e` (`AccessoryHook.kt`) and `88f3ccaf61bf5a9da5b97584c7be3cd5e9e1d410` (`VendorBluetoothMusicClient.kt`).

At ACC OFF, `AccessoryHook` now:

1. Resolves `android.sourceservice.SourceInfo` using the Xposed class lookup.
2. Calls the static `getInstance()` method.
3. Calls `getSourcePackage()` on the returned instance.
4. Trims the returned string and treats null/empty values as unknown.
5. Publishes a `source_snapshot` playback event containing the package and whether it matches the Bluetooth source package.
6. Passes the snapshot to `VendorBluetoothMusicClient`.

The Bluetooth client only queries `isA2dpPlaying()` when the supplied source package exactly matches `com.autochips.bluetooth`. For all other values, including unknown/null, it records that the query was skipped and clears the pre-ACC Bluetooth-playing flag. Thus an unknown source cannot arm Bluetooth restoration.

The ACC ON path does not perform a second SourceInfo read in this implementation. This is an ACC OFF snapshot, not continuous source monitoring. No polling, timer, or SourceInfo event listener was added.

### 15.1 Diagnostics expected in Events

- `source_snapshot`: source package and Bluetooth classification captured at ACC OFF.
- `bluetooth_acc_off_snapshot`: either the A2DP playing result, an unavailable-service/error result, or an explicit skip because the source did not match Bluetooth.

### 15.2 Known limitations

- The runtime class/method lookup and invocation have not yet been verified on the target MT8163 after this change.
- Kotlin compilation has not yet been confirmed.
- `getSourcePackage()` reads `sys.sourcing.package`; all property writers and freshness on every transition are not yet exhaustively established. The user has observed the property changing on manual and automatic source switches, but that does not prove every edge case.
- The Bluetooth package comparison is exact and currently uses `com.autochips.bluetooth`, based on the vendor Bluetooth service's own SourceInfo check.
- The existing Bluetooth ACC ON path still receives the `mediaSessionOwnsRestore` flag and can skip Bluetooth restoration when that flag is true. This is a separate existing behavior and does not belong to the SourceInfo source check. It conflicts with the intended rule that Bluetooth restoration must be independent of MediaSession; it should be corrected separately rather than silently described as independent.
