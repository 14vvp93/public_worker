# Media Module — Test Matrix

**Status:** Initial validation matrix  
**Related documents:** MEDIA_MODULE.md and MEDIA_MODULE_ANALYSIS.md  
**Target:** MT8163 head unit, Android 9 (API 28)  
**Scope:** Functional behavior, event ordering, race conditions, lifecycle, and runtime verification  
**Code status:** Tests only; no implementation changes are authorized by this document.

## 1. Test principles

1. SourceInfo selected-source identity determines which playback path owns the ACC cycle.
2. Playback state is observed independently from selected-source identity.
3. Capture the pre-ACC-OFF state before sending Pause.
4. Restore only the source recorded at ACC OFF.
5. Never send Play when the saved state was not playing.
6. A command request is not proof of a state transition; record requests and callback-confirmed state separately.
7. Readiness must be event-driven. Do not use polling or timer-based checks.
8. Unknown source, unknown playback state, or unconfirmed readiness must not trigger speculative commands.
9. Record firmware build, installed package versions/hashes, and diagnostic logs for every test.

## 2. ACC transition tests

| ID | Initial condition | Event | Expected result |
|---|---|---|---|
| ACC-01 | SourceInfo identifies selected Media Player; matching session is PLAYING | ACC OFF | Snapshot records package and PLAYING before Pause; Pause targets that session only |
| ACC-02 | Selected Media Player session is PAUSED | ACC OFF | No Pause and no later Play |
| ACC-03 | SourceInfo identifies Bluetooth; vendor service reports playback | ACC OFF | Snapshot records Bluetooth and PLAYING before vendor PAUSE command 1 |
| ACC-04 | Bluetooth is selected but not playing | ACC OFF | No PAUSE and no later PLAY |
| ACC-05 | SourceInfo reports no supported source | ACC OFF | No transport command; state remains explicitly non-restorable or UNKNOWN |
| ACC-06 | Media Player was PLAYING and Pause was requested | ACC ON; matching player becomes ready | Resume only the saved Media Player |
| ACC-07 | Bluetooth was PLAYING and Pause was requested | ACC ON; Bluetooth becomes fully ready | Resume only Bluetooth |
| ACC-08 | Saved source was not playing | ACC ON | No Play command to either source |
| ACC-09 | Selected Media Player has no playing session; another package has a playing session | ACC OFF | Do not pause the unrelated session |
| ACC-10 | Source or playback state is UNKNOWN | ACC OFF | No speculative Pause; record diagnostic reason |

## 3. Source isolation tests

| ID | Initial condition | Event | Expected result |
|---|---|---|---|
| SRC-01 | Media Player saved; Bluetooth is connected and ready | ACC ON | Restore Media Player only |
| SRC-02 | Bluetooth saved; unrelated Android MediaSession is active | ACC ON | Restore Bluetooth only |
| SRC-03 | Both transport paths become ready | ACC ON | At most one Play request, for the saved source |
| SRC-04 | Saved Media Player unavailable; Bluetooth ready | ACC ON | No Bluetooth fallback |
| SRC-05 | Saved Bluetooth unavailable; Media Player ready | ACC ON | No Media Player fallback |
| SRC-06 | SourceInfo cannot identify selected source | ACC OFF | No source-specific command |
| SRC-07 | SourceInfo changes after snapshot capture | Restore pending | Do not redirect restore to the newly selected source |
| SRC-08 | Multiple non-Bluetooth MediaSessions are active | ACC OFF | Match by SourceInfo package; do not select the first arbitrary playing session |
| SRC-09 | Both Bluetooth packages are present | Source classification | Treat com.autochips.bluetooth as source/UI package and com.hcn.bluetoothservice as Binder service |

## 4. Event ordering and race-condition tests

