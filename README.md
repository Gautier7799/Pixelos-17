# PixelOS 17 Standalone Launcher

An independent Android launcher project targeting Pixel 8 / Android 17, with the goal of becoming a reliable daily-use HOME app.

## Project direction

Use the legally reusable parts of PixelOS/AOSP Launcher3 as the technical foundation, then adapt them into a normal installable APK. This is not a ROM port and does not require root, an unlocked bootloader, or modification of SystemUI/framework.

## Status

**Current stage: Phase 0 — source and dependency audit.**

The PixelOS manifest selects AOSP tag `android-17.0.0_r1` as its default baseline and references AOSP Launcher3. The AOSP build defines both a non-Quickstep `Launcher3` target and a platform/privileged `Launcher3QuickStep` target. The first is the practical starting point for a standalone APK; the second cannot be used unchanged as an ordinary app.

The repository currently contains audit and product-planning documentation. It does not yet contain an Android Gradle application module, and no standalone APK has been built.

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
