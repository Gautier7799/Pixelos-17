# PixelOS 17 Standalone Launcher

An independent Android launcher project targeting Pixel 8 / Android 17.

## Project direction

Use the legally reusable parts of PixelOS/AOSP Launcher3 as the technical foundation, then adapt them into a normal installable APK. This is not a ROM port and does not require root, an unlocked bootloader, or modification of SystemUI/framework.

## Status

**Current stage: Phase 0 — source and dependency audit.**

No standalone APK has been built yet. The PixelOS manifest references AOSP Launcher3 and a separate proprietary Pixel launcher vendor project; these sources must be audited independently before importing code or assets.

## Plan and technical constraints

- [Source and licensing audit](docs/SOURCE_AUDIT.md)
- [Standalone porting plan](docs/PORTING_PLAN.md)

## Upstream references

- [PixelOS Android 17 manifest](https://github.com/PixelOS-AOSP/android_manifest/tree/seventeen)
- [AOSP Launcher3](https://android.googlesource.com/platform/packages/apps/Launcher3/)
- [PixelOS proprietary vendor launcher](https://gitlab.com/PixelOS-AOSP/proprietary_vendor_pixel_launcher)

## Licensing

The MIT license in this repository applies to original project contributions only. It does not grant rights to redistribute upstream AOSP, PixelOS, Google, or third-party components. Imported files must retain their original notices and satisfy their respective licenses.
