# T-003 Establish package structure

**Owner:** Dev, implementation assisted by Codex · **Created:** 2026-11-21 · **Source:** [P-003](P-003-package-structure.md), agreed on 2026-11-21 by Dev; roadmap M1.3

**Status:** Done — T-003 approved by Dev on 2026-11-21 through “lets start the implementation of docs\requirements\T-003-package-structure.md”. Completed in separate Session B; Dev confirmed `sol-medium` and instructed proceeding on 2026-11-21. Exact runtime model ID is unavailable.

**Classification:** Simple — bounded documentation edits and a read-only source audit; no runtime, API, build or lifecycle change.

**Recommended execution profile:** `sol-medium` for the separate Session B; confirm the supported model/profile in the client. Session A requested `sol-high`; exact runtime model ID/profile mapping is unknown.

**Execution method:** Recommended direct execution with `superpowers:executing-plans` and focused self-review. No subagents are needed for this small documentation task. The repository's template, approval gates, records and no-commit rule override generic skill paths and commit steps.

## Requirement

Establish a concrete package ownership map for contributors in the existing single `:app`, under `com.acp.snakemath`, while keeping current correctly placed source unchanged. Planned packages are documented and become physical directories only when approved work introduces useful files, as accepted in [DD-005](../decisions/DD-005-package-structure.md).

**Dependencies:** T-002/M1.2 is complete; retain [DD-001](../decisions/DD-001-application-identity.md), [DD-002](../decisions/DD-002-minimum-android-version.md), [DD-003](../decisions/DD-003-foundation-build-baseline.md) and [DD-004](../decisions/DD-004-compose-configuration.md). [ISS-001](../issues/ISS-001-sandbox-gradle-access.md) and [ISS-002](../issues/ISS-002-emulator-startup-instability.md) are inherited environment limitations, not blockers for a documentation-only task.

## Steps

- [x] **1. Confirm authorization and baseline in a new Session B.** Read AGENTS.md, tracker, P-003, this task, DD-005 and the T-002 handoff. Require separate T-003 approval. Inspect branch/status and preserve existing Session A/user changes. Verify the local execution/verification skills; record the actual model/profile evidence and initial source/build status. Mark this task In progress in the tracker only when authorized execution starts. No worktree is needed solely for prose edits if the existing checkout is suitable.
- [x] **2. Audit source placement without editing it.** Inventory `app/src`, read package declarations and record the two production files and one instrumented test, the private preview's owner, manifest launcher, build namespace/application ID and single-module declaration. Confirm they match P-003's map. Put the audit table in the eventual worklog, with path, declared package/identity and ownership result. If the baseline differs from Session A or reveals a source defect, explain it and reassess scope before any code move or fix.
- [x] **3. Consolidate the package map in `docs/architecture.md`.** Keep the existing Section 4 heading/anchor. Replace its placeholder root with `app/src/main/java/com/acp/snakemath/`; distinguish existing files from every planned package listed in P-003 Section 3, including `feature/modes/`, `feature/game/` and conditional `app/device/`. Include responsibilities and dependency directions, test-package mirroring, future font/art resource destinations, and create-when-needed guidance. Make `MainApplication.kt`/`AppContainer.kt` conditional in the tree and adjacent prose; adjust directly related wording in Sections 2–3 only for consistency. Keep detailed construction, ViewModel and navigation APIs explicitly proposed. State that package boundaries in one module require review and have no new build enforcement.
- [x] **4. Reconcile related stale initialization facts in the architecture.** Update the top status, Section 4 namespace statement and Section 12 initialization bullet/closing text to reflect the existing T-002 foundation and DD-001–DD-005. Link accepted identity/build choices and the worklog for actual versions/evidence; avoid creating another version inventory. Limit acceptance to the agreed package contract. Leave gameplay, lifecycle, math bounds, device enforcement, preferences defaults, navigation behavior and visual review status unchanged. Do not rewrite the PRD, older handoff or architecture diagrams.
- [x] **5. Link the canonical map from `README.md`.** Add a concise contributor-facing link to `docs/architecture.md#4-package-structure-and-construction` and explain that planned directories appear when their first useful source file is added. Do not duplicate the map or change setup instructions.
- [x] **6. Verify documentation and preserved source.** Run the checks below. Inspect the complete diff and new files against P-003's success criteria. Verify all changed-document relative file links and the README package-section anchor. Check that only authorized documentation changed, with no tracked or untracked source/build scaffolding. Use focused self-review; no tests, builds, IDE sync or device run is necessary for unchanged application files. If scope changes, reassess authorization and verification before proceeding.
- [x] **7. Record results and hand off.** Create the actual dated T-003 worklog using `TEMPLATE-worklog.md`, including the source audit, changed sections, verification, Session A accounting below and any remaining limitations. Update task/tracker checkboxes only for verified outcomes; check M1.3 only when the definition of done is met. Create an issue record only for an actual newly discovered limitation. End with M1.4 theme planning as the next separate Session A; do not implement it. No commit or push without authorization.

