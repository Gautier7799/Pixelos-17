# Standalone Launcher Porting Plan

## Objective

Create a maintainable, independently installable launcher APK based on the legally usable parts of PixelOS/AOSP Launcher3, targeting the user's Pixel 8 on Android 17. Keep this project separate from Lawnchair and all NothingOS/Caddy work.

## Phase 0 — Source audit (current phase)

1. Resolve the exact PixelOS Android 17 manifest revision and Launcher3 project revision.
2. Inspect Launcher3 build files, manifest, source sets, resources, overlays, and dependencies.
3. Map dependencies into public SDK, AndroidX/redistributable, platform-only, privileged/system, and proprietary categories.
4. Verify licenses and notices for every source/resource intended for import.
5. Produce a concrete porting matrix: keep, adapt, replace, or exclude.

**Exit criteria:** a reviewed source snapshot and a dependency/licensing matrix. No speculative source copy.

## Phase 1 — Standalone baseline APK

1. Create a minimal Gradle Android application structure with a stable application ID and Java/Kotlin toolchain selected from the audited source requirements.
2. Port the smallest viable Launcher3 surface and resources.
3. Add the standard HOME intent filter and launcher activity.
4. Replace unsupported platform integrations with public-SDK equivalents where possible.
5. Build a debug APK and run static/build checks.
6. Install and test on Pixel 8 / Android 17: first launch, HOME selection, app list, app launch, widgets/shortcuts where supported, rotation, process restart, and crash-free navigation.

**Exit criteria:** reproducible debug APK that installs and works as a normal launcher without root or bootloader changes.

## Phase 2 — Pixel experience parity

1. Add only features whose source and licensing are verified.
2. Reimplement unavailable system integrations through supported Android APIs where feasible.
3. Document features that inherently require privileged/system integration and therefore cannot be reproduced in an ordinary APK.
4. Compare behavior and performance against the baseline and maintain regression tests.

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
