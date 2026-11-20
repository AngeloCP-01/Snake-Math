# DD-003 Foundation build baseline

**Decided by:** Dev, through agreement to P-001's engineering recommendations · **Date:** 2026-11-20 · **Status:** accepted  
**Satisfies:** [P-001](../requirements/P-001-kotlin-android-foundation.md), [T-001](../requirements/T-001-project-setup.md)

## Question

What is the smallest useful native foundation for M1.1, and which build combination should its implementation verify?

## Options considered

| Option | For | Against |
|---|---|---|
| One app module and minimal Kotlin launcher | Compiles application Kotlin and permits startup verification; respects M1.1 | Compose will adapt the entry point in M1.2 |
| Compose Empty Activity template | Immediately displays Compose UI | Overlaps M1.2 and later theme work |
| No Activity | Fewest application files | Provides no launch check and may compile no application Kotlin |

## Decision

Create one `:app` in the existing repository with Gradle Kotlin DSL, a version catalog, complete checksummed wrapper and a minimal platform `Activity` launcher. Its blank platform window is verification scaffolding, not approved UI. No Compose or application theme is introduced in M1.1.

Use AGP 9.4.0, Gradle 9.6.0, compile/target SDK 36 and Build Tools 36.0.0 as the researched starting combination. Use AGP's built-in Kotlin without a duplicate Kotlin Android plugin; record the actual resolved compiler version during implementation. Run Gradle with the installed Studio JBR 25 and compile Java/Kotlin to JVM target 17. Keep local JDK/SDK configuration out of tracked files.

## Why

The single-module structure is already required by the PRD. Dev's instruction to begin task creation, following the request for overall P-001 agreement, accepts this scoped proposal. The technical rationale comes from the agent's proposal, rather than an independently stated user rationale.

[AGP 9.4 documentation](https://developer.android.com/build/releases/agp-9-4-0-release-notes) specifies Gradle 9.6.0 and Build Tools 36.0.0 with minimum JDK 17. [Gradle's matrix](https://docs.gradle.org/current/userguide/compatibility.html) supports Java 25 for this Gradle line. [Built-in Kotlin guidance](https://developer.android.com/build/migrate-to-built-in-kotlin) avoids applying the redundant Kotlin Android plugin. Local SDK 36 and Studio 2026.2.2 metadata were inspected; no native build has yet verified this combination.

## Consequences

T-001 must verify wrapper/build/lint, IDE sync, packaged identity and launcher startup separately. Compatible stable patch corrections within these release lines may be made and recorded when evidence warrants them. A different major/minor toolchain approach or changed product/device scope returns to review. No benchmark, native gameplay or full-architecture approval follows from this foundation decision.