### Verification commands and expected outcomes

Run from the repository root. The first group inventories the real application; the second checks the diff. These commands do not build or launch Android.

```powershell
git status --short
git branch --show-current
rg --files app/src
rg -n '^package |class MainActivity|fun SnakeMathApp|fun launcherDisplaysComposeSetupMarker' app/src
rg -n 'android:name|namespace|applicationId|include\(' app/src/main/AndroidManifest.xml app/build.gradle.kts settings.gradle.kts
```

Expected at the inspected foundation: both production declarations and the test use `com.acp.snakemath.app`; the private preview stays in `SnakeMathApp.kt`; launcher is `.app.MainActivity`; namespace/application ID are `com.acp.snakemath`; settings includes only `:app`. Inspect the files to confirm ownership rather than relying on a matching line alone. Record any pre-existing changes separately.

```powershell
git diff --check
git diff --stat
git diff --name-only
git ls-files --others --exclude-standard
git diff --exit-code HEAD -- app build.gradle.kts settings.gradle.kts gradle.properties gradle gradlew gradlew.bat
git ls-files --others --exclude-standard -- app gradle
```

Expected: tracked whitespace check exits zero; runtime/build comparison has no diff and exits zero if the baseline was unchanged; no new files under `app`/`gradle`. Any existing user changes must be identified at step 1 rather than mistaken for this task's work. Review untracked documentation separately because `git diff` omits it; check its whitespace and content too.

For each Markdown link in changed documents, resolve the relative file from its containing directory and confirm it exists. For the new README anchor, confirm Section 4's heading is unchanged and the rendered link targets that section. Worklog/template placeholders must not become broken file links. Record these checks explicitly; do not equate file existence with anchor validation.

**Review focus:** planned packages mistaken for created files (step 3); a mandatory container introduced through prose (3–4); broader architecture proposals marked accepted (4); hidden source/build scaffolding missed by tracked diff (6); historical T-002 Android evidence presented as new verification (7). Each is checked by the named inspection; artificial unit tests would add no evidence here.

## Definition of done

- [x] Architecture Section 4 uses `com.acp.snakemath`, covers every P-003 package with responsibilities/current-versus-planned status, and makes future application/container/device files conditional.
- [x] Ownership/dependency guidance keeps engines pure Kotlin, repository contracts independent of DataStore types, drawing separate from outcomes and tests in matching packages. No empty folders/files, speculative APIs, module split or new enforcement tool is added.
- [x] Directly related stale native-initialization/identity/build statements are corrected and linked to accepted records, while unrelated architecture and visual proposal statuses remain unchanged.
- [x] README links to the canonical Section 4 anchor without duplicating the map or changing setup instructions.
- [x] The worklog records the actual three-file source/package/preview audit, manifest launcher and build/module identity; application, tests, resources, manifest and build files remain unchanged by this task.
- [x] Whitespace, relative file-link/anchor and full-diff/new-file checks pass; no fresh build/device/IDE result is claimed. Any discovered blocker is documented honestly.
- [x] Dated worklog, task and tracker reflect verified results and complete-unit accounting. M1.3 is checked only after these criteria pass; the next handoff is M1.4 planning.

## Existing behaviour that must not change

