# T-004 review corrections

**Scope:** ISS-003–ISS-005 and stale root README instructions, following the
[2026-11-22 review](review.md). Correction continuation: 2026-11-24.
**State:** Corrections verified; fresh read-only review has no remaining findings
within this correction scope. Ready for independent review here. All changes remain
uncommitted at correction review. Dev subsequently checked the result and
explicitly authorized committing and opening a PR to `main`; that latest request
supersedes the earlier no-commit/no-push instruction. Merge is not authorized.

## Changes for review

- **ISS-003:** All navigation callbacks check their originating back-stack entry.
  Returning uses typed, non-inclusive pop to Home. Permanent tests cover repeated
  return actions from both temporary destinations, both stale mixed forward
  action orders and a stale destination return callback.
- **ISS-004:** Fit checks use full unclipped bounds for the title, subtitle,
  mascot, score panel and actions, plus text-layout and sibling-separation checks.
  Six finite content-area/font cases include tablet enlarged text. Permanent
  fixtures demonstrate rejection of clipped children, internal text overflow and
  overlapping siblings; the clipping fixture also proves the former assertions
  accept its partial visibility.
- **ISS-005:** Restored native vector leaf clusters, gradient title spans, 6dp
  outline/4dp extrusion, specified PLAY gradient stops/gloss/rounded triangle,
  136dp Settings pill with 26dp cream gear, and 48dp trophy/16dp score-panel radius
  with shadow/inner darkening. Scoped compact typography, dual mode labels,
  unavailable live scores, no-scroll Home and the tighter `0.8.em` title remain.
  Native rendered-pixel checks cover gloss and actual gear/trophy canvases; mode
  score association remains tested. Artwork has a standalone preview.
- **README:** Removed setup-marker test, preview and launch instructions; points
  to current Home/component previews, test coverage and verification evidence.

## Regression and diagnostic evidence

- [Navigation red](revisions-red.xml): eight navigation tests, five failures
  before the fix. The failures reproduce repeated/stale callback behavior.
- [First stronger fit run](revisions-fit-first.xml): 25 tests, six fit failures;
  navigation/artwork/negative fixtures passed. Those fit failures were diagnosed
  as a String `BasicText` semantics discrepancy, not accepted as production clipping.
