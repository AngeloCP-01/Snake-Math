# P-004 Theme, navigation and basic Home — proposal

**Proposed by:** Codex, following Dev's combined-scope request · **Date:** 2026-11-22 · **Status:** draft — planning review gates waived for this work unit

**Roadmap scope:** M1.4 Create application theme; M1.5 Implement navigation; M1.6 Create basic home screen.

**Authorization:** Dev requested one proposal and one task for M1.4–M1.6 and said approval is no longer needed for planning them. [DD-006](../decisions/DD-006-combined-foundation-planning.md) records this scoped workflow exception. This authorizes drafting both records without intermediate approval questions; it does not assert approval of unseen implementation details or authorize coding in Session A. [T-004](T-004-theme-navigation-home.md) is the implementation handoff.

**Planning profile:** Requested `sol-high` under project routing. This agent identifies as Codex based on GPT-6; exact runtime model ID and Sol/profile mapping are unavailable. No confirmed mapping is asserted. One known contributing planning conversation on 2026-11-22; whole-unit accounting remains unknown until execution history is established.

## 1. Purpose, as I understand it

**Implementation correction, Dev 2026-11-22:** After emulator inspection, Dev requested Home fit all components on screen without scrolling while remaining responsive. [DD-008](../decisions/DD-008-home-fits-without-scrolling.md) supersedes this proposal's Home scrolling fallback. This is an authorized correction within active T-004, not a new work unit.

- **For whom:** Players opening Snake & Math, and Dev building the Android foundation incrementally.
- **Problem:** T-002 still displays a white Compose setup marker. Home styling already exists, but there is no native design system or navigation host. Planning these as three unrelated work units would repeat the same visual and integration context.
- **Business value:** Deliver one coherent, reviewable native entry point and reusable styling without advancing game engines or recreating the design discovery.
- **Source of this understanding:** Dev's 2026-11-22 request; [tracker](../task-tracker.md), [PRD Development Approach](../snake-math-prd.md#development-approach), [handoff](../android-prototype-handoff.md), [architecture](../architecture.md), [design system](../design-system/README.md), [tokens](../design-system/tokens.json), [asset allowlist](../assets/README.md), completed T-001–T-003 and accepted DD-001–DD-005.

## 2. Hypothesis

One work unit can replace the setup marker with a styled, responsive native Home backed by a small Navigation Compose graph. Theme → navigation → Home is an implementation order inside the task, with independently recorded verification checkpoints. No additional visual exploration is needed for the approved phone Home styling.

Home renders supplied immutable values and callbacks. Initially both best scores are unavailable and display an em dash, with accessible unavailable descriptions; neither the illustrative 1280 nor a fabricated persisted zero is used. Preferences integration and its ViewModel belong to later approved persistence work. Production Home uses no demo scores; previews/tests can inject distinct values to prove the labels are bound correctly.

For this foundation, PLAY opens a temporary Modes destination and Settings opens a temporary Settings destination. Each shows its title, a short unavailable-feature message and Back to Home. No mode choices, difficulty controls or settings switches are implemented. M1.7 retains the complete PLAY → mode selection → gameplay flow. THEMES is omitted because it is deferred in the release requirements despite appearing in the historical image.

## 3. Proposed first version (MVP)

| In | Why |
|---|---|
| Custom color, typography and dimension tokens with CompositionLocals | Reuse the supplied visual system without forcing it into Material roles |
| Bundled Baloo 2 ExtraBold 800 and Nunito Bold 700, with source/license records | Correct offline typography, including first launch on API 23 |
| Only the Home components actually used: background, logo, primary/secondary actions and two-score panel | Avoid creating the entire component catalog prematurely |
| Approved mascot and forest PNGs, copied unchanged | Reuse allowlisted artwork; preserve alpha and proportions |
| One Activity, one Navigation Compose host, Home/Modes/Settings destinations | Establish routing and Back behavior that can be extended in M1.7 |
| Native Home: title, subtitle, mascot, PLAY, labeled Classic/MDAS bests and Settings | Replace the setup marker with the basic product entry point |
| Phone portrait/tablet landscape requests and responsive Home composition | Apply the existing device policy to the screens introduced here |
| Native UI/navigation checks, screenshots and honest verification records | Browser mockups are references, not evidence that Android works |

| Out (later) | Why it can wait |
|---|---|
| M1.7 mode cards, MDAS difficulty UI and gameplay destinations | Separate roadmap item; difficulty layout/bounds remain unresolved |
| Engines, board, animated snake, pause/results, game lifecycle loop | Later milestones, unrelated to rendering basic Home |
| DataStore, repositories, score writes and operational settings/audio | No gameplay or persisted score source is introduced here |
| Loading motion, THEMES, new runtime raster art and full seven-screen implementation | Existing deferrals/review statuses remain in effect |

## 4. Configurable, not fixed

