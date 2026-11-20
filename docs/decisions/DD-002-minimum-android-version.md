# DD-002 Minimum Android version

**Decided by:** Dev · **Date:** 2026-11-20 · **Status:** accepted  
**Satisfies:** [P-001 D2](../requirements/P-001-kotlin-android-foundation.md#d2-which-android-versions-should-the-initial-project-support), [T-001](../requirements/T-001-project-setup.md)

## Question

What is the initial supported Android version floor?

## Options considered

| Option | For | Against |
|---|---|---|
| API 23 / Android 6.0+ | Broader device baseline; aligned with the documented Compose minimum | Later dependencies and behavior still need compatibility checks |
| API 26 / Android 8.0+ | Smaller supported range to validate | Excludes older devices without an M1.1 requirement needing that restriction |

## Decision

Set `minSdk = 23`. Keep this distinct from the initial compile/target SDK 36 in DD-003.

## Why

The agent recommended API 23 for broader support, informed by the [Compose Runtime minimum SDK notes](https://developer.android.com/jetpack/androidx/releases/compose-runtime). Dev replied “agree” to that specific recommendation. Dev supplied no additional rationale.

## Consequences

Check future dependencies against API 23. This decision establishes the intended support floor, not proof of full-game compatibility or testing on Android 6.0. Report which API/device was actually used for each native check; do not silently raise the floor to resolve a dependency conflict.
