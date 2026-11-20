# Snake & Math — Agent Guide

## Project Identity
- **App:** Snake & Math — native Android Snake game with Classic Apple and MDAS Learning modes; title remains tentative.
- **Stack:** Kotlin, Jetpack Compose, Compose Canvas, MVVM, StateFlow, Coroutines, Preferences DataStore, JUnit and Compose UI Testing.
- **Structure:** One Android application module initially; keep the game and math engines independent of Android and Compose.
- **Scope:** Local best scores and minimal settings. Backend, accounts, cloud sync, ads, purchases, multiplayer and themes/skin selection are outside the initial release.
- **Product source:** Read `docs/snake-math-prd.md` and `docs/android-prototype-handoff.md` before implementation. User instructions supersede their earlier documentation-only stopping point when coding is explicitly requested.
- **Shared instructions:** This file applies to Codex and Claude. `CLAUDE.md` imports it; maintain project guidance here.
- **Workflow:** Use the Superpowers plugin/skills with the two-session proposal → task review → implementation process in Session Protocol. Repository templates determine document format and location.
- **Development approach:** Before planning or implementing, read and follow the PRD's [Development Approach](docs/snake-math-prd.md#development-approach). It applies to both session types, every model and any delegated agent; the documentation workflow supplements it.

## Current State
- Product discovery and the browser gameplay/smooth-joins v3 snake baseline are complete. Native implementation has not yet been verified.
- The prepared design system is in `docs/design-system/`. Home styling and the moving snake are approved; the seven-screen gallery and loading motion remain proposals awaiting review.
- **Progress source:** `docs/task-tracker.md` contains the roadmap checklists, current milestone, active work and evidence links. Read it to choose the next approved scope; initialize incrementally rather than implementing the whole roadmap at once.
- Architecture is documented for review in `docs/architecture.md`, with an interactive component diagram in `docs/diagrams/architecture.html`. Review it before drafting implementation tasks; Android initialization has not started.
- The user-defined documentation/workflow below is being prepared for review. No feature proposal or implementation task has been approved merely by adding these instructions; use the existing PRD development roadmap to choose the next proposal scope.
- Home shows both Classic and MDAS best scores with explicit mode labels (user confirmed 2026-11-20); retain the prepared panel styling.
- MDAS difficulty is selectable: Easy guides ages 6–7, Medium 8–9, Hard 10–12. Read PRD Section 2.2 for skills/operator limits; agree numeric bounds and selection UI before implementation. Automatic progression remains deferred.
- Native movement supersedes the browser's collision rules: both modes wrap across borders; Classic ends on self-collision only; MDAS passes through its body and loses lives only on incorrect answers.
- Inspect the working tree and build files at session start. Update progress in the tracker; keep this section as project context and pointers. Record actual checks rather than claiming browser evidence validates Android.