| ID | Event sequence | Expected result |
|---|---|---|
| RC-01 | ACC OFF → ACC ON before Pause callback | Preserve original pre-OFF snapshot; late Pause confirmation must not overwrite it |
| RC-02 | Restore starts → a new ACC OFF arrives | Invalidate previous restore generation; stale callbacks cannot initiate Play |
| RC-03 | Duplicate ACC OFF for the same physical transition | Do not replace snapshot with post-Pause state or issue another Pause |
| RC-04 | Duplicate ACC ON for the same transition | No duplicate Play |
| RC-05 | Readiness callback from an earlier cycle arrives during a newer cycle | Ignore using cycle/generation identity |
| RC-06 | SourceInfo changes while restore is pending | Do not redirect pending restore; apply the explicitly approved cancellation policy |
| RC-07 | Saved MediaSession is destroyed during restore | Do not use stale token/controller; wait only for valid matching-session events |
| RC-08 | Bluetooth Binder disconnects during restore | Cancel pending Bluetooth command; never use dead Binder |
| RC-09 | Pause callback arrives after ACC ON | Treat as late confirmation; do not mutate pre-OFF snapshot |
| RC-10 | Readiness callback is delivered more than once | At most one Play request per restore generation |
| RC-11 | Source changes during listener registration and initial read | Reconcile initial snapshot and callbacks without losing the latest source |
| RC-12 | Bluetooth Binder binds after ACC OFF snapshot time | Preserve UNKNOWN; do not silently interpret missing data as NOT_PLAYING |
| RC-13 | Playback changes between observation and Pause request | Preserve observed pre-command state and record Pause confirmation separately |
| RC-14 | ACC changes while Binder transaction is in progress | Late transaction result cannot advance an obsolete ACC cycle |

## 5. Media Player readiness tests

| ID | Initial condition | Event | Expected result |
|---|---|---|---|
| MP-01 | Saved player process and matching session are ready | ACC ON | Play only if saved state was PLAYING; target matching package/session |
| MP-02 | Process runs but matching MediaSession is absent | ACC ON | No Play; wait for matching-session event |
| MP-03 | Process is absent | ACC ON | Follow approved launch policy; process start alone is not readiness |
| MP-04 | Main Activity launched but matching session is absent | Activity/process event | No Play until matching session and transport action are available |
| MP-05 | Matching session appears after launch | Active-session callback | Reconcile identity, then send at most one Play if still required |
| MP-06 | Matching session is already PLAYING | Readiness callback | No duplicate Play |
| MP-07 | A different player's session appears first | Active-session callback | Ignore it |
| MP-08 | Selected app is removed or disabled | Restore pending | No fallback; record failure |
| MP-09 | Matching session reports STOPPED or ERROR | Restore pending | Do not send Play unless an explicit recovery rule is approved |
| MP-10 | Session lacks required Play transport action | Restore pending | Do not mark restoration successful |

## 6. Bluetooth Player readiness tests

| ID | Initial condition | Event | Expected result |
|---|---|---|---|
| BT-01 | Binder connected; A2DP and AVRCP connected | Restore pending | Connection flags alone do not prove internal player readiness |
| BT-02 | A2DP connected; AVRCP disconnected | Restore pending | No Play |
| BT-03 | A2DP/AVRCP connected; internal MediaController not ready | Restore pending | No Play |
| BT-04 | Verified full-readiness condition is satisfied | Readiness event | At most one vendor PLAY command 0 if saved state was PLAYING |
| BT-05 | Binder connects after ACC ON | Service callback | Register callbacks, reconcile initial state, continue event-driven readiness |
| BT-06 | Binder dies while waiting | Death callback | Invalidate old Binder and wait for reconnection |
| BT-07 | A2DP/AVRCP disconnect during restore | Disconnect callback | Cancel pending command |
| BT-08 | Vendor service auto-plays after reconnect | Playback callback | Detect PLAYING and avoid duplicate PLAY |
| BT-09 | Vendor auto-play preference enabled | ACC ON/reconnect | Verify no duplicate or competing vendor/custom Play |
| BT-10 | Bluetooth connected but SourceInfo identifies another source | ACC ON | Do not activate or play Bluetooth |
| BT-11 | Bluetooth service unavailable at ACC OFF | ACC OFF | Preserve UNKNOWN rather than assuming not playing |
| BT-12 | Normal module teardown | Unregister/stop | Unregister exact callback and unbind; do not use destructive unRegMusicClientBinder() as generic cleanup |

## 7. SourceInfo initialization and callback tests

| ID | Initial condition | Event | Expected result |
|---|---|---|---|
| SI-01 | SourceInfo service available | Module initialization | Register source/music callbacks before initial snapshot |
| SI-02 | Source changes during registration and initial read | Callback + initial read | Reconcile both; final state reflects newest event |
| SI-03 | onSourceChanged received | Source transition | Update selected-source identity independently of playback state |
| SI-04 | onMusicEvent(MusicInfo) received | Playback/metadata update | Update playback information without treating it as source-selection change |
| SI-05 | SourceInfo unavailable at initialization | Service unavailable | Keep source UNKNOWN; recover only through documented availability event |
| SI-06 | SourceInfo reports Bluetooth package | Classification | Classify com.autochips.bluetooth as Bluetooth source |
| SI-07 | SourceInfo reports regular media package | Classification | Match only a MediaSession belonging to that package |

