package com.acp.snakemath.feature.home

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

internal fun assertFullyContained(node: SemanticsNodeInteraction, viewport: DpRect): DpRect {
    val bounds = node.getUnclippedBoundsInRoot()
    val tolerance = .5.dp // Pixel rounding at the injected layout density.
    assertTrue("Empty content bounds: $bounds", bounds.right > bounds.left && bounds.bottom > bounds.top)
    assertTrue("Unclipped $bounds must fit in $viewport",
        bounds.left >= viewport.left - tolerance && bounds.top >= viewport.top - tolerance &&
            bounds.right <= viewport.right + tolerance && bounds.bottom <= viewport.bottom + tolerance)
    return bounds
}

internal fun assertTextFits(node: SemanticsNodeInteraction) {
    val layouts = mutableListOf<TextLayoutResult>()
    node.performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
    assertTrue("Expected real text layout results", layouts.isNotEmpty())
    layouts.forEach { layout ->
        // Compose 1.9.5 String BasicText semantics reconstruct the paragraph using
        // the parent's max width while retaining the narrower actual layout size.
        // Remeasure the real text/font at its actual allocation, with 1 physical px
        // of rounding slack, rather than mistaking unused paragraph width for ink.
        val input = layout.layoutInput
        val measured = TextMeasurer(input.fontFamilyResolver, input.density, input.layoutDirection, cacheSize = 0)
            .measure(input.text, style = input.style, placeholders = input.placeholders,
                maxLines = input.maxLines, softWrap = input.softWrap, overflow = input.overflow,
                constraints = Constraints(maxWidth = layout.size.width + 1, maxHeight = layout.size.height + 1))
        assertFalse("Text overflows its actual allocation: ${input.text}", measured.hasVisualOverflow)
        assertFalse("Text was truncated: ${input.text}", layout.multiParagraph.didExceedMaxLines || measured.multiParagraph.didExceedMaxLines)
        assertFalse("Text is ellipsized: ${layout.layoutInput.text}",
            (0 until layout.lineCount).any(layout::isLineEllipsized) ||
                (0 until measured.lineCount).any(measured::isLineEllipsized))
    }
}

internal fun assertSeparatedAbove(first: DpRect, second: DpRect) {
    assertTrue("Sibling content overlaps: $first / $second", first.bottom <= second.top + .5.dp)
}
