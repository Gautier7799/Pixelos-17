# Source and Licensing Audit

## Target

Build a normal installable HOME APK for Pixel 8 / Android 17 (API 37). It must not require root, an unlocked bootloader, privileged installation, a custom ROM, or modifications to SystemUI/framework.

## Android 17 baseline

PixelOS Android 17 manifest branch `seventeen` selects AOSP tag `android-17.0.0_r1` and references AOSP Launcher3 at `packages/apps/Launcher3`.

The Android 17 AOSP `Launcher3` target is the compatibility baseline to investigate. The separate `Launcher3QuickStep` target depends on platform/privileged integration and cannot be copied unchanged into a user-installed APK. Its system-dependent features must be isolated and treated separately.

## Newly supplied PixelOS/AOSPA source

Repository: https://github.com/PixelOS-AOSP/packages_apps_Launcher3  
Inspected branch: `thirteen`  
Repository status: archived  
Build system: legacy Gradle / Android Gradle Plugin 3.5.1  
Declared compile SDK: `android-S`  
Build tools: `28.0.3`  
Manifest target SDK: `33`  
Manifest package: `com.android.launcher3`

### What is useful

- Real Launcher3 source, resources, tests, protos, plugins, and icon-loader code.
- AOSPA launcher extension class `co.aospa.launcher.ParanoidLauncher`, which subclasses `com.android.launcher3.Launcher`.
- Explicit `withQuickstep` and `withoutQuickstep` product flavors.
- The non-Quickstep source-set split is a useful reference for identifying the boundary between launcher UI and recents/system integration.
- AOSPA-specific source directories such as `src_base`, `src_shortcuts_overrides`, and `src_ui_overrides` are candidates for a later file-by-file comparison against Android 17.

### Why this fork cannot be used unchanged

1. It is an archived legacy branch, not an Android 17 source snapshot.
2. Its Gradle configuration uses AGP 3.5.1, Build Tools 28.0.3, and `android-S`; its manifest targets API 33. These are not a compatible ready-made toolchain for the Android 17/API 37 project.
3. Its `withoutQuickstep` flavor is not proof of a self-contained ordinary APK. It still has a special `withoutQuickstepImplementation` dependency on `plugin_core.jar`, a project dependency on `IconLoader`, generated protobuf code, and AOSP-oriented repository/prebuilt paths.
4. The shared manifest contains platform-oriented permissions and signature/system permission declarations. Each permission and component must be audited for a normal app; simply merging the manifest is unsafe and may create install/runtime failures.
5. Its Soong `Android.bp` declares the Launcher3 app as `privileged: true` and `system_ext_specific: true`, with required privileged whitelist files. That Soong target is a system app configuration, not the desired distribution model.
6. The Android.bp dependency graph includes AOSP libraries and platform build targets that are not automatically available through Maven or the public Android SDK.

### Architectural conclusion

Use Android 17 AOSP Launcher3 as the API and platform compatibility baseline. Treat the archived PixelOS/AOSPA fork as a change reference only. Compare its AOSPA-specific commits/files with Android 17 and port only changes that are still applicable, license-compatible, and independent of unavailable platform APIs.

Do not import the entire old fork, its manifests, or its build files wholesale. Do not copy proprietary Pixel Launcher binaries/assets.

## Current repository assessment

The current `Gautier7799/Pixelos-17` app is a small hand-written Java UI prototype. The latest commit `52d4001` adds a simple home page, app grid, and dock. It is not derived from Launcher3 and does not implement Launcher3 workspace or widget-host architecture. Workflow run #20 succeeded, but that success only verifies the prototype build.

The prototype is frozen and must not be used as the technical foundation. Keep it only as historical reference until a real Launcher3 baseline builds.

## Licensing

The user's repository MIT license applies only to original contributions. It does not relicense upstream AOSP or PixelOS/AOSPA code. The AOSP Launcher3 build declares Apache-2.0 as its default applicable license; imported files must preserve their copyright/license notices. The exact license and notice files in each upstream snapshot must be captured before source import. No proprietary Google/Pixel assets or binaries may be bundled without explicit redistribution rights.

## Audit gates

- [x] Confirm PixelOS Android 17 manifest baseline and AOSP Launcher3 reference.
- [x] Inspect legacy PixelOS/AOSPA fork Gradle flavors and key build properties.
- [x] Inspect legacy fork manifest and Soong system-app declarations.
- [x] Identify why legacy `withoutQuickstep` is not a drop-in standalone build.
- [ ] Capture exact Android 17 AOSP Launcher3 source snapshot and license notices.
- [ ] Generate file-level diff of AOSPA custom source against Android 17 Launcher3.
- [ ] Produce dependency matrix: keep / replace / port / exclude.
- [ ] Verify standalone manifest and app identity.
- [ ] Build the minimal real Launcher3 APK before UI customization.

No new APK build is approved until the remaining gates are completed.
