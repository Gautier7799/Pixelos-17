# Source and Licensing Audit

## Project target

Build an independently installable Android launcher APK for the Pixel 8 running Android 17. The deliverable is an ordinary user-installed application, not a replacement system image, privileged system package, or modified ROM.

## Confirmed PixelOS source selection

- PixelOS Android 17 manifest: https://github.com/PixelOS-AOSP/android_manifest/tree/seventeen
- Its default manifest revision is `refs/tags/android-17.0.0_r1`.
- The manifest declares AOSP as its default remote and points the Launcher3 project to `platform/packages/apps/Launcher3` (manifest path `packages/apps/Launcher3`).
- Exact upstream Launcher3 build definition at that tag: https://android.googlesource.com/platform/packages/apps/Launcher3/+/refs/tags/android-17.0.0_r1/Android.bp
- PixelOS also lists a separate vendor project at `vendor/pixel/launcher`: https://gitlab.com/PixelOS-AOSP/proprietary_vendor_pixel_launcher

This establishes the intended AOSP source tag for the initial audit. It does not prove that Pixel-exclusive vendor code is open or redistributable.

## Important findings from Android.bp

The Android 17 AOSP build defines separate products:

1. `Launcher3`: built with `sdk_version: "current"`, `min_sdk_version: 31`, and `target_sdk_version: "current"`. This is the more realistic candidate for a third-party APK port.
2. `Launcher3QuickStep`: explicitly sets `platform_apis: true`, `privileged: true`, and `system_ext_specific: true`; it also requires privileged whitelist configuration and system-side resources. This target cannot be copied unchanged into a normal Play-installable/user-installed APK.
3. The common `Launcher3ResLib` dependency graph includes AndroidX libraries but also platform-tree libraries such as SystemUI shared components, animation/contextual education libraries, and other AOSP build targets. These must be individually evaluated; they are not automatically available as Maven dependencies or public SDK APIs.

The practical strategy is therefore **not** to try to package the complete Quickstep target as an ordinary APK. Start from the non-Quickstep Launcher3 target, then remove or replace unavailable dependencies and integrations in a controlled way. Gesture navigation/Recents integration, overview transitions, taskbar, and other system-coupled behavior must be treated as separate capability work, not assumed to work merely because the launcher installs.

## Initial technical assessment

- Launcher3 is the open-source foundation to audit and port.
- Pixel-exclusive vendor components must be assessed separately. Do not copy proprietary APKs, resources, overlays, or binaries into this repository unless their licensing and redistribution rights are verified.
- Classify dependencies as:
  - Public Android SDK
  - AndroidX / other redistributable libraries
  - AOSP libraries requiring source porting or adaptation
  - Platform-only or hidden APIs
  - Privileged/system integration
  - Proprietary or licensing-restricted assets
- Replace or disable unsupported integrations only after identifying their call sites and user-visible impact. Preserve upstream launcher behavior wherever feasible.

## Repository license warning

This repository currently contains an MIT license for this repository's own original contributions. That license does not relicense AOSP, PixelOS, Google, or third-party source code imported later. The AOSP Launcher3 build definition identifies its default applicable license as Apache-2.0. Every imported component must retain its applicable copyright and license notices, and compatibility must be checked before distribution.

## Audit status

- [x] Confirmed PixelOS manifest default AOSP tag: `android-17.0.0_r1`.
- [x] Confirmed manifest reference to AOSP Launcher3.
- [x] Confirmed separate PixelOS vendor launcher project.
- [x] Compared the AOSP `Launcher3` and `Launcher3QuickStep` build targets.
- [x] Identified platform/privileged integration as a major barrier for QuickStep parity in a normal APK.
- [ ] Inspect Launcher3 manifest, source sets, resources, overlays, and exact dependency graph.
- [ ] Produce a file-by-file porting and licensing decision before importing source.

## Current build status

No standalone Gradle project or APK has been created or built yet. GitHub write access has been verified; that is not a build test.
