# T-004 native verification

2026-11-22; Session B, user-confirmed `sol-high`, exact runtime ID unavailable.

**Latest correction evidence, 2026-11-24:** [Review corrections and handoff](review-fixes.md) cover ISS-003–ISS-005 and README fixes. Final build/lint passed; [25 native API 34 tests passed](revisions-final-tests.xml). [Current native Home](revisions-home-api34.png) preserves no-scroll behavior and the tighter title while restoring the missing reference details. Fresh read-only review has no remaining findings within that scope. Earlier evidence below is historical. Broader device/Studio coverage remains pending; no commit/push.

## Subsequent implementation review

[Review findings and fresh verification](review.md): the original 13-test API 34 suite and build/lint passed, but a focused repeated-Back regression failed with null current destination. The conditional commit/push/PR was held. Navigation, fit-test assurance and visual-fidelity issues are linked from that report. No production code changed during review; the temporary reproducer is archived outside the Android source sets.

## Follow-up: Home fits without scrolling

Dev requested no scrolling after checking Home in the emulator. [DD-008](../../decisions/DD-008-home-fits-without-scrolling.md) records the correction. Home now measures within the safe drawing area, reserves space for native controls/scores, adapts title/spacing/text presentation, and allocates remaining height to the mascot. The tablet retains two columns. No scroll-dependent Home interactions remain in the tests.

- [Red results](fit-red.xml): five new layout cases failed against the old scrolling Home; three existing Home cases passed. The reference-phone failure reported Settings outside the visible area.
- [Final results](fit-tests.xml): **12 tests, zero failures/errors** on API 34; includes original navigation/score behavior and five fit regressions. Each checks title/subtitle/mascot, score labels/values, 48dp actions, containment and absence of scrolling.
- Fit cases use available content areas of 320×504dp, 390×780dp, 1024×704dp (nominal 320×568, 390×844, 1024×768 screens with 64dp reserved for insets), plus font scale 1.5 on both phone areas. These are bounded native Compose layouts at injected density 1 inside the API 34 emulator; no physical display overrides or separate tablet device runs are claimed.
- [Build output](fit-build-checks.txt): assemble/lint/connected suite successful; JVM test task `NO-SOURCE`. [Current lint](fit-lint.txt): zero errors, 15 warnings, including baseline version notices, API-specific appCategory, intentionally bundled license resources and missing launcher icon. No new UI layout warning.
- API 23/API 36, complete physical rotation behavior and Studio checks remain pending. The earlier screenshot failure evidence below is historical and does not depict the revised Home.
- Reinstalled and launched the verified APK on emulator-5554. [Current native Home](home-fit-api34.png), inspected at 1440×3120px/density 560 (approximately 411×891dp), font scale 1.0: title, subtitle, mascot, PLAY, both score labels/values and Settings fit in one screen with system bars clear. No display configuration was changed for this capture. Other sizes/font scale are covered by the bounded native layout tests above, not a new screenshot matrix.

## Automated checks

**Latest title-spacing follow-up:** [13-test final suite](logo-spacing-tests.xml) and [successful build/lint output](logo-spacing-build-checks.txt). The [new spacing test first failed](logo-spacing-red.xml) on the separate title boxes (358px baseline separation). Both title layers now share `SNAKE\n& MATH`, `0.8.em` line height and fixed line-height mode; fill spans preserve the two colors. Existing no-scroll layout tests remain green. Lint: zero errors/15 warnings; JVM tests: `NO-SOURCE`.

[Updated native title screenshot](home-logo-tight-api34.png), inspected on the existing API 34 phone emulator: the two title rows sit closer together without visible glyph overlap; colors/outline remain present and every Home action/score remains visible. The verified APK was reinstalled and opened for Dev.

`./gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug :app:connectedDebugAndroidTest --console=plain --no-daemon`

