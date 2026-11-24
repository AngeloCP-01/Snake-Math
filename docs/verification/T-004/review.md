# T-004 implementation review — 2026-11-22

**Outcome:** Changes required. The user's conditional commit, push and PR instruction was not exercised because a focused native regression failed and task acceptance remains incomplete. No production code was changed, no files were staged, and no commit/push/PR/merge was performed.

**Current follow-up, reconciled 2026-11-24:** This is the historical failed review outcome. ISS-003–ISS-005 and stale README instructions were subsequently corrected, with permanent regressions, 25 passing API 34 tests and a fresh read-only review. Dev then authorized publication and merged PR #3 as `08bb33c`. See [current correction handoff/evidence](review-fixes.md). Earlier no-commit/no-push restrictions describe their review stage, not the present integration state. Broader T-004 device/Studio/visual acceptance remains open; no new native verification is claimed here.

**Scope:** Current tracked and untracked T-004 implementation and records. Fetched `origin/main`: `8b896f13408be71eff9a122806a3ac1c4cec1d7a`; current HEAD: `1b7c59b` (the existing T-003 package/architecture documentation commit). The branch therefore also contains earlier unmerged documentation. The pre-existing `TEMPLATE-decision.md` punctuation edit remains untouched and outside T-004 scope.

The locally installed code-review skill required separate standards/spec reviewers. Both were launched with fresh contexts, requested model `gpt-6.2-sol`, reasoning `high`; exact executing model identity is not independently exposed. Root runtime model/profile mapping remains unknown. Review feedback was checked against actual code, task corrections, native screenshot, resource hashes and current official platform documentation.

## Standards

1. **P2 — Repeated return actions can empty the navigation host.** `SnakeMathNavHost.kt:22` and `:26` unconditionally pop the current destination. A focused API 34 test delivered the same Back to Home callback twice and failed with `Repeated Back removed Home: null`. Both destinations have the same pattern. The source review also found stale mixed forward callbacks can stack Modes then Settings; only the repeated Back case was dynamically reproduced. [ISS-003](../../issues/ISS-003-repeated-back-removes-home.md).
2. **P2 — Fit tests can accept partial clipping.** `HomeScreenTest.kt:45` uses already-clipped semantics bounds and partial visibility. The title, subtitle and mascot do not receive the promised full containment/overlap checks. This is a verification defect, not evidence that the current native layout actually clips. [ISS-004](../../issues/ISS-004-home-fit-checks-use-clipped-bounds.md).
3. **P3 — Shared components omit documented styling.** PLAY lacks gloss; Settings lacks its icon/minimum width; the panel radius differs from the design system. The accepted responsive/dual-score changes do not supersede those requirements. [ISS-005](../../issues/ISS-005-home-reference-details-incomplete.md).
4. **P3 — Contributor instructions describe removed code.** `README.md:75` still names `launcherDisplaysComposeSetupMarker`; its Studio instructions still require `SnakeMathAppPreview` and setup-marker strings, although the implementation replaced them. Update these pointers during corrections.

## Spec

1. **Medium — Approved Home treatment is incomplete.** T-004's reference acceptance requires the supplied styling. `HomeLogo.kt` omits gradient/leaf details and changes outline/extrusion; `PrimaryButton.kt` omits gloss and rounds neither triangle corners nor the specified gradient stops. Source and the inspected native capture confirm these differences. [ISS-005](../../issues/ISS-005-home-reference-details-incomplete.md).
2. **Medium — Settings and score-panel reference details are missing.** The design system requires a 136dp-wide Settings pill with a 26dp cream icon and a trophy in the panel; those are absent. The dual-score change authorizes a panel layout adaptation, not automatic removal of the artwork treatment. [ISS-005](../../issues/ISS-005-home-reference-details-incomplete.md).
3. **Low — Full fit acceptance is only partly tested.** T-004/DD-008 require all content visible without scrolling; clipped-bounds/partial-visibility assertions do not establish that. Remaining API 23/API 36, rotation and Studio checks are already reported as pending. [ISS-004](../../issues/ISS-004-home-fit-checks-use-clipped-bounds.md).

**Axis summary:** Standards: four findings, worst P2 navigation; Spec: three findings, worst Medium visual acceptance. The axes intentionally retain overlapping observations.

## Fresh verification

| Check | Actual result |
|---|---|
| Assemble / JVM test task / lint / original connected suite | `BUILD SUCCESSFUL in 3m 20s`; original API 34 runner logged **13 tests, 0 failed, 0 ignored**. [Build log](review-build-checks.txt). JVM task `NO-SOURCE`; lint **0 errors, 15 warnings**. The 13 count was observed in current TestRunner logcat, not inferred from an older result file. |
| Focused repeated-Back regression | **1 test, 1 failure, 0 errors** on API 34, `Repeated Back removed Home: null`. [XML](review-navigation-failure.xml), [build output](review-navigation-check.txt), [reproducer](review-navigation-reproducer.kt). |
| Runtime PNGs | Both mascot/backdrop hashes match `docs/assets/asset-manifest.json`; source images unchanged. |
| Bundled fonts | Baloo 2 `61bdf7f21385955c7be41c53c5e53924ce1febe767b5dcb8a637e7667ba7b33f`; Nunito `da730c7d1b296e594385837c9b092695cd57d246684474392f6ce42a8f912154`; both match provenance. |
| API 23 / API 36 / rotation / Studio | Not newly verified. API 23 image is absent; API 36 image exists but its prior boot problem remains recorded. No broader device/Studio completion claim. |

The first restricted wrapper attempt failed creating a lock under `C:/.gradle`; the supported retry used Studio JBR and an explicit existing Gradle user home outside the sandbox. It succeeded. This environment failure is separate from the reproduced application regression.

The focused reproducer was compiled temporarily as `app/src/androidTest/java/com/acp/snakemath/app/navigation/NavigationReviewTest.kt`, then removed after saving its source/results here. To repeat it, restore the archived file at that exact source path and run:

```powershell
./gradlew.bat :app:connectedDebugAndroidTest '-Pandroid.testInstrumentationRunnerArguments.class=com.acp.snakemath.app.navigation.NavigationReviewTest' --console=plain --no-daemon
```

The production implementation and original tests remain unchanged by this review. Do not interpret removing the diagnostic source as fixing its failed assertion. Correct the production issue and promote the regression to the permanent suite before re-reviewing.

## Platform evidence

Context7 resolved and queried the official Compose reference: [`boundsInRoot` applies clipping](https://developer.android.com/reference/kotlin/androidx/compose/ui/semantics/SemanticsNode); use unclipped bounds for complete containment. Navigation search returned no matching passage, so the primary reviewer checked [official back-stack guidance](https://developer.android.com/guide/navigation/backstack#handle_a_failed_pop_back), which explains that removing the last destination leaves null current destination/blank content. The emulator reproduction above establishes the behavior in this project's version.
