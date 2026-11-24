# DD-011 Shell state across recreation and process death

**Decided by:** Dev, agreeing P-005 · **Date:** 2026-11-24 · **Status:** accepted
**Satisfies:** [P-005 interaction contract](../requirements/P-005-play-mode-gameplay-flow.md#2-hypothesis), planned [T-005](../requirements/T-005-play-mode-gameplay-flow.md) (separately approved by Dev on 2026-11-24; implementation remains Session B)

## Question

Should saved Modes/placeholder state return after Android recreates the app process?

## Options considered

| Option | For | Against |
|---|---|---|
| Restore the saved task's previous destination in a new process | Retains navigation context | Conflicts with P-005's fresh-process Home contract and eventual no-saved-round policy |
| Preserve same-process Activity recreation; start a new process at Home | Retains ordinary recreation UX with an explicit fresh-process boundary | Needs a process-identity distinction and real process-relaunch verification |

## Decision

Preserve the current screen, route configuration and Modes difficulty across ordinary Activity recreation within the same process. Any new process, including relaunch of an Android task saved on Modes or a game-entry placeholder, starts at Home and discards the old selection; next PLAY starts with Easy. Do not persist active rounds or a last-used difficulty.

## Why

The builder check exposed a difference between Activity recreation and saved-task process restoration. P-005 explicitly recommends the distinction above, consistent with the architecture's proposed fresh-process policy. Dev's overall proposal agreement on 2026-11-24 accepts that written contract; no separate user rationale is claimed.

## Consequences

Implement the smallest explicit process/restoration boundary in T-005. `rememberSaveable` alone restores across both Activity and process recreation, so it cannot establish this policy by itself. Test recreation and saved-task process relaunch separately; force-stop/new launch alone is not proof of saved-task restoration. This decision governs the navigation shell, not future game-loop pause/Back/resume behavior. Carry the policy into later gameplay design without claiming those mechanisms exist now.
