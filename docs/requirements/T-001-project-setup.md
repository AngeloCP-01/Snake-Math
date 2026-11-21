# T-001 Create Kotlin Android project

**Owner:** Dev · **Created:** 2026-11-20 · **Source:** [Agreed P-001](P-001-kotlin-android-foundation.md), M1.1, Dev's request to create tasks  
**Status:** Done — verified in Session B on 2026-11-21

**Task approval:** T-001 approved on 2026-11-21 by Dev through “lets start our next task docs\requirements\T-001-project-setup.md” in this separate implementation session
**Complexity:** Simple — bounded project/build scaffolding and one launcher, with no game logic, lifecycle loop or feature integration  
**Recommended execution profile:** `sol-medium` — an available Sol model with medium reasoning; verify in the Session B runtime. Escalate difficult toolchain debugging to `sol-high`, explaining the evidence.  
**Execution:** Separate Session B; implement directly using Superpowers `executing-plans`. Focused self-review is sufficient for this scaffold; no delegation is requested.  
**Execution profile confirmation:** Dev confirmed “Sol with medium reasoning is selected” on 2026-11-21. Exact provider model ID is not exposed to the agent.
**Dependencies:** P-001 agreed; [DD-001](../decisions/DD-001-application-identity.md), [DD-002](../decisions/DD-002-minimum-android-version.md), [DD-003](../decisions/DD-003-foundation-build-baseline.md). No related issue record exists at drafting.

**Implementation evidence:** [2026-11-21 worklog](../worklog/T-001-2026-11-21.md). CLI build/lint, packaged identity, API 34 emulator startup and actual IDE sync passed. Dev supplied Help → About evidence for Android Studio Rabbit 2 | 2026.2.2 Patch 2, matching build `AI-262.10968.63.2622.16975389` and JBR 25.0.4. Environment limitations: [ISS-001](../issues/ISS-001-sandbox-gradle-access.md), [ISS-002](../issues/ISS-002-emulator-startup-instability.md).

## Requirement

Dev can open the existing repository as a single-module Kotlin Android project, build a debug APK and launch its minimal Activity. The application uses `com.acp.snakemath`, minimum API 23, and the agreed foundation build baseline without implementing later milestone features.

**Inputs:** Existing repository and references; local Android SDK/JDK; downloads for the pinned wrapper/plugins when needed. **Output interface for M1.2:** module `:app`, namespace/application ID `com.acp.snakemath`, launcher `com.acp.snakemath.app.MainActivity`, resource `R.string.app_name`. There are no engine, repository or navigation interfaces yet.

## Steps

### Preparation and build files

- [x] **1. Verify authorization and environment.** Read the tracker, P-001, this task and linked decisions. Confirm explicit T-001 approval and the execution profile before coding. Inspect `git status --short`, current branch and any newly existing build files; preserve user changes. Mark T-001 In progress in the tracker only when authorized implementation begins. Read the PRD Development Approach and handoff. Do not commit or push without separate authorization.
- [x] **2. Confirm local prerequisites.** Verify Android Studio's displayed release (metadata identified 2026.2.2), SDK platform 36, Build Tools 36.0.0 and JBR 25. Configure IDE Gradle JDK and command-line `JAVA_HOME` locally to the same JBR; use local `ANDROID_HOME`/ignored `local.properties` for SDK discovery. Record versions, not personal installation paths, in the worklog. Check official documentation/Context7 before changing integrations. Explain any required manual IDE step before asking Dev to perform it.
- [x] **3. Add `.gitignore`.** Ignore `.gradle/`, `.kotlin/`, module/root build outputs, `local.properties`, `.idea/`, `*.iml` and OS metadata. Do not remove existing tracked IDE files or accidentally ignore `gradle/wrapper/gradle-wrapper.jar`. Local settings remain local.
- [x] **4. Create root build configuration and version catalog.** Set `rootProject.name = "SnakeMath"` and include exactly `:app` in `settings.gradle.kts`. Use `google()`, `mavenCentral()` and the plugin portal in their appropriate plugin/dependency scopes; prohibit subproject dependency repositories. Pin AGP 9.4.0 in `gradle/libs.versions.toml` under plugin alias `android-application`; root `build.gradle.kts` declares it with `apply false`. Keep `gradle.properties` free of local paths, credentials and opt-outs of AGP built-in Kotlin. Add no Compose, navigation, DataStore, DI or other feature dependencies.
- [x] **5. Create the complete Gradle 9.6.0 wrapper.** Generate the official wrapper scripts/JAR with the verified Gradle 9.6.0 distribution using a temporary empty bootstrap project outside the repository; copy its complete wrapper files into this project. Use the official `gradle-9.6.0-bin.zip`, set `distributionSha256Sum` from Gradle's published checksum, verify the wrapper JAR against its published checksum and make `gradlew` executable. Record sources/checksums in the worklog. Do not run `gradle init` over the repository or handwrite a wrapper JAR. Check `./gradlew --version`: expected Gradle 9.6.0 and the configured Java 25 runtime. A download failure is a blocker to record, not proof of build success.

