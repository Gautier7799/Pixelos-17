# PixelOS 17 Standalone Launcher

An independent Android launcher APK for Pixel 8 / Android 17. The project is intended to become a reliable daily-use HOME app without root, an unlocked bootloader, or ROM/framework modification.

## Current engineering status

**The current APK is a UI prototype, not a Launcher3 port.** The successful workflow run #20 only proves that the small Java prototype compiles. It does not provide the Launcher3 workspace, app drawer architecture, widget hosting, workspace persistence, or PixelOS/AOSP launcher behavior. Do not treat that APK as a project milestone or daily-use candidate.

Work is continuing on branch `port/pixelos-launcher3`. The prototype is frozen while the source foundation is corrected. Automatic builds on every push are disabled to avoid spending time and CI resources on an implementation that is being replaced.

## Source decisions

- Android 17 baseline: PixelOS manifest branch `seventeen`, which selects AOSP tag `android-17.0.0_r1` and AOSP Launcher3.
- PixelOS/AOSPA reference: [PixelOS-AOSP/packages_apps_Launcher3](https://github.com/PixelOS-AOSP/packages_apps_Launcher3), archived legacy branch `thirteen`.
- The legacy fork is valuable for identifying AOSPA customizations and its non-Quickstep source split, but it is not a current Android 17 Gradle-ready source tree.
- The intended engineering direction is to use Android 17 AOSP Launcher3 as the compatibility baseline, then selectively port verified and licensed AOSPA/PixelOS changes.
- The old hand-written `LauncherActivity` prototype is not the product architecture and will not be extended as a substitute for Launcher3.

Detailed findings and next gates:
- [PixelOS fork audit](docs/PIXElOS_FORK_AUDIT.md)
- [Source and licensing audit](docs/SOURCE_AUDIT.md)
- [Standalone porting plan](docs/PORTING_PLAN.md)
- [Product requirements](docs/PRODUCT_REQUIREMENTS.md)

## Locked product requirements

- iOS-inspired Home Screen, App Drawer, Widgets surface, and fully automatic iOS-style Dock.
- Original app icon artwork must remain untouched: no recoloring, tinting, monochrome conversion, replacement, or masks that alter its appearance.
- No Lock Screen work; that proposal was explicitly rejected.
- Ordinary installable APK only. No privileged/system installation or framework modifications.

## Licensing

The repository MIT license applies only to original project contributions. It does not relicense AOSP, PixelOS/AOSPA, Google, or third-party code and assets. Imported code must retain its original notices and satisfy its own license. Proprietary Pixel Launcher assets or binaries will not be bundled without verified redistribution rights.

## Build policy

No new APK build will be launched until the Launcher3 source snapshot, dependency replacements, manifest strategy, and standalone build path pass the source-port review. A green CI result for the frozen prototype is not considered a successful launcher build.
