# ISS-005 Home reference details remain incomplete

**Found by:** Codex standards and spec reviewers · **Date:** 2026-11-22 · **Status:** fixed in working tree, verified 2026-11-24; uncommitted

## Symptom

Native Home preserves the broad forest/mascot/action hierarchy but omits visible details required by the supplied design system. The current native screenshot shows a flat lime title, no Settings gear, no panel trophy and no PLAY gloss.

## Cause

The initial component implementation simplified the supplied styling. No documented decision approves those simplifications. The dual-score panel, no-scroll layout, tighter multiline title and component previews are authorized adaptations; they do not establish approval of the other missing details.

## How to recognise it again

- `HomeLogo.kt`: flat lime fill, 3dp outline/shadow treatment and no logo leaf clusters, against the documented title gradient, 6dp outline and 4dp extrusion.
- `PrimaryButton.kt`: no white top-third gloss; evenly distributed gradient stops and sharp triangle corners differ from the component specification.
- `PillButton.kt`: no 136dp minimum width or 26dp icon; Settings is text only.
- `BestScoresPanel.kt` / `Dimens.kt`: no trophy and 24dp panel radius instead of the specified 16dp.

These are confirmed source/reference differences. Final visual acceptance and remaining native device coverage are still open; the review did not redesign the user's components.

## Fix or workaround

Restored native gradient title fills/leaf clusters/6dp outline/4dp extrusion; PLAY's specified gradient stops, top-third gloss and rounded triangle; a minimum-136dp Settings pill with a 26dp cream gear; and the 48dp trophy, 16dp panel radius, shadow and inner darkening. Compact type remains scoped to constrained Home layouts. No-scroll Home, the tighter multiline title and both mode labels remain intact. No raster artwork was generated or changed.

The final [native capture](../verification/T-004/revisions-home-api34.png) was inspected against the supplied reference and authorized adaptations. The fresh read-only reviewer reported no material visual finding. The [25-test API 34 suite](../verification/T-004/revisions-final-tests.xml) includes native pixel checks for actual gloss/gear/trophy rendering and strengthened fit/score-binding tests. See [correction handoff](../verification/T-004/review-fixes.md) for results and remaining API 23/API 36/rotation/Studio limits. Dev's broader visual acceptance is not inferred from this engineering review.

## Related

- [Design system](../design-system/README.md), [primary reference](../assets/mockups/home-v3.png).
- [T-004](../requirements/T-004-theme-navigation-home.md), [DD-008](../decisions/DD-008-home-fits-without-scrolling.md).
- [Inspected native screenshot](../verification/T-004/home-logo-tight-api34.png), [review](../verification/T-004/review.md).
