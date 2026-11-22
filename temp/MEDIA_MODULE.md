# Media Module — Concept and Technical Analysis

## 1. Purpose

The Media module manages audio playback state across vehicle ignition transitions (ACC OFF and ACC ON).

It supports two mutually exclusive audio sources:

- **Media Player** — an Android application that exposes an active Android `MediaSession`.
- **Bluetooth Player** — the head unit's built-in Bluetooth audio player, which receives audio from a connected phone and uses a separate, device-specific control mechanism rather than an Android `MediaSession`.

The module must use one shared controller for both sources. The source-specific control mechanisms remain separate implementation details behind that controller.

This document records the agreed concept. It is not an implementation specification for undocumented Bluetooth APIs.

## 2. Media Player Selection

The module must allow the user to select the application that acts as the Media Player.

Selection requirements:

- Applications are eligible only while they have an active `MediaSession`.
- Applications without an active media session must not appear as selectable players.
- The selected package identifies the Media Player that the controller manages.
- The active-session filter is a selection requirement; it does not mean that the controller should depend on a stale session object across ACC transitions.

The exact discovery, refresh, and command-delivery mechanisms remain to be verified against the target Android build and the supplied device documentation.

## 3. Audio Sources and Shared Controller

The module has one **Media Controller** responsible for:

- identifying the relevant audio source;
- capturing the source's state at ACC OFF;
- sending Pause to that source when appropriate;
- retaining which source was paused by the ACC transition;
- restoring only that same source at ACC ON.

The existing **Audio Source** mechanism is the intended source of information for identifying which source is currently producing audio.

The controller must distinguish:

1. the selected Media Player;
2. the built-in Bluetooth Player.

The two sources are assumed not to play simultaneously under the head unit's existing audio-source behavior. This assumption must be verified against the existing Audio Source implementation and device behavior.

**Critical rule:** restoration is determined by the source recorded at ACC OFF—not by which player happens to be running, connected, or available at ACC ON.

If both players are available at ACC ON, the controller must still restore only the recorded source. It must not start, activate, or send playback commands to the other source.

## 4. ACC OFF — Capture and Pause

When ACC changes to OFF, the controller evaluates the selected Media Player's foreground state, playback state, and the active audio source.

The initial agreed Media Player behavior is:

| Media Player foreground | Playing before ACC OFF | Required action |
|---|---|---|
| Yes | Yes | Save the source and playing state; send Pause |
| No | Yes | Save the source and playing state; send Pause |
| Yes | No | Save the non-playing/foreground state; do not send Play later |
| No | No | Do nothing |

The state must be captured **before** sending Pause. The controller must not mistake the Pause command's resulting state for the state that existed before ACC OFF.

For Bluetooth playback:

- identify Bluetooth as the active audio source;
- capture its playback state;
- send Pause through the Bluetooth Player's existing device-specific mechanism;
- retain Bluetooth as the source to restore.

The exact Bluetooth state fields and command mechanism must be taken from the documentation that will be supplied.

If no supported source is active and there is no qualifying foreground Media Player state, the controller does nothing.

## 5. ACC ON — Restore the Recorded Source Only

At ACC ON, the controller first reads the state captured at ACC OFF.

It must not choose a source based on current process state, Bluetooth connectivity, or whichever player is easiest to reach.

Restoration is source-specific:

### 5.1 Recorded source: Media Player

If the saved state says that the Media Player was playing before ACC OFF:

1. Check whether the selected application's process is running.
2. If it is running, send the Play command to the selected Media Player.
3. If it is not running, launch its main activity.
4. Wait for the application to become ready to receive playback control.
5. Send Play to that application.

Do not attempt to reuse a stale `MediaSession` object from before ACC OFF. The controller does not need to restore the old session object. The command-delivery mechanism and the readiness signal must be verified for the target player and Android build.

If the saved state says that the Media Player was not playing, do not send Play. Preserve the non-playing state.

### 5.2 Recorded source: Bluetooth Player

If the saved state says that Bluetooth was playing before ACC OFF:

1. Wait for Bluetooth to reconnect.
2. Wait for the built-in Bluetooth Player to reach its fully ready state.
3. Only then send Play through the Bluetooth Player's documented control mechanism.

Bluetooth connectivity alone is not sufficient. The controller must wait for the specific readiness condition defined by the device's Bluetooth implementation.

If Bluetooth is not ready, do not send Play prematurely and do not fall back to the Media Player.

