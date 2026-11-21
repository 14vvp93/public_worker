# MT8163 Tools

A single-APK Android application for MT8163-based head units. Features are independent functions packaged inside one Gradle application module.

## Platform

- Android 9 (API 28), minSdk 28 / targetSdk 28
- Kotlin and Android XML Views
- One Gradle module: `:app`
- Target display: 1280×720 at 160 dpi
- Visual theme: dark graphite with Neon Red accents

## Project Structure

All application code, feature code, shared resources, and LSPosed hooks live under `app/src/main`:

- `app/src/main/java/com/mt8163tools/app` — application entry point, feature registry, navigation, and feature state.
- `app/src/main/java/com/mt8163tools/core` — shared contracts and utilities.
- `app/src/main/java/com/mt8163tools/modules` — independent feature packages.
- `app/src/main/java/com/mt8163tools/hooks` — LSPosed entry point and system hooks.
- `app/src/main/res` — application, feature, and shared resources.
- `app/src/main/assets/xposed_init` — LSPosed entry point declaration.

Feature packages are not separate Gradle projects, APKs, or dynamically loaded plugins. Adding a feature means adding a package, its resources, and one registration in `ModuleRegistry`.

## Runtime Model

The application and all features are strictly event-driven. No background operations are allowed: no worker threads, executors, timers, polling, recurring callbacks, scheduled refreshes, or always-running tasks. Work may run only in response to explicit user actions, Android/system callbacks, incoming events, or hooked-method invocations. Voltage live settings are delivered to SystemUI through a package-targeted broadcast receiver registered after Application startup. See [Event-Driven Execution](docs/ARCHITECTURE.md#event-driven-execution).

## Documentation

- [Project Architecture](docs/ARCHITECTURE.md)
- [LSPosed Integration and Device Compatibility](docs/hooks/LSPOSED.md)
- [Accessory technical analysis](docs/modules/accessory/ANALYSIS.md)
- [Voltage technical analysis](docs/modules/voltage/ANALYSIS.md)
- [Events module technical analysis](docs/modules/events/ANALYSIS.md)

## Versioning

Current application version: **1.0.0** (`versionCode = 10000`). Update `appVersionName` in `app/build.gradle.kts` for each new version. Keep `versionCode` increasing using `major * 10000 + minor * 100 + patch`. Every distributable build artifact must include the same version in its filename.

## Manual Debug APK Build

The project requires JDK 17, Android SDK Platform 37, and Gradle 9.6.0. Only the **debug** build variant is configured.

Build the APK from the repository root:

```bash
./gradlew --no-daemon :app:assembleDebug
```

APK output:

```
app/build/outputs/apk/debug/MT8163-Tools-v1.0.0-debug.apk
```

Copy this APK to the head unit and install it manually. No release build variant is configured.

## GitHub Actions Build Cache

The manual GitHub Actions workflow uses `gradle/actions/setup-gradle` to cache the Gradle distribution, downloaded dependencies, and other Gradle User Home data. The cache is writable on `development`; other refs use read-only cache access.

The build also enables Gradle's task output cache with `--build-cache` and parallel task execution with `--parallel`. The first run after a cache miss still downloads required components; subsequent compatible runs can reuse cached data. Changes to Gradle configuration or dependency versions may require cache entries to be refreshed.

The next workflow run (run number 6) clears the restored Gradle cache once before building. Gradle then recreates the required cache data, and the setup action saves the refreshed cache for later runs. Cache support remains enabled.

The uploaded artifact is named `mt8163-tools-debug-v<version>`. The workflow reads the version directly from `app/build.gradle.kts`, so the artifact name updates automatically when `appVersionName` changes.

Gradle Configuration Cache is not enabled until compatibility with the project's Android and Kotlin plugins has been verified.
