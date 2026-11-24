package com.acp.snakemath.core.ui.components

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acp.snakemath.app.MainActivity
import com.acp.snakemath.core.ui.theme.SnakeMathTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeLogoTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun titleRowsHaveCompactBaselineSpacing() {
        rule.activity.setContent {
            SnakeMathTheme { Box(Modifier.padding(16.dp)) { HomeLogo() } }
        }
        // Measure actual baselines, whether the title uses separate or shared text nodes.
        // Reintroducing the font's full-height text boxes must fail this assertion.
        val gap = baseline("& MATH") - baseline("SNAKE")
        val fontSize = with(rule.density) { 64.sp.toPx() }
        assertTrue("Title rows must stay ordered", gap > 0)
        assertTrue("Title baselines are too far apart: $gap px", gap <= fontSize * .85f)
        rule.onNodeWithContentDescription("Snake & Math").assertIsDisplayed()
    }

    private fun baseline(text: String): Float {
        val node = rule.onAllNodes(hasText(text, substring = true), useUnmergedTree = true)[0]
        val layouts = mutableListOf<TextLayoutResult>()
        node.performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
        val layout = layouts.single()
        val line = layout.getLineForOffset(layout.layoutInput.text.text.indexOf(text))
        return node.fetchSemanticsNode().boundsInRoot.top + layout.getLineBaseline(line)
    }
}
