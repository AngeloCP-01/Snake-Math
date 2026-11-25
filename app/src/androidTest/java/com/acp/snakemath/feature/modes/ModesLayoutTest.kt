package com.acp.snakemath.feature.modes

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.acp.snakemath.app.MainActivity
import com.acp.snakemath.core.ui.theme.SnakeMathTheme
import com.acp.snakemath.core.model.GameEntryConfig
import com.acp.snakemath.core.model.GameMode
import com.acp.snakemath.core.model.MdasDifficulty
import com.acp.snakemath.feature.game.GameEntryScreen
import com.acp.snakemath.feature.home.assertFullyContained
import com.acp.snakemath.feature.home.assertTextFits
import com.acp.snakemath.feature.home.assertSeparatedAbove
import org.junit.Rule
import org.junit.Test

class ModesLayoutTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    @Test fun smallPhone() = fits(320, 568, 1f)
    @Test fun smallPhoneLargeText() = fits(320, 568, 1.5f)
    @Test fun phone() = fits(390, 844, 1f)
    @Test fun phoneLargeText() = fits(390, 844, 1.5f)
    @Test fun tablet() = fits(1024, 768, 1f)
    @Test fun tabletLargeText() = fits(1024, 768, 1.5f)

    @Test fun placeholdersFitAllSizeAndFontProfiles() {
        for ((width, height) in listOf(320 to 568, 390 to 844, 1024 to 768)) {
            for (scale in listOf(1f, 1.5f)) {
                for (config in listOf(GameEntryConfig(GameMode.CLASSIC, null), GameEntryConfig(GameMode.MDAS, MdasDifficulty.HARD))) {
                    rule.activity.setContent {
                        CompositionLocalProvider(LocalDensity provides Density(1f, scale)) {
                            Box(Modifier.requiredSize(width.dp, (height - 64).dp).testTag("game_viewport")
                                .consumeWindowInsets(WindowInsets.safeDrawing)) {
                                SnakeMathTheme { GameEntryScreen(config, {}, {}) }
                            }
                        }
                    }
                    val viewport = rule.onNodeWithTag("game_viewport").getUnclippedBoundsInRoot()
                    for (label in listOf("Gameplay is not available yet.", "Back to modes", "Home")) {
                        val node = rule.onNodeWithText(label).performScrollTo()
                        assertFullyContained(node, viewport)
                        if (label != "Gameplay is not available yet.") {
                            node.assertWidthIsAtLeast(48.dp).assertHeightIsAtLeast(48.dp)
                        }
                        assertTextFits(rule.onNodeWithText(label, useUnmergedTree = true))
                    }
                }
            }
        }
    }

    private fun fits(width: Int, height: Int, scale: Float) {
        rule.activity.setContent {
            CompositionLocalProvider(LocalDensity provides Density(1f, scale)) {
                // Subtract the same 64dp safe-area allowance once, as T-004 does.
                Box(Modifier.requiredSize(width.dp, (height - 64).dp).testTag("modes_viewport")
                    .consumeWindowInsets(WindowInsets.safeDrawing)) {
                    SnakeMathTheme { ModesScreen(ModesUiState(120, 340), {}, {}, {}, {}) }
                }
            }
        }
        val viewport = rule.onNodeWithTag("modes_viewport").getUnclippedBoundsInRoot()
        for (label in listOf("PLAY CLASSIC", "Easy", "Medium", "Hard", "PLAY MDAS", "Back to Home")) {
            val node = rule.onNodeWithText(label).performScrollTo()
            assertFullyContained(node, viewport)
            node.assertWidthIsAtLeast(48.dp).assertHeightIsAtLeast(48.dp)
        }
        val texts = rule.onAllNodes(hasText("", substring = true), useUnmergedTree = true)
        repeat(texts.fetchSemanticsNodes().size) { assertTextFits(texts[it]) }
        assertSeparatedAbove(rule.onNodeWithText("Easy").getUnclippedBoundsInRoot(),
            rule.onNodeWithText("Medium").getUnclippedBoundsInRoot())
        assertSeparatedAbove(rule.onNodeWithText("Medium").getUnclippedBoundsInRoot(),
            rule.onNodeWithText("Hard").getUnclippedBoundsInRoot())
        assertSeparatedAbove(rule.onNodeWithText("Hard").getUnclippedBoundsInRoot(),
            rule.onNodeWithText("PLAY MDAS").getUnclippedBoundsInRoot())
    }
}
