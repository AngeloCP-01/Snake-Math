# DD-004 Compose configuration and setup marker

**Decided by:** Dev, through agreement to P-002 · **Date:** 2026-11-21 · **Status:** accepted  
**Satisfies:** [P-002](../requirements/P-002-jetpack-compose-configuration.md), [T-002](../requirements/T-002-compose-configuration.md)

## Question

How should M1.2 establish and verify Compose while preserving the incremental foundation roadmap?

## Options considered

| Option | For | Against |
|---|---|---|
| Configure Compose with a blank launcher | Minimal visible change | Weaker evidence that the Activity hosts visible Compose content |
| Configure Compose with a small visible setup marker | Checks compilation, hosting and rendering; easy to replace | Temporary content remains until subsequent approved UI work |
| Generate a complete themed template/sample screen | Provides ready-made styling | Overlaps package, theme and Home milestones |

## Decision

Configure Compose in the existing `:app`, retaining AGP built-in Kotlin. Use the Kotlin-matched Compose compiler plugin, stable BOM-managed Compose UI/Foundation libraries, Activity hosting, preview support and debug tooling. Do not introduce Material solely for the marker.

Adapt the existing launcher to `ComponentActivity` and host a stateless `SnakeMathApp` composition. Display `Snake & Math` and `Compose ready` as resource-backed plain text on a neutral background, clear of system bars. Add one instrumented smoke test launching the production Activity and asserting visible content, plus a repository-independent preview.

Retain DD-001–DD-003 identity, SDK and build constraints. Pin a verified compatible dependency combination; compiler version `2.2.10` is the starting point from T-001's resolved app compiler, not Gradle's embedded Kotlin. Theme, Home, navigation, device policy and gameplay remain later work.

## Why

Dev explicitly chose the visible setup marker, then approved P-002 overall after being asked about configuration, preview support and the Activity smoke test. The observable-checkpoint rationale is the agent's recommendation accepted through proposal agreement; Dev supplied no additional rationale. A blank launcher provides less runtime evidence, and a complete template overlaps later scope.

## Consequences

T-002 must verify actual compiler/dependency compatibility, build/lint, Studio sync/preview and native rendering/test results separately. No exact BOM combination has been built during planning. Replace the marker through later approved UI work; it is not Home or theme approval. Dev separately approved T-002 on 2026-11-21; implementation requires a new Session B.