## Architecture Rules (ENFORCED)
1. **MVVM with separate engines:** Compose UI → ViewModel → pure Kotlin game/math logic; ViewModels use repositories for preferences. Keep abstractions proportional to the feature.
2. **Core logic:** `core/model/`, `core/game/` and `core/math/` contain immutable models, movement, collisions, spawning, scores, lives and arithmetic. No Android, Compose, resources or persistence dependencies.
3. **UI:** `feature/` contains screens and ViewModels. `core/ui/` contains theme, reusable components and drawing. Composables forward events and render state; they do not decide gameplay outcomes.
4. **Data:** `data/preferences/` owns Preferences DataStore. Put repository contracts used by ViewModels in `data/repository/` alongside implementations initially; those contracts expose plain Kotlin values/Flow, not DataStore types. Engines never depend on this layer.
5. **State:** ViewModels expose immutable `StateFlow<UiState>`; screens collect with `collectAsStateWithLifecycle()`. Keep mutable state private and inject randomness/time where deterministic tests require them.
6. **Navigation:** Single Activity and Compose navigation; Home → PLAY → mode selection → Classic/MDAS. Pause, retry and results preserve the selected mode. Reuse the established routing approach once initialized.
7. **Loop:** One coroutine-driven logical loop per session. Pause on backgrounding, require explicit resume, cancel on ViewModel clearance and avoid catch-up ticks or duplicate restart loops. Frame interpolation is visual only.
8. **Input:** Swipes only. Queue up to two valid turns, reject reversals against the last queued direction and apply turns at cell boundaries. First swipe starts a ready round. Calibrate browser pixel thresholds for native density.
9. **Board:** Phones use 12 columns × 16 rows in portrait; tablets use 20 × 16 in landscape across all screens. Fit square cells, select a profile at session creation and keep it fixed during the round. Verify native device classification and orientation behavior.
10. **Rendering:** Canvas consumes snapshots plus interpolation; grid cells determine collisions. Use one rounded route for a continuous shaded body, tapered tail and feathered atlas head. Cache prepared artwork outside drawing; follow the v3 specification.
11. **Gameplay:** Both modes wrap across all borders. Classic has one apple, +10 and one-cell growth; only self-collision ends the game, and filling the board completes it. MDAS starts with five lives and three answer apples. Correct: +10/growth/next question. Wrong: lose a life/no growth/next question. MDAS passes through its own body without pausing, resetting or losing lives; only incorrect answers can reduce lives to zero and end the round.
12. **Math and spawning:** Generate from the round's MDAS difficulty profile in PRD Section 2.2; age bands guide selection without age entry. Retry retains difficulty. Integer operands, exact division, standard precedence and equal-precedence left-to-right evaluation. One correct value, two distinct distractors, shuffled positions, no timer. Wrap coordinates before Classic collision/collection checks. Classic uses reachable unoccupied cells and its vacating-tail exception. MDAS body cells are traversable; prefer body-free answer positions but allow body overlap if needed, keeping three distinct apples away from the head and labels readable. Never reset MDAS because of body overlap or coverage.
13. **Persistence:** Separate Classic/MDAS best scores and settings in Preferences DataStore. Active rounds stay in memory. Add use cases or dependency-injection libraries only when concrete needs justify them.

## File Organization
Use the PRD's package layout under the application namespace selected at initialization:

```text****
app/src/main/java/<package>/
├── app/
│   ├── MainActivity.kt
│   ├── SnakeMathApp.kt
│   └── navigation/
├── core/
│   ├── model/
│   ├── game/
│   ├── math/
│   └── ui/
│       ├── theme/
│       ├── components/
│       └── drawing/
├── data/
│   ├── preferences/
│   └── repository/
├── feature/
│   ├── home/
│   ├── modes/
│   ├── game/                     # Shared game owner/host and pause overlay
│   ├── classic/
│   ├── mdas/
│   ├── settings/
│   └── results/
└── MainApplication.kt             # Only if application-wide setup needs it
```

Mirror relevant packages in `app/src/test/` and `app/src/androidTest/`. Bundle fonts in `res/font/`; approved PNGs belong in `res/drawable-nodpi/`. Create files when needed rather than filling this tree with empty classes.

Project records use the existing templates; read the relevant template before drafting a document. Preserve its sections, replace placeholders and sample content with Snake & Math facts, and choose the next unused ID after inspecting the folder.

| Record | Location/template | Purpose |
|---|---|---|
| Progress tracker | `docs/task-tracker.md` | PRD roadmap checklist, milestone/task status, current focus and links to supporting evidence |
| Proposal `P-nnn` | `docs/requirements/TEMPLATE-proposal.md` | Interview-informed problem, hypothesis, MVP scope, options, recommendations, success criteria and uncertainties |
| Task `T-nnn` | `docs/requirements/TEMPLATE-task.md` | Approved proposal broken into implementable work with steps, checkable definition of done, preserved behavior, exact files, references and exclusions |
| Decision `DD-nnn` | `docs/decisions/TEMPLATE-decision.md` | Each substantive product/engineering choice: alternatives, chosen option, rationale and consequences |
| Issue/debt `ISS-nnn` | `docs/issues/TEMPLATE-issue.md` | Bugs, technical debt and known limitations with symptoms, cause or `unknown`, recognition clues, workaround and related records |
| Worklog | `docs/worklog/TEMPLATE-worklog.md` | Actual task result, changed files, decisions, unresolved work, verification pointers and next-session handoff |