### Application and native verification

- [x] **6. Configure `app/build.gradle.kts`.** Apply `libs.plugins.android.application`; use AGP built-in Kotlin without `org.jetbrains.kotlin.android`. Set namespace/application ID `com.acp.snakemath`, `compileSdk = 36`, `targetSdk = 36`, `minSdk = 23`, Build Tools 36.0.0, `versionCode = 1`, `versionName = "0.1.0"`, and matching Java/Kotlin JVM target 17. Use `compileOptions` and modern `kotlin.compilerOptions` where needed. Do not confuse the Gradle Kotlin DSL version with the app compiler version. No custom release signing or minification configuration is needed for this debug baseline.
- [x] **7. Add the Kotlin entry point and manifest.** Create `class MainActivity : android.app.Activity()` in `app/src/main/java/com/acp/snakemath/app/MainActivity.kt`, with no custom content, lifecycle work or game behavior. Register `.app.MainActivity` in the manifest as exported with MAIN/LAUNCHER. Use `@string/app_name` with value `Snake & Math` and platform `@android:style/Theme.Material.Light.NoActionBar` for a blank startup window. Do not add a custom theme resource, XML layout, application subclass, icon art or permissions.
- [x] **8. Run command-line foundation checks.** Execute the commands below from the repository root. Require successful APK assembly and lint without errors; investigate actual failures using Superpowers `systematic-debugging`. Record the real unit-test task outcome, including `NO-SOURCE` when applicable. Do not add a fake assertion to manufacture test coverage. Use dependency insight to record the AGP-selected Kotlin stdlib/compiler evidence, inspecting the resolved build classpath if the compiler version is not established by the stdlib report alone.

```sh
./gradlew projects
./gradlew :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
./gradlew :app:dependencyInsight --dependency kotlin-stdlib --configuration debugRuntimeClasspath
./gradlew :app:buildEnvironment
```

Expected: one application project `:app`; a successful Kotlin compilation and debug APK at `app/build/outputs/apk/debug/app-debug.apk`; lint report at `app/build/reports/lint-results-debug.html`. Report exact warnings and test-task outcomes separately.

- [x] **9. Inspect the packaged manifest.** With local `ANDROID_HOME` set to the actual SDK, run the following. Require package `com.acp.snakemath`, minimum SDK 23, target SDK 36 and launchable Activity `com.acp.snakemath.app.MainActivity`. Check that no unintended permission or custom asset entered the APK.

```sh
"$ANDROID_HOME/build-tools/36.0.0/aapt2" dump badging app/build/outputs/apk/debug/app-debug.apk
```

