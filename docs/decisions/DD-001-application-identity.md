# DD-001 Application identity

**Decided by:** Dev · **Date:** 2026-11-20 · **Status:** accepted  
**Satisfies:** [P-001 D1](../requirements/P-001-kotlin-android-foundation.md#d1-which-application-identity-should-this-project-use), [T-001](../requirements/T-001-project-setup.md)

## Question

Which application ID and namespace should the initial Android project use?

## Options considered

| Option | For | Against |
|---|---|---|
| Agent-proposed personal-name prefix | A concrete initial identifier | Dev selected a different prefix; no further rationale was supplied |
| `com.acp.snakemath` for both | Dev's explicit selection; consistent source and installation identity | Changing application ID after publication creates a different app |

## Decision

Use `com.acp.snakemath` as both application ID and namespace. The launcher class will live in the `com.acp.snakemath.app` package. The tentative display title remains independently editable as `Snake & Math`.

## Why

Dev explicitly instructed “we will use com.acp.snakemath”. No additional rationale or claim of domain ownership/store availability was supplied. This record preserves the choice without inventing one.

## Consequences

Use the same ID in the build, manifest verification and launch commands. Renaming the display title need not rename the application ID. If identity changes later, record a superseding decision and assess installation/update consequences using Android's [application ID guidance](https://developer.android.com/build/configure-app-module#set-application-id).
