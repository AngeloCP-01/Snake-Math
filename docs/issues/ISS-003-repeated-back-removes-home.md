# ISS-003 Repeated Back to Home removes the start destination

**Found by:** Codex standards reviewer; reproduced by primary reviewer · **Date:** 2026-11-22 · **Status:** fixed in `600596d`, verified 2026-11-24; [PR #3](https://github.com/AngeloCP-01/Snake-Math/pull/3) merged as `08bb33c` on 2026-11-24. Broader T-004 device/Studio acceptance remains separate.

## Symptom

Delivering the temporary Modes destination's captured Back to Home callback twice in the same UI turn removes Home and leaves the navigation host without a destination. The focused API 34 review test fails with `Repeated Back removed Home: null`. Both temporary destinations use the same unguarded callback pattern.

## Cause

`app/src/main/java/com/acp/snakemath/app/navigation/SnakeMathNavHost.kt` uses unconditional `popBackStack()` for both Back to Home callbacks. The first invocation pops Modes; the second pops Home. `launchSingleTop` on forward navigation does not guard this return action.

## How to recognise it again

- Reuse the temporary destination's captured click action twice before recomposition, following the existing rapid-action test pattern.
- Observe null `currentDestination` instead of the typed Home route.
- [Focused failing native test](../verification/T-004/review-navigation-failure.xml), [command output](../verification/T-004/review-navigation-check.txt), [test source](../verification/T-004/review-navigation-reproducer.kt).

## Fix or workaround

Callbacks now check that their originating back-stack entry is still current. Return callbacks use typed non-inclusive pop to Home. Permanent regressions cover repeated Back from both temporary destinations, both stale mixed forward orders and a stale destination Back callback. All eight navigation cases passed in the final [25-test API 34 suite](../verification/T-004/revisions-final-tests.xml); [correction handoff](../verification/T-004/review-fixes.md). Dev subsequently authorized publication and merged PR #3 as `08bb33c`. Preserve these guards when M1.7 is approved and extends the host.

The original review reproducer remains archived. Its failure is now covered by permanent `SnakeMathNavigationTest` cases; the historical review itself made no production changes.

## Related

- [T-004](../requirements/T-004-theme-navigation-home.md), repeated-action/Back contract.
- [Review](../verification/T-004/review.md), [worklog](../worklog/T-004-2026-11-22.md).
- [Official Navigation back-stack guidance](https://developer.android.com/guide/navigation/backstack#handle_a_failed_pop_back).
