# P-003 Package structure — proposal

**Proposed by:** Codex, for Dev's review · **Date:** 2026-11-21 · **Status:** agreed on 2026-11-21 by Dev

**Roadmap scope:** M1.3 only — Establish package structure.

**Session:** A — proposal agreed; T-003 drafted for separate task review. No implementation authorization yet.

**Planning profile:** Requested `sol-high`. This session identifies as Codex based on GPT-6; its exact runtime model ID and profile mapping are unavailable. A confirmed Sol mapping is not asserted.

Dev owns product intent and approval. Dev confirmed the documentation-and-audit scope (option A in D1), then replied “agree” to the explicit request for overall P-003 agreement on 2026-11-21. The scoped recommendations below are accepted through that agreement. See [DD-005](../decisions/DD-005-package-structure.md) and [T-003](T-003-package-structure.md), which requires separate task approval before a new Session B.

## 1. Purpose, as I understand it

- **For whom:** Dev and future contributors implementing Snake & Math incrementally, including agents continuing from repository handoffs.
- **Problem:** T-002 already puts the launcher, root composition and integration test in the intended `app` package. The remaining package tree exists only as guidance. The architecture still describes a pre-native repository and an undecided namespace, and its sample application container looks mandatory even though AGENTS.md requires files only when needed. Without clarifying what exists versus what is planned, M1.3 could create unnecessary scaffolding or prematurely implement later milestones.
- **Business value:** Reduce placement mistakes, duplicated ownership and future restructuring while keeping each development step understandable. No player-facing capability or monetization change is proposed.
- **Source of this understanding:** Dev's 2026-11-21 request for Session A/M1.3; [AGENTS.md](../../AGENTS.md), [tracker](../task-tracker.md#milestone-1--android-project-foundation), [T-002 handoff](../worklog/T-002-2026-11-21.md#for-the-next-developer-or-ai-session), [PRD package structure](../snake-math-prd.md#32-suggested-package-structure), [PRD Development Approach](../snake-math-prd.md#development-approach) and [architecture](../architecture.md#4-package-structure-and-construction).

## 2. Hypothesis

Establish M1.3 as a small package-contract and source-audit task in the existing single `:app` module. Use `com.acp.snakemath`, retain the correctly placed T-002 files, and refine the existing architecture document into a concrete map of package responsibilities, allowed dependency directions and present versus future files. Link that map from the root README rather than create another competing architecture guide.

Under the agreed approach, M1.3's later Session B is documentation and source inspection only: no production, test, resource, manifest or build changes are expected. Future packages become physical source directories when approved work adds their first useful file. An absent future directory is intentional, not unfinished scaffolding. Dev confirmed this completion interpretation in D1.

### Approaches considered

| Approach | Trade-off | Recommendation |
|---|---|---|
| A — Consolidate the package contract in the existing architecture; audit current source; create future directories when needed | Keeps one package map and avoids unused files; the IDE initially shows only the populated `app` package | Recommended: matches the incremental roadmap and AGENTS.md's create-when-needed rule |
| B — Add package-local README files throughout the future tree | Makes planned folders visible in checkout and records nearby guidance; adds many documentation-only source folders and spreads maintenance across them | Possible, but unnecessary duplication for this small application |

Empty classes, interfaces or `.gitkeep` files solely to populate folders add no capability and are not recommended. Creating domain models, a container or feature stubs would anticipate later approved work. Splitting into Gradle modules is outside the agreed initial structure. These are constraints/exclusions, not alternatives Dev has rejected in this session.

**Selection:** Dev chose A on 2026-11-21 by replying “yes” to whether the documentation-and-audit scope matches the intended M1.3 outcome, then separately agreed to P-003 overall. No additional user rationale was supplied.

## 3. Proposed first version (MVP)

| In | Why |
|---|---|
| Update the existing architecture's package section to use the accepted namespace and distinguish existing source from planned packages | Remove ambiguity without changing application identity |
| Document package ownership and dependency directions below, with test-package mirroring and conditional file creation | Give later tasks a concrete placement contract |
| Correct narrowly related stale architecture statements about native initialization and unresolved identity/build decisions, linking accepted DD-001–DD-004 and T-002 evidence | Prevent historical status from contradicting the current source; preserve unrelated proposal status |
| Audit the three existing Kotlin files, manifest launcher and build namespace against the map | Verify the real foundation already fits; no move is expected |
| Add a root README link to the canonical package section | Make the structure easy to find without duplicating it |
| Later approved task/worklog and tracker evidence | Define when M1.3 can be checked complete without treating this draft as implementation |

### Proposed package contract

All paths below are relative to `app/src/main/java/com/acp/snakemath/`. “Planned” means a reserved responsibility, not a request to create that directory or a named class now.

