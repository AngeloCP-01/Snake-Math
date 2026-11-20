# P-001 Kotlin Android foundation — proposal

**Proposed by:** Codex, for Dev's review · **Date:** 2026-11-20 · **Status:** agreed on 2026-11-20 by Dev

**Roadmap scope:** M1.1 only — Create Kotlin Android project.  
**Session:** A — proposal agreed; implementation and task approval remain pending.  
**Planning profile:** `sol-high`, confirmed by the user in this session. The precise runtime model ID is not exposed to the agent.

**Agreement evidence:** After the revised proposal was presented for overall agreement, Dev instructed “lets start with the task creation”. This authorizes moving the agreed M1.1 proposal to task drafting; it does not approve the resulting task or begin implementation.

Dev owns product intent and approval. Accepted choices are recorded in [DD-001](../decisions/DD-001-application-identity.md), [DD-002](../decisions/DD-002-minimum-android-version.md) and [DD-003](../decisions/DD-003-foundation-build-baseline.md). The implementation plan is [T-001](T-001-project-setup.md), pending separate approval.

## 1. Purpose, as I understand it

- **For whom:** the developer and learner; ultimately players of the Classic and MDAS game.
- **Problem:** The repository contains product/design references but no Gradle wrapper, Android application module or native build to develop against.
- **Business value:** Establish a reproducible foundation for a personal game and portfolio project, while making the initial Android/Kotlin/build decisions understandable. No monetization is introduced.
- **Source of this understanding:** The user's request to start M1.1 on 2026-11-20; [tracker](../task-tracker.md#milestone-1--android-project-foundation); [PRD technology decisions](../snake-math-prd.md#12-confirmed-technology-decisions) and [Development Approach](../snake-math-prd.md#development-approach); [Android handoff](../android-prototype-handoff.md#incremental-android-studio-work-when-resumed); [architecture](../architecture.md#4-package-structure-and-construction); [agent workflow](../../AGENTS.md#session-protocol).

## 2. Hypothesis

A small Android application in the existing repository will let us verify build configuration, Kotlin compilation and application startup before adding Compose or gameplay. One `:app` module is sufficient, as already specified by the PRD. Use Gradle Kotlin DSL, a version catalog and a checked-in wrapper so the build does not depend on a separately installed Gradle version.

The accepted baseline has one Kotlin `MainActivity` under `com.acp.snakemath.app`, registered as the launcher. It opens a blank platform window with no custom content. This is a startup check, not a proposed screen design. M1.2 will introduce Compose and adapt the Activity; M1.4 and M1.6 own the application theme and Home. No XML layout or temporary View-based screen is needed.

### Approaches considered

| Approach | Trade-off | Recommendation |
|---|---|---|
| Minimal project with a Kotlin launcher Activity | Verifies compilation and startup with very little source; Activity will be adapted for Compose in M1.2 | Recommended for the requested M1.1 slice |
| Android Studio Compose Empty Activity template | Produces a Compose screen immediately, but overlaps M1.2 and generates sample theme/UI files belonging to later work | Use only if the user explicitly expands this work unit |
| Project with no Activity | Smallest build configuration, but cannot verify launch and may contain no Kotlin application source to compile | Less useful evidence for M1.1 |

The implementation session can author the minimal project files directly, then open/sync the existing repository in Android Studio. It must not generate a second nested project or replace the repository's documentation and working changes.

## 3. Proposed first version (MVP)

| In | Why |
|---|---|
| Root settings/build files, Gradle properties, version catalog and complete wrapper, including distribution checksum | Reproducible builds on macOS and other supported hosts |
| One Kotlin Android application module with explicit namespace/application ID and SDK configuration | Establish the native project and its identity |
| One minimal launcher Activity, manifest and string-resource app label | Compile real Kotlin source and provide a launch target |
| Ignore generated builds, Gradle caches, local SDK settings and IDE caches | Keep machine-specific state out of future commits; preserve existing tracked files |
| Short setup/build instructions and recorded verification evidence | Explain how to use the project and distinguish build evidence from device evidence |