- [Build output](build-checks.txt): successful. Studio JBR, SDK 36, existing isolated Gradle user home from T-001. Sandbox catalog compilation failed as recorded in ISS-001; authorized outside-sandbox execution succeeded.
- Unit-test task: `NO-SOURCE`, no JVM behavior suite claimed.
- [API 34 instrumented results](api34-tests.xml): seven tests, zero failures/errors. Launcher Home, typed start/destinations, repeated actions with one target entry, explicit/system Back, injected 120/340 score association, null score accessibility and separate callbacks.
- [Behavioral red evidence](behavior-red.xml): six tests; existing marker test passed, five new Home/navigation tests failed for missing controls/score semantics before implementation. Typed-route/repeated-action assertions were added during integration; they were not part of that initial red run.
- [Lint](lint.txt): zero errors, eight warnings: target/compile SDK, Gradle/AGP, Compose BOM, Activity, Kotlin version notices and missing launcher icon. Baseline versions remain intentionally unchanged. Java protobuf/Unsafe deprecation warning also appeared during connected testing.

Navigation 2.10.2 failed AAR metadata checks because it requires SDK 37. Navigation 2.9.7 passed with the retained SDK 36 baseline; see [DD-007](../../decisions/DD-007-home-foundation-integration.md). Earlier setup failures (PowerShell argument splitting, font-name collision, source added while a build was running) are not behavioral red evidence. A first integration test clicked offscreen Settings without scrolling; the corrected interaction scrolls first.

## Native screenshots

Attempted on API 34 `Pixel_6_Pro_API_34`, emulator-5554, using reversible display overrides at density 320. These are emulator configurations, not separate physical devices. Initial pre-trimming Home frames were visually inspected during the session; the final refresh saved startup frames, then failed the readiness check even after choosing Wait on the system ANR dialog. Final visual acceptance is **pending**. The failed frames below are diagnostic artifacts only. [Capture script](capture_native.py) restores display/font/airplane settings and refuses new captures without Home content. [Attempt observations](device-observations.txt) are incomplete and do not establish final rendered configuration.

| Configuration | Initial | Scrolled |
|---|---|---|
| Phone 390 × 844dp, font 1.0 | [failed capture](failed-captures/phone.png) | — |
| Small phone 320 × 568dp, font 1.0 | [failed capture](failed-captures/small-phone.png) | [failed scroll capture](failed-captures/small-phone-scrolled.png) |
| Phone 390 × 844dp, font 1.5 | [failed capture](failed-captures/large-font.png) | [failed scroll capture](failed-captures/large-font-scrolled.png) |
| Tablet 1024 × 768dp, font 1.0 | [failed capture](failed-captures/tablet.png) | — |
| Tablet with airplane mode enabled before launch | [failed capture](failed-captures/tablet-offline.png) | — |

Initial visual inspection found excessive Baloo line metrics; `LineHeightStyle.Trim.Both` was applied and passed build/UI tests, but its final visual result still needs capture/review. Home adapts the primary reference with two labeled unavailable scores and no THEMES action; the tablet composition remains proposed. Decorative mascot/background have null content descriptions; the layered logo exposes one app-name description. Actions specify minimum 48dp heights and constrained content scrolls; final native bounds review remains pending.

## Remaining coverage

- API 23 image/device is absent: static font resources compile, but API 23 rendering remains unverified.
- API 36 tablet startup reached ADB but did not complete boot; it was stopped. API 36 orientation/system-bar verification remains open.
- Physical device rotation, Android Studio sync and rendered Studio previews have not been verified in this session.
- Visual fidelity remains available for Dev review; browser mockups are references only. M1.7 is outside this task.

During capture refresh, the UI hierarchy exposed the known “Process system isn't responding” dialog ([ISS-002](../../issues/ISS-002-emulator-startup-instability.md)); [diagnostic frame](startup-diagnostic.png). The script chooses Wait for this system dialog and then requires Home content. Startup frames are not accepted as Home evidence. No Java app crash appeared in the collected AndroidRuntime diagnostic output.