- Preserve `com.acp.snakemath`, `.app.MainActivity`, one `:app`, API 23 floor, SDK 36, Build Tools 36.0.0, JVM target 17 and all current toolchain/dependency configuration. This task has no dependency changes.
- Retain the production Activity, stateless root, private preview, strings, safe-inset/marker presentation and `MainActivityTest` unchanged. T-002's native evidence remains historical.
- Preserve pure Kotlin engine boundaries, current PRD rules, all design/asset review statuses, unrelated files/user changes and reusable templates. Package agreement does not approve future behavior.

## Files and lines that matter

Line references describe the current files; section names remain the stable edit targets.

| Action | Exact path | Responsibility |
|---|---|---|
| Modify | `docs/architecture.md:3`, Sections 2–4 and 12 | Scoped package contract and related initialization facts only |
| Modify | `README.md` | One package-map link and creation guidance |
| Inspect only | `app/src/main/java/com/acp/snakemath/app/MainActivity.kt`, `app/src/main/java/com/acp/snakemath/app/SnakeMathApp.kt` | Existing launcher/root/preview placement |
| Inspect only | `app/src/androidTest/java/com/acp/snakemath/app/MainActivityTest.kt` | Existing matching test package |
| Inspect only | `app/src/main/AndroidManifest.xml`, `app/build.gradle.kts`, `settings.gradle.kts` | Launcher, namespace/application ID and module declarations |
| Update | `docs/requirements/T-003-package-structure.md`, `docs/task-tracker.md` | Actual task/milestone progress |
| Create in Session B | `docs/worklog/T-003-YYYY-MM-DD.md` | Actual execution date, source audit, results and accounting from the worklog template |

P-003 and DD-005 are accepted inputs; routine implementation should not rewrite them. No new package guide, source-tree README files or script files are planned.

## Reference

- Agreed [P-003](P-003-package-structure.md), particularly its package table and five success criteria; accepted [DD-005](../decisions/DD-005-package-structure.md).
- [Architecture package section](../architecture.md#4-package-structure-and-construction), [PRD Development Approach](../snake-math-prd.md#development-approach), [AGENTS.md](../../AGENTS.md) and [T-002 handoff](../worklog/T-002-2026-11-21.md#for-the-next-developer-or-ai-session).
- This is a documentation/source-audit task, not screen work. No primary image or visual acceptance is needed.
- Local Superpowers `7.0.1`: `C:/Users/ThinkPad/.codex/plugins/cache/openai-curated-remote/superpowers/7.0.1/skills/`. `brainstorming` and `writing-plans` were read in Session A; execution, TDD, verification and debugging `SKILL.md` files were found locally. Recheck access and read required instructions/supporting resources in Session B. Exact source revision unknown; no remote fallback. TDD adds no red/green cycle for unchanged runtime behavior.

**Session/model accounting handoff:** One known contributing conversation so far: Session A on 2026-11-21, proposal/decision/task drafting and review; requested `sol-high`, exact runtime ID/profile mapping unknown. User scope confirmation and proposal agreement are turns in this conversation, not additional sessions. Later Session B must carry this contribution into the worklog and add its actual model/date; whole-unit corrections, rework, waits and limits are unknown unless established by evidence. Do not copy T-002's accounting into T-003.

**Planning self-review:** P-003 success criteria 1–2 map to steps 3/5, criterion 3 to step 2 and the worklog, criterion 4 to step 6, and criterion 5 to steps 6–7. Related stale status cleanup is bounded in step 4. No implementation signatures or new behavioral tests are required. Templates are unchanged; no product or architecture scope expansion is proposed.

## Out of scope

- Product/source changes, package moves, placeholders, scaffolding, app containers, models, engines, ViewModels and repositories.
- Theme/fonts/artwork, navigation/Home/flow, persistence, device/orientation policy, loop/lifecycle behavior and architecture-diagram revisions.
- New build tools/dependencies/modules, native verification, broad documentation cleanup, PRD changes, release/publish, commit or push.

**Session B handoff after task approval:** Read tracker → P-003 → T-003 → DD-005 → T-002 handoff and inherited decisions. Confirm `sol-medium` in the client, inspect working-tree/approval evidence and then follow steps 1–7 with local `executing-plans`. Use documentation/source-diff verification above; implementation does not begin in this Session A.

**Verified result:** M1.3 completed on 2026-11-21; source/build preserved and documentation checks passed. See [worklog](../worklog/T-003-2026-11-21.md). Next: separate M1.4 planning.