Create filled records beside their templates, e.g. `P-001-android-foundation.md`, `T-001-project-setup.md`, `DD-001-application-id.md`, `ISS-001-<topic>.md` and `docs/worklog/T-001-YYYY-MM-DD.md`. Templates are reusable sources, not status trackers; leave them unchanged unless the user requests template changes. Link records by ID and path; use an `FR-nnn` only if that requirement actually exists. The tracker carries the PRD's milestone order and owns ongoing progress; proposals/tasks refine it. Keep routine status/checkbox updates out of the PRD; edit the PRD only when requirements change.

## Coding Conventions
- Kotlin only. PascalCase types/composables, camelCase functions/properties, SCREAMING_SNAKE_CASE constants; descriptive names tied to game concepts.
- Screen content receives state and callbacks; obtain ViewModels at navigation boundaries. Keep previews independent of repositories and live loops.
- Prefer immutable data classes, explicit game statuses and a single authoritative state transition per tick. Keep grid coordinates separate from pixel/dp rendering geometry.
- Use structured concurrency and explicit ownership of jobs. Keep asset decoding, file I/O and expensive geometry preparation out of frame callbacks.
- Centralize colors, typography and dimensions in `core/ui/theme/`; use string resources for user-facing text and content descriptions for icon actions.
- Tests mirror production packages and use `<Class>Test.kt`. Engine/math tests use deterministic seeds or fakes and assert behavior, including edge cases in the PRD.
- Use Gradle Kotlin DSL and a version catalog. Verify compatible stable versions together; derive SDK/JVM/dependency versions from build configuration rather than copying the old project's stack.

## Git Conventions

### Commit Messages (Conventional Commits)
Use `<type>(<scope>): <summary>`, imperative subject, at most 50 characters; wrap any body at 72 characters and explain the reason for the change.

| Type | Use |
|---|---|
| `feat` | New capability |
| `fix` | Bug fix |
| `refactor` | Restructuring without behavior changes |
| `chore` | Build configuration, dependencies or tooling |
| `docs` | Documentation |
| `style` | Formatting |
| `test` | Tests |
| `perf` | Performance improvements |

Scopes describe this project, e.g. `build`, `theme`, `navigation`, `classic`, `mdas`, `renderer` or `settings`.

```text
chore(build): initialize Compose application
fix(classic): allow entry into vacating tail
docs(agents): align shared project guidance
```

### Branching Strategy
- Inspect the current branch and user changes before editing. Keep `main` buildable; use `feature/<milestone>-<name>` or `fix/<description>` when isolation is useful.
- Keep changes focused and commits small. Commit/push only within the user's authorized scope, preserving unrelated working changes.
- Never force-push `main`. Keep SDK paths, signing keys, credentials and local machine settings out of commits.

## Design Guide (Summary — full spec: `docs/design-system/README.md`)
- Read the design-system README and `tokens.json` before building UI. Approved Home/MDAS mockups govern styling; the current gameplay prototype and smooth-joins v3 govern the moving snake.
- Forest background `#023E21`, dark panels `#053F1C`, cream text `#F7F2BB`, gold scores `#F7D548` and lime primary actions. Preserve the gradient, raised button lip and rounded panel treatment.
- Bundle Baloo 2 ExtraBold and Nunito Bold locally. Use custom theme tokens/CompositionLocals; avoid forcing the full palette into Material roles.
- Use a 4dp spacing grid, 16dp screen gutters and at least 48dp touch targets. Handle system insets, font scaling and small screens.
- Keep the MDAS expression and all three answer labels readable; answer apples look identical. Decoration stays clear of occupied playable cells.
- Copy only allowlisted artwork from `docs/assets/README.md`. Keep source PNGs unchanged; mockups, browser exports and archived studies are references, not runtime backgrounds.
- Preserve the continuous body/tail and original atlas head. Static mascot art is for Home; loading uses its separate proposed motion contract.
- Consult `screen-designs.md` for phone/tablet layout proposals. Preserve their review status; THEMES and extra skins remain deferred. Loading ends on real readiness, with no artificial delay.

## Autonomous Decision Rules

### Decide Alone (no need to ask):
- During the implementation session, implementation details, naming, file placement, focused refactoring and build fixes within the approved proposal/task and agreed stack.
- Compatible stable patch versions and routine AndroidX dependencies needed for an approved capability, after checking official documentation.
- Layout composition within the design system, deterministic test seams and lifecycle/error handling.
- Read-only investigation and reversible verification. Explain substantial engineering choices as work progresses so the user can learn.