- [x] **10. Verify Android Studio sync.** Open the existing repository root, select the local JBR 25 as Gradle JDK and sync using the wrapper. Record the displayed Studio version and actual sync result. When using computer/browser UI tools, read the applicable computer-use/browser skills first. A successful CLI build does not establish IDE sync evidence.
- [x] **11. Verify launch on an explicit local target.** Run `adb devices -l`, choose an available development emulator/device and set local `DEVICE_SERIAL` to that target. Install the built debug APK, launch the exact component and check the Activity remains running without a crash. Record device/API, `am start` result and observed blank window; note that no custom screen is expected. If no target exists, record launch as unverified and keep the task partial; do not fabricate a pass or create an instrumented suite solely for this empty Activity.

```sh
adb -s "$DEVICE_SERIAL" install -r app/build/outputs/apk/debug/app-debug.apk
adb -s "$DEVICE_SERIAL" shell am start -W -n com.acp.snakemath/com.acp.snakemath.app.MainActivity
adb -s "$DEVICE_SERIAL" shell pidof com.acp.snakemath
```

Expected: install succeeds, launch reports success, a process exists and the blank Activity is observed without a startup crash. PID alone is not visual evidence. Inspect relevant app crash output if the launch fails; do not clear global device logs.

### Review, records and handoff

- [x] **12. Add `README.md` setup instructions.** Explain the JDK runtime versus JVM target, local SDK configuration, Android Studio opening/sync, wrapper/build commands, startup-only nature of the Activity and current test coverage. Document dependency downloads for initial setup and future Compose work in M1.2; omit personal paths.
- [x] **13. Self-review the actual diff and tracked-file hygiene.** Run `git diff --check` and inspect `git status --short`, including newly created files. Confirm the wrapper JAR is not ignored, `local.properties` and IDE caches are ignored, no local path/credential is in added files, and no existing user documentation or tracked IDE file was overwritten. Compare implementation to every P-001 acceptance point before completion claims. Re-run affected checks only if changes/failures warrant it.
- [x] **14. Record the result and stop at this work unit.** Create `docs/worklog/T-001-2026-11-20.md` from its template if implementation occurs on this date; otherwise use the actual implementation date in that filename. Record actual models, versions, commands/results, unavailable checks and unknown measurements honestly. Create the next unused `ISS-nnn` from its template only for an actual discovered blocker/debt, and link it. Update this task's checkboxes/status and tracker to verified outcomes. M1.1 is checked only when its definition of done passes; M1.2–M1.7 remain unchecked. Hand off to M1.2 planning. No commit is authorized by this task.

**Review focus covered by steps:** mismatched IDE/shell JDK (2, 5, 10); first-build download failure or incomplete wrapper (5, 8); buildable APK with missing/wrong launcher (7, 9, 11); no device or misleading test coverage (8, 11, 14); local-file leakage or overwritten existing work (1, 3, 13). These use real build/package/device checks rather than implementation-mirroring unit tests. This task adds no gameplay behavior; apply TDD when later approved behavior work begins.

## Definition of done

- [x] Repository-root project has exactly one Android `:app` module with Kotlin source; wrapper 9.6.0 and checksums are present, `gradlew` is executable, AGP 9.4.0 is pinned and selected Kotlin/runtime versions are recorded. Any compatible patch correction has evidence and an updated decision/worklog.
- [x] Manifest/APK identity is `com.acp.snakemath`, min SDK is 23, compile/target SDK is 36 and JVM target is 17. The exact Kotlin launcher component is registered and compiled.
- [x] `:app:assembleDebug :app:testDebugUnitTest :app:lintDebug` completes successfully with no lint errors. Test execution or absence is reported accurately; no behavioral-test coverage is claimed without tests.
- [x] Android Studio successfully syncs the repository using the wrapper; IDE/JDK versions and evidence are recorded separately from CLI results.
- [x] An identified emulator/device installs and launches the blank Activity without a startup crash, with observed-window evidence. If unavailable, leave this item unchecked and report the task as partial.
- [x] README supports repeatable setup/build/launch checks without committed local SDK/JDK paths or secrets; ignore rules exclude generated/local artifacts and retain the wrapper.
- [x] No later-milestone feature code, custom art/theme or unrelated user changes are included. Worklog and tracker link the actual result; incomplete checks remain unchecked.

