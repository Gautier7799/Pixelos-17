# PixelOS/AOSPA Launcher3 Fork — Technical Audit

Source: https://github.com/PixelOS-AOSP/packages_apps_Launcher3  
Inspected ref: `thirteen` (archived legacy source)

## Verified build configuration

- Android Gradle Plugin: `3.5.1`
- Protobuf Gradle plugin: `0.8.8`
- Build Tools: `28.0.3`
- Compile SDK: `android-S`
- Default app min SDK: `26`; target SDK: `30`
- Manifest target SDK: `33`
- Flavors: `aosp` / `l3go` and `withQuickstep` / `withoutQuickstep`
- Application ID for AOSP flavor: `com.android.launcher3`
- Launcher activity: `co.aospa.launcher.ParanoidLauncher`

## Important source-set details

The `withoutQuickstep` flavor changes the Java source set and excludes the Quickstep source directories, but it still depends on the `IconLoader` project and declares a `withoutQuickstepImplementation` file-tree dependency on AOSP `plugin_core.jar`. Protobuf generation and AOSP-oriented repository/prebuilt paths are also part of the Gradle project. Therefore, the flavor name alone does not establish a portable standalone build.

## Manifest and system integration

The common manifest contains platform-oriented permissions, signature/system permissions, notification listener service, backup/widget receivers, and other integrations. The derivative manifest registers the AOSPA Launcher subclass as HOME. Each entry needs an explicit decision for a regular user-installed APK.

## Soong target

The `Android.bp` target for Launcher3 declares `privileged: true` and `system_ext_specific: true`, and lists privileged whitelist files as required. This describes a system-image installation target, not the standalone APK distribution target. The Android.bp dependency graph also references AOSP libraries and platform modules.

## Decision

Do not copy this branch wholesale into the current Android 17 Gradle project. Use it to identify AOSPA-specific customizations. Use Android 17 AOSP Launcher3 as the compatibility baseline, then selectively port changes after file-level comparison and license review.
