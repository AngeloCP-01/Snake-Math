# DD-010 MDAS entry difficulty selection

**Decided by:** Dev, agreeing P-005 · **Date:** 2026-11-24 · **Status:** accepted
**Satisfies:** [P-005 D2](../requirements/P-005-play-mode-gameplay-flow.md#d2-how-should-mdas-difficulty-fit-the-mode-selection-screen), planned [T-005](../requirements/T-005-play-mode-gameplay-flow.md) (separately approved by Dev on 2026-11-24; implementation remains Session B)

## Question

Where should players choose MDAS difficulty, and how long does that choice last?

## Options considered

| Option | For | Against |
|---|---|---|
| A — Inline Easy/Medium/Hard choices in the MDAS card | Keeps mode and skill choice together | Needs reflow/scrolling for readable 48dp choices |
| B — Separate difficulty destination | More space for descriptions | Extra screen and Back/retention behavior |
| C — Defer difficulty selection | Smaller entry slice | MDAS configuration remains incomplete |

## Decision

Use A. Easy is selected on a fresh Modes visit; changing a level does not navigate. PLAY MDAS carries exactly that level; PLAY CLASSIC carries none. Show accessible selected state and the PRD's age-guided skill descriptions, with no age entry/gate. Preserve the selected level on return from either placeholder and through ordinary Activity recreation. Leaving for Home discards it; a fresh visit starts at Easy. Add no persisted last-used preference.

Use stacked phone cards and side-by-side landscape tablet cards when readable. Modes may vertically scroll at constrained bounds/enlarged text; every choice/action remains at least 48dp. This permission does not apply to Home, which remains bounded and non-scrolling.

## Why

The proposal recommends keeping the choice in the prepared two-mode flow. Dev agreed the overall proposal on 2026-11-24; that accepts the documented A recommendation/default/retention contract, without inventing a separate user rationale.

## Consequences

The mode-selection adaptation is agreed; native visual fit still needs verification. Numeric operand/intermediate/final/distractor limits are not chosen by this decision and remain prerequisites for generator implementation. Retry retains a future round's selected level, as the PRD already requires; no retry or question generation belongs to T-005. Follow [DD-011](DD-011-shell-process-restoration.md) for process death.
