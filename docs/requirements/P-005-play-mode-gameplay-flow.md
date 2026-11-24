# P-005 PLAY, mode selection and gameplay entry — proposal

**Proposed by:** Codex, following Dev's M1.7 request · **Date:** 2026-11-24 · **Status:** agreed on 2026-11-24 by Dev

**Roadmap scope:** M1.7 — Add the PLAY → mode selection → gameplay flow.

**Authorization:** Dev agreed the overall proposal on 2026-11-24: “overall proposal is good to go, proceed with the task creation”. The documented A/A recommendations and interaction contract below are agreed, preserving T-004's no-scroll Home and tighter title. [T-005](T-005-play-mode-gameplay-flow.md) was separately approved by Dev on 2026-11-24 with `sol-medium` selected for execution. Implementation belongs in a later Session B. The T-004 planning waiver in DD-006 does not apply.

**Accepted decisions:** [DD-009 — M1.7 boundary](../decisions/DD-009-m17-navigation-foundation.md), [DD-010 — MDAS entry difficulty](../decisions/DD-010-mdas-entry-difficulty.md), [DD-011 — shell process restoration](../decisions/DD-011-shell-process-restoration.md).

**Planning profile and tools:** Project-requested `sol-high`; this session identifies as Codex based on GPT-6, with exact runtime model ID and Sol/profile mapping unavailable. No confirmed mapping is asserted. Local Superpowers **7.0.1** brainstorming and writing-plans instructions were readable at `C:/Users/ThinkPad/.codex/plugins/cache/openai-curated-remote/superpowers/7.0.1/skills/`; upstream revision unknown. Brainstorming prepared the proposal; writing-plans is used after Dev's agreement to draft T-005. One known Session A conversation on 2026-11-24; whole-unit totals remain unknown.

## 1. Purpose, as I understand it

- **For whom:** Players choosing Classic Apple or MDAS Learning, including children practicing arithmetic, and Dev building the Android app incrementally.
- **Problem:** Home's PLAY currently opens a temporary Modes message. There are no selectable modes or gameplay destinations. M1.7 names the full entry flow, while playable engines, rendering and integration are scheduled in M2–M6; its delivery boundary needs agreement.
- **Business value:** Make mode choice and navigation reviewable on Android before gameplay integration, avoiding a premature implementation of later milestones. No monetization or service is proposed.
- **Source of this understanding:** Dev's 2026-11-24 request; [AGENTS.md](../../AGENTS.md), [tracker](../task-tracker.md), [PRD Development Approach](../snake-math-prd.md#development-approach), [PRD difficulty profiles](../snake-math-prd.md#22-mathematical-question-generation), [handoff](../android-prototype-handoff.md), [architecture §5](../architecture.md#5-navigation-and-screen-ownership), [T-004](T-004-theme-navigation-home.md), its [worklog](../worklog/T-004-2026-11-22.md) and [correction handoff](../verification/T-004/review-fixes.md).

## 2. Hypothesis

**Agreed foundation scope:** Deliver M1.7 as a navigation foundation: Home → an actual mode-selection screen → a clearly identified, non-playable destination for the selected mode. These destinations establish the entry contract that later milestones replace with real gameplay. They contain no simulated board, snake, score, lives, expression, movement or pause/results behavior. This defines M1.7's agreed boundary without claiming the flow already exists or approving later milestones.

Keep the existing Activity, Navigation Compose host, typed routes, tokens and bundled fonts/artwork. Add mode and optional MDAS difficulty as small immutable configuration values; never pass mutable game state through navigation. Screens receive values and callbacks; navigation boundaries own route changes and temporary selection. No gameplay ViewModel, repository, coroutine loop or engine factory is needed for this selection-only slice. The task should choose the smallest owner needed to distinguish Activity recreation from a new process; later gameplay ownership remains governed by its approved tasks.

**Agreed interaction contract:**

