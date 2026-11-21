# T-002 Configure Jetpack Compose

**Owner:** Dev, implementation assisted by Codex · **Created:** 2026-11-21 · **Source:** [P-002](P-002-jetpack-compose-configuration.md), agreed by Dev on 2026-11-21; roadmap M1.2

**Status:** Done — 2026-11-21; CLI/native checks passed and Dev confirmed Studio sync/preview.

**Task approval:** T-002 approved on 2026-11-21 by Dev. Approval evidence: “task approved”, responding to the explicit request to approve T-002 as written. This approves T-002 only; implementation begins in a new Session B.

**Complexity:** Simple — bounded single-module toolkit integration, stateless content and one Activity integration test; no engine, lifecycle loop or cross-feature state.  
**Recommended execution profile:** `sol-medium`, selected through the user's client for Session B. Exact available runtime mapping must be confirmed, not invented. Escalate to `sol-high` if substantial compiler/integration debugging emerges.  
**Execution method:** Direct execution using Superpowers `executing-plans` in a new Session B, with TDD for the marker integration and focused self-review. No subagent execution selected.  
**Dependencies:** Completed [T-001](T-001-project-setup.md), [worklog](../worklog/T-001-2026-11-21.md); accepted [DD-001](../decisions/DD-001-application-identity.md), [DD-002](../decisions/DD-002-minimum-android-version.md), [DD-003](../decisions/DD-003-foundation-build-baseline.md), [DD-004](../decisions/DD-004-compose-configuration.md). Environment limitations: [ISS-001](../issues/ISS-001-sandbox-gradle-access.md), [ISS-002](../issues/ISS-002-emulator-startup-instability.md).

