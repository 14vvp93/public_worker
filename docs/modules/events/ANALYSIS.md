# Events Module — Technical Analysis

**Status:** App and Accessory event forwarding implemented; runtime verification pending  
**Platform:** Android 9 / API 28  
**Package:** `com.mt8163tools.modules.events`

## 1. Purpose

Events is the central persistent journal for meaningful events explicitly emitted by application modules and supported system hooks. It is not a Logcat collector and does not automatically capture every Android or vendor-system message.

Producers submit records through `com.mt8163tools.core.common.EventsLog`. System-process hooks must forward events through the package-scoped `AccessoryStateEvent` broadcast; they must not access app-private storage.

## 2. Event record

Each record contains:

| Field | Description |
|---|---|
| `timestamp` | Event time in Unix milliseconds |
| `module_id` | Stable feature ID |
| `type` | Machine-readable event type |
| `message` | Human-readable event description |

Do not include credentials, tokens, personal data, or unnecessary raw payloads.

## 3. Storage and limits

- Private preferences: `events_log`, key `entries`.
- JSON array using Android `org.json`.
- Maximum history: 300 newest records.
- Newest records are displayed first.
- Writes use `SharedPreferences.apply()`.
- Invalid stored JSON is treated as an empty log.
- Module ID and type are limited to 64 characters; message is limited to 500 characters.

## 4. Collection enablement

`MainActivity` initializes the in-memory `AtomicBoolean` collection gate from the persisted Events module state.

When Events is OFF:
- `EventsLog.record()` returns without persisting or broadcasting.
- Events are discarded, not queued or replayed.
- Existing history is retained.
- The disable action is not recorded because collection has already been stopped.

When Events is enabled, the enable action is recorded after reopening the gate. The gate is checked before and inside the synchronized write section.

The Accessory receiver calls `EventsLog.initialize()` before recording a forwarded event. This reloads the persisted module state in the app process; it does not enable collection unconditionally.

## 5. Delivery and UI

App-process producers call `EventsLog.record()` directly. After persistence, the logger sends the package-scoped `ACTION_EVENT_RECORDED` broadcast.

The Events Fragment listens only while started, reloads the stored history on notification, and unregisters when stopped. No polling, timer, service, worker, or recurring callback is used.

System-process producers use this flow:

```
AccessoryHook / system_server
        |
        | package-scoped ACCESSORY_STATE_CHANGED
        v
AccessoryStateReceiver / app process
        |
        v
EventsLog.record()
        |
        +--> SharedPreferences
        |
        +--> EVENT_RECORDED broadcast
                  |
                  v
             EventsFragment
```

## 6. Current event coverage

| Producer | Recorded event types |
|---|---|
| MainActivity | `module_enabled`, `module_disabled` for feature modules; `collection_enabled` for Events |
| VoltageSettings | `voltage_offset_changed` |
| AccessoryHook | `acc_state_sync`, `acc_state_changed` |
| AccessoryPlaybackController | `playback_pause_requested`, `playback_paused`, `playback_restore_pending`, `playback_restore_requested`, `playback_resumed`, `playback_restore_cancelled`, `playback_error` |
| VendorBluetoothMusicClient | `bluetooth_service_connected`, `bluetooth_state_snapshot`, `bluetooth_a2dp_state`, `bluetooth_avrcp_state`, `bluetooth_metadata`, `bluetooth_playback_state`, service bind/disconnect/death/error/disabled events |

Accessory events are forwarded from the system process using `AccessoryStateEvent.send()` and `sendPlaybackEvent()`, then recorded by `AccessoryStateReceiver` with module ID `accessory`.

The ACC state entry is the state observed by AccessoryHook: it is emitted after the existing MCU receiver callback or after the initial `McuService.AccStatus()` synchronization. Duplicate ACC transitions are filtered by the hook.

Bluetooth is an independent read-only observer. It records the vendor service connection, initial A2DP/AVRCP/playing snapshot, and callback notifications. It does not issue AVRCP commands or call `requestA2dp()`.

MediaSession entries describe the targeted pause/restore flow. They do not represent every playback-state callback from every player; only callbacks relevant to the saved session and restoration state are emitted.

## 7. Known coverage gaps

Events does **not** currently contain every system log or every state transition. In particular:

- SourceService source-selection transitions are not yet forwarded.
- Arbitrary Android framework, MCU, Bluetooth stack, and vendor Logcat messages are not collected.
- MediaSession state changes unrelated to the current ACC pause/restore operation are not collected.
- Events occurring while the Events module is disabled are intentionally discarded.
- A failed or unavailable system-to-app broadcast path can result in missing records; target-device verification is still pending.

Add new event producers explicitly and keep records meaningful and low-volume. Do not collect high-frequency getters, dump raw Logcat, or add polling just to make the journal appear complete.

## 8. Event-driven and performance constraints

- No background threads, executors, pools, timers, polling, or recurring callbacks.
- The collection gate is in memory and uses `AtomicBoolean`.
- Storage mutation is synchronized.
- History is bounded to 300 records.
- Event text is trimmed and length-limited.
- No storage work is performed when collection is disabled.

## 9. Verification status

Source-level integration is present for app events and Accessory/MediaSession/vendor-Bluetooth event forwarding. Kotlin compilation, end-to-end broadcast delivery, persistence, UI refresh, and real MT8163 runtime behavior remain unverified.