| Package | Responsibility | Current state / creation rule |
|---|---|---|
| `app/` | Activity and root composition; future application wiring/adapters | Exists: `MainActivity.kt`, `SnakeMathApp.kt`; retain both and the private preview |
| `app/navigation/` | Routing and navigation boundaries | Planned for approved navigation work |
| `app/device/` | Platform device/orientation/lifecycle adapters, if needed | Proposed architecture extension; create only with approved device/lifecycle work |
| `core/model/` | Immutable grid/domain values shared by engines and presentation | Planned; no Android, Compose, resources or persistence dependencies |
| `core/game/` | Movement, turns, collisions, spawning, score/lives and transitions | Planned; pure Kotlin, using shared models and math where needed |
| `core/math/` | Arithmetic generation/evaluation and answer values | Planned; pure Kotlin, with shared model dependencies only where needed |
| `core/ui/theme/` | Product colors, typography, dimensions and shapes | Planned for M1.4; no tokens/fonts implemented here |
| `core/ui/components/` | Reusable presentation receiving state/callbacks | Planned; no persistence access or gameplay decisions |
| `core/ui/drawing/` | Canvas geometry, interpolation and prepared artwork | Planned; consumes models/snapshots, never decides outcomes |
| `data/repository/` | Repository contracts and their implementations initially | Planned; contracts expose Kotlin values/Flow, not DataStore types |
| `data/preferences/` | Preferences DataStore construction, keys and storage mapping | Planned for persistence work; engines never depend on it |
| `feature/home/`, `feature/modes/`, `feature/game/`, `feature/classic/`, `feature/mdas/`, `feature/settings/`, `feature/results/` | Screen content/state and appropriate route-level ViewModels; shared game host/pause presentation belongs in `feature/game/` | Planned; no empty screens, ViewModels or separate session owners created here |

`MainApplication.kt` at the namespace root and `app/AppContainer.kt` remain conditional on a concrete application-wide setup need. M1.3 does not add them or approve their eventual construction APIs.

Presentation may use core models/engines and repository contracts; repository implementations use preferences storage; drawing consumes core models. Core models do not depend on engines or presentation. Engines never depend on `app`, `feature`, `core/ui` or `data`. Packages group responsibilities inside one module; their separation is maintained by review, not claimed as an enforced module boundary. No new architecture enforcement tool is proposed.

Tests mirror the relevant production package under `app/src/test/java/` or `app/src/androidTest/java/`. The existing `app/src/androidTest/java/com/acp/snakemath/app/MainActivityTest.kt` stays in place. No empty unit-test tree or artificial package test is needed. Fonts and approved runtime PNGs retain their existing future destinations in `res/font/` and `res/drawable-nodpi/`; no assets are copied.

| Out (later) | Why it can wait |
|---|---|
| Models, engines, ViewModels, repository APIs, app container, application subclass and placeholder classes/folders | Create each only when approved behavior or setup needs it |
| Theme, fonts, artwork, styled components, navigation and Home/mode screens | M1.4–M1.7 have their own review and implementation scope |
| Lifecycle/loop implementation, device classification/orientation, persistence and gameplay | Package names do not approve their contracts or behavior |
| Full architecture approval, module splitting, DI libraries and build enforcement plugins | This proposal confirms package ownership only; unrelated architecture proposals remain proposals |

## 4. Configurable, not fixed

| Setting | Who sets it | Default I propose | Why it should not be fixed |
|---|---|---|---|
| When to create a planned package/file | Approved feature task and its implementer | At the first concrete need | Avoids empty scaffolding and premature APIs |
| Future finer subpackages | Implementer within approved scope | Keep the documented ownership boundaries; subdivide only as real code grows | File names and private helpers need not be frozen now |
| Application identity and build setup | Accepted decisions and existing build | Retain `com.acp.snakemath`, single `:app`, current SDK/JVM/toolchain/dependencies | Inherited constraints, not configuration reopened here |

No player-facing setting is added. Structural changes outside these boundaries return to review.

## 5. Decisions I have made

These scoped recommendations are accepted through Dev's overall proposal agreement; inherited constraints remain unchanged. Detailed choices elsewhere in the architecture are not approved by this agreement.

| Decision | Reason | Easy to reverse? |
|---|---|---|
| Reuse the architecture package section as the canonical map | It already describes ownership; a second guide would drift | Yes |
| Retain the existing Activity, root composition, preview and test locations | They already match DD-001 and the intended tree | Yes, but no move is justified now |
| Document planned packages without populating them | Follow AGENTS.md and avoid borrowing work from later milestones | Yes |
| Keep one module and pure Kotlin engine boundaries | Inherited PRD/AGENTS.md constraints | Major boundary changes require review |
| Use focused self-review for this small documentation/audit task | No runtime behavior or new integration is proposed | Yes if the eventual scope grows |

### Local evidence and planning method — 2026-11-21