**Skill access handoff:** No local Superpowers installation was found during Session A. The agent historically read published `brainstorming` and `writing-plans` SKILL.md files on 2026-11-21; this did not install the plugin. Dev subsequently rejected the remote-instruction fallback and chose local reinstallation. Before implementation, verify local `executing-plans`, `test-driven-development`, `verification-before-completion` and supporting resources through [AGENTS.md — Superpowers availability and handoff](../../AGENTS.md#superpowers-availability-and-handoff). The earlier upstream fallback is withdrawn. T-002's approval is retained; local skill availability remains a prerequisite.

**Worklog accounting handoff:** Carry forward Session A's [P-002 planning-profile evidence](P-002-jetpack-compose-configuration.md): requested `sol-high`, exact runtime mapping unavailable. Record Session B's actual confirmed profile separately. Dev also supplied a Session B response on 2026-11-21 reporting a stop after local skill discovery, with no changes/checks; this is user-supplied evidence of an additional contributing session attempt, not independently verified implementation. Include it in the stage breakdown without inventing a model or exact total. Use `sessions: unknown` if the complete distinct-session count cannot be established; do not default to 1.

## Requirement

Dev can build, preview and launch Compose inside the existing Android application. The real launcher displays `Snake & Math` and `Compose ready`, and an instrumented test verifies that Activity-to-composition integration without implementing the application theme or later screens.

**Inputs:** Existing `:app`, namespace/application ID `com.acp.snakemath`, launcher `com.acp.snakemath.app.MainActivity`, and `R.string.app_name`. **Output interface:** `@Composable fun SnakeMathApp()` in `com.acp.snakemath.app`, called by `MainActivity.onCreate(savedInstanceState: Bundle?)`; no state, repository or navigation dependency. A private `SnakeMathAppPreview()` invokes that same root.

## Steps

- [x] **1. Verify authorization and baseline.** In Session B read the tracker, P-002, this task and linked decisions/issues. Verify explicit T-002 approval and execution profile; inspect branch, working tree and build files. Preserve uncommitted M1.1/IDE changes. Mark T-002 In progress only when implementation begins. Use the PRD Development Approach and explain substantive integration choices. No commit/push is authorized.
- [x] **2. Configure Compose and test dependencies.** Extend the existing catalog/root/app builds with `org.jetbrains.kotlin.plugin.compose`, starting at `2.2.10`, and enable `buildFeatures.compose`. Keep AGP built-in Kotlin; do not add `org.jetbrains.kotlin.android` or legacy `kotlinCompilerExtensionVersion`. Pin Compose BOM `2026.09.00` as the researched starting candidate from Android's setup sample, and Activity Compose `1.11.0` (official notes specify compile API 36). Add BOM-managed `ui`, `foundation`, `ui-tooling-preview` as implementation, `ui-tooling` as debug-only, and `ui-test-junit4` as androidTest. Import the BOM into implementation and androidTest. Add AndroidX Test runner `1.7.0` and ext JUnit `1.3.0` for androidTest; set `testInstrumentationRunner` to `androidx.test.runner.AndroidJUnitRunner`. No test-only Activity is needed because the smoke test launches the production Activity. Keep JUnit 4 and other transitive runtime versions recorded as actually resolved.
- [x] **3. Check the resolved combination before UI work.** Run `:app:assembleDebug :app:assembleDebugAndroidTest` plus the dependency inspections below. Confirm Kotlin/plugin alignment, compile SDK requirements, API 23 minimum and no unstable artifacts. The proposed pins are candidates, not a verified build: if one conflicts, select a compatible stable release using official metadata/release notes and record the reason and actual versions. Do not bypass Kotlin metadata checks, raise API 23, opt out of built-in Kotlin or upgrade the accepted major/minor toolchain without returning to review. Keep compatible corrections within this capability; unresolved compatibility means partial work, not a fabricated pass.
- [x] **4. Prepare a meaningful failing integration test.** Adapt `MainActivity` to `ComponentActivity`, calling `super.onCreate`, but retain its empty content initially. Create `MainActivityTest` using `createAndroidComposeRule<MainActivity>()`; add `launcherDisplaysComposeSetupMarker()`. Assert `onNodeWithText("Snake & Math").assertIsDisplayed()` and `onNodeWithText("Compose ready").assertIsDisplayed()`. Do not call test-owned `setContent`. On a ready emulator/device, run the targeted test and confirm failure because production content is absent, not due to test infrastructure, compilation or device startup. If no usable target exists, record that the red phase was unavailable and keep device checks pending.
- [x] **5. Add the minimal production composition.** Create `SnakeMathApp()` and call it from the launcher's `setContent`. Add `compose_ready` = `Compose ready` to strings; reuse `app_name`. Use Foundation layout/`BasicText`, resource lookups, a neutral white background and explicit dark system-font text. Keep text in a simple vertical layout with 16dp gutter and 8dp separation, respecting safe drawing insets once. Use an explicit text size of 20sp for the app name and 16sp for the marker; permit wrapping and font scaling. Root background fills the window. Use Activity edge-to-edge support with readable system bars and Compose insets. These are temporary marker defaults, not shared theme tokens or a product theme. Add one private preview invoking the root without repositories or live state.
- [x] **6. Verify the native integration and foundation checks.** Re-run the targeted test: both nodes must be displayed from the production Activity. Run the full build/lint/unit-task and connected test commands below. Observe the marker on an identified target, including text clear of system bars and no app crash; inspect a native screenshot if available. Inspect the same marker with increased system font scaling without clipping; record and restore any device setting changed for this check. A stable available target suffices for this setup task; record its actual API, and do not claim an API 23 device run unless performed.
- [x] **7. Verify Studio and preview separately.** Open/sync the existing Gradle project using the recorded JBR/runtime setup. Render `SnakeMathAppPreview`; confirm both strings are visible. Record actual IDE sync and preview evidence separately from CLI compilation and emulator output. Explain required manual IDE steps if tools cannot perform them. Unavailable IDE checks remain unchecked.
- [x] **8. Review, document and hand off.** Update README with Compose setup, preview and test commands/coverage. Review the actual diff against P-002 and this task; check new files, dependencies, scopes, resources, inset handling and production-Activity test ownership. Run `git diff --check`. Read the worklog template and create `docs/worklog/T-002-YYYY-MM-DD.md` using the actual implementation date, outcomes, versions, model/profile evidence and unresolved checks. Create issue records only for actual new limitations. Update task/tracker with verified evidence; check M1.2 only when all required checks pass. End with M1.3 planning as the next handoff. Do not implement it.

### Verification commands and expected outcomes

Use the configured local JBR/SDK from T-001; keep host paths out of committed files. Commands use wrapper shell notation; on Windows use `./gradlew.bat`. Use the environment's approval mechanism for the documented sandbox failure rather than changing the build to evade it.

```sh
./gradlew :app:assembleDebug :app:assembleDebugAndroidTest --console=plain --no-daemon
./gradlew :app:dependencyInsight --dependency kotlin-compiler-embeddable --configuration kotlinCompilerClasspath --console=plain --no-daemon
./gradlew :app:dependencies --configuration debugRuntimeClasspath --console=plain --no-daemon
./gradlew :app:dependencies --configuration debugAndroidTestRuntimeClasspath --console=plain --no-daemon
./gradlew :app:buildEnvironment --console=plain --no-daemon
```

Expected: both APKs compile, compiler/plugin versions align, resolved dependencies satisfy SDK/Kotlin constraints. Inspect the complete reports: a successful dependency-report command alone does not prove every artifact resolved. Record any transitive Kotlin upgrade and explain it; Gradle's embedded Kotlin is not the app compiler.

```sh
adb devices -l
./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.acp.snakemath.app.MainActivityTest --console=plain --no-daemon
./gradlew :app:assembleDebug :app:testDebugUnitTest :app:lintDebug --console=plain --no-daemon
./gradlew :app:connectedDebugAndroidTest --console=plain --no-daemon
```

Select the development target explicitly when multiple devices exist (for example local `ANDROID_SERIAL`). Expected: the targeted test fails for missing nodes before step 5 and passes afterward; the complete connected suite passes. Build/lint has no errors. Report `NO-SOURCE` honestly for unit tests; it is not behavioral coverage. Keep the negative and passing results in the worklog. Do not repeat the same full suite unless changes or failures warrant it.

For separate manual launch observation on the selected target:

```sh
adb -s "$DEVICE_SERIAL" install -r app/build/outputs/apk/debug/app-debug.apk
adb -s "$DEVICE_SERIAL" shell am start -W -n com.acp.snakemath/com.acp.snakemath.app.MainActivity
```

Expected: successful install/launch and both strings observed without an app crash. Use the shell's appropriate variable syntax. No global log clearing, forced data deletion or emulator replacement is required.

**Review focus:** compiler/library mismatch (steps 2–3); a test that substitutes its own content (4–6); system-bar overlap or large-font clipping (5–6); preview success mistaken for a native run (6–7); environment stalls mistaken for app failure (3–7, ISS-001/ISS-002).

## Definition of done

- [x] Compose compiles in the existing single `:app` with the Kotlin-matched plugin and BOM-managed libraries; actual pinned/resolved versions and compatibility evidence are recorded, retaining the accepted SDK/build baseline.
- [x] Production `MainActivity` hosts stateless `SnakeMathApp`; `Snake & Math` and `Compose ready` come from resources and are visibly readable on a neutral background within safe insets, including the recorded increased-font check.
- [x] A repository-independent Studio preview renders both strings, and actual Studio sync succeeds; evidence is recorded separately from CLI results.
- [x] `:app:assembleDebug :app:assembleDebugAndroidTest :app:testDebugUnitTest :app:lintDebug` succeeds without errors; unit-test absence and warnings are reported accurately.
- [x] `MainActivityTest.launcherDisplaysComposeSetupMarker` launches the real Activity, asserts both displayed strings and passes on an identified device/API through `:app:connectedDebugAndroidTest`. Red-phase evidence or its exact availability limitation is recorded.
- [x] Native launch/visible-marker evidence and app crash inspection are recorded; any unavailable native, IDE or preview acceptance remains unchecked and the task stays partial.
- [x] README, task/worklog and tracker reflect actual results; no future feature/theme/artwork, local paths or unrelated user changes are introduced. No commit/push occurs without authorization.

## Existing behaviour that must not change

- Preserve application/namespace `com.acp.snakemath`, launcher component, app label, one module, AGP 9.4.0 / Gradle 9.6.0, compile/target SDK 36, Build Tools 36.0.0, API 23 floor and JVM target 17. The Activity gains Compose content; its class/component remains stable.
- Keep product/design references, templates, uncommitted M1.1 source/docs and local IDE edits intact. Do not untrack or rewrite unrelated files.
- Retain the planned pure Kotlin engine boundaries, approved Home/snake references and current review status of other screen/loading proposals. This configuration does not implement or validate gameplay/device policy.

## Files and lines that matter

Existing line references describe the current small files; new files have no line numbers yet.

| Action | Exact path | Responsibility |
|---|---|---|
| Modify | `gradle/libs.versions.toml:1` | Compose plugin/BOM, Activity and test aliases/versions |
| Modify | `build.gradle.kts:1` | Declare Compose compiler plugin with `apply false` |
| Modify | `app/build.gradle.kts:1` | Enable Compose, dependencies and instrumentation runner |
| Modify | `app/src/main/java/com/acp/snakemath/app/MainActivity.kt:1` | ComponentActivity and production `setContent` |
| Create | `app/src/main/java/com/acp/snakemath/app/SnakeMathApp.kt` | Stateless root marker and private preview |
| Modify | `app/src/main/res/values/strings.xml:1` | Add `compose_ready`; preserve `app_name` |
| Create | `app/src/androidTest/java/com/acp/snakemath/app/MainActivityTest.kt` | Actual launcher integration smoke test |
| Modify | `README.md` | Compose setup/preview/testing instructions |
| Update | `docs/requirements/T-002-compose-configuration.md`, `docs/task-tracker.md` | Approval/progress and actual evidence links |
| Create | `docs/worklog/T-002-YYYY-MM-DD.md` | Actual dated result from the existing worklog template |

Manifest identity/platform window theme, settings, wrapper and Gradle properties require no planned change. If an evidenced integration fix needs another file, explain and record it without expanding scope. No empty package scaffold.

## Reference

- Approved [P-002](P-002-jetpack-compose-configuration.md), especially the marker sketch and sections 3/7; [DD-004](../decisions/DD-004-compose-configuration.md).
- This is toolkit configuration with temporary verification text, not a product-screen task. No mockup image governs the marker. Written acceptance: exactly the two resource-backed strings, plain neutral presentation, safe insets, readable font scaling, no actions or product artwork. Home's primary image remains reserved for its later task.
- [Android compiler/dependency setup](https://developer.android.com/develop/ui/compose/setup-compose-dependencies-and-compiler), [BOM guidance](https://developer.android.com/develop/ui/compose/bom), [built-in Kotlin](https://developer.android.com/build/migrate-to-built-in-kotlin), [Activity release notes](https://developer.android.com/jetpack/androidx/releases/activity#1.11.0), [AndroidX Test releases](https://developer.android.com/jetpack/androidx/releases/test), [Compose testing](https://developer.android.com/develop/ui/compose/testing).
- Planning research on 2026-11-21 confirmed the setup/BOM pattern, Activity 1.11.0's API 36 compilation and stable test runner/ext JUnit versions. The latest sample versions are not proof of compatibility; Maven POM access through the web tool failed. Step 3 resolves/verifies the proposed combination in Session B, without claiming it was built during planning.
- [PRD Development Approach](../snake-math-prd.md#development-approach), [architecture entry-point boundaries](../architecture.md#4-package-structure-and-construction), [Android handoff](../android-prototype-handoff.md), [design-system authority](../design-system/README.md).

## Out of scope

- M1.3 package scaffolding, M1.4 theme/tokens/fonts, M1.5 navigation, M1.6 Home and M1.7 mode/gameplay flow.
- Material sample screens, new artwork, shared styled components, loading animation and artificial delays.
- Game/math engines, ViewModels, loops, input, persistence, settings, DI libraries, phone/tablet classification and orientation enforcement.
- Major/minor baseline toolchain changes, increased minimum SDK, metadata bypasses, release configuration, publishing, commit or push.

**Session B handoff after task approval:** Read tracker → P-002 → T-002 → DD-004 and inherited decisions/issues. Confirm `sol-medium` in the client. First action is authorization/working-tree inspection; then follow steps 2–8 using `executing-plans`. No Session B implementation starts from proposal agreement alone.

**Session B result (2026-11-21):** See [T-002 worklog](../worklog/T-002-2026-11-21.md). BOM corrected to 2025.11.01 / Compose 1.9.5 to retain SDK 36. APKs/build/lint and red-to-green production-Activity test verified on API 34; full connected suite passed. Font scale 2.0 visually checked and restored. Dev confirmed “Sync and preview both pass” on 2026-11-21 in response to the explicit IDE sync and visible-marker preview check; step 7 and all definition-of-done items are complete. No commit/push performed.
