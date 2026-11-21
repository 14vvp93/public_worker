# Voltage — LSPosed integration

The authoritative implementation details are in SYSTEM_SERVER.md.

- Target package/process: android (system_server)
- Service: android.carsource.McuService
- Hook: after onBattery(McuPacket)
- Descriptor: (Landroid/carsource/McuService$McuPacket;)V
- Battery packet: subCmd() == 3
- Voltage source: unsigned param(0) / 10.0
- Corrected field: mBatteryVoltage
- Live configuration: package-scoped broadcast to android

LSPosed 1.9.2 in Zygisk mode remains the confirmed device baseline. The module's recommended scope is android.

Build verification and target-device runtime verification are pending.
