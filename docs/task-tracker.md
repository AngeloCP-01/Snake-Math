# Snake & Math — Task tracker

**Updated:** 2026-11-24
**Purpose:** The single place to check development progress. The milestone order and checklist items below come from [PRD Part 6 — Development Roadmap](snake-math-prd.md#part-6--development-roadmap). Keep the PRD focused on product requirements; update progress here.

## Current position

- **Completed baseline:** Milestone 0's initial browser discovery and visual references.
- **Current milestone:** Milestone 1 — Android Project Foundation; M1.1–M1.3 complete. M1.4–M1.6 implemented in T-004, with remaining native/Studio validation.
- **Native implementation:** T-001 scaffold verified: build/lint passed (0 errors / 5 warnings), unit-test task `NO-SOURCE`, blank launcher observed on API 34 emulator, and actual Studio sync passed with JBR 25. Dev confirmed Rabbit 2 | 2026.2.2 Patch 2 from Help → About.
- **Approved implementation tasks:** T-001, completed on 2026-11-21; [T-002](requirements/T-002-compose-configuration.md), separately approved by Dev on 2026-11-21 with implementation/native checks recorded in the T-002 worklog; [T-003](requirements/T-003-package-structure.md), authorized by Dev on 2026-11-21 and complete.
- **Latest implementation task:** [T-004](requirements/T-004-theme-navigation-home.md), merged into `main` through [PR #3](https://github.com/AngeloCP-01/Snake-Math/pull/3) on 2026-11-24, merge commit `08bb33c`. Includes prerequisite T-003 commit `1b7c59b`. ISS-003–ISS-005 and README fixes are in `600596d`; final build/lint and 25 API 34 tests passed before merge, with no remaining scoped findings in the fresh read-only correction review. [Changes/evidence](verification/T-004/review-fixes.md). T-004 remains partial for broader device/Studio acceptance; merge does not complete those checks.
- **Agreed proposals:** [P-001 — Kotlin Android foundation](requirements/P-001-kotlin-android-foundation.md), agreed on 2026-11-20 by Dev (M1.1); [P-002 — Compose configuration](requirements/P-002-jetpack-compose-configuration.md), agreed on 2026-11-21 by Dev (M1.2); [P-003 — Package structure](requirements/P-003-package-structure.md), agreed on 2026-11-21 by Dev (M1.3).
- **Last implementation profile:** T-004 `sol-high`, confirmed by Dev on 2026-11-22; exact runtime model ID unavailable.
- **Confirmed proposal choices:** Application ID and namespace `com.acp.snakemath`; minimum Android API 23 / Android 6.0 (Dev, 2026-11-20; P-001 D1–D2).
- **Supporting decisions:** [DD-001 identity](decisions/DD-001-application-identity.md), [DD-002 minimum Android](decisions/DD-002-minimum-android-version.md), [DD-003 foundation build](decisions/DD-003-foundation-build-baseline.md), [DD-005 package contract/audit](decisions/DD-005-package-structure.md).
- **Result evidence:** [T-001 worklog](worklog/T-001-2026-11-21.md); environment limitations [ISS-001](issues/ISS-001-sandbox-gradle-access.md) and [ISS-002](issues/ISS-002-emulator-startup-instability.md).
- **Approved M1.2 records:** P-002 agreed; accepted [DD-004 — Compose configuration](decisions/DD-004-compose-configuration.md); [T-002 — Configure Jetpack Compose](requirements/T-002-compose-configuration.md) separately approved by Dev on 2026-11-21 (“task approved”).
- **M1.7 status: Ready for implementation.** Dev agreed [P-005](requirements/P-005-play-mode-gameplay-flow.md) and separately approved [T-005](requirements/T-005-play-mode-gameplay-flow.md) on 2026-11-24 (“sol medium should be enough for this task, overall its good”). [DD-009 boundary](decisions/DD-009-m17-navigation-foundation.md), [DD-010 difficulty](decisions/DD-010-mdas-entry-difficulty.md) and [DD-011 restoration](decisions/DD-011-shell-process-restoration.md) define the navigation foundation while M2–M6 retain gameplay. Dev selected **`sol-medium` for T-005**, superseding the earlier `sol-high` execution recommendation; scope/verification and general routing rules are unchanged. Session A planning requested `sol-high`; exact runtime model IDs/profile mapping remain unknown. Local Superpowers 7.0.1 brainstorming/writing-plans instructions verified readable. No implementation has started.
- **Next action:** Start a new Session B for approved T-005 using Dev-selected `sol-medium` and locally available executing-plans/TDD/verification skills. First read P-005, T-005 and DD-009–DD-011, inspect status/build files and verify the execution profile through the client/runtime; the requested profile does not establish an actual model switch here. Follow T-005's ordered steps and verification commands. Preserve no-scroll Home and the tighter `0.8.em` title. Carry forward pending T-004 API 23/API 36, complete rotation, Studio and visual acceptance without marking M1.4/M1.6 done. The prior handoff confirmed local `main` and `origin/main` at `08bb33c`; Session A changes documentation only, with no new native build/test or post-merge verification claimed.
- **Component editing:** All six shared components now include standalone themed previews, requested by Dev for manual refinement; preview compilation passed. HomeScreen retains its responsive sizing overrides. See [T-004 worklog](worklog/T-004-2026-11-22.md).
- **Documentation reconciliation (2026-11-24):** At Dev's request during P-005 review, reconciled the PRD, Android handoff, architecture/package map, design guidance/tokens, asset notes, P-004/T-004 references and issue/verification summaries with confirmed T-004 Home/title changes, merged fixes and outstanding validation. PRD architecture summaries reuse the maintained architecture source/figure instead of divergent model/Mermaid sketches. Propagated P-005 agreement, accepted DD-009–DD-011 choices and subsequent separate T-005 approval with Dev-selected `sol-medium`. Historical exports/test narratives remain labeled; no raster/diagram export or runtime source changed. Implementation remains a new Session B. This pass closes no native acceptance item.
- **Documentation maintenance (2026-11-21):** Dev requested replacing all architecture Mermaid diagrams with directly viewable images. The six source diagrams are presented as eight embedded PNG figures, with [editable sources and verification](diagrams/architecture-figures/README.md). This presentation update does not advance the roadmap or approve the remaining architecture proposals.

This is a roadmap tracker, not a detailed implementation plan or approval to start coding. Detailed proposals/tasks live in [requirements](requirements/); decisions, blockers and results link to their own records. Initial browser approval does not establish native behavior, testing or performance.

## Milestone overview

| Milestone | Status | Proposal / task / worklog links |
|---|---|---|
| [0 — Product Discovery and Technical Planning](#milestone-0--product-discovery-and-technical-planning) | Initial prototype baseline complete; native planning review remains | [Handoff](android-prototype-handoff.md), [architecture draft](architecture.md) |
| [1 — Android Project Foundation](#milestone-1--android-project-foundation) | In progress; M1.1–M1.3 done; M1.4–M1.6 implemented; validation partial | [P-001 agreed](requirements/P-001-kotlin-android-foundation.md); [T-001 done](requirements/T-001-project-setup.md); [worklog](worklog/T-001-2026-11-21.md); [P-002 agreed](requirements/P-002-jetpack-compose-configuration.md); [T-002 done](requirements/T-002-compose-configuration.md); [P-003 agreed](requirements/P-003-package-structure.md); [T-003 done](requirements/T-003-package-structure.md); [P-004 planning record](requirements/P-004-theme-navigation-home.md); [T-004 partial](requirements/T-004-theme-navigation-home.md); [DD-006 planning waiver](decisions/DD-006-combined-foundation-planning.md) |
| [2 — Classic Game Engine](#milestone-2--classic-game-engine) | Not started | No proposal/task records yet |
| [3 — Custom Snake Rendering](#milestone-3--custom-snake-rendering) | Not started | No proposal/task records yet |
| [4 — Classic Gameplay UI](#milestone-4--classic-gameplay-ui) | Not started | No proposal/task records yet |
| [5 — MDAS Engine](#milestone-5--mdas-engine) | Not started | No proposal/task records yet |
| [6 — MDAS Gameplay UI](#milestone-6--mdas-gameplay-ui) | Not started | No proposal/task records yet |
| [7 — Polish and Quality Assurance](#milestone-7--polish-and-quality-assurance) | Not started | No proposal/task records yet |

## How to maintain this tracker

- Update **Current position**, milestone status and record links when work changes state; update the date when progress changes.
- Use statuses **Not started**, **Planning**, **Ready for implementation**, **In progress**, **Blocked** and **Done**. Planning includes proposal/task review; Ready requires both approvals. Milestone 0 retains its explicitly limited baseline status until remaining planning is resolved.
- Check an item only after its approved task's relevant acceptance criteria pass. Link the actual `T-nnn` and worklog beside that item; partial work stays unchecked, with a short status note.
- A roadmap item ID such as `M2.1` is a stable tracking reference, not a reserved `T-nnn`. One approved task may cover several items, or an item may require several tasks. Add actual links once those files exist.
- Record proposal agreement and task approval separately in their documents. Starting/completing a milestone does not waive the two-session workflow or authorize later milestones.
- Keep unresolved bugs/debt in [issues](issues/) and link blockers here. Record outcomes/evidence in [worklogs](worklog/) and substantive decisions in [decisions](decisions/).
- The checklists retain the PRD roadmap wording. Apply the latest requirements and accepted decisions when drafting tasks: both modes wrap at borders; Classic ends only on self-hit; MDAS passes through its body and loses lives only through wrong answers. MDAS uses the agreed age-guided difficulty profiles.
- Add or revise tracker items only to reflect an agreed scope change or approved task breakdown. Progress-only changes belong here; change the PRD when product requirements change, not when a checkbox is completed.

## Milestone 0 — Product Discovery and Technical Planning

**Status:** Initial prototype baseline complete; native planning review remains.

- [x] Initial Classic/MDAS browser behavior and swipe controls reviewed. Subsequent native rules are recorded in the current PRD.
- [x] Phone portrait 12 × 16 and tablet landscape 20 × 16 board profiles documented.
- [x] Final smooth-joins v3 snake appearance approved for the browser baseline.
- [x] Design references and Android handoff prepared.

**Evidence:** [PRD roadmap](snake-math-prd.md#milestone-0--product-discovery-and-technical-planning), [handoff](android-prototype-handoff.md), [renderer contract](assets/game/path-snake/smooth-joins-v3.md), [design system](design-system/README.md).

**Still open for planning:** review remaining detailed [gameplay architecture](architecture.md) and agree per-difficulty operand/intermediate/result/distractor bounds before generator work. T-005 is separately approved; difficulty-selection placement/default/retention and shell restoration are agreed through P-005/DD-010/DD-011 but not implemented. Other screen/loading review status remains documented in the design system. Browser approval does not complete native planning/verification.

## Milestone 1 — Android Project Foundation

**Status:** In progress — M1.1 is done through [T-001](requirements/T-001-project-setup.md), with build/lint, API 34 startup, actual Studio sync and displayed IDE-version evidence in the [worklog](worklog/T-001-2026-11-21.md). M1.2 proposal [P-002](requirements/P-002-jetpack-compose-configuration.md) and task [T-002](requirements/T-002-compose-configuration.md) were separately approved by Dev on 2026-11-21. M1.2 is complete: CLI/native checks passed and Dev confirmed Studio sync/preview on 2026-11-21. See the [T-002 worklog](worklog/T-002-2026-11-21.md).

- [x] **M1.1** Create Kotlin Android project. — [T-001](requirements/T-001-project-setup.md), [worklog](worklog/T-001-2026-11-21.md).
- [x] **M1.2** Configure Jetpack Compose. — Done; build/lint, API 34 red/green and full connected tests, native font-scale check passed; Dev confirmed Studio sync/preview on 2026-11-21. [Worklog](worklog/T-002-2026-11-21.md). Approved records: [P-002 agreed](requirements/P-002-jetpack-compose-configuration.md), [T-002 approved](requirements/T-002-compose-configuration.md), [DD-004](decisions/DD-004-compose-configuration.md).
- [x] **M1.3** Establish package structure. — Done through [T-003](requirements/T-003-package-structure.md), authorized by Dev on 2026-11-21; documentation/source audit and preserved-source checks in the [worklog](worklog/T-003-2026-11-21.md).
- [ ] **M1.4** Create application theme. — Implemented; fonts/assets verified, API 23 rendering pending. [T-004 worklog](worklog/T-004-2026-11-22.md).
- [x] **M1.5** Implement navigation. — Typed shell, repeated/stale callbacks and Back verified in final 25-test API 34 suite; [evidence](verification/T-004/review-fixes.md). Mode-entry foundation remains M1.7; playable integration belongs to later milestones.
- [ ] **M1.6** Create basic home screen. — Implemented with dual-score labels and proposed tablet composition; remaining visual/rotation/Studio validation in [worklog](worklog/T-004-2026-11-22.md).
- [ ] **M1.7** Add the PLAY → mode selection → gameplay flow. — **Ready for implementation:** [P-005 agreed](requirements/P-005-play-mode-gameplay-flow.md), [T-005 separately approved](requirements/T-005-play-mode-gameplay-flow.md) on 2026-11-24; Dev-selected `sol-medium` for a new Session B. DD-009 defines this as the non-playable navigation foundation; M2–M6 retain gameplay. Not started.

## Milestone 2 — Classic Game Engine

- [ ] **M2.1** Implement grid system.
- [ ] **M2.2** Implement snake movement.
- [ ] **M2.3** Implement direction handling.
- [ ] **M2.4** Implement apple spawning.
- [ ] **M2.5** Implement growth.
- [ ] **M2.6** Implement collision detection.
- [ ] **M2.7** Implement scoring.
- [ ] **M2.8** Write unit tests.

## Milestone 3 — Custom Snake Rendering

- [ ] **M3.1** Implement Canvas board.
- [ ] **M3.2** Draw continuous snake body.
- [ ] **M3.3** Draw snake spots.
- [ ] **M3.4** Draw snake head and face.
- [ ] **M3.5** Draw apples.
- [ ] **M3.6** Match the approved smooth-joins v3 snake in the current grid prototype.

## Milestone 4 — Classic Gameplay UI

- [ ] **M4.1** Connect game engine to Compose.
- [ ] **M4.2** Implement swipe gestures.
- [ ] **M4.3** Implement score display.
- [ ] **M4.4** Implement pause/resume.
- [ ] **M4.5** Implement game-over screen.
- [ ] **M4.6** Save best score using DataStore.

## Milestone 5 — MDAS Engine

- [ ] **M5.1** Implement mathematical expression generation.
- [ ] **M5.2** Implement expression evaluation.
- [ ] **M5.3** Implement answer generation.
- [ ] **M5.4** Implement distractor generation.
- [ ] **M5.5** Implement five-life system.
- [ ] **M5.6** Implement scoring.
- [ ] **M5.7** Write unit tests.

## Milestone 6 — MDAS Gameplay UI

- [ ] **M6.1** Display mathematical question.
- [ ] **M6.2** Display three answer apples.
- [ ] **M6.3** Add numerical labels.
- [ ] **M6.4** Implement correct/wrong answer handling.
- [ ] **M6.5** Display remaining lives.
- [ ] **M6.6** Implement results screen.

## Milestone 7 — Polish and Quality Assurance

- [ ] **M7.1** Test lifecycle behavior.
- [ ] **M7.2** Improve animations.
- [ ] **M7.3** Improve swipe responsiveness.
- [ ] **M7.4** Verify different screen sizes.
- [ ] **M7.5** Improve accessibility.
- [ ] **M7.6** Optimize rendering if necessary.
- [ ] **M7.7** Finalize portfolio documentation.

## Detailed records

P-001–P-005, T-001–T-005, DD-001–DD-011 and the issue/worklog evidence are linked above. P-003 is agreed; T-003 is complete. See the [T-003 worklog](worklog/T-003-2026-11-21.md). P-004/T-004 were planned under DD-006; Dev authorized Session B on 2026-11-22. T-004 is merged via PR #3 (`08bb33c`) and remains partial for outstanding device/Studio acceptance. P-005 and T-005 received separate approval on 2026-11-24; T-005 is ready for a new Session B using Dev-selected `sol-medium`. Keep implementation detail and actual verification evidence in those records.

| Record | Folder | Template |
|---|---|---|
| Proposals and tasks | [requirements](requirements/) | [Proposal](requirements/TEMPLATE-proposal.md), [task](requirements/TEMPLATE-task.md) |
| Decisions | [decisions](decisions/) | [Decision](decisions/TEMPLATE-decision.md) |
| Issues and technical debt | [issues](issues/) | [Issue](issues/TEMPLATE-issue.md) |
| Task outcomes and handoffs | [worklog](worklog/) | [Worklog](worklog/TEMPLATE-worklog.md) |
