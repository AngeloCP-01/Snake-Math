# P-002 Jetpack Compose configuration — proposal

**Proposed by:** Codex, for Dev's review · **Date:** 2026-11-21 · **Status:** agreed on 2026-11-21 by Dev

**Roadmap scope:** M1.2 only — Configure Jetpack Compose.  
**Session:** A — proposal and T-002 approved; ready for a new implementation Session B.

**Planning profile:** Requested `sol-high`. This session identifies as Codex based on GPT-6; its exact runtime model ID/profile mapping is unavailable, so a Sol mapping is not asserted.

Dev owns product intent and approval. Dev confirmed the visible setup marker in D1 and then replied “approve” to the explicit request for overall P-002 agreement on 2026-11-21. Dev separately replied “task approved” to T-002's review request on the same date. Accepted choices are recorded in [DD-004](../decisions/DD-004-compose-configuration.md). [T-002](T-002-compose-configuration.md) is approved and ready for implementation in a new Session B.

## 1. Purpose, as I understand it

- **For whom:** Dev, learning and developing the native Snake & Math application; ultimately its Classic and MDAS players.
- **Problem:** M1.1 builds and launches a blank platform Activity. It has no Compose compiler configuration, UI dependencies or composition to support the planned native screens.
- **Business value:** Establish and verify the UI toolkit in a small, understandable increment before investing in the application theme and screens. No monetization or new player capability is introduced.
- **Source of this understanding:** Dev's request to start the M1.2 proposal on 2026-11-21; [tracker](../task-tracker.md#milestone-1--android-project-foundation); [PRD Development Approach](../snake-math-prd.md#development-approach); [handoff](../android-prototype-handoff.md#incremental-android-studio-work-when-resumed); [architecture](../architecture.md#4-package-structure-and-construction); [T-001 result/handoff](../worklog/T-001-2026-11-21.md#for-the-next-developer-or-ai-session).

## 2. Hypothesis

Configure Compose inside the existing single `:app` module, adapt `MainActivity` to `ComponentActivity`, and host a small stateless root composition through `setContent`. A visible setup marker proves more than an empty composition: it lets us check that the Activity actually renders Compose content on Android.

The proposed marker is plain text, `Snake & Math` followed by `Compose ready`, on a neutral background inside system-bar insets. Use string resources and Foundation text/layout primitives with explicit readable defaults. It has no buttons, scores, mascot, game board, artificial loading delay or stateful interaction. It is temporary verification content that later approved UI work replaces. It establishes no Home styling or application-theme approval.

Illustrative content, not a screen-design reference:

```text
Snake & Math
Compose ready
```

### Approaches considered

| Approach | Trade-off | Recommendation |
|---|---|---|
| Configure compiler/dependencies and retain a blank launcher | Smallest source change; compilation cannot show that Compose content is hosted and visible | Feasible, but weaker runtime evidence |
| Configure Compose and show the minimal setup marker | Exercises compilation, Activity hosting and actual rendering with a small replaceable composition | Recommended for M1.2 |
| Generate a full Compose template with Material theme and sample screen | Convenient starting sample; introduces styling and screen structure owned by M1.3–M1.6 | Defer those capabilities to their own approved scope |

## 3. Proposed first version (MVP)

| In | Why |
|---|---|
| Compose compiler Gradle plugin and `buildFeatures.compose`, declared through the existing build/catalog conventions | Enable compilation and IDE support in the existing project |
| A stable Compose BOM and only UI/Foundation, Activity hosting and preview/tooling dependencies needed here | Keep Compose libraries aligned and distinguish library versions from the compiler |
| Adapt the existing launcher and add `app/SnakeMathApp.kt` as a small stateless root composition | Provide the intended application entry point without creating the future package tree |
| Minimal marker, string resources, system-inset handling and one repository-independent preview | Verify visible composition and preview integration without adopting sample product UI |
| One instrumented smoke test launching the real Activity and asserting the marker is displayed | Verify the Activity-to-composition integration rather than merely rendering a test-owned composable |
| Setup notes and actual build, IDE, preview and device/test evidence | Preserve the distinction between successful compilation and a native run |

