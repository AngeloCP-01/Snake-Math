# ISS-004 Home fit checks can accept partially clipped content

**Found by:** Codex standards and spec reviewers · **Date:** 2026-11-22 · **Status:** fixed in working tree, verified 2026-11-24; uncommitted

## Symptom

The five bounded Home layout tests can report success when text is partially clipped. Their green result does not establish DD-008's complete visibility requirement. No actual production clipping defect is asserted from this finding; the inspected native screenshot fits at its recorded configuration.

## Cause

`HomeScreenTest.kt` compares `fetchSemanticsNode().boundsInRoot` with the viewport even though those bounds already have clipping applied. `assertIsDisplayed()` accepts partial visibility. The title/subtitle receive visibility checks rather than complete bounds checks, and mascot validation requires only positive visible dimensions. The suite does not check text-layout overflow or sibling overlap.

## How to recognise it again

Inspect `HomeScreenTest.assertFits`, particularly the containment assertions and checks for the heading/mascot. A child extending beyond an ancestor can have apparently contained clipped bounds. Context7's official Compose reference confirmed the distinction between clipped and unclipped bounds during review.

## Fix or workaround

Fit checks now compare unclipped title/subtitle/mascot/panel/action bounds with the safe viewport, inspect actual-allocation text overflow/truncation/ellipsis, and check sibling/column separation. Permanent negative fixtures reject a clipped child, internally overflowing text and overlapping siblings. The clipped-child fixture also demonstrates that the former partial-visibility/clipped-bounds assertions pass incorrectly. Six bounded layouts and the three negative fixtures passed in the final [25-test API 34 suite](../verification/T-004/revisions-final-tests.xml).

Compose Foundation 1.9.5 String `BasicText` semantics can reconstruct a paragraph using the parent's maximum width rather than the actual text allocation. The focused [diagnostic](../verification/T-004/revisions-fit-diagnostic.xml) showed PLAY's 86.66797px line inside an 87px node but a 228px semantics paragraph. The helper therefore remeasures matching real text/fonts/style/wrapping at the actual allocation, with one native pixel of rounding slack, retaining original and remeasured truncation/ellipsis checks. This prevents a framework false positive without skipping genuine clipping. See [correction rationale and limits](../verification/T-004/review-fixes.md). Finite bounded layouts do not establish universal fit, hardware orientation or broader device coverage.

## Related

- [DD-008](../decisions/DD-008-home-fits-without-scrolling.md), [T-004](../requirements/T-004-theme-navigation-home.md).
- [Review](../verification/T-004/review.md), [worklog](../worklog/T-004-2026-11-22.md).
- [Compose SemanticsNode bounds](https://developer.android.com/reference/kotlin/androidx/compose/ui/semantics/SemanticsNode), [unclipped bounds API](https://developer.android.com/reference/kotlin/androidx/compose/ui/test/package-summary#getUnclippedBoundsInRoot(androidx.compose.ui.test.SemanticsNodeInteraction)).