| Out (later) | Why it can wait |
|---|---|
| Compose dependencies/compiler configuration and UI | M1.2 owns Compose setup |
| Full feature/core/data package tree or empty classes | M1.3 and later tasks create packages when needed |
| Custom theme, bundled fonts/art, Home and navigation | M1.4–M1.7 have their own scope and visual acceptance |
| Game/math engines, ViewModels, loops and DataStore | No behavior or persistence is needed to establish the build |
| Phone/tablet classification, orientation enforcement and gameplay grids | Must be implemented and verified with native presentation; this blank startup baseline does not establish device-policy acceptance |
| New icons, loading animation, runtime art, release signing or publication | Neither necessary nor approved for M1.1 |

## 4. Configurable, not fixed

| Setting | Who sets it | Default I propose | Why it should not be fixed |
|---|---|---|---|
| Project name / display label | User approves; build/resources hold it | `SnakeMath` / `Snake & Math` | The title remains tentative; display branding can change independently of identity |
| Application ID and namespace | Dev confirmed on 2026-11-20 | `com.acp.snakemath` for both (confirmed) | Keep identity explicit and independent of the display title |
| Minimum Android version | Dev confirmed on 2026-11-20 | API 23 / Android 6.0 (confirmed) | Supports older devices without going below the documented modern Compose baseline; later dependencies must be checked against it |
| Compile/target SDK | Build configuration, reviewed for compatibility | API 36 initially | Already installed; future platform changes must be deliberate and verified |
| Tool versions | Version catalog and wrapper | Candidate AGP 9.4.0 / Gradle 9.6.0; AGP built-in Kotlin | Pin a verified stable combination; record the actual Kotlin version rather than assuming the Gradle Kotlin DSL version is the app compiler |
| Gradle runtime / JVM bytecode target | Local IDE/shell / project build | Installed Studio JBR 25 / matching Java and Kotlin target 17 | Running Gradle and choosing app bytecode are separate concerns; local JDK/SDK paths must not be committed |

## 5. Decisions I have made

These scoped engineering recommendations are accepted through proposal agreement. Existing Kotlin/Compose and single-module requirements are retained, not reopened. The researched build combination remains subject to actual verification in Session B.

| Decision | Reason | Easy to reverse? |
|---|---|---|
| Keep this work unit at M1.1 | Respect the requested incremental milestone and separate review gates | Yes, but expansion requires explicit agreement |
| Use the existing repository root and one `:app` | PRD structure; no need for extra modules or repository migration | Yes before substantial code exists |
| Use the blank Kotlin launcher approach | Give M1.1 a concrete compilation/startup result without building future screens | Yes; M1.2 adapts the same entry point |
| Keep dependencies minimal and use AGP's built-in Kotlin if AGP 9.x is selected | Avoid redundant Kotlin Android plugin configuration and premature feature integrations | Yes with a verified build change |
| Validate through build/lint and an actual launch when a target is available | No gameplay logic exists to justify artificial unit tests | Build validation is repeatable; device availability is an external condition |

### Research and local evidence — 2026-11-20

