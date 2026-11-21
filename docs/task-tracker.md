# Snake & Math — Task tracker

**Updated:** 2026-11-21
**Purpose:** The single place to check development progress. The milestone order and checklist items below come from [PRD Part 6 — Development Roadmap](snake-math-prd.md#part-6--development-roadmap). Keep the PRD focused on product requirements; update progress here.

## Current position

- **Completed baseline:** Milestone 0's initial browser discovery and visual references.
- **Current milestone:** Milestone 1 — Android Project Foundation; M1.1 complete, M1.2 in Session A task review.
- **Native implementation:** T-001 scaffold verified: build/lint passed (0 errors / 5 warnings), unit-test task `NO-SOURCE`, blank launcher observed on API 34 emulator, and actual Studio sync passed with JBR 25. Dev confirmed Rabbit 2 | 2026.2.2 Patch 2 from Help → About.
- **Approved implementation tasks:** T-001, approved by Dev and completed on 2026-11-21; no later implementation task is approved.
- **Active implementation task:** None. [T-001 — Create Kotlin Android project](requirements/T-001-project-setup.md) is done.
- **Agreed proposals:** [P-001 — Kotlin Android foundation](requirements/P-001-kotlin-android-foundation.md), agreed on 2026-11-20 by Dev (M1.1); [P-002 — Compose configuration](requirements/P-002-jetpack-compose-configuration.md), agreed on 2026-11-21 by Dev (M1.2).
- **Execution profile:** `sol-medium`, confirmed by Dev on 2026-11-21; exact runtime model ID unavailable.
- **Confirmed proposal choices:** Application ID and namespace `com.acp.snakemath`; minimum Android API 23 / Android 6.0 (Dev, 2026-11-20; P-001 D1–D2).
- **Supporting decisions:** [DD-001 identity](decisions/DD-001-application-identity.md), [DD-002 minimum Android](decisions/DD-002-minimum-android-version.md), [DD-003 foundation build](decisions/DD-003-foundation-build-baseline.md).
- **Result evidence:** [T-001 worklog](worklog/T-001-2026-11-21.md); environment limitations [ISS-001](issues/ISS-001-sandbox-gradle-access.md) and [ISS-002](issues/ISS-002-emulator-startup-instability.md).
- **Active planning records:** P-002 agreed; accepted [DD-004 — Compose configuration](decisions/DD-004-compose-configuration.md); [T-002 — Configure Jetpack Compose](requirements/T-002-compose-configuration.md) drafted for separate review. Dev's proposal approval does not approve T-002.
- **Next action:** Review and explicitly approve T-002, then hand off to a new Session B with recommended `sol-medium` profile. Requested planning profile: `sol-high`; exact runtime mapping unavailable. No M1.2 implementation is authorized yet.

This is a roadmap tracker, not a detailed implementation plan or approval to start coding. Detailed proposals/tasks live in [requirements](requirements/); decisions, blockers and results link to their own records. Initial browser approval does not establish native behavior, testing or performance.

## Milestone overview

| Milestone | Status | Proposal / task / worklog links |
|---|---|---|
| [0 — Product Discovery and Technical Planning](#milestone-0--product-discovery-and-technical-planning) | Initial prototype baseline complete; native planning review remains | [Handoff](android-prototype-handoff.md), [architecture draft](architecture.md) |
| [1 — Android Project Foundation](#milestone-1--android-project-foundation) | In progress; M1.1 done, M1.2 task review | [P-001 agreed](requirements/P-001-kotlin-android-foundation.md); [T-001 done](requirements/T-001-project-setup.md); [worklog](worklog/T-001-2026-11-21.md); [P-002 agreed](requirements/P-002-jetpack-compose-configuration.md); [T-002 draft](requirements/T-002-compose-configuration.md) |
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

**Still open for planning:** review the [architecture draft](architecture.md), define per-difficulty number/result/distractor bounds and difficulty-selection UI, and complete the next proposal/task reviews. Additional screen/loading review status remains documented in the design system. None of these are claimed complete by the browser baseline.

## Milestone 1 — Android Project Foundation

**Status:** In progress — M1.1 is done through [T-001](requirements/T-001-project-setup.md), with build/lint, API 34 startup, actual Studio sync and displayed IDE-version evidence in the [worklog](worklog/T-001-2026-11-21.md). M1.2 proposal [P-002](requirements/P-002-jetpack-compose-configuration.md) is agreed by Dev on 2026-11-21; [T-002](requirements/T-002-compose-configuration.md) awaits separate task approval in Session A.

- [x] **M1.1** Create Kotlin Android project. — [T-001](requirements/T-001-project-setup.md), [worklog](worklog/T-001-2026-11-21.md).
- [ ] **M1.2** Configure Jetpack Compose. — Planning: [P-002 agreed](requirements/P-002-jetpack-compose-configuration.md), [T-002 draft](requirements/T-002-compose-configuration.md), [DD-004](decisions/DD-004-compose-configuration.md).
- [ ] **M1.3** Establish package structure.
- [ ] **M1.4** Create application theme.
- [ ] **M1.5** Implement navigation.
- [ ] **M1.6** Create basic home screen.
- [ ] **M1.7** Add the PLAY → mode selection → gameplay flow.

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

P-001, T-001, DD-001–DD-003, ISS-001–ISS-002 and the T-001 worklog are linked above. Keep implementation detail and actual verification evidence in those records.

| Record | Folder | Template |
|---|---|---|
| Proposals and tasks | [requirements](requirements/) | [Proposal](requirements/TEMPLATE-proposal.md), [task](requirements/TEMPLATE-task.md) |
| Decisions | [decisions](decisions/) | [Decision](decisions/TEMPLATE-decision.md) |
| Issues and technical debt | [issues](issues/) | [Issue](issues/TEMPLATE-issue.md) |
| Task outcomes and handoffs | [worklog](worklog/) | [Worklog](worklog/TEMPLATE-worklog.md) |