| Setting | Who sets it | Default I propose | Why it should not be fixed |
|---|---|---|---|
| Best-score values | Future route owner; tests/previews inject them now | Unavailable in the native shell | Keep presentation independent of persistence; allow separate Classic/MDAS values |
| Window bounds, safe insets and font scale | Android/user | Current configuration | Fit content without clipping; scroll when enlarged text needs more height |
| Phone/tablet classification | Activity adapter | `smallestScreenWidthDp >= 600` means tablet | Agent implementation recommendation from the architecture; do not classify from current width alone |
| Theme tokens | Developer, from supplied tokens | Fixed approved forest palette | Centralize tuning; no user theme picker or system dynamic palette |

## 5. Decisions I have made

These are agent recommendations for the implementation handoff, except the combined planning scope explicitly requested by Dev. They are not newly recorded user approvals.

| Decision | Reason | Easy to reverse? |
|---|---|---|
| Combine M1.4–M1.6 in P-004/T-004 | Dev requested this grouping; deliverables share a single native Home integration | Yes, checkpoints remain separate |
| Use Navigation Compose with typed Home/Modes/Settings routes | Follows the proposed architecture and avoids passing a controller into screen content | Moderate; later routes extend the same host |
| Use stateless Home with nullable independent scores | No persistence or observable business state exists yet to justify a ViewModel/repository | Yes, a later route owner supplies values |
| Use temporary destinations for unavailable functionality | Navigation can be exercised without implementing M1.7 or settings behavior | Yes, replace content behind the same routes |
| Omit THEMES; retain Settings styling | Matches initial-release exclusions; no dead theme action | Yes, only through a future scope decision |
| Adapt Home to two columns on wide tablets and stack/scroll in constrained windows | Preserve approved styling and readable actions without treating a phone screenshot as a runtime layout | Yes; tablet composition remains an agent proposal |

## 6. Decisions that need business judgement (keep this short)

### D1. Combined planning or three separate planning units?

| Option | What it costs / means |
|---|---|
| A — One proposal/task with theme, navigation and Home checkpoints | Shares context and verifies the integrated entry point; a larger implementation diff |
| B — Separate proposal/tasks for M1.4, M1.5 and M1.6 | Smaller individual reviews, with repeated setup/reference work |

**Selected by Dev:** A, with planning approval pauses waived for this work unit. The rationale Dev supplied is that the design mockups already exist. No claim is made that Dev separately assessed every engineering recommendation above.

The phone Home is the approved visual baseline. Tablet Home and the wider screen gallery remain proposals. No additional business question blocks writing this plan; implementation authorization remains a separate instruction for a new Session B. The planning waiver does not approve the remaining gallery or MDAS choices.

## 7. How we will know it works

1. **M1.4:** Home uses central forest/cream/gold/lime tokens, bundled fonts and the gradient/lip/panel treatment; works offline without downloading fonts or images.
2. **M1.5:** Home is the start destination; PLAY/Settings reach their respective temporary destinations; Back returns Home; repeated action taps do not stack duplicate copies of the same destination.
3. **M1.6:** Native Home matches the approved image's visual hierarchy, with the documented dual-score and deferred-THEMES changes. Tests using Classic 120 and MDAS 340 show the correct labels/values independently; production uses unavailable values until integration.
4. At 390 × 844dp phone, 320 × 568dp small phone and 1024 × 768dp tablet reference bounds, content and 48dp actions remain reachable with safe insets; at font scale 1.5 text is readable and controls do not overlap. Tablet two-column treatment is identified as proposed in review evidence.
5. Phone/tablet orientation requests survive navigation and device rotation where supported. Record actual device/API coverage and any platform override separately from responsive-layout success.
6. Assemble, unit-test task and lint results plus connected tests, native screenshots and Studio preview/sync checks are recorded individually. Missing device/Studio checks leave their acceptance items open.

## 8. Risks and what I do not know yet

- **Mockups are not complete native specifications.** Build the approved Home styling, adapt the explicit dual-score binding, and retain pending tablet/gallery review status in screenshots and handoff.
- **Navigation compatibility is unbuilt.** Context7 resolved official Android Jetpack docs and returned Navigation 2.10.2 setup; official [release notes](https://developer.android.com/jetpack/androidx/releases/navigation) checked 2026-11-22 identify 2.10.2 as stable and API 23 as the 2.10 minimum. Session B must verify compiler/transitive compatibility with the current catalog before pinning; no dependency is installed during planning.
- **Custom styling needs native calibration.** Official [custom design-system guidance](https://developer.android.com/develop/ui/compose/designsystems/custom) supports custom token providers. Match the supplied image through native text/vector drawing; do not import the mockup or generate replacement raster artwork.
- **Fonts are not yet bundled.** Obtain official font files and licenses during execution; use static weights, or prepare static instances, so API 23 does not rely on variable-font support. Record provenance; do not silently substitute fonts.
- **Android orientation may be overridden.** The [Android 16 guidance](https://developer.android.com/about/versions/16/behavior-changes-16#large-screens) describes large-screen constraints and the game-category exception. Request the existing policy, mark the app as a game and test the actual target/API; a usable fallback does not prove a lock passed.
- **Native tools may be unavailable.** Carry [ISS-001](../issues/ISS-001-sandbox-gradle-access.md) and [ISS-002](../issues/ISS-002-emulator-startup-instability.md) into execution; report actual failures and incomplete checks rather than borrowing earlier results.
