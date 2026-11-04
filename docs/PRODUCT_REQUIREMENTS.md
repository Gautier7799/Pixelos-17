# PixelOS-17 Launcher — Product Requirements

## Product objective

Build a polished, dependable, independently installable Android HOME application for Pixel 8 running Android 17. It should be suitable for daily use, not merely demonstrate a Pixel-like screenshot.

The application remains a normal APK. It must not require root, an unlocked bootloader, a custom ROM, privileged installation, or modification of SystemUI/framework.

## Product principles

1. Reliability before feature count.
2. Native Android behavior before visual imitation.
3. Pixel-inspired interaction and visual language, implemented from auditable sources or original code.
4. Fast cold start, responsive touch, smooth scrolling and transitions, and controlled memory/battery use.
5. User data and workspace state must survive process death and app upgrades.
6. Every setting must work, persist, and have a clear effect.
7. Unsupported system integrations must be disclosed, not simulated deceptively.
8. Keep the project independent of Lawnchair and NothingOS/Caddy code.

## Target device and platform

- Primary device: Google Pixel 8.
- Primary platform: Android 17 / API 37.
- Delivery: signed, installable APK registered as a HOME candidate.
- Initial form factor: handheld phone; tablet/foldable adaptation is not a Phase 1 requirement.

## Feature scope and delivery order

### P0 — Daily-driver foundation

- Correct HOME role/intent handling and reliable default-launcher switching.
- Workspace with persistent app shortcuts, folders, pages, and dock.
- App discovery from PackageManager, correct launch intents, labels and icons.
- App drawer with predictable sorting, scrolling, search filtering, and empty states.
- Add/remove/move shortcuts without losing workspace state.
- Folder open/close, rename, add/remove apps, and stable persistence.
- Widget host support using public Android APIs, including resize and removal where supported.
- Wallpaper-aware light/dark surfaces and Android dynamic color where public APIs permit.
- Accessibility: content descriptions, touch targets, keyboard/focus basics, and contrast.
- Configuration/state migration and recovery from malformed or stale workspace records.
- Crash-free core navigation and no destructive behavior during upgrades.

### P1 — Pixel experience refinement

- Pixel-inspired grid, icon sizing, spacing, typography, dock and folder geometry.
- Adaptive icon rendering and themed icon support where the platform and installed icon packs permit.
- App drawer search UX and useful local results.
- Recent/suggested apps only where reliable data is available through supported APIs.
- Gesture affordances that can be implemented by an ordinary launcher without claiming privileged navigation ownership.
- Responsive animations with reduced-motion/accessibility handling.
- Per-device density and inset calibration for Pixel 8.

### P2 — Optional enhancements

- At-a-glance style information only through supported, permissioned providers or user-configured widgets.
- Additional customization, backup/restore, and optional integrations after the baseline is stable.
- Any AI/search provider integration must be optional, transparent, privacy-conscious, and isolated from core launcher operation.

## Explicit platform boundary

A normal third-party launcher cannot assume access to privileged/platform APIs or the system's private Quickstep/Recents integration. Full system overview replacement, privileged gesture-navigation transitions, protected task management, and other OEM-only capabilities must be treated as unavailable unless proven through public APIs on the target build.

Do not package the Pixel Launcher APK, proprietary Google code, private assets, or vendor binaries as a shortcut. Use the APK as a behavioral/resource inventory only. Every imported source file must have a verified license and retain its notices.

## Architecture requirements

- Keep the Android application module separate from source-audit notes and upstream reference material.
- Port the non-Quickstep Launcher3 surface first; isolate platform-only dependencies behind adapters or replace them with public SDK implementations.
- Keep workspace persistence, app discovery, rendering, search, and platform integration as separable components.
- Avoid reflection/hidden APIs as a foundation for core behavior.
- Use deterministic Gradle builds and pinned toolchain/dependency versions.
- Add unit tests for persistence, grid placement, package/app model mapping, and migration logic.
- Add instrumentation tests for HOME launch, workspace restoration, app drawer, folders, widget lifecycle, and rotation/configuration changes.
- Keep a known-good baseline and use small, reviewable commits.

## Quality gates

A build is not a release candidate until all applicable gates pass:

1. Clean checkout builds reproducibly in CI.
2. APK installs and updates on Pixel 8 / Android 17 without privileged permissions.
3. Launcher can be selected as default HOME and remains selected after process/device restart.
4. Workspace, dock, folders, and app drawer remain correct after force-stop and reboot.
5. App launch, search, folder operations, and widget lifecycle pass functional tests.
6. No crash, ANR, data loss, or major layout defect in the core test path.
7. Performance is measured on-device; animation smoothness is not inferred from source inspection.
8. License, notices, signing, versioning, and known limitations are documented.

## Current status

The repository is still at Phase 0 (source and dependency audit). The current contents are planning/audit documentation; there is not yet an Android Gradle application module or a built APK. The next engineering milestone is a reviewed porting matrix and a minimal reproducible standalone baseline, not a cosmetic feature pass.

## Reference APKs

- Legacy Nexus Launcher NMR1-3063178: historical reference only.
- Pixel Launcher 17-907 (min API 37): modern behavioral/resource reference only; not a redistribution or implementation base.