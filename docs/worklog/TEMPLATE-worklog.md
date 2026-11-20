---
task: T-nnn
date: YYYY-MM-DD
owner: <name>
result: done            # done | partial | discarded
models: [<model>, <model>]
sessions: 1
corrections: 0          # prompts asking the AI to fix its own wrong output (new scope is not a correction)
rework: none            # or: what was redone and why
waited_min: 0           # minutes you could not work because of the AI or a limit
limits: none            # or: claude-5h, claude-weekly, codex-5h, codex-weekly
defects_after: 0        # filled in later by whoever finds a defect in this work
exceptions: none        # any handbook rule not followed, with the reason
---

# T-nnn <Task title>

## What changed
- <file or area> — <what and why, one line each>

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