- PLAY opens Modes. Classic Apple and MDAS Learning each have their own labeled best score and explicit action. Classic opens its destination directly and carries no arithmetic difficulty.
- The MDAS card includes Easy, Medium and Hard choices, with one visibly and accessibly selected; **Easy is the initial choice**. Show the PRD's suggested ages and short skill descriptions: Easy 6–7, addition/subtraction; Medium 8–9, addition/subtraction, multiplication and exact division; Hard 10–12, mixed operations and precedence. Ages are guidance, with no age entry or restriction.
- Changing difficulty only updates selection. PLAY MDAS opens the MDAS destination with the selected level visibly confirmed. The future round/retry contract retains that level; no round or retry is implemented here.
- Retain the current screen/configuration through ordinary Activity recreation, and retain difficulty when returning from either destination to the same Modes entry. Leaving Modes for Home discards that selection; the next visit starts at Easy. Do not persist a last-used preference. **A new process, including relaunch of an Android task saved on Modes or a placeholder destination, starts at Home and discards the prior selection.** [DD-011](../decisions/DD-011-shell-process-restoration.md) accepts the architecture's fresh-process policy for this foundation; it does not implement live-round lifecycle behavior or restore any active round.
- Each destination shows its mode, MDAS level when applicable, and the honest message **“Gameplay is not available yet.”** Provide **“Back to modes”** and **“Home”** actions. System Back returns to Modes; Modes Back returns Home. Home removes the intermediate entries so Back cannot reopen a departed destination. Settings retains T-004's existing temporary behavior.
- Preserve the originating-entry guards added for ISS-003. Repeated or stale actions cannot duplicate destinations, remove Home, replace a first accepted mode with a stale choice, or pop a newer screen. Reject invalid mode/difficulty combinations by returning to Modes rather than silently inventing a level.

**Agreed visual direction for Modes:** Use [phone Modes](../design-system/previews/phone-modes.png) as the primary reference and [tablet Modes](../design-system/previews/tablet-modes.png) as the landscape counterpart, with these written adaptations: unavailable scores instead of sample numbers, corrected Classic instructions, and an inline MDAS difficulty group. Agreement covers this Modes direction only; the rest of the gallery retains its review status and native fit still needs validation. Use existing tokens/fonts/background and simple native icon drawing or text glyphs; no new raster artwork or copied screen PNGs. Exact native composition is left to the task within this contract.

Phone cards stack; landscape tablet cards sit side by side when readable. Modes may vertically scroll at small bounds/enlarged text, keeping every choice/action reachable and at least 48dp; this permission applies only to the new Modes screen. It does not alter Home's no-scroll requirement. Placeholder destinations use a simple themed panel, with no gameplay mockup treated as an implemented board.

## 3. Proposed first version (MVP)

| In | Why |
|---|---|
| Replace the temporary Modes message with Classic/MDAS cards and actions | Supplies the missing mode-choice step |
| Inline MDAS Easy/Medium/Hard selection, agreed in DD-010 | Makes the future entry configuration explicit without inventing an age gate |
| Mode-specific non-playable destinations and configuration validation, agreed in DD-009 | Establishes routes without implementing M2–M6 |
| Back/Home behavior, repeated/stale callback protection and recreation checks | Extends T-004's verified navigation contract |
| Correct instructions: “Wrap around the edges. Avoid your own body.” for Classic | Historical wall-warning copy conflicts with confirmed native rules |
| Independently labeled, supplied nullable best scores; production displays an em dash with an unavailable description | Persistence does not exist; neither sample numbers nor fabricated stored zero is truthful |
| Phone/tablet layouts, safe insets, font scaling, accessibility labels and native evidence | A browser export cannot validate Android behavior |
| Regression verification for unchanged Home/title/Settings | Protects the user-approved T-004 corrections |

| Out (later) | Why it can wait |
|---|---|
| Engines, grid, snake/answer rendering, swipes and real game sessions | M2–M6 own playable behavior |
| Pause, resume, results, restart, retry and game-loop lifecycle | Requires a real session; destination Back here is not approval of in-game Back behavior |
| Generator numeric bounds, expressions, distractors and difficulty enforcement | Must be agreed before the later generator/profile implementation; selecting a named level here generates no question |
| DataStore, score writes, Home/Modes repositories and remembered difficulty preferences | Later approved persistence work supplies real values |
| Home restyling, scrolling, title-spacing changes or fixing unrelated T-004 acceptance gaps | Existing approved behavior and outstanding checks carry forward |
| Settings functionality, audio, loading motion, themes/skins and other gallery screens | Outside M1.7; their review statuses remain unchanged |
| Dependency/toolchain upgrades and new runtime raster art | Existing pinned stack and assets support the proposed slice |

## 4. Configurable, not fixed

| Setting | Who sets it | Default I propose | Why it should not be fixed |
|---|---|---|---|
| Mode | Player on Modes | No mode auto-started | Explicitly select Classic or MDAS |
| MDAS difficulty | Player, with PRD skill guidance | Easy on each fresh Modes visit; retained within that visit | Learning needs vary; no automatic progression or age gate |
| Best-score values | Future data owner; tests/previews supply values now | Both unavailable in production | Independent mode values must later bind without changing presentation |
| Window bounds, insets and font scale | Android/user | Current configuration | Modes reflows or scrolls while Home retains its bounded layout |
| Device policy | Existing Activity adapter | Existing phone portrait / tablet landscape request | Reuse T-004 classification and policy; do not introduce a new board profile |

