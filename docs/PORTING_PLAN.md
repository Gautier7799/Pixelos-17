# Standalone Launcher Porting Plan

## Objective

Create a maintainable, independently installable launcher APK based on the legally usable parts of PixelOS/AOSP Launcher3, targeting Pixel 8 / Android 17. Keep this project separate from Lawnchair and all NothingOS/Caddy work.

## Phase 0 — Source and dependency audit

1. Use the PixelOS manifest's AOSP baseline tag, `android-17.0.0_r1`, for the initial source audit.
2. Inspect Launcher3's manifest, Android.bp, source sets, resources, overlays, and dependency graph.
3. Prioritize the non-Quickstep `Launcher3` target. Do not attempt to package the privileged/platform-API `Launcher3QuickStep` target unchanged.
4. Map dependencies into public SDK, AndroidX/redistributable, AOSP library requiring adaptation, platform-only/hidden API, privileged/system, and proprietary categories.
5. Verify licenses and notices for every source/resource intended for import.
6. Produce a concrete porting matrix: keep, adapt, replace, or exclude.

**Exit criteria:** a reviewed source snapshot and dependency/licensing matrix. No speculative source copy.

## Phase 1 — Standalone baseline APK

1. Create a minimal Gradle Android application structure with a stable application ID and toolchain selected from the audited source requirements.
2. Port the smallest viable non-Quickstep Launcher3 surface and resources.
3. Add the standard HOME intent filter and launcher activity.
4. Replace unsupported platform integrations with public-SDK equivalents where possible.
5. Build a debug APK and run static/build checks.
6. Install and test on Pixel 8 / Android 17: first launch, HOME selection, app list, app launch, shortcuts where supported, rotation, process restart, and crash-free navigation.

**Exit criteria:** reproducible debug APK that installs and works as a normal launcher without root or bootloader changes.

## Phase 2 — Pixel experience parity

1. Add only features whose source and licensing are verified.
2. Evaluate Quickstep-dependent features separately. Recents/overview integration, gesture navigation transitions, taskbar, and other privileged/system interactions may require OS integration and may not be reproducible in an ordinary APK.
3. Reimplement unavailable integrations through supported Android APIs where feasible.
4. Document features that inherently require privileged/system integration and therefore cannot be reproduced in an ordinary APK.
5. Compare behavior and performance against the baseline and maintain regression tests.

## Phase 3 — Release hardening

1. Verify notices, licenses, package identity, signing, and versioning.
2. Test clean install, upgrade, default launcher switching, backup/restore behavior where applicable, and uninstall.
3. Produce a release APK and a concise known-limitations document.

## Engineering rules

- Keep changes isolated and reviewable; do not modify unrelated behavior without an explicit project decision.
- Do not claim Pixel Launcher parity until each feature is implemented and tested.
- Do not bundle proprietary Google/Pixel assets or binaries without verified redistribution rights.
- Never treat a successful source commit as evidence that the Android project builds.
- Preserve a known-good baseline and record each build/test result.