| Out (later) | Why it can wait |
|---|---|
| Full package structure and empty feature/core/data classes | M1.3 owns structure; create only the entry-point files needed now |
| Application theme, custom tokens, fonts, artwork and reusable styled components | M1.4 owns theme implementation and visual acceptance |
| Home, mode selection, navigation, difficulty UI and gameplay | M1.5–M1.7 and later milestones |
| ViewModels, StateFlow collection, DataStore, engines, coroutine loops and dependency injection | No stateful feature exists in this slice |
| Phone/tablet classification and enforced orientation | Remain native acceptance requirements for later presentation scope; this setup check does not validate them |
| Benchmarking, release publication, new artwork or loading animation | Unnecessary for configuring the toolkit |

## 4. Configurable, not fixed

| Setting | Who sets it | Default I propose | Why it should not be fixed |
|---|---|---|---|
| Compose compiler plugin version | Build/catalog, checked against resolved app compiler | Start with `2.2.10`, matching T-001's resolved compiler | It follows Kotlin; Gradle's embedded Kotlin is a different version |
| Compose library versions | Version catalog and stable BOM | Select and pin a compatible stable BOM during task preparation | BOM alignment does not itself prove compiler or minimum-SDK compatibility |
| Activity and Android test dependencies | Version catalog | Pin compatible stable versions independently of the BOM | The Compose BOM does not manage every AndroidX library |
| Setup-marker copy | String resources | App name plus `Compose ready` | Verification content is replaceable when real UI arrives |
| Existing application/build identity | Existing accepted decisions | Preserve `com.acp.snakemath`, API 23 minimum, SDK 36 and JVM target 17 | These are inherited constraints, not choices reopened by this proposal |

No player-facing setting is added by M1.2.

## 5. Decisions I have made

These scoped engineering recommendations are accepted through Dev's overall proposal agreement. Dependency compatibility still requires verification.

| Decision | Reason | Easy to reverse? |
|---|---|---|
| Extend the current project and retain AGP's built-in Kotlin | Preserve the verified M1.1 baseline and avoid a redundant Kotlin Android plugin | Yes, through a reviewed build change |
| Use the Kotlin Compose compiler plugin and a stable Compose BOM | Separate compiler compatibility from UI-library alignment | Yes, with compatibility checks |
| Use Foundation primitives for the marker; defer Material dependencies until needed | The marker needs text/layout only; the planned product uses its custom design tokens | Yes; this does not prohibit later Material components |
| Keep root content stateless and previewable | No repository, ViewModel or live loop is needed | Yes |
| Add one Activity integration smoke test; no artificial engine/unit tests | The new integration boundary is the behavior that needs verification | Yes |

### Research and local evidence — 2026-11-21