- Read-only inspection found Android Studio installed (bundle reports `2026.2`), its JBR reporting Java 25.0.4, Android SDK platforms 35 and 36, and Build Tools 36.0.0 among the installed versions. No wrapper/build files exist in this repository. IDE metadata references `jbr-21`; that reference does not prove a matching installed JDK and must be reconciled during setup.
- [AGP 9.4 compatibility](https://developer.android.com/build/releases/agp-9-4-0-release-notes) lists Gradle 9.6.0, Build Tools 36.0.0 and minimum JDK 17. This is a candidate combination, not a successful local build.
- [Gradle Java compatibility](https://docs.gradle.org/current/userguide/compatibility.html) supports running Gradle on Java 25 starting with Gradle 9.1.0. JVM target 17 remains a separate proposed build setting.
- [Built-in Kotlin guidance](https://developer.android.com/build/migrate-to-built-in-kotlin) states that AGP 9.0+ enables Kotlin support without applying `org.jetbrains.kotlin.android` separately. Context7 was also consulted for this integration.
- [Compose Runtime release notes](https://developer.android.com/jetpack/androidx/releases/compose-runtime) document the minimum SDK move to API 23. This informs the proposed floor; Compose dependencies are selected in M1.2.
- [Android Studio compatibility](https://developer.android.com/studio/releases#android_gradle_plugin_and_android_studio_compatibility) lists supported AGP ranges. Confirm the exact installed Studio release/channel during task preparation rather than relying solely on its abbreviated bundle version.
- Task-preparation inspection found the installed Studio product metadata uses `AndroidStudio2026.2.2` as its data-directory name and build `AI-262.10968.63.2622.16840690`. The official compatibility table supports AGP 9.4 in that Studio release line. Channel was not independently confirmed; verify the displayed release at IDE sync. The official Maven POM could not be read through the web tool, so the exact AGP-supplied Kotlin compiler version remains unverified; record the resolved version during T-001 instead of inventing it.

## 6. Decisions that need business judgement (keep this short)

### D1. Which application identity should this project use?

| Option | What it costs / means |
|---|---|
| Agent's initial proposal: a personal-name prefix | A personal-project identifier; not selected |
| User's selection: `com.acp.snakemath` for both | Use consistently for namespace and installation identity; no claim of domain ownership or reserved store availability |

**User decision — confirmed on 2026-11-20 by Dev:** “we will use com.acp.snakemath”. Use `com.acp.snakemath` as both application ID and namespace. No additional rationale was supplied. Keep the display title independently editable. Android's [application ID guidance](https://developer.android.com/build/configure-app-module#set-application-id) explains why changing the ID after publication creates a different app.

### D2. Which Android versions should the initial project support?

| Option | What it costs / means |
|---|---|
| API 23 / Android 6.0 and newer | Broad baseline aligned with documented Compose minimum; older-device behavior still requires later testing |
| API 26 / Android 8.0 and newer | Narrows the device range; no M1.1 requirement currently needs the higher floor |

**My recommendation:** API 23. This is a supported-device proposal, not a claim that every eventual feature already works on Android 6.0.

**User decision — confirmed on 2026-11-20 by Dev:** “agree”, in response to the API 23 / Android 6.0+ recommendation. Set `minSdk = 23`. No additional rationale was supplied.

D1 and D2 are resolved and the revised proposal is agreed. Decision records and T-001 refine this agreed scope. Task approval remains a separate gate; implementation starts in a new Session B after task approval.

## 7. How we will know it works

1. Android Studio opens and syncs the project at the existing repository root with exactly one application module; record actual IDE sync evidence separately from command-line checks.
2. The wrapper runs using the recorded local JDK, compiles the Kotlin launcher and produces a debug APK through `./gradlew :app:assembleDebug :app:testDebugUnitTest :app:lintDebug` without build/lint errors. If the test task has no tests, record `NO-SOURCE` or the actual outcome; do not describe it as behavioral coverage.
3. Confirm the packaged application ID, minimum SDK and launcher registration match the accepted values.
4. When an emulator/device is available, install and launch the debug APK and record whether the blank Activity remains open without a crash. If unavailable, record the exact missing target/check and retain the launch acceptance as unverified; do not claim complete startup validation.
5. Review the resulting diff for generated/local files, copied assets, future milestone code and changes to existing user work. Link the implementation task and worklog from the tracker; leave M1.2–M1.7 unchecked.

These are proposed acceptance outcomes for later task drafting. No native build, automated test, IDE sync or launch has been performed in Session A.

## 8. Risks and what I do not know yet

- **Supported-device behavior remains unverified** — the API 23 floor is confirmed in D2; verify dependency compatibility during setup and older-device behavior in relevant later tasks. Application identity is confirmed in D1.
- **Toolchain compatibility is researched but untested locally** — Studio metadata identifies the 2026.2.2 release line; its displayed release/channel and the AGP-supplied Kotlin compiler version remain verification items in T-001. Session B verifies the pinned combination by building. Use compatible stable patch updates within the agreed line when necessary and record the actual versions.
- **First build may need wrapper/plugin downloads and local SDK/JDK configuration** — report exact access or installation blockers; keep local paths outside version control. No dependencies are installed during Session A.
- **A blank platform window could be mistaken for approved UI** — explicitly retain it as startup evidence only; the approved Home art and future Compose styling remain governed by their existing references.
- **Emulator/device availability is unknown** — check before the launch verification; distinguish an APK build from a native run.
- **The working tree already has modified and untracked documentation/IDE files** — preserve those changes, limit Session A changes to proposal/task/decision records and the tracker, and do not commit without authorization.
- **Broader architecture remains proposed** — M1.1 accepts only the entry point/module/build boundaries needed here; it does not approve future engine, navigation or lifecycle details.