### Escalate to User:
- Proposal agreement and task approval are separate required gates. Prepare concrete documents for review; never infer either approval from file creation, silence, a roadmap entry or approval of another document.
- Missing information that changes the product, application identity or public behavior and cannot be resolved from existing documents.
- Scope additions, gameplay rule changes, replacement of approved visuals, new runtime art or changes to the phone/tablet policy.
- Major architectural changes, substantial third-party services/libraries outside the agreed stack, or persistence changes that could lose user data.
- Irreversible destructive actions or publishing/releasing beyond existing authorization.
- Ask focused questions only where needed; continue independent authorized work while awaiting answers. Existing authorization carries across turns.

## Session Protocol

Use two distinct sessions for each approved work unit. Session A prepares and reviews the proposal and task documents. Session B implements the approved tasks. Approval in Session A does not start implementation there; finish with a handoff for a new session. The user may explicitly change this workflow, but agents must not collapse the sessions on their own.

### Session A — Planning, proposal and review
1. Read this guide, the active milestone in `docs/task-tracker.md`, current architecture and existing related proposals/decisions/issues. Inspect only the source/design evidence needed for that scope. Reuse accepted decisions rather than interviewing the user again about settled choices.
2. Use Superpowers `brainstorming` for discovery/design. Research and planning use the planning model profile below. Draft `P-nnn` from `TEMPLATE-proposal.md`, including what is being proposed, why, options/trade-offs, assumptions, exclusions, risks and evidence needed. Initial research and planning reasoning belong in this proposal; do not create implementation task files yet.
3. Link the draft and conduct an interview-style review with the user: explain the specific behavior/change and recommendation, then ask focused questions about unresolved choices, preferably one at a time. Invite corrections, revise the document and distinguish user decisions from agent proposals. The agent drafts the text as requested; the user owns product intent and approval, despite the template's generic "Write this yourself" note. Replace unrelated template examples rather than adopting their product/language defaults.
4. Obtain explicit agreement to the revised proposal. Mark its template status `agreed on YYYY-MM-DD by <name>` using the actual approval date/person; record accepted choices in `DD-nnn` files and link them. Do not invent missing rationale or claim unreviewed alternatives were approved. Changes to an agreed decision get a linked superseding decision, preserving history.
5. Only after proposal approval, use Superpowers `writing-plans` to produce `T-nnn` files in `docs/requirements/`, using `TEMPLATE-task.md`. These task files are the implementation plan; the user's folder/template conventions override Superpowers' default `docs/superpowers/` paths/header. Include scoped ordered steps, dependencies/interfaces where needed, exact files, checkable acceptance criteria, verification commands, preserved behavior and out-of-scope items. For screen tasks, identify one primary reference image plus written acceptance points as the template requires.
6. Classify each task as simple or complex, state the reason and record the recommended execution profile in its metadata/steps. Link its approved proposal, relevant decisions and related issues. Self-review coverage against the proposal and roadmap.
7. Present the task files and interview the user about the concrete breakdown, acceptance criteria and remaining trade-offs. Revise, then obtain explicit task approval separately from proposal agreement. Record the approval/date and exact task IDs in task metadata; preserve the template's sections. If scope changes materially, revise/review the proposal before approving affected tasks.
8. Update the tracker's planning/approval status and link actual proposal/task records. End with a concise Session B handoff: approved proposal/task paths and IDs, recommended model profiles, dependencies, relevant decisions/issues, first action and verification commands. Share newly authored supporting documents for review too. Do not scaffold, install project dependencies or implement product code in this session.