No user-facing settings beyond mode/difficulty are added. Generator number ranges are an explicit future agreement, not a new preference or hidden default.

## 5. Decisions I have made

Inherited requirements and engineering recommendations below form the agreed proposal. Approval comes from Dev's explicit agreement above, not file creation; routine implementation choices remain subordinate to the separate task review.

| Decision | Reason | Easy to reverse? |
|---|---|---|
| **Preserve:** bounded no-scroll Home, 16dp gutters, minimum 48dp controls, responsive mascot, dual labeled best scores and current component styling | Dev's current instruction and accepted [DD-008](../decisions/DD-008-home-fits-without-scrolling.md) supersede P-004's historical scroll fallback | Only through a new user decision |
| **Preserve:** tighter two-line Home title with `0.8.em` line height, matching outline/fill metrics, trimmed font padding and one accessible app-name label | Explicit T-004 user correction, visible in [HomeLogo](../../app/src/main/java/com/acp/snakemath/core/ui/components/HomeLogo.kt) and covered by its regression | Only through a new user decision |
| **Preserve:** Navigation Compose/testing 2.9.7, Kotlin/serialization and SDK baseline | [DD-007](../decisions/DD-007-home-foundation-integration.md); no upgrade is needed to extend existing typed routes | Moderate; upgrades require separate justification |
| **Recommend:** one game-entry route parameterized by mode and MDAS level | Fits architecture §5 and avoids separate future game owners per mode; no live state argument | Yes, before gameplay integration |
| **Recommend:** temporary selection owned at the navigation boundary, stateless screen content | No engine, asynchronous data or business state warrants a ViewModel/Flow layer yet | Yes, add an owner when concrete behavior needs it |
| **Recommend:** show unavailability explicitly and retain separate mode-score bindings | Avoids presenting a placeholder as working gameplay or sample scores as saved progress | Yes, later approved integrations replace the content |

