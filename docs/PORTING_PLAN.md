# Standalone Launcher Porting Plan

## Objective

Deliver a stable, independently installable Android 17 launcher APK for Pixel 8, based on real AOSP Launcher3 code with selectively ported PixelOS/AOSPA changes. Keep this project separate from Lawnchair and NothingOS/Caddy.

## Phase 0 — Correct the source foundation

1. Freeze the current hand-written UI prototype. Do not extend it as if it were Launcher3.
2. Pin the Android 17 AOSP Launcher3 source at PixelOS manifest baseline `android-17.0.0_r1`.
3. Capture source revision, copyright notices, license files, and all required generated/source dependencies.
4. Inspect Launcher3's manifest, Android.bp, source sets, resources, overlays, and dependency graph.
5. Compare the archived PixelOS/AOSPA `thirteen` fork against Android 17. Identify AOSPA-specific changes and map them to current files.
6. Classify every dependency: public SDK, AndroidX/Maven, AOSP source library, hidden/platform API, privileged/system integration, or proprietary.
7. Build a dependency replacement matrix and define the ordinary-app manifest/package strategy.

**Exit criteria:** pinned Android 17 source, reproducible source comparison, license inventory, and a reviewed keep/port/replace/exclude matrix.

## Phase 1 — Minimal real Launcher3 APK

1. Integrate the minimum Android 17 Launcher3 source and resources into the Gradle build.
2. Resolve dependencies deliberately; do not silently stub core workspace behavior.
3. Remove or replace platform-only components only after tracing their call sites and impact.
4. Provide a normal HOME activity and user-installable package identity.
5. Implement supported app discovery, workspace, app launch, and persistence.
6. Build and verify a debug APK in CI.
7. Test install, HOME selection, cold start, app launch, app list, shortcuts, rotation, process death/restart, and crash-free navigation on Pixel 8 / Android 17.

**Exit criteria:** reproducible APK built from real Launcher3 code, installable as a normal app, with the baseline launcher functions working.

## Phase 2 — Approved visual/product features

Only after Phase 1 is stable:

- iOS-inspired Home Screen.
- Fully automatic iOS-style Dock; no manual geometry tuning required.
- iOS-inspired App Drawer.
- iOS-inspired widget surface using real Android AppWidget hosting and lifecycle APIs.
- Preserve original application icon artwork unchanged at every surface.
- Keep launcher settings functional and persistent.

The Lock Screen is explicitly out of scope and must not be implemented.

## Phase 3 — PixelOS/AOSPA feature port

1. Port only AOSPA changes verified to remain applicable to Android 17.
2. Treat Quickstep, Recents, overview transitions, taskbar, and other system-coupled features as a separate capability audit.
3. Reimplement only what public Android APIs support in a normal APK.
4. Document features that inherently require privileged/system integration; do not simulate them deceptively.

## Phase 4 — Release hardening

Verify licenses/notices, package identity, signing, versioning, clean install/upgrade, default launcher switching, workspace backup/restore where supported, performance, and uninstall.

## Engineering controls

- Keep a known-good baseline and isolated commits.
- Do not run builds for documentation-only or speculative UI changes.
- No APK build until the source/dependency/manifest gates pass.
- A successful CI run is not sufficient: verify the artifact is built from the intended Launcher3 source.
- Never claim a feature is implemented until code and device behavior confirm it.
- Do not change the locked icon requirement or revive the rejected Lock Screen scope.