### Session B — Development and implementation
1. Read the tracker, handoff and approved proposal/tasks plus their linked decisions/issues. Verify approval and the available execution profile. A roadmap or unapproved draft is context, not an implementation authorization. If approval is missing, return the document for review instead of starting that task. Mark the approved work In progress in the tracker when implementation begins.
2. Use Superpowers `executing-plans` for the approved plan in this separate session. Follow applicable implementation skills, including `test-driven-development` for behavior changes, `systematic-debugging` for failures and `verification-before-completion` for result claims. Use `subagent-driven-development` only when that execution method is explicitly chosen or an applicable skill requires delegation; Superpowers use alone is not a request for unrestricted parallel agents.
3. Complete only the approved task scope, walking through implementation step by step within the current milestone. Explain why a library or architecture is chosen, important Kotlin/Android concepts, and each command before asking the user to execute it; run routine authorized commands directly when tools permit. Prefer clean, maintainable, testable code and include unit tests where appropriate. Deliver incremental changes rather than an entire application dump or work from future milestones. Record new substantive decisions in `docs/decisions/`; scope/product changes return to the proposal review gate before dependent implementation.
4. Verify against the task's definition of done. Once the wrapper exists, code/foundation work normally uses `./gradlew :app:assembleDebug :app:testDebugUnitTest :app:lintDebug`; use `:app:connectedDebugAndroidTest` when relevant and an emulator/device exists. Apply checks proportional to the change; document unavailable checks and exact failures.
5. Record discovered bugs/debt/limitations in `ISS-nnn`, linking the task and evidence. Write/update a task worklog using `TEMPLATE-worklog.md`, recording `done`, `partial` or `discarded` honestly. Include verification results under What changed/Unresolved and links to decisions/issues. Model names, contributing session counts, corrections, rework, waits and limits must reflect actual records; use `unknown` for unavailable measurements rather than fabricating zeros.
6. Update task checkboxes, the tracker's current focus/milestone status and related issue/decision status to match verified outcomes. Link completed tracker items to their task/worklog evidence; keep partial items unchecked and link blockers to `ISS-nnn`. Share the result/worklog for review, summarize remaining work and the next handoff, and commit only when authorized. Distinguish document review, browser tests, native builds, instrumented tests and device validation; no completion claim may exceed its evidence.

### Model routing and context budget

| Work | Codex preference | Claude preference |
|---|---|---|
| Research, brainstorming, proposal/requirements writing, planning and planning review | `sol-high` — available Sol model, high reasoning | User's `Opus 5.5` profile |
| Simple implementation: bounded changes with clear approved steps and low interaction risk | `sol-medium` — available Sol model, medium reasoning | User's `sonnet-high` profile |
| Complex implementation: engine rules, lifecycle/concurrency, wrap rendering, cross-feature work or difficult debugging | `sol-high` — available Sol model, high reasoning | User's `sonnet-high` profile |

These are user-requested profiles, not proof of installed model IDs. Resolve them against the actual runtime; do not invent a provider/version mapping or silently substitute a different family. A new main-session model is selected through the user's client/runtime; instructions cannot switch the running session. State any mismatch/unavailable profile before substantial dependent work and arrange a supported handoff. If authorized agents are used, set their supported model and reasoning effort explicitly. Record actual models used in the worklog. Escalate a simple task to the complex profile when new evidence warrants it, explaining why; material scope changes still need review.

Keep context small: start from the active proposal/task and follow its precise links. Carry decisions in `DD-nnn` and outcomes in worklogs instead of repeatedly loading the entire chat or every document. Interview questions should resolve concrete uncertainty rather than repeat the PRD. Keep handoffs short and sufficient for a fresh implementation session. Save tokens through focused context and reusable records while preserving the PRD's explanations and the user's understanding.

### Work Unit Size
- One focused approved task or coherent milestone slice per work unit, with its planning/review session and separate implementation session. Initialize the foundation before implementing engines, renderer fidelity and full gameplay integration.
- Finish the authorized scope without expanding to later milestones. If blocked, preserve useful work and record the exact remaining dependency.

### Context Recovery (after auto-compaction)
1. Re-read Current State, `docs/task-tracker.md` and the latest request; identify Session A or B and retain existing approval evidence. The tracker owns progress; source files and worklogs verify it.
2. Read the active `P-nnn`/`T-nnn`, linked `DD-nnn`/`ISS-nnn` and latest relevant worklog. Check git status and edited files; do not reconstruct implementation state from memory.
3. Follow Key Docs only for the active scope: PRD/handoff for rules, design tokens for UI, v3 spec for drawing. Re-read a template only when creating/changing its record type.
4. Briefly state the session type, active task/proposal, last verified result and next action. Compaction does not approve drafts or turn a planning session into implementation.

## MCP Tool Usage

