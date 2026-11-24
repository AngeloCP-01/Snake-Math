# DD-007 Home foundation integration

**Decided by:** Codex within Dev-authorized T-004 implementation · **Date:** 2026-11-22 · **Status:** accepted
**Satisfies:** [T-004](../requirements/T-004-theme-navigation-home.md)

## Question
How should the Home foundation fit the existing SDK/Kotlin baseline and offline resources?

## Options considered

| Option | For | Against |
|---|---|---|
| Navigation 2.10.2 | Current stable release | AAR metadata requires SDK 37; violates T-004 SDK 36 constraint |
| Navigation 2.9.7 | Stable typed-route API, retains SDK baseline | Older maintained line |
| Variable fonts at runtime | Original upstream files | API 23 font compatibility |
| Static font instances | Offline fixed approved weights | Requires documented font preparation |

## Decision
Use matching Navigation Compose/testing 2.9.7, serialization plugin 2.2.10 matching Kotlin, and serialization JSON 1.9.0 (requires Kotlin 2.2). Generate static Baloo 2 800 and Nunito 700 with FontTools 4.68.0. Keep the existing checkout/feature branch and preserve pending planning/template edits; no commits. Execute directly and self-review as T-004 specifies; repository records supersede generic skill scratch-ledger, commit and delegation instructions.

## Why
The actual 2.10.2 build rejected compile SDK 36 via AAR metadata; changing the SDK is outside this task. [Official Navigation releases](https://developer.android.com/jetpack/androidx/releases/navigation) identify 2.9.7 as stable. [Serialization 1.9.0 notes](https://github.com/Kotlin/kotlinx.serialization/releases/tag/v1.9.0) specify Kotlin 2.2 compatibility. Static font instances avoid requiring variable-font platform support.

## Consequences
Keep dependencies pinned in the catalog and validate native behavior separately from compilation. Font provenance/hashes are in [font-sources](../assets/font-sources.md). Keep the tablet composition proposed; device classification uses the task's suggested 600dp smallest-width threshold. API 36 platform orientation overrides still require device verification; do not imply enforcement merely from setting an Activity request.
