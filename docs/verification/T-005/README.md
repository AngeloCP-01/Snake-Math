# T-005 native verification

Dates: 2026-11-24–25. Scope: approved non-playable mode-entry foundation from
[T-005](../../requirements/T-005-play-mode-gameplay-flow.md), DD-009–DD-011.

## Executed checks

- `final-build.log`: final app/test APK assembly, all six JVM tests and lint passed.
  Lint: 0 errors / 15 warnings (pinned-version notices, licenses/resources and existing
  manifest/icon warnings); no toolchain upgrade performed.
- `process-verified.log` / [process receipts](process-restart/receipts.json): all three
  saved-task cases passed after matching saved STOPPED Activity state. Task 451 remained;
  Modes/Hard PID 31735→31985, Classic from Hard 31985→32487, MDAS/Hard 32487→724.
  Each relaunched Home and fresh PLAY/Easy. Background Activity dumps, hierarchies and
  screenshots are in `process-restart/`. Earlier script attempts are retained separately.
  This run used the same process-boundary code as the final APK; the APK installed for
  captures additionally contains the visible Selected label fix.

- `unit-red.log`: 6 JVM tests, 4 expected assertion failures with minimal stubs.
  The first sandbox attempt failed before tests; the archived red is the approved rerun.
- `modes-red.log` / `modes-red.xml`: 2 UI tests, both expected missing-content failures.
- `navigation-red.log` / `navigation-red.xml`: 6 navigation tests, all expected
  missing-mode-content failures; the same command ran all 6 JVM tests green.
  Its comma-separated class filter did not run recreation/layout tests.
- `first-green.log` / `first-suite.xml`: build and JVM tasks ran; lint 0 errors,
  15 warnings. Instrumented run: **43 tests, 4 failures** in old navigation tests
  whose Modes Back action was now offscreen. All new navigation (6), recreation (4),
  Modes layout (6), Modes UI (2), and preserved Home/component tests passed.
  Updated only replaced-content interactions to scroll before tapping Back.
- `selection-cue-red.log` / `selection-cue-red.xml`: two focused tests, one expected
  failure on absent visible `Selected` text; implemented that cue afterward.
- `captures.log`: capture helper exited 0, producing 15 native PNGs and matching
  hierarchies for phone 390×844/font 1.0, small phone 320×568/font 1.5 and tablet
  1024×768/font 1.0. [Metadata](captures/device-observations.json) records API,
  actual size/density/font and Activity configuration. Display overrides were reset
  to physical 1440×3120 / 560dpi and initial font scale 1.0 afterward.
  Inspected phone/tablet Modes, enlarged-text scrolling, MDAS/Hard and Home captures.
  Home source stayed unchanged. Native visual approval remains Dev's review.

## Reproduction

Use README JDK/SDK setup and the existing Gradle cache. No runtime/toolchain upgrade.

```powershell
./gradlew.bat :app:assembleDebug :app:assembleDebugAndroidTest :app:testDebugUnitTest :app:lintDebug --console=plain --no-daemon
./gradlew.bat :app:connectedDebugAndroidTest --console=plain --no-daemon
```

After connected tests exit, install `app/build/outputs/apk/debug/app-debug.apk`.
Use explicit local paths/serial:

```powershell
./docs/verification/T-005/verify_process_restart.ps1 -AdbPath '<adb.exe>' -Serial '<serial>'
python ./docs/verification/T-005/capture_native.py --adb '<adb.exe>' --serial '<serial>'
```

The process script uses background `am kill`, retaining the task. It must establish
old/new PID and task identity; cold launch or Activity recreation is not equivalent.
The capture script's force-stop launches are **layout captures only**, not process
restoration proof. Display/font settings are restored in `finally`.

## Coverage boundaries

- Actual device: Pixel_6_Pro_API_34 AVD, Android 14 / API 34, emulator-5554.
- Finite layout checks use native Compose content rectangles with one 64dp safe-area
  allowance at 320×568, 390×844, 1024×768dp and font scales 1.0/1.5.
  These are separate from physical display/classification and rotation acceptance.
- No Studio sync/rendered-preview acceptance or API 23/API 36 device check is claimed.
- `final-connected.log` / `final-suite.xml`: **45 tests, zero failures/errors**, API 34. Includes all original regressions, seven mode-entry navigation cases, four real recreation cases, six Modes profiles and both placeholders at all six profiles. Explicit rotation,
  Studio preview/sync and additional device acceptance remain open in ISS-006.
- TDD limitation: recreation/layout tests were authored before their production
  integration, but the filtered red command did not execute them. Their first observed
  execution was green in the full suite. Do not relabel that as red/green evidence.

## Documentation and review

Local Superpowers 7.0.1 executing-plans, TDD, verification and debugging instructions
were used. The required read-only reviewer was requested as `gpt-6.2-sol`/high;
runtime identity was not independently verified. Findings and their fix evidence
are recorded in the worklog/receipt updates.

Context7 resolved official Android documentation before the integrations:
[typed navigation](https://developer.android.com/develop/ui/compose/migrate/migration-scenarios/navigation),
[saveable state lifespans](https://developer.android.com/develop/ui/compose/state-lifespans),
[selection semantics](https://developer.android.com/reference/kotlin/androidx/compose/foundation/selection/package-summary),
and [restoration tests](https://developer.android.com/develop/ui/compose/testing/common-patterns).
The task pins Navigation 2.9.7 and the existing Compose BOM; newer API examples did
not authorize a dependency upgrade.

Final document-link and diff whitespace checks passed. Dev authorized commit/push
and a PR to `main` on 2026-11-25; merge remains reserved for Dev.