### Codebase Navigation & Editing
- Start file/text discovery with `rg --files` and `rg`; inspect relevant source before editing. Use structured symbol tools if available and useful, verifying changes in the actual files.
- Use focused patches. Check the diff for unrelated changes and preserve user edits.

### Sequential Thinking — Complex Planning & Debugging
- If available, use for genuinely intricate loop/lifecycle issues, queued turns, collision ordering or renderer geometry. Straightforward documentation and build edits need no external reasoning tool.
- Diagnose from reproducible evidence before changing behavior; reduce the failing case and verify the fix with an appropriate check.

### Context7 — Library Documentation & API Reference
- Discover available tools, resolve the library ID, then query relevant documentation before introducing or changing framework integrations.
- Relevant libraries include Compose/Canvas, Android lifecycle/ViewModel, navigation, Coroutines, Preferences DataStore and Gradle/AGP/Kotlin tooling.
- Check official Android/Kotlin/Gradle sources when current compatibility or an API is unclear. Tools are optional capabilities, not project dependencies; use official documentation when a connector is unavailable.

## Code Review Subagent (when available and useful)
- After a substantial implementation slice, review the actual diff against the PRD, design system and current task before an authorized commit.
- When delegation is authorized and available, give the reviewer scope, changed files, requirements and verification results. Do not rely on a nonexistent WorkoutTracker review template.
- Check architecture boundaries, deterministic rules, lifecycle ownership, renderer alignment, accessibility and appropriate test coverage. Resolve critical/major findings, then repeat affected checks.
- For small documentation or scaffolding changes, a focused self-review is sufficient. Record unresolved material findings plainly.

## Key Docs
| File | When to read |
|---|---|
| `docs/task-tracker.md` | At session start, handoff and completion: current focus, milestone checklist, status and evidence links; update progress here |
| `docs/snake-math-prd.md` | Product scope, stack, rules, testing and milestone order |
| `docs/android-prototype-handoff.md` | Approved prototype baseline, reference precedence and native acceptance checks |
| `docs/architecture.md` | Before task planning or structural changes: proposed ownership, navigation, game lifecycle, rendering and persistence contracts |
| `docs/requirements/TEMPLATE-proposal.md` | Before Session A proposal drafting; use its sections for the interview/review document |
| `docs/requirements/TEMPLATE-task.md` | After proposal agreement, before drafting tasks for separate review |
| `docs/decisions/TEMPLATE-decision.md` | When recording a substantive decision, alternatives, rationale or superseding choice |
| `docs/issues/TEMPLATE-issue.md` | When capturing a bug, technical debt or unresolved limitation |
| `docs/worklog/TEMPLATE-worklog.md` | Before documenting a task result and handoff at implementation-session end |
| `docs/diagrams/architecture.html` | Interactive view of the proposed in-process components |
| `docs/design-system/README.md` | Before any screen/component styling |
| `docs/design-system/tokens.json` | Implementing theme values and component metrics |
| `docs/design-system/screen-designs.md` | Screen flows and proposed phone/tablet layouts |
| `docs/design-system/screens-prototype.html` | Visual review of the seven screen proposals |
| `docs/design-system/loading-animation.md` | Before implementing or reviewing loading motion |
| `docs/assets/game/snake-grid-gameplay-prototype.html` | Rendering/motion and historical behavior reference; current PRD overrides its old collision/question rules |
| `docs/assets/game/path-snake/smooth-joins-v3.md` | Before changing snake rendering; exact geometry/head assembly contract |
| `docs/assets/README.md` | Before copying artwork into Android resources |
| `docs/assets/asset-manifest.json` | Artwork roles and source checksums |
| `docs/assets/mockups/home-v3.png` | Approved Home composition, mascot and button treatment |
| `docs/assets/mockups/mdas-gameplay-v3.png` | MDAS screen styling; v3 renderer overrides its snake illustration |

## Memory Files
- Keep progress in `docs/task-tracker.md`, shared decisions in `docs/decisions/`, proposals/tasks in `docs/requirements/`, issues/debt in `docs/issues/` and task outcomes/handoffs in `docs/worklog/`. This guide holds project pointers and durable workflow; avoid duplicate external memory logs or competing task trackers.
- Tool-specific memory is optional supporting context. Keep it free of secrets and machine-specific paths, and verify it against the repository before acting.