## 8. Lifecycle and stability tests

| ID | Initial condition | Event | Expected result |
|---|---|---|---|
| LC-01 | Media module disabled | ACC/player events | No Media commands |
| LC-02 | Media module enabled | Initialization | Each listener/callback registered once |
| LC-03 | Initialization invoked again | Repeated initialization | No duplicate listeners or Binder callbacks |
| LC-04 | Bluetooth Binder reconnects | Service connection | One active callback registration; discard old Binder references |
| LC-05 | MediaSession replaced | Session callback | Unregister old callback; track new matching session |
| LC-06 | ACC remains OFF for extended period | No new transition | No repeated commands or periodic work |
| LC-07 | system_server restarts | Reinitialization | Rebuild listeners/Binder clients without stale objects |
| LC-08 | Bluetooth service absent | Module use | Media Player path remains functional |
| LC-09 | Media Player absent | Module use | Bluetooth path remains functional |
| LC-10 | Repeated source changes and ACC cycles | Long-duration test | No leaks, stale callbacks, duplicate commands, or unbounded retained state |
| LC-11 | Vendor Binder responds slowly | ACC transition | Measure latency and verify system_server responsiveness |
| LC-12 | Callback arrives after teardown | Late callback | Ignore safely; do not access released state |

## 9. Required diagnostics

Capture a timestamped event sequence for every test:

- ACC state and transition/cycle identifier.
- SourceInfo package, source-change events, and MusicInfo events.
- Selected Media Player package.
- MediaSession package, session identity, playback state, and available transport actions.
- Bluetooth Binder connection/death/reconnection events.
- A2DP and AVRCP states.
- Bluetooth playback state and callback events.
- Snapshot contents before Pause.
- Pause/Play request, target, and callback-confirmed result.
- Readiness events and the reason a command was allowed or withheld.
- Restore generation and cancellation decisions.
- Binder transaction duration and exceptions.

Do not log secrets or unnecessary personal/media data.

## 10. Acceptance criteria

Acceptance requires on-device evidence that:

- Only the source selected at ACC OFF can be restored.
- A source that was not playing before ACC OFF is never started by ACC ON.
- Snapshot capture precedes Pause.
- Source identity and playback state remain separate.
- An unrelated MediaSession cannot be paused or restored.
- Bluetooth connection alone cannot trigger Play.
- Vendor auto-play does not cause duplicate or competing commands.
- Duplicate ACC/readiness events do not cause duplicate commands.
- Late callbacks from obsolete cycles cannot affect the current cycle.
- Binder death/reconnection and MediaSession destruction are handled safely.
- No polling or timer-based readiness checks are used.
- system_server responsiveness is verified under slow Binder responses.
- Logs explain every command decision and withheld command.

## 11. Test execution order

1. Record package/build identity and enable diagnostic logging.
2. Run ACC-01 through ACC-10 with each source independently.
3. Run SRC-01 through SRC-09.
4. Run SI-01 through SI-07.
5. Run MP-01 through MP-10.
6. Run BT-01 through BT-12.
7. Run RC-01 through RC-14.
8. Run LC-01 through LC-12.
9. Repeat critical tests across multiple real ACC cycles and extended runtime.

## 12. Open decisions — do not assume

These policies must be agreed before implementation. Tests depending on them must be marked **Blocked — decision required**, not passed by assumption.

1. **Readiness failure:** If the saved player never emits readiness, should the pending restore be discarded or retained? Safe default: no premature command.
2. **Snapshot persistence:** Must the snapshot survive system_server/process restart, or only the current process lifetime?
3. **Media Player launch:** Is automatic Main Activity launch allowed for every selected player?
4. **Source change during ACC OFF:** Does a source change before ACC ON invalidate the saved restore, or does the original snapshot remain authoritative?
5. **Pause confirmation failure:** Is a source eligible for later Play if Pause was requested but not confirmed?
6. **Duplicate ACC notifications:** Which signal distinguishes a duplicate broadcast from a genuinely new ACC cycle?
7. **Bluetooth full readiness:** Which reliable event proves that the vendor internal MediaController is ready? A2DP/AVRCP alone are insufficient according to the static audit.
