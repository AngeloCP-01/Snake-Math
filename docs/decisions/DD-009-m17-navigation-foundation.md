# DD-009 M1.7 navigation foundation boundary

**Decided by:** Dev, agreeing P-005 · **Date:** 2026-11-24 · **Status:** accepted
**Satisfies:** [P-005 D1](../requirements/P-005-play-mode-gameplay-flow.md#d1-what-counts-as-m17-before-gameplay-engines-exist), planned [T-005](../requirements/T-005-play-mode-gameplay-flow.md) (separately approved by Dev on 2026-11-24; implementation remains Session B)

## Question

What should M1.7 deliver before the game engines and renderer exist?

## Options considered

| Option | For | Against |
|---|---|---|
| A — Mode choices and honest non-playable destinations now | Makes native entry/navigation independently verifiable within the foundation milestone | Temporary destination content must later be replaced |
| B — Defer M1.7 delivery until playable integration | Avoids temporary game content | Holds entry-flow delivery across later milestones |

## Decision

Use A: Home → Modes → a mode-specific non-playable destination. Show the selected mode and MDAS level when applicable, “Gameplay is not available yet.”, “Back to modes” and “Home”. Classic has no arithmetic difficulty. System Back returns to Modes; Modes Back returns Home. Home removes departed entries. Preserve origin-entry guards against repeated/stale callbacks. Reject invalid entry configuration by returning to Modes without inventing a level.

Do not add a board, simulated scores/lives/questions, engine, animation loop, pause or results. M1.7 completion means the verified navigation foundation, while M2–M6 retain playable delivery. Preserve T-004's no-scroll Home, tighter title, styling and Settings behavior.

## Why

The proposal recommends this boundary to follow the foundation-first roadmap without pulling later gameplay milestones into one task. Dev agreed the overall proposal on 2026-11-24 (“overall proposal is good to go, proceed with the task creation”); no additional personal rationale is inferred.

## Consequences

T-005 was separately approved by Dev on 2026-11-24 with `sol-medium` selected. Implementation belongs in a new Session B; no coding starts in this planning session. Reuse the current typed host and pinned dependencies. The Modes design is accepted only with the written P-005 adaptations, not as approval of all gallery screens. Carry native/device/Studio acceptance gaps forward honestly.
