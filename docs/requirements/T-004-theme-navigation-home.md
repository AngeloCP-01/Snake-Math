# T-004 Create theme, navigation and basic Home

**Owner:** Dev, implementation assisted by Codex · **Created:** 2026-11-22 · **Source:** Dev's combined M1.4–M1.6 planning request; [P-004](P-004-theme-navigation-home.md)

**Status:** Partial overall — ISS-003–ISS-005 and stale README corrections verified on 2026-11-24, with final build/lint and 25 passing API 34 tests. Fresh read-only correction review has no remaining source/material visual findings. See [correction handoff](../verification/T-004/review-fixes.md). Broader API 23/API 36/rotation/Studio coverage remains open. Dev subsequently checked the result and authorized publication on 2026-11-24: commit `600596d` was published through [PR #3](https://github.com/AngeloCP-01/Snake-Math/pull/3), merged by Dev into main on 2026-11-24 as `08bb33c`. Merge was verified on GitHub and in local/remote main; outstanding acceptance remains pending. Session B authorization and user-confirmed sol-high remain in effect; exact runtime ID unknown.

**Authorization:** [DD-006](../decisions/DD-006-combined-foundation-planning.md) permits drafting this task without separate proposal/task planning approval pauses. It is not approval of unseen code or permission to start implementation in Session A. A new Session B needs Dev's instruction to implement T-004; do not repeat planning approval questions already waived.

**Complexity:** Complex — combines custom theme/assets, navigation integration, responsive Home, orientation and native verification across packages.

**Recommended execution profile:** `sol-high`, selected in the client; exact runtime model/profile mapping must be confirmed when available, not invented.

**Execution method recommendation:** Direct execution with local Superpowers `executing-plans` in Session B; focused self-review. No subagent execution selected.
**Dependencies:** Completed T-001–T-003; accepted [DD-001](../decisions/DD-001-application-identity.md), [DD-002](../decisions/DD-002-minimum-android-version.md), [DD-003](../decisions/DD-003-foundation-build-baseline.md), [DD-004](../decisions/DD-004-compose-configuration.md), [DD-005](../decisions/DD-005-package-structure.md). Environment references: [ISS-001](../issues/ISS-001-sandbox-gradle-access.md), [ISS-002](../issues/ISS-002-emulator-startup-instability.md), [T-003 handoff](../worklog/T-003-2026-11-21.md#for-the-next-developer-or-ai-session).

## Requirement

**Dev correction, 2026-11-22:** Home must fit all components on screen without scrolling. Use adaptive illustration/title sizes and spacing while preserving readable score labels and 48dp actions. This supersedes Home scrolling allowances in the original steps/acceptance text below; see [DD-008](../decisions/DD-008-home-fits-without-scrolling.md). Add native bounded-layout tests and remove scroll-dependent test interactions. Other destinations and outstanding device checks retain their existing scope/status.

Replace the Compose setup marker with native Home using the existing design system and a tested Navigation Compose shell. Complete M1.4–M1.6 as one work unit with separate verification checkpoints; preserve M1.7 and all later gameplay/persistence scope.

**Architecture/interfaces:** `SnakeMathApp()` owns theme and host composition. `SnakeMathNavHost(navController: NavHostController, modifier: Modifier = Modifier)` owns typed routes and navigation callbacks. `HomeUiState(classicBest: Int? = null, mdasBest: Int? = null)` contains presentation values; null means unavailable. `HomeScreen(state: HomeUiState, onPlay: () -> Unit, onSettings: () -> Unit, modifier: Modifier = Modifier)` renders values and forwards events, with no NavController, repositories or ViewModel. `FoundationDestinationScreen(title: String, message: String, onBack: () -> Unit, modifier: Modifier = Modifier)` renders the two temporary destinations. No Game route is introduced.

**Global constraints:** Keep namespace/application ID `com.acp.snakemath`, min SDK 23, compile/target SDK 36 and JVM 17. Preserve the current AGP/Kotlin/Compose baseline unless a specific compatibility fix is necessary and documented. One application module; no DI framework, network runtime service or persistence. Use immutable tokens/CompositionLocals, string resources, 4dp spacing grid, 16dp gutters and at least 48dp actions. Phone portrait/tablet landscape remains the product policy. Do not implement placeholder engines or create future feature packages without useful content.

**Review focus:** Insets and enlarged fonts must leave actions reachable; score values must retain their labels; repeated taps/Back must leave a sensible stack; offline launch must use bundled artwork/fonts; tablet rotation must not be confused with a phone landscape layout. Steps below assign native checks to each concern.

## Steps

1. **Establish Session B prerequisites.**
   - [x] Read tracker, P-004/T-004, DD-006 and inherited decisions/issues; verify the implementation instruction and execution profile. Inspect branch/status and retain unrelated edits, especially any decision-template changes.
   - [x] Read installed `executing-plans`, `test-driven-development`, `verification-before-completion` and required supporting resources; use `systematic-debugging` on failures. Mark T-004 In progress in the tracker. Use isolation when useful under the worktree skill; no commit/push without authorization.

2. **Verify dependencies and prepare offline assets (M1.4 prerequisites).**
   - [x] Resolve/query current official docs through Context7 before integrations. Start with stable Navigation Compose/testing 2.10.2, then verify compatibility with min SDK 23, Kotlin 2.2.10 and the existing Compose BOM. Add only Navigation Compose, matching navigation-testing and the Kotlin serialization support required for typed routes. Serialization compiler plugin must match the project's Kotlin version; verify a compatible stable runtime rather than copying an example version. Pin resolved versions in the catalog; leave unrelated dependencies unchanged.
   - [x] Copy the allowlisted mascot/backdrop unchanged into `drawable-nodpi`; compare SHA-256 with the source manifest. Obtain Baloo 2 weight 800 and Nunito weight 700 from official Google Fonts sources, retain licenses and document source URLs/hashes and any static-instance generation in `docs/assets/font-sources.md`. Use offline font resources that render correctly on API 23. If a source is inaccessible, record the exact dependency rather than substitute art/fonts.
   - [x] Run `./gradlew.bat :app:assembleDebug` to verify dependency/resource compilation before building the screens. Expected: successful build; this alone does not verify rendering.

3. **Create the theme and Home components (M1.4).**
   - [x] Implement `SnakeMathTheme(content: @Composable () -> Unit)` and custom immutable `SnakeMathColors`, `SnakeMathTypography` and dimension tokens from `tokens.json`. Expose them via CompositionLocals. Use forest `#023E21`, panel `#053F1C`, cream `#F7F2BB`, gold `#F7D548`; primary gradient `#C1EB43` → `#B1E630` → `#9EDE1E` → `#95DC18`, lip `#3C851B`. Do not enable dynamic colors or user theme selection.
   - [x] Build only Home's shared pieces: `ForestBackground`, `HomeLogo`, `PrimaryButton`, `PillButton` and `BestScoresPanel`. Keep the 6dp primary lip, 4dp press translation/2dp pressed lip, rounded bordered panel and forest backdrop. Native text/vector drawing supplies logo outline/extrusion and simple icons; no screenshot background or new raster asset. Use theme values rather than repeating literal styling throughout screens.
   - [ ] Inspect a themed component preview and native offline startup later in step 6. No tests that merely repeat every token constant are required.

4. **Implement and test the navigation shell (M1.5).**
   - [ ] In `SnakeMathNavigationTest`, add behavior tests for Home as start; PLAY reaches Modes; Settings reaches Settings; both toolbar/explicit Back and system Back return Home. Assert current destination using typed `hasRoute<T>()` and visible content. For rapid repeated taps, assert one target entry and one Back returns Home. Run the focused instrumented class and observe the expected failure against the current marker before implementation; setup/compile failures do not count as behavioral red evidence.
   - [x] Define serializable `Home`, `Modes` and `Settings` data-object routes. Use one remembered controller and one NavHost. Apply `launchSingleTop` for actions; screen callbacks navigate and `popBackStack()` returns Home. Do not pass navigation objects into Home content. Use simple temporary Home content until step 5 if needed to establish the tested shell.
   - [x] Render temporary Modes/Settings content through `FoundationDestinationScreen`: localized title, feature-unavailable message and Back to Home. No operational mode choices/settings controls. Run the focused tests again; expected pass. Record native red/green results, or explicitly leave verification pending if no device is available.

5. **Build native Home and device behavior (M1.6).**
   - [ ] Add `HomeScreenTest` cases with `HomeUiState(classicBest = 120, mdasBest = 340)`: each value is associated with its mode label; PLAY and Settings invoke their separate callback once. With null values, show em dashes with localized unavailable descriptions. Assert no THEMES action or fabricated 1280. Observe behavioral red, then implement the stated Home interface and rerun to green.
   - [x] Compose title/subtitle, static mascot, PLAY, the two-mode score panel and Settings from the primary reference. Keep the approved visual hierarchy, gradient, fonts and artwork proportions. Increase panel height for two readable values rather than shrinking the established typography excessively. Previews inject demo state; live host supplies unavailable state pending persistence. Remove the setup marker/string and update its existing launcher test to check Home.
   - [x] Fit phone portrait Home and propose a two-column landscape tablet layout using the same components. At constrained width/height or enlarged text, adapt title, spacing and mascot height so all Home components fit without scrolling; retain 48dp targets and avoid background decoration behind text. Keep previews free of live navigation/data loops.
   - [x] At the Activity boundary request portrait for phones and landscape for `smallestScreenWidthDp >= 600` tablet configurations; declare `android:appCategory="game"`. Use appropriate light-on-forest system-bar appearance while preserving edge-to-edge/safe insets. The threshold is an agent implementation recommendation, not a newly user-approved device-classification decision. No game-board profile is created or changed here.

6. **Verify the integrated native result.**
   - [x] Run `./gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug`. Record exact outcomes and warnings; if no JVM tests are added, report `NO-SOURCE` without calling it a passed behavioral suite. No pure engine behavior is added in this task.
   - [ ] With an emulator/device, run `./gradlew.bat :app:connectedDebugAndroidTest`. Focused runs can use `-Pandroid.testInstrumentationRunnerArguments.class=com.acp.snakemath.app.navigation.SnakeMathNavigationTest` or `com.acp.snakemath.feature.home.HomeScreenTest`; finish with the full relevant connected suite. Launch normally and offline to confirm bundled fonts/artwork, clear score labels, readable system bars and no setup marker.
   - [ ] Inspect/capture native Home at 390 × 844dp phone, 320 × 568dp small phone and 1024 × 768dp tablet, plus font scale 1.5. Confirm text/buttons remain visible without scrolling, all interactive bounds meet 48dp, and decorative images do not create duplicate accessibility announcements. Compare to the primary reference with documented dual-score/THEMES adaptations; identify tablet layout as proposed. Previews alone do not satisfy native-device checks.
   - [ ] Verify portrait/landscape requests through rotation and Home/Modes/Settings navigation on phone/tablet. Include API 23 font/resource compatibility and API 36 orientation/system-bar checks when devices exist. Record tested configurations and missing coverage separately; platform orientation overrides are acceptance failures to track, even if the fallback layout remains usable.
   - [ ] Verify Android Studio sync and Home previews when Studio access exists. Record unavailable Studio/device checks as pending. Save evidence under `docs/verification/T-004/` with a concise index identifying device/API, dimensions, font scale, commands and outcomes.

7. **Review and hand off the actual result.**
   - [x] Self-review the actual diff against P-004, design tokens and DD-001–DD-006. Check that no persistence, game destination, difficulty selection or gallery approval slipped into scope. Resolve material findings and repeat only affected checks.
   - [x] Record actual substantive implementation choices in the next unused DD record; record blockers/debt in ISS records. Do not label proposed tablet/gallery designs accepted without evidence.
   - [x] Write `docs/worklog/T-004-YYYY-MM-DD.md` from the worklog template, carrying the Session A contribution below and actual Session B profile/results. Update task checkboxes and tracker M1.4/M1.5/M1.6 individually; retain incomplete acceptance items unchecked. Share evidence and the M1.7 handoff. No commit/push is authorized by this plan.

**Actual execution evidence:** [worklog](../worklog/T-004-2026-11-22.md), [verification](../verification/T-004/README.md), [DD-007](../decisions/DD-007-home-foundation-integration.md). Navigation 2.10.2 required SDK 37, so 2.9.7 was selected and verified. Initial behavioral red covered the five Home/navigation cases; typed-route/repeated-action coverage was added during integration. The follow-up 12-test API 34 suite passed, including no-scroll layout coverage. Missing API 23/API 36, comprehensive rotation and Studio checks remain open.

## Definition of done

- [ ] **M1.4:** Custom theme supplies Home colors/type/dimensions; bundled Baloo 2 800/Nunito 700 render offline, with recorded font sources/licenses and unchanged approved artwork hashes.
- [x] **M1.5:** One tested Navigation Compose host starts at Home; PLAY/Settings reach temporary destinations; explicit/system Back return Home; rapid repeated actions do not accumulate duplicate target entries.
- [x] **M1.6:** Native Home replaces the marker and preserves the approved hierarchy/style, with clearly labeled independent Classic/MDAS bests, unavailable production values and no THEMES action. Injected 120/340 values map to the correct labels and callbacks work.
- [ ] Small phone, reference phone and tablet bounds plus font scale 1.5 have readable content fully visible without scrolling, safe insets and at least 48dp action targets; native screenshots and any unresolved visual review are linked.
- [ ] Phone/tablet orientation behavior across rotation/navigation is recorded; API 23 font and API 36 system-bar/orientation coverage is verified or explicitly remains incomplete.
- [ ] Assemble, unit-test task, lint, connected UI tests and Studio checks have individually recorded outcomes. Missing checks remain open; browser evidence is not substituted.
- [ ] Task/worklog/tracker reflect the actual outcome and whole-unit accounting; M1.7 remains unchecked and implementation details have supporting decisions where substantive.

## Existing behaviour that must not change

- Single Activity/Compose launcher and offline operation; application identity, SDK/JVM baseline, package boundaries and approved source artwork.
- Home best scores retain distinct explicit mode labels. No mockup number is treated as a real saved score.
- PLAY's eventual product flow is mode selection before gameplay. No direct jump to a game or default MDAS difficulty is introduced.
- Phone portrait/tablet landscape policy, approved moving-snake contract and all existing gameplay rules remain unchanged.
- Existing seven-screen/tablet/loading proposals retain their review status. No forced loading delay, THEMES/skins, audio or persistence is added.

## Files and lines that matter

Paths below are exact planned files; line numbers are omitted for files that do not exist. Production/test package prefix is `com/acp/snakemath`.

| Action | Files | Responsibility |
|---|---|---|
| Modify | `gradle/libs.versions.toml`, `build.gradle.kts`, `app/build.gradle.kts` | Verified navigation/testing/serialization dependencies and compiler plugin |
| Modify | `app/src/main/java/com/acp/snakemath/app/SnakeMathApp.kt`, `app/src/main/java/com/acp/snakemath/app/MainActivity.kt` | Root theme/host and device/system-bar adapter |
| Create | `app/src/main/java/com/acp/snakemath/app/navigation/Destinations.kt`, `app/src/main/java/com/acp/snakemath/app/navigation/SnakeMathNavHost.kt` | Typed route objects and single host |
| Create | `app/src/main/java/com/acp/snakemath/core/ui/theme/Color.kt`, `app/src/main/java/com/acp/snakemath/core/ui/theme/Type.kt`, `app/src/main/java/com/acp/snakemath/core/ui/theme/Dimens.kt`, `app/src/main/java/com/acp/snakemath/core/ui/theme/Theme.kt` | Custom tokens, providers and bundled font families |
| Create | `app/src/main/java/com/acp/snakemath/core/ui/components/ForestBackground.kt`, `app/src/main/java/com/acp/snakemath/core/ui/components/HomeLogo.kt`, `app/src/main/java/com/acp/snakemath/core/ui/components/PrimaryButton.kt`, `app/src/main/java/com/acp/snakemath/core/ui/components/PillButton.kt`, `app/src/main/java/com/acp/snakemath/core/ui/components/BestScoresPanel.kt`, `app/src/main/java/com/acp/snakemath/core/ui/components/FoundationDestinationScreen.kt` | Only shared presentation needed by this task |
| Create | `app/src/main/java/com/acp/snakemath/feature/home/HomeUiState.kt`, `app/src/main/java/com/acp/snakemath/feature/home/HomeScreen.kt` | Independent score presentation, callbacks and responsive previews |
| Modify | `app/src/main/res/values/strings.xml`, `app/src/main/AndroidManifest.xml` | Localized content/accessibility and game category |
| Create | `app/src/main/res/font/baloo2_extra_bold.ttf`, `app/src/main/res/font/nunito_bold.ttf`, `app/src/main/res/raw/baloo2_license.txt`, `app/src/main/res/raw/nunito_license.txt` | Offline static font resources and licenses |
| Copy unchanged | `app/src/main/res/drawable-nodpi/snake_mascot_v1.png`, `app/src/main/res/drawable-nodpi/forest_background_v1.png` | Allowlisted source artwork |
| Modify/create tests | `app/src/androidTest/java/com/acp/snakemath/app/MainActivityTest.kt`, `app/src/androidTest/java/com/acp/snakemath/app/navigation/SnakeMathNavigationTest.kt`, `app/src/androidTest/java/com/acp/snakemath/feature/home/HomeScreenTest.kt` | Replace marker smoke test; verify navigation and meaningful Home behavior |
| Create/update evidence | `docs/assets/font-sources.md`, `docs/verification/T-004/README.md`, `docs/worklog/T-004-YYYY-MM-DD.md`, this task, `docs/task-tracker.md` | Provenance, actual checks/accounting and progress |

## Reference

**One primary screen image:** [approved Home mockup](../assets/mockups/home-v3.png).

Written acceptance: preserve title/subtitle/mascot/PLAY/panel/secondary-action hierarchy, supplied forest backdrop, cream/lime outlined title, gold numbers, pill lip and rounded panel. Rebuild native controls rather than using the image as a background. Confirmed adaptations to the historical image are two labeled best scores, deferred THEMES, DD-008's no-scroll responsive layout and Dev's tighter multiline title (`0.8.em`, matched outline/fill metrics). These corrections override older fixed spacing and scrolling guidance. Production scores remain unavailable pending persistence; broader tablet/visual acceptance remains open as recorded in the worklog.

Supporting specifications: [P-004](P-004-theme-navigation-home.md), [DD-006](../decisions/DD-006-combined-foundation-planning.md), [design README](../design-system/README.md), [tokens](../design-system/tokens.json), [allowlist/manifest](../assets/README.md), [architecture navigation](../architecture.md#5-navigation-and-screen-ownership) and [PRD Development Approach](../snake-math-prd.md#development-approach). P-004 records Context7/official documentation findings; recheck compatibility before changing build files.

**Local skills handoff:** Superpowers `7.0.1` is catalogued and locally readable at `C:/Users/ThinkPad/.codex/plugins/cache/openai-curated-remote/superpowers/7.0.1/skills/`. Session A read using-superpowers/Codex adaptation, brainstorming and writing-plans. Execution/TDD/verification/debugging SKILL.md paths were found locally; Session B must read them and their required resources. Exact source revision unknown; no remote fallback, installation or skill scripts were used.

**Session/model accounting handoff:** One known contributing conversation: Session A, 2026-11-22, combined planning/documents; requested `sol-high`, agent identity Codex based on GPT-6, exact model/profile mapping unavailable. No subagents. Carry this contribution into the eventual worklog and add actual implementation/review sessions; complete session count and whole-unit correction/rework/wait/limit totals are unknown rather than zero.

**Planning self-review:** P-004 success criteria 1–6 map respectively to steps 2–3, 4, 5, 6, 5–6 and 6–7. Interfaces use the same names/types throughout. The five review concerns have behavioral tests or explicit native checks. This task's internal checkpoints preserve milestone traceability; no task or roadmap checkbox is completed by planning.

## Out of scope

- M1.7 mode-selection/gameplay flow, MDAS numeric bounds and difficulty controls; Game routes, engines, drawing, loop/lifecycle session behavior, pause and results.
- DataStore, repositories, HomeViewModel, saved scores, functional settings/audio, loading motion, THEMES, generated runtime art or wholesale screen-gallery implementation.
- Architecture diagram changes, changing SDK/application identity, unrelated cleanup, release/publishing, commit or push.

**Session B handoff:** Start from tracker → P-004 → T-004 → DD-006 and inherited decisions. Confirm the complex `sol-high` execution profile when available and Dev's instruction to implement T-004, then follow steps in order. Planning approval questions are waived; coding remains in a separate session. First implementation check: dependency/font access and compatibility, followed by native theme → navigation → Home checkpoints. Finish with actual build/lint/UI/device evidence and the M1.7 handoff.