- [Focused diagnostic](revisions-fit-diagnostic.xml): PLAY reports actual size
  87×61px, reconstructed paragraph width 228px, actual line right 86.66797px,
  no truncation. The exact Compose Foundation 1.9.5 cache bytecode and
  [AndroidX source](https://github.com/androidx/androidx/blob/androidx-main/compose/foundation/foundation/src/commonMain/kotlin/androidx/compose/foundation/text/modifiers/ParagraphLayoutCache.kt)
  explain that semantics reconstruct a paragraph with the prior max constraints
  but retain the actual layout size. A bare `hasVisualOverflow` therefore gives
  a false positive. The helper remeasures the same real text, fonts, density,
  direction, style and wrap/overflow settings against the actual allocation,
  with one native pixel of rounding slack. It retains original and remeasured
  truncation/ellipsis checks; the negative fixture still rejects genuine overflow.

## Final verification

Final run, 2026-11-24, against the final source:

| Check | Result / evidence |
|---|---|
| `:app:assembleDebug :app:testDebugUnitTest :app:lintDebug :app:connectedDebugAndroidTest` | **BUILD SUCCESSFUL, 4m44s**; [full output](revisions-final-build.txt) |
| Native API 34 suite | **25 tests, 0 failures, 0 errors, 0 skipped**; [XML](revisions-final-tests.xml) |
| Native case breakdown | Launcher 1; navigation 8; component rendering 3; title spacing 1; negative fit fixtures 3; Home behavior/layout 9 (six fit configurations) |
| JVM task | `NO-SOURCE`; no JVM behavioral coverage claimed |
| Lint | **0 errors, 15 warnings**; [report](revisions-final-lint.txt). Baseline SDK/tool/library notices, API-specific appCategory, retained license resources and absent launcher icon; no new layout error |
| Native visual | [Final Home](revisions-home-api34.png), [UI hierarchy](revisions-home-api34.xml), [device/capture metadata](revisions-native-state.json): API 34, 1440×3120px, density 560, font scale 1.0 (about 411×891dp). All Home content visible, tight title preserved, gradients/leaves/gloss/gear/trophy present |
| Source/assets | [Source/build fingerprints](revisions-source-hashes.json); [font/PNG hashes](revisions-asset-hashes.json). Both source PNGs match the allowlist manifest; font hashes match provenance |

Commands use the unchanged SDK 36/Kotlin/AGP baseline and documented Studio
JBR/isolated Gradle home. The first final compile caught a `cSnakeMathTheme`
score-row typo, corrected to `SnakeMathTheme`; this was not behavioral red.
The server restart interrupted another run after APK packaging. That incomplete
run was checked before restarting; neither is the final success evidence above.

Normal launch initially timed out behind a **System UI** ANR. The
[obstructed capture](revisions-systemui-anr.png) is diagnostic only (ISS-002).
After choosing Wait, Home readiness was confirmed in the UI hierarchy and the
successful frame captured. Metadata was updated to the successful frame's timestamp.
This is no performance or universal startup claim.

## Fresh review

One read-only review context was dispatched under the locally installed
`requesting-code-review` skill with explicit requested `gpt-6.2-sol`/high;
actual executing model identity is independently unavailable. It verified the
navigation/fit approach and requested the exact trophy gradient stop and narrower
README preview wording; both were corrected. A panel-stroke note was withdrawn
after re-reading the current 4dp source. No remaining source findings were reported;
the final API 34 XML/build/lint and recovered native capture were also inspected.
The reviewer reported no remaining source or material visual findings within
ISS-003–ISS-005/README. Its minor timestamp note was corrected and checked against
the successful image's file time. This is an engineering review, not Dev's final
visual approval or completion of all T-004 device acceptance.

## Source and regression entry points

- [Navigation host](../../../app/src/main/java/com/acp/snakemath/app/navigation/SnakeMathNavHost.kt), [permanent navigation regressions](../../../app/src/androidTest/java/com/acp/snakemath/app/navigation/SnakeMathNavigationTest.kt).
- [Fit assertions](../../../app/src/androidTest/java/com/acp/snakemath/feature/home/HomeFitAssertions.kt), [rejecting fixtures](../../../app/src/androidTest/java/com/acp/snakemath/feature/home/HomeFitAssertionsTest.kt), [Home tests](../../../app/src/androidTest/java/com/acp/snakemath/feature/home/HomeScreenTest.kt).
- [Native artwork](../../../app/src/main/java/com/acp/snakemath/core/ui/components/HomeArtwork.kt), [component regressions](../../../app/src/androidTest/java/com/acp/snakemath/core/ui/components/HomeComponentsTest.kt); shared components/theme hold the other styling changes.
- [Contributor README](../../../README.md), [tracker](../../task-tracker.md), [worklog](../../worklog/T-004-2026-11-22.md).

## Limits and next handoff

**Published, 2026-11-24:** implementation commit `600596d` is pushed on
`feature/m1-theme-navigation-home`; [PR #3](https://github.com/AngeloCP-01/Snake-Math/pull/3)
is open against `main`, with native screenshot and verification evidence.
No merge performed. The unrelated decision-template edit remains uncommitted.

**Publication authorization, 2026-11-24:** Dev requested commit and PR to `main`
after checking the corrections. Pre-publication verification passed again:
[25 native tests](pr-checks-tests.xml), [build output](pr-checks-build.txt),
[lint](pr-checks-lint.txt) (0 errors/15 warnings); JVM task `NO-SOURCE`.
The reviewed source/build fingerprints still match. Branch:
`feature/m1-theme-navigation-home`; it retains prerequisite T-003 commit
`1b7c59b`, which is not yet on `main`. The unrelated decision-template edit is
excluded from staging. The user's publication request does not approve merge or
close the remaining device/Studio coverage.

API 23 rendering, API 36/complete rotation validation, Studio sync/rendered IDE
previews and full device screenshot coverage remain open as recorded previously.
They are not inferred from bounded layout tests. No gameplay, persistence,
dependency baseline or source raster assets were changed. M1.7 remains outside
this correction. Review the published branch/PR; merge remains Dev's decision.