- Working tree inspected clean on `main`; latest commit `8b896f1` merges the Compose work. No branch, commit or push was created in this session.
- Source inspection found two production Kotlin files in `com.acp.snakemath.app` and one instrumented test in the matching package. Manifest uses `.app.MainActivity`; build namespace/application ID are `com.acp.snakemath`. There are no domain/data/feature source files to relocate.
- Inspected existing build/catalog configuration: one `:app`, Compose enabled, AGP `9.4.0`, Kotlin/Compose plugin `2.2.10`, BOM `2025.11.01`, SDK 36, API 23 floor and JVM target 17. These are file observations; no dependency resolution or build was run here.
- T-002's [worklog](../worklog/T-002-2026-11-21.md) records passing build/lint, one API 34 connected test, native font-scale observation and Dev-confirmed Studio sync/preview. Those are prior results, not fresh M1.3 verification.
- Local Superpowers `7.0.1` was read from `C:/Users/ThinkPad/.codex/plugins/cache/openai-curated-remote/superpowers/7.0.1/skills/`: `using-superpowers`, its Codex reference, and `brainstorming`. Local `writing-plans/SKILL.md` is readable for the post-agreement stage. No upstream execution fallback was used; the exact package source revision is unknown. Host paths here record skill access evidence, not product configuration.
- The project template and explicit request to draft a proposal govern the artifact: the chosen approach is recorded separately from overall proposal agreement, and no task is drafted before that agreement. This is a small design with focused self-review, without delegation.
- This scope introduces no library/API integration or tooling change; no new platform API claim or external documentation research is needed to map the inspected source.

## 6. Decisions that need business judgement (keep this short)

### D1. What counts as establishing M1.3 when the existing source is already correctly placed?

| Option | What it costs / means |
|---|---|
| A — Package contract and source audit | Later Session B updates architecture/README and records the audit; only useful, populated source packages exist. No runtime or build change is expected |
| B — Populate planned paths with package README files | Later Session B adds documentation files throughout the future source tree so folders are visible; ongoing guidance has more places to maintain |

**My recommendation:** A. It makes ownership and future placement explicit while respecting the create-when-needed rule. M1.3 would be complete through an approved, verified documentation/audit task; theme remains the next independent work unit.

**User decision — confirmed on 2026-11-21 by Dev:** “yes”, answering “Does this documentation-and-audit scope match what you want M1.3 to establish?” Select A. Dev subsequently replied “agree” to the explicit overall P-003 agreement question. Proposal agreement is recorded; T-003 task approval remains pending.

## 7. How we will know it works

After proposal agreement, separate task approval and a new Session B, the recommended scope succeeds when:

1. The architecture package section names the real namespace, labels every package as existing or planned, includes the additional `modes`/shared `game` responsibilities, and marks application/container/device files as conditional. Root README links to it.
2. Responsibility/dependency guidance preserves pure Kotlin engines and test mirroring without requiring empty files, speculative APIs or a new module. No unrelated architecture, screen or loading proposal is marked accepted.
3. An audit records the actual three Kotlin file paths/package declarations, manifest launcher and build identity against that map. Every existing source file has a documented owner; no move is expected.
4. The resulting diff contains only agreed documentation changes. Production/test/resource/manifest/build files remain unchanged, as do the marker's text, layout and behavior. No new tests are warranted for prose-only changes.
5. Documentation links and `git diff --check` pass; the approved task, worklog and tracker record actual checks. Builds/native tests are unnecessary for an unchanged application; T-002 evidence remains explicitly historical. If a source defect is discovered, report it and reassess scope/verification before changing code.

This Session A prepares P-003, DD-005 and the separately reviewable T-003 plan, and updates planning status in the tracker. It performs no implementation, builds, native checks or IDE changes. The architecture/README changes described above remain for Session B after task approval.

## 8. Risks and what I do not know yet

- **Completion interpretation resolved** — Dev confirmed option A in D1 and agreed to P-003 overall: a documented contract and audit satisfy the intended M1.3 scope. Separate task approval remains pending.
- **A single module cannot enforce package isolation** — later code reviews must check imports/ownership. Module splitting and automated enforcement remain deferred, not promised here.
- **Architecture contains broader unapproved choices** — restrict edits to package guidance and related stale initialization facts. Navigation, transition APIs, loop scheduling, defaults and device policy details retain their separate review needs.
- **Requested planning model mapping is unavailable** — record requested versus confirmed information distinctly. Confirm the supported profile before later execution; exact runtime ID is unknown.
- **Future code might expose a placement conflict** — use this map as an ownership contract, not a demand for predetermined class APIs. A substantive boundary change returns to review.
- **Inherited environment limitations** — [ISS-001](../issues/ISS-001-sandbox-gradle-access.md) and [ISS-002](../issues/ISS-002-emulator-startup-instability.md) remain relevant if later scope needs a build/device; neither blocks this proposed documentation-only task.

**Next gate:** Dev reviews [T-003](T-003-package-structure.md) and explicitly approves the task. Local Superpowers `writing-plans` was used after proposal agreement to prepare it from `TEMPLATE-task.md`. Even after task approval, implementation waits for a new Session B.
