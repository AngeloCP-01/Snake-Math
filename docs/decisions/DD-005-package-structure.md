# DD-005 Package structure without empty scaffolding

**Decided by:** Dev, through scope confirmation and agreement to P-003 · **Date:** 2026-11-21 · **Status:** accepted

**Satisfies:** [P-003](../requirements/P-003-package-structure.md), [T-003](../requirements/T-003-package-structure.md) (task approval pending)

## Question

What should M1.3 establish when T-002's source already occupies the intended application package and AGENTS.md requires files only when needed?

## Options considered

| Option | For | Against |
|---|---|---|
| A — Package contract in the existing architecture, source audit and README link | One maintained map; preserves existing correct source placement and incremental development | Planned packages are not visible as physical source folders until useful files exist |
| B — Package-local README files throughout the future source tree | Planned folders appear in checkout with nearby guidance | Spreads guidance across documentation-only source folders and duplicates the main map |

## Decision

Choose A. M1.3 is a documentation-and-source-audit work unit inside the existing single `:app` module and `com.acp.snakemath` namespace. Use the existing architecture package section as the canonical map, label current versus planned packages, record ownership/dependency directions and test mirroring, and link it from README.

Retain `app/MainActivity.kt`, `app/SnakeMathApp.kt`, its private preview and the matching instrumented test. Create future directories only when approved work adds useful files. Application/container/device files are conditional on concrete setup needs. No production, test, resource, manifest or build change is expected; no placeholders or package enforcement tools are added.

## Why

Dev replied “yes” to whether the documentation-and-audit scope matches the intended M1.3 outcome, then replied “agree” to the separate overall P-003 agreement question on 2026-11-21. The maintenance and incremental-development rationale comes from the accepted agent recommendation; Dev supplied no additional rationale. This does not record an independent user assessment of every alternative.

## Consequences

M1.3 can be completed by an approved, verified documentation/audit task without creating the future package tree. Later tasks use this ownership map and create real files as needed. One module does not enforce package boundaries at compilation; review remains necessary.

Correct only package guidance and related stale initialization facts in the architecture. Other architecture choices and all screen/loading review statuses remain unchanged. T-003 needs separate approval and a new Session B. Documentation-only verification is sufficient for this scope; discovered source defects require scope reassessment before a code change. No commit/push is authorized.
