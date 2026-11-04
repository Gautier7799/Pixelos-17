# Source and Licensing Audit

## Project target

Build an independently installable Android launcher APK for the Pixel 8 running Android 17. The deliverable is an ordinary user-installed application, not a replacement system image, privileged system package, or modified ROM.

## Confirmed upstream structure

- PixelOS Android 17 source manifest: https://github.com/PixelOS-AOSP/android_manifest/tree/seventeen
- The PixelOS manifest references AOSP Launcher3 at `platform/packages/apps/Launcher3`.
- The PixelOS manifest also references a separate Pixel launcher vendor repository: https://gitlab.com/PixelOS-AOSP/proprietary_vendor_pixel_launcher
- AOSP Launcher3 upstream: https://android.googlesource.com/platform/packages/apps/Launcher3/

These references establish the source locations in the manifest; they do not establish that every Pixel-exclusive component is open source, redistributable, or independently buildable as an APK.

## Initial technical assessment

1. Launcher3 is the candidate open-source foundation to audit and port.
2. Pixel-exclusive vendor components must be assessed separately. Do not copy proprietary APKs, resources, overlays, or binaries into this repository unless their licensing and redistribution rights are verified.
3. Launcher3/Quickstep may depend on platform APIs, privileged permissions, framework internals, overlays, or services that are unavailable to an ordinary APK.
4. The port must classify dependencies as:
   - Public Android SDK
   - AndroidX / other redistributable libraries
   - Platform-only or hidden APIs
   - Privileged/system integration
   - Proprietary or licensing-restricted assets
5. Replace or disable unsupported integrations only after identifying their call sites and user-visible impact. Preserve the upstream launcher behavior wherever feasible.

## Repository license warning

This repository currently contains an MIT license for this repository's own original contributions. That license does not relicense AOSP, PixelOS, Google, or third-party source code imported later. Every imported component must retain its applicable copyright and license notices, and compatibility must be checked before distribution.

## Audit status

- [x] Confirmed PixelOS manifest references for Launcher3 and the separate vendor launcher repository.
- [x] Confirmed this repository's current LICENSE text is MIT.
- [ ] Inspect the exact Launcher3 source revision selected by the PixelOS manifest.
- [ ] Inspect Android.bp, manifests, resources, overlays, and dependency graph.
- [ ] Identify platform-only and privileged API usage.
- [ ] Produce a file-by-file porting and licensing decision before importing source.

## Current build status

No standalone Gradle project or APK has been created or built yet. A successful GitHub write-access test has been completed; it is not a build test.