The inspected build uses one `:app` module, `com.acp.snakemath`, minSdk 23, compile/target SDK 36, AGP 9.4.0, Kotlin 2.2.10, Compose BOM 2025.11.01 and Navigation 2.9.7. These are repository values, not a recommendation to copy newer documentation examples. Context7 resolved official Android Jetpack docs (`/websites/developer_android_jetpack`) on 2026-11-24; the [Navigation release documentation](https://developer.android.com/jetpack/androidx/releases/navigation) supports serializable typed destinations with `composable<T>`/`toRoute<T>`. Retaining the current route approach is an engineering inference from those docs and the existing source, not a dependency compatibility test run in this session.

## 6. Decisions that need business judgement (keep this short)

### D1. What counts as M1.7 before gameplay engines exist?

| Option | What it costs / means |
|---|---|
| A — Mode selection plus honest non-playable destinations now | Independently reviewable navigation work; no playable round until later milestones. Accepting this explicitly defines M1.7 as the foundation flow |
| B — Prepare mode-selection design, defer M1.7 delivery until engines/rendering are ready | Avoids shipping temporary destinations but leaves M1.7 open across later milestones; immediate work remains design-only, with native delivery/acceptance deferred |

**Recommendation accepted with Dev's overall agreement, 2026-11-24:** A; [DD-009](../decisions/DD-009-m17-navigation-foundation.md). The proposal's rationale is to follow the foundation-first sequence without pulling M2–M6 into one task. No separate user rationale is inferred.

### D2. How should MDAS difficulty fit the mode-selection screen?

| Option | What it costs / means |
|---|---|
| A — Inline Easy/Medium/Hard group in the MDAS card, initially Easy | Keeps the prepared two-mode flow, makes the selected level explicit and needs responsive space for three 48dp choices |
| B — MDAS opens a separate difficulty screen before its destination | Gives the skill descriptions more room but adds another destination, Back behavior and an extra step |
| C — Defer the selector until MDAS integration | Smaller M1.7 slice; the MDAS destination must state difficulty is not configured and cannot create a future round unchanged |

**Recommendation accepted with Dev's overall agreement, 2026-11-24:** A, including the retention/reset rules in §2; [DD-010](../decisions/DD-010-mdas-entry-difficulty.md), [DD-011](../decisions/DD-011-shell-process-restoration.md). This agrees only the Modes adaptation; it does not approve all seven gallery screens or numerical generation bounds.

Sections 2–5 and 7 describe the agreed A/A combination. The alternatives remain recorded as review history, not unresolved choices. A later material change needs a revised proposal and a superseding decision where applicable.

## 7. How we will know it works

These are agreed acceptance outcomes planned in separately approved T-005 and to be verified in the later Session B; they are not checks performed in Session A.

1. Home → PLAY → Modes → Classic reaches a clearly non-playable Classic destination with no difficulty. Each of Easy/Medium/Hard reaches the MDAS destination showing exactly the chosen level. Selecting a level alone does not navigate. Invalid configurations return to Modes.
2. Explicit and system Back from either destination return to the same Modes entry; the current screen/configuration survives ordinary Activity recreation. Modes Back and destination Home return to the original Home. A subsequent PLAY visit starts with Easy. Separately relaunch a saved task after process death from Modes and from each placeholder: each starts at Home, with no previous selection restored on the next PLAY visit.
3. Repeated starts/returns and stale callbacks from Home, Modes and either destination leave one intended entry, preserve the first accepted action and never remove Home or affect a newer screen. Existing Settings behavior remains covered.
4. Home retains all existing no-scroll fit, unclipped containment, text-overflow, negative-fixture, title-spacing and component-rendering regressions. Preserve the `0.8.em` title and current approved styling; compare against the latest [native T-004 Home](../verification/T-004/revisions-home-api34.png), rather than copying older reference spacing.
5. Modes follows the phone/tablet references with the documented changes. Classic copy uses wrapping, MDAS copy promises five lives and question/answer play without body penalties, every action/choice is at least 48dp, difficulty has an accessible selected state, and each unavailable best has the right mode association. Tests inject distinct Classic 120 / MDAS 340 values to detect crossed bindings.
6. Check 320 × 568dp and 390 × 844dp portrait phone plus 1024 × 768dp landscape tablet layouts at font scales 1.0 and 1.5 with safe insets. Every Modes choice/action is reachable without clipped or truncated text; scrolling there is allowed. Verify actual orientation across the extended navigation flow separately from bounded layout tests. Record actual API/device/font/bounds in native captures.
7. The later task records `./gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug`, relevant `:app:connectedDebugAndroidTest`, native screenshots and Studio sync/previews individually. JVM `NO-SOURCE` is not passing behavioral coverage. Device/Studio gaps remain open; inherited T-004 API 23/API 36, complete rotation and visual/Studio acceptance are not closed by this proposal or by API 34-only results.

## 8. Risks and what I do not know yet

- **M1.7 wording can imply playable delivery.** DD-009 resolves the scope boundary; task/tracker completion must explicitly say navigation foundation, leaving M2–M6 unchecked.
- **Difficulty controls are absent from historical exports.** DD-010 accepts the written inline adaptation; native fit still needs verification. Numeric operand/intermediate/final/distractor limits remain a prerequisite for later generator implementation, not inferred from age labels or examples.
- **Modes is denser with difficulty controls.** Verify readable reflow/scrolling on phones and enlarged-text tablets; preserve Home's distinct no-scroll contract. Approving this proposal does not establish native visual fit.
- **Restoration and rapid navigation can expose stale configuration/actions.** Retain entry guards, verify each selected level after recreation/Back and reject invalid configurations. Distinguish same-process Activity recreation from process-death task restoration rather than blindly restoring a prior destination. Future live-round background/Back/process-death behavior needs its own approved gameplay work.
- **T-004 validation is incomplete.** Carry its [worklog gaps](../worklog/T-004-2026-11-22.md#unresolved), [ISS-001](../issues/ISS-001-sandbox-gradle-access.md) and [ISS-002](../issues/ISS-002-emulator-startup-instability.md). [ISS-003](../issues/ISS-003-repeated-back-removes-home.md), [ISS-004](../issues/ISS-004-home-fit-checks-use-clipped-bounds.md) and [ISS-005](../issues/ISS-005-home-reference-details-incomplete.md) are fixed/merged historical regression context, not new blockers. Their current headers and the tracker/worklog carry the merge state; dated verification narratives preserve the earlier review/publication stages.
- **Implementation remains in Session B.** Dev agreed P-005 and separately approved T-005 on 2026-11-24, selecting `sol-medium` instead of the earlier `sol-high` recommendation. DD-009–DD-011 and the task retain the agreed scope/verification. Start implementation only in a new Session B; DD-006's waiver does not carry forward.

**Session A review:** The brainstorming skill's required read-only builder check used a fresh context requested as `gpt-6.2-sol` with high reasoning; actual runtime identity independently unavailable. It found the new-process restoration ambiguity, resolved in the reviewed contract, and requested explicit revision if an alternative to A/A were chosen. It reported no other important planning gaps. That check preceded Dev's later proposal agreement recorded above and is not native verification.
