---
task: T-nnn
date: YYYY-MM-DD
owner: <name>
result: done            # done | partial | discarded
models: [<documented model/profile and confirmation status per stage>]
sessions: unknown      # total distinct planning, review and implementation sessions; not only the writer's session
corrections: unknown   # whole-work-unit prompts asking AI to fix wrong output; new scope is not a correction
rework: unknown         # whole-work-unit rework and why; use none only when established
waited_min: unknown     # whole-work-unit minutes unable to work because of AI or a limit
limits: unknown         # use none only when established; otherwise name the observed limit
defects_after: unknown  # filled in later by whoever finds a defect in this work
exceptions: none        # any handbook rule not followed, with the reason
---

# T-nnn <Task title>

## What changed
- <file or area> — <what and why, one line each>

### Contributing sessions and models

Count the complete work unit, including planning, task review, implementation and additional review/blocked sessions. Count distinct sessions, not chat turns or compactions. Carry forward evidence from Session A; distinguish requested profiles from confirmed execution and exact runtime model IDs. Use `unknown` for an unestablished total and state the known minimum. Scope corrections, rework, waits and limits the same way.

| Stage / session | Date | Model/profile and evidence status | Exact runtime model ID | Evidence |
|---|---|---|---|---|
| Session A — planning/proposal/task review | <actual date(s)> | <confirmed or requested profile; do not assume execution> | <verified ID or unknown> | <proposal/task link> |
| Session B — implementation | <actual date(s)> | <confirmed model/profile or unknown> | <verified ID or unknown> | <task/session evidence> |

**Accounting:** <established total, or unknown total with known minimum; explain coverage and missing measurements.>

## Decisions
- <DD-nnn, or "none">

## Unresolved
- <What is not done, and what is needed to finish it>

## Check next
- `<path>:<lines>` — <why the next person should look here>

## Related history
- <ISS-nnn, DD-nnn, earlier worklog entries that were relevant>

## For the next developer or AI session
<Anything else they need to know. Keep it short.>
