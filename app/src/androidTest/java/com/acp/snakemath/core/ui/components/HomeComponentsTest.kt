package com.acp.snakemath.core.ui.components

import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import com.acp.snakemath.app.MainActivity
import com.acp.snakemath.core.ui.theme.SnakeMathTheme
import com.acp.snakemath.feature.home.HomeScreen
import com.acp.snakemath.feature.home.HomeUiState
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeComponentsTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun settingsHasMinimumWidthAndVisibleCreamIcon() {
        rule.activity.setContent { SnakeMathTheme { HomeScreen(HomeUiState(), {}, {}) } }
        val button = rule.onNodeWithText("Settings")
        button.assertWidthIsAtLeast(136.dp).assertHeightIsAtLeast(48.dp)
        val gear = rule.onNodeWithTag("settings_gear", useUnmergedTree = true)
        gear.assertWidthIsEqualTo(26.dp).assertHeightIsEqualTo(26.dp)
        val pixels = gear.captureToImage().toPixelMap()
        assertTrue("Settings must draw its cream gear", (0 until pixels.width).any { x ->
            (0 until pixels.height).any { y -> pixels[x, y].run { red > .8f && green > .8f && blue > .5f } }
        })
        assertTrue("Gear must retain a dark central opening", pixels[pixels.width / 2, pixels.height / 2].red < .3f)
    }

    @Test fun scorePanelKeepsTrophyBesideBothModeValues() {
        rule.activity.setContent { SnakeMathTheme { HomeScreen(HomeUiState(120, 340), {}, {}) } }
        val trophy = rule.onNodeWithTag("best_scores_trophy", useUnmergedTree = true)
        trophy.assertWidthIsEqualTo(48.dp).assertHeightIsEqualTo(48.dp)
        val pixels = trophy.captureToImage().toPixelMap()
        assertTrue("Panel must draw a gold trophy", (0 until pixels.width).any { x ->
            (0 until pixels.height).any { y -> pixels[x, y].run { red > .8f && green > .5f && blue < .5f } }
        })
        rule.onNodeWithContentDescription("Classic best, 120").assertExists()
        rule.onNodeWithContentDescription("MDAS best, 340").assertExists()
    }

    @Test fun playHasVisibleTopGloss() {
        rule.activity.setContent { SnakeMathTheme { PrimaryButton("PLAY", {}) } }
        val pixels = rule.onNodeWithText("PLAY").captureToImage().toPixelMap()
        val highlight = pixels[pixels.width / 2, (pixels.height * .1f).toInt()]
        assertTrue("Top-third white gloss must brighten the lime fill", highlight.blue > .35f && highlight.green > .9f)
    }
}