- Local inspection found AGP `9.4.0`, one `:app`, API 23 minimum, compile/target SDK 36, JVM target 17, and `MainActivity : android.app.Activity` with no content. The catalog currently contains only AGP. No Compose dependencies or compiler plugin are present.
- [T-001's worklog](../worklog/T-001-2026-11-21.md) records app Kotlin compiler/stdlib `2.2.10`; Gradle's embedded Kotlin `2.3.21` is separate. That is prior recorded verification, not a compiler resolution rerun in this planning session.
- [Android's compiler/dependency setup](https://developer.android.com/develop/ui/compose/setup-compose-dependencies-and-compiler) documents the Kotlin Compose plugin, Compose build feature, Foundation option, previews, tooling and UI-test dependencies. Its current sample uses newer Kotlin and Compose versions; copying them does not establish compatibility with this project's resolved Kotlin.
- [Android's BOM guidance](https://developer.android.com/develop/ui/compose/bom) distinguishes the Kotlin-matched compiler from BOM-managed libraries. The BOM also requires explicit library declarations. Exact stable BOM, Activity and test versions remain task-preparation research items; no dependency combination has been resolved or built here.
- [Built-in Kotlin guidance](https://developer.android.com/build/migrate-to-built-in-kotlin) supports retaining the current AGP approach without `org.jetbrains.kotlin.android`. Check the selected dependency combination before prescribing a Kotlin upgrade; do not silently change the accepted baseline or API floor.
- Used the [published Superpowers brainstorming skill](https://raw.githubusercontent.com/obra/superpowers/main/skills/brainstorming/SKILL.md), accessed read-only because no local installation was found. Repository proposal format and approval/session gates take precedence. Context7 tools were not available; official Android documentation supplied integration guidance.

## 6. Decisions that need business judgement (keep this short)

### D1. What should appear when M1.2 launches?

| Option | What it costs / means |
|---|---|
| A — Minimal setup marker | Visible proof of Compose hosting; temporary plain content to replace in later UI work |
| B — Blank launcher | No temporary visible content, but less direct evidence of the UI pipeline |

**My recommendation:** A. The small marker gives Dev an observable checkpoint while keeping the approved Home and theme work in their own milestones. It has no gameplay interaction or implication of visual approval.

**User decision — confirmed on 2026-11-21 by Dev:** “yes agree with visible setup marker”. Select A: show the minimal visible setup marker. No additional rationale was supplied.

**Review status:** D1 resolved and overall proposal agreed on 2026-11-21 by Dev. Dev's subsequent “approve” answered the explicit overall-agreement question covering Compose configuration, preview support and the Activity smoke test. T-002 was then separately approved on 2026-11-21 by Dev; implementation remains for a new Session B.

## 7. How we will know it works

1. The existing project syncs in Android Studio with Compose enabled, with no duplicate Kotlin Android plugin and no change to application identity, SDK floor or JVM target.
2. `./gradlew :app:assembleDebug :app:testDebugUnitTest :app:lintDebug` succeeds without build/lint errors. Report actual warnings and `NO-SOURCE` outcomes; no pure unit behavior is introduced here.
3. Dependency/compiler inspection records the actual Kotlin compiler, Compose plugin, BOM-resolved libraries, Activity and test versions. They form a compatible stable combination that retains API 23 support; metadata bypass flags are not an acceptable compatibility fix.
4. Studio renders the marker preview without a device, repository or application state. Record preview evidence separately from IDE sync and the APK build.
5. Install and launch on an available stable emulator/device: observe readable marker text outside system bars and no app crash. Run `./gradlew :app:connectedDebugAndroidTest` for the Activity integration smoke test. If the target is unavailable or blocked, retain the affected acceptance as unverified and document the exact limitation.
6. Review the diff and resulting dependencies for scope: no product theme, assets, future feature classes, navigation or persistence. Update the tracker and implementation worklog with evidence; only M1.2 becomes eligible to be checked complete.

This Session A performs document/source research only. No Compose build, preview, native run or instrumented test has been performed.

## 8. Risks and what I do not know yet

- **Exact dependency combination is unsettled** — research stable candidates during task drafting, including Kotlin metadata and API requirements. A conflict requiring a substantive baseline change returns to proposal/decision review before implementation.
- **Model-profile mapping is unavailable** — requested planning profile is recorded without claiming an unverified Sol model. Confirm the supported execution profile for the later implementation handoff.
- **Gradle sandbox access can fail before app compilation** — carry [ISS-001](../issues/ISS-001-sandbox-gradle-access.md); use its verified workaround and the environment's approval mechanism if required. No downloads/builds occur in Session A.
- **Emulator system services previously stalled** — carry [ISS-002](../issues/ISS-002-emulator-startup-instability.md). Verify target readiness and distinguish device instability from an app/test failure.
- **Temporary content could be mistaken for approved design** — keep it explicitly a setup marker, with no new theme or runtime art. [Design-system references](../design-system/README.md) retain their current authority and review status.
- **Working tree contains uncommitted M1.1 and IDE changes** — preserve them. This proposal changes only its own document and tracker; no commit or push is authorized.
- **Older narrative documents still describe a pre-native repository** — use current source, tracker and T-001 evidence for actual progress. Broader architecture proposals are not approved by configuring Compose.