## Existing behaviour that must not change

- Preserve all existing product/design/reference files, user edits, templates and the tentative display title. Application identity and minimum SDK are the explicit accepted values in DD-001/DD-002.
- Preserve the approved future stack, independent engines and single-module direction; do not add an alternative UI toolkit or put gameplay rules in the Activity.
- Both modes' wrap rules, MDAS body pass-through, selected difficulty and phone/tablet policy remain future implementation requirements. A blank launcher does not establish their native acceptance.

## Files and lines that matter

Future files have no current line numbers. Create only these production/build/setup files:

| File | Responsibility |
|---|---|
| `.gitignore` | Exclude generated/local state |
| `settings.gradle.kts` | Root identity, repositories, one module |
| `build.gradle.kts` | Root plugin alias declaration |
| `gradle.properties` | Shared portable Gradle settings |
| `gradle/libs.versions.toml` | Pinned AGP plugin alias |
| `gradlew`, `gradlew.bat` | Official wrapper launch scripts |
| `gradle/wrapper/gradle-wrapper.jar`, `gradle/wrapper/gradle-wrapper.properties` | Wrapper runtime, distribution pin/checksum |
| `app/build.gradle.kts` | App identity, SDK/JVM/build configuration |
| `app/src/main/AndroidManifest.xml` | Application and launcher registration |
| `app/src/main/java/com/acp/snakemath/app/MainActivity.kt` | Minimal Kotlin launcher |
| `app/src/main/res/values/strings.xml` | `app_name` resource |
| `README.md` | Setup/build/verification instructions |

Update this task and `docs/task-tracker.md`; create the actual-date T-001 worklog from `docs/worklog/TEMPLATE-worklog.md`. Read `docs/issues/TEMPLATE-issue.md` only if a real issue needs recording. Local `local.properties` may be generated but is ignored. Do not create empty feature packages, sample tests, resource assets, a custom theme, or a release ProGuard file for this scope.

## Reference

- [P-001](P-001-kotlin-android-foundation.md) and [DD-001](../decisions/DD-001-application-identity.md), [DD-002](../decisions/DD-002-minimum-android-version.md), [DD-003](../decisions/DD-003-foundation-build-baseline.md) are the scope and decision sources.
- [PRD Development Approach](../snake-math-prd.md#development-approach), [architecture foundation](../architecture.md#4-package-structure-and-construction), [Android handoff](../android-prototype-handoff.md#incremental-android-studio-work-when-resumed), [tracker](../task-tracker.md#milestone-1--android-project-foundation).
- Official [AGP compatibility](https://developer.android.com/build/releases/agp-9-4-0-release-notes), [built-in Kotlin](https://developer.android.com/build/migrate-to-built-in-kotlin), [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html), [published checksums](https://gradle.org/release-checksums/) and [Java compatibility](https://docs.gradle.org/current/userguide/compatibility.html).
- **Primary image:** Not applicable: infrastructure/startup scaffold, with no designed screen. Existing approved screen art is not a reference for this blank verification window.

## Out of scope

- M1.2–M1.7: Compose configuration, full package structure, application theme, Home, navigation and game flow.
- Game/math rules, persistence, lifecycle loops, input, board/device/orientation policy implementation and renderer fidelity.
- Runtime artwork, icons, loading motion, release builds/signing, store publication, CI, extra Gradle modules or dependency-injection frameworks.
- Instrumented/Compose test suites or dummy unit tests for the blank Activity; no Android gameplay/performance claims.
- Committing, pushing, deleting user work, or changing reusable templates.
