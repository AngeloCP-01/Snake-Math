# DD-008 Fit Home without scrolling

**Decided by:** Dev (behavior), Codex (layout implementation) · **Date:** 2026-11-22 · **Status:** accepted
**Satisfies:** [T-004](../requirements/T-004-theme-navigation-home.md), follow-up to [DD-007](DD-007-home-foundation-integration.md)

## Question

How should Home respond when its components exceed the available screen height?

## Options considered

| Option | For | Against |
|---|---|---|
| Scroll the fixed-size composition | Preserves fixed illustration/type sizes | Dev rejected scrolling after emulator inspection |
| Allocate height to content and flex the illustration, with compact spacing/type on constrained screens | All Home elements visible, retains responsive phone/tablet layouts | Illustration and title become smaller on constrained displays |
| Scale the entire screen as an image | Easy geometric fit | Shrinks touch targets/text indiscriminately; discards native controls |

## Decision

Dev requested all Home components fit on screen without scrolling while remaining responsive. This directly authorizes a correction within the active T-004 implementation. Replace Home's scrolling fallback with a bounded layout. Preserve controls of at least 48dp and native text; use available height for the mascot after measuring content, adapt title/spacing and use compact tokens when needed. Keep the two-column tablet composition. Do not add new product controls or change navigation/gameplay.

## Why

The previous fixed 240dp mascot plus fixed typography, panels and gaps forced scrolling. A weighted illustration and bounded content layout can respond to the actual safe drawing area. Compact presentation avoids sacrificing action reachability.

## Consequences

Supersedes T-004/P-004's permission to stack/scroll Home and DD-007's implied fallback only; other decisions remain unchanged. Verify actual component bounds and minimum action size at small/reference phone and tablet content areas, including font scale 1.5. Preserve accessible labels and score association. Extremely small windows or font scales beyond tested coverage still require separate validation; do not claim universal fit from finite configurations.
