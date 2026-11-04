# PixelOS 17 Standalone Launcher

An independent Android launcher project targeting Pixel 8 / Android 17, with the goal of becoming a reliable daily-use HOME app.

## Project direction

Use the legally reusable parts of PixelOS/AOSP Launcher3 as the technical foundation, then adapt them into a normal installable APK. This is not a ROM port and does not require root, an unlocked bootloader, or modification of SystemUI/framework.

## Status

**Current stage: Phase 1 — first standalone Android application foundation added; CI build verification pending.**

The PixelOS manifest selects AOSP tag `android-17.0.0_r1` as its default baseline and references AOSP Launcher3. The AOSP build defines both a non-Quickstep `Launcher3` target and a platform/privileged `Launcher3QuickStep` target. The first is the practical starting point for a standalone APK; the second cannot be used unchanged as an ordinary app.

The repository now contains an initial Android Gradle application module and a GitHub Actions debug-APK workflow. The first foundation build exposes installed launchable apps, preserves their original app-provided icons, and launches selected apps. This is an initial functional scaffold only: workspace persistence, automatic iOS-style Dock, the iOS-inspired home layout, app-library design, and real widget hosting remain to be implemented. An APK is not considered available until the CI run succeeds and its artifact is verified.

## Product requirements and engineering plan

- [Daily-driver product requirements and quality gates](docs/PRODUCT_REQUIREMENTS.md)
- [Source and licensing audit](docs/SOURCE_AUDIT.md)
- [Standalone porting plan](docs/PORTING_PLAN.md)

## Upstream references

- [PixelOS Android 17 manifest](https://github.com/PixelOS-AOSP/android_manifest/tree/seventeen)
- [AOSP Launcher3 Android.bp at android-17.0.0_r1](https://android.googlesource.com/platform/packages/apps/Launcher3/+/refs/tags/android-17.0.0_r1/Android.bp)
- [AOSP Launcher3 source](https://android.googlesource.com/platform/packages/apps/Launcher3/)
- [PixelOS vendor launcher project](https://gitlab.com/PixelOS-AOSP/proprietary_vendor_pixel_launcher)

## Reference APKs

- Legacy Nexus Launcher NMR1-3063178: historical behavior reference only.
- Pixel Launcher 17-907 (min API 37): modern behavior/resource reference only; not a redistribution or implementation base.

## Licensing

The MIT license in this repository applies to original project contributions only. It does not grant rights to redistribute upstream AOSP, PixelOS, Google, or third-party components. The AOSP Launcher3 build declares Apache-2.0 as its applicable license; imported files must retain their original notices and satisfy their respective licenses.


## CI build status

The first Android 17 CI attempts failed during Android SDK setup because the workflow requested the obsolete `tools` SDK package. The workflow has been corrected to install `platform-tools` only before installing the Android 37 platform and build tools. A subsequent run must complete successfully before an APK artifact is considered available.


## Build pipeline correction

The Android setup action was removed because its default bootstrap attempted to install the obsolete SDK package `tools`. The workflow now locates the runner-provided `sdkmanager`, accepts licenses, installs Android platform 36/build-tools 36 directly, verifies the SDK paths, and then runs `assembleDebug`. This change isolates SDK provisioning from the deprecated setup action.