### 5.3 No saved playback to resume

If no source was playing before ACC OFF, the controller must not start playback at ACC ON.

## 6. State Ownership and Restoration Invariant

The shared controller owns the ACC transition snapshot.

At minimum, the conceptual snapshot must distinguish:

- the source to which the snapshot belongs (`MEDIA_PLAYER` or `BLUETOOTH_PLAYER`);
- whether that source was playing before Pause;
- the Media Player foreground state when relevant to the agreed behavior.

Additional fields may be required after reviewing the existing Bluetooth implementation and Audio Source mechanism.

The following invariant is mandatory:

> ACC ON may restore only the source recorded by the controller at ACC OFF. Availability of another source must never change that decision.

The snapshot must be captured before issuing Pause. A source that was not playing must not be started merely because it is available after ACC ON.

## 7. Event-Driven Readiness

Restoration must use events, callbacks, listeners, or broadcasts provided by the relevant system or player.

- Do not use polling loops or periodic checks to wait for an application process or Bluetooth readiness.
- For Media Player, identify a reliable readiness signal after launching the main activity.
- For Bluetooth Player, identify the exact event that means Bluetooth is connected **and** the player is fully ready to accept Play.
- Handle the case where readiness is never reached without sending a premature command.

The available events and their reliability are not yet established. They must be researched from the supplied documentation and target-device behavior.

## 8. Technical Analysis and Constraints

### 8.1 MediaSession lifecycle

An Android application may lose its process and its active `MediaSession` after ACC OFF. A session object retained from before process termination cannot be treated as a valid restoration handle.

The concept therefore avoids depending on the old session object. It launches the selected application when necessary and sends Play only after the application is ready. The exact way to address the Play command to the selected application remains an implementation question.

### 8.2 Process availability is not readiness

A running process does not necessarily mean that its activity, playback service, or command receiver is ready. Process detection alone must not be used as the readiness condition.

### 8.3 Bluetooth readiness is stricter than connection

A connected Bluetooth link does not necessarily mean that the built-in player is ready to resume audio. The device-specific ready event and command sequence must be identified from the supplied documentation.

### 8.4 Single-source restoration

The controller must not independently restore both players. It must not infer the intended source from whichever player is currently available at ACC ON.

### 8.5 State capture and command ordering

Capture the pre-ACC-OFF state before sending Pause. Keep the source identity and playback state together so that a later ACC ON cannot accidentally restore a different source.

## 9. Agreed Requirements

- One shared Media Controller manages both audio sources.
- The existing Audio Source mechanism is used to identify the active source.
- The Media Player selection list contains only applications with an active `MediaSession`.
- The Bluetooth Player is controlled through its own existing mechanism, not through `MediaSession`.
- ACC OFF captures the relevant state and sends Pause to the appropriate source.
- ACC ON restores only the source recorded at ACC OFF.
- Media Player restoration uses the selected application; launch its main activity if its process is absent, wait for readiness, then send Play when the saved state requires playback.
- Bluetooth Play is sent only after Bluetooth reconnection and full Bluetooth Player readiness.
- No playback command is sent when the saved state was not playing.
- No polling or timer-based readiness checks are to be introduced.
- No implementation or undocumented Bluetooth API is assumed by this concept document.

## 10. Pending Research

The following items must be resolved using the documentation and source material that will be provided:

1. Exact Audio Source behavior, events, and source-identification semantics.
2. Bluetooth Player state representation and persistence requirements.
3. Bluetooth Pause command and its delivery mechanism.
4. Bluetooth reconnection event and the definitive full-readiness event.
5. Bluetooth Play command and any required ordering or prerequisites.
6. Reliable Media Player process/activity readiness signal on the target Android 9 build.
7. A reliable, player-targeted Play command path that does not depend on a stale `MediaSession` object.
8. Behavior when the selected Media Player is uninstalled, disabled, or no longer exposes an active `MediaSession`.
9. Behavior when ACC changes again while a restoration is pending.

These are open research items, not decisions. They must not be filled in by assumption.

## 11. Scope

This document records the agreed Media module concept and the current technical analysis only.

It does not define:

- package names or Java class structure;
- concrete Xposed hooks;
- Bluetooth API names or undocumented broadcasts;
- exact persistence implementation;
- final timeout or failure policy;
- UI layout beyond the Media Player selection requirement.

Those details will be defined after reviewing the existing project architecture and the additional Bluetooth and Audio Source documentation.
