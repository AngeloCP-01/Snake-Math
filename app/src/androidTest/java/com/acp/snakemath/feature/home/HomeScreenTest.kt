package com.acp.snakemath.feature.home

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.semantics.SemanticsActions
import org.junit.Assert.assertTrue
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.acp.snakemath.app.MainActivity
import com.acp.snakemath.core.ui.theme.SnakeMathTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun homeFitsSmallPhoneWithoutScrolling() = assertFits(320, 504, 1f)
    @Test fun homeFitsReferencePhoneWithoutScrolling() = assertFits(390, 780, 1f)
    @Test fun homeFitsTabletWithoutScrolling() = assertFits(1024, 704, 1f)
    @Test fun homeFitsTabletWithLargeTextWithoutScrolling() = assertFits(1024, 704, 1.5f)
    @Test fun homeFitsSmallPhoneWithLargeTextWithoutScrolling() = assertFits(320, 504, 1.5f)
    @Test fun homeFitsReferencePhoneWithLargeTextWithoutScrolling() = assertFits(390, 780, 1.5f)

    private fun assertFits(width: Int, height: Int, fontScale: Float) {
        // Test the available content area after system insets at native layout density 1.
        // This lets a phone emulator measure tablet content without display overrides.
        rule.activity.setContent {
            CompositionLocalProvider(LocalDensity provides Density(1f, fontScale)) {
                Box(Modifier.requiredSize(width.dp, height.dp).testTag("viewport")
                    .consumeWindowInsets(WindowInsets.safeDrawing)) {
                    SnakeMathTheme { HomeScreen(HomeUiState(120, 340), {}, {}) }
                }
            }
        }
        val viewport = rule.onNodeWithTag("viewport").getUnclippedBoundsInRoot()
        listOf("PLAY", "Settings", "Classic best", "MDAS best", "120", "340").forEach { text ->
            val node = rule.onNodeWithText(text, useUnmergedTree = true)
            node.assertIsDisplayed()
            assertFullyContained(node, viewport)
            assertTextFits(node)
        }
        rule.onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollBy)).assertCountEquals(0)
        val title = assertFullyContained(rule.onNodeWithContentDescription("Snake & Math"), viewport)
        val subtitle = assertFullyContained(rule.onNodeWithText("A little game of growth"), viewport)
        val mascot = assertFullyContained(rule.onNodeWithTag("home_mascot", useUnmergedTree = true), viewport)
        val play = assertFullyContained(rule.onNodeWithText("PLAY"), viewport)
        val panel = assertFullyContained(rule.onNodeWithTag("best_scores_panel"), viewport)
        val settings = assertFullyContained(rule.onNodeWithText("Settings"), viewport)
        val textNodes = rule.onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsActions.GetTextLayoutResult), useUnmergedTree = true)
        repeat(textNodes.fetchSemanticsNodes().size) { index ->
            assertFullyContained(textNodes[index], viewport)
            assertTextFits(textNodes[index])
        }
        assertSeparatedAbove(title, subtitle)
        assertSeparatedAbove(subtitle, mascot)
        assertSeparatedAbove(play, panel)
        assertSeparatedAbove(panel, settings)
        if (width > height) {
            assertTrue("Tablet columns overlap", maxOf(title.right, subtitle.right, mascot.right) <=
                minOf(play.left, panel.left, settings.left))
        } else {
            assertSeparatedAbove(mascot, play)
        }
        for (text in listOf("PLAY", "Settings")) {
            rule.onNodeWithText(text).assertWidthIsAtLeast(48.dp).assertHeightIsAtLeast(48.dp)
        }
    }

    private fun show(state: HomeUiState, play: () -> Unit = {}, settings: () -> Unit = {}) {
        rule.activity.setContentForTest(state, play, settings)
    }

    @Test fun scoresRetainModeAssociation() {
        show(HomeUiState(120, 340))
        rule.onNodeWithContentDescription("Classic best, 120").assertExists()
        rule.onNodeWithContentDescription("MDAS best, 340").assertExists()
        rule.onNodeWithText("1280").assertDoesNotExist()
        rule.onNodeWithText("THEMES").assertDoesNotExist()
    }

    @Test fun missingScoresAreUnavailable() {
        show(HomeUiState())
        rule.onNodeWithContentDescription("Classic best, unavailable").assertExists()
        rule.onNodeWithContentDescription("MDAS best, unavailable").assertExists()
        rule.onAllNodesWithText("—", useUnmergedTree = true).assertCountEquals(2)
    }

    @Test fun actionsDispatchSeparateCallbacks() {
        var plays = 0
        var settings = 0
        show(HomeUiState(), { plays++ }, { settings++ })
        rule.onNodeWithText("PLAY").performClick()
        rule.runOnIdle { assertEquals(1, plays); assertEquals(0, settings) }
        rule.onNodeWithText("Settings").performClick()
        rule.runOnIdle { assertEquals(1, plays); assertEquals(1, settings) }
    }
}

private fun MainActivity.setContentForTest(state: HomeUiState, play: () -> Unit, settings: () -> Unit) {
    setContent { SnakeMathTheme { HomeScreen(state, play, settings) } }
}
