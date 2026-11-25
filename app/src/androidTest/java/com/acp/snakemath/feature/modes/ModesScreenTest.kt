package com.acp.snakemath.feature.modes

import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.acp.snakemath.app.MainActivity
import com.acp.snakemath.core.model.MdasDifficulty
import com.acp.snakemath.core.ui.theme.SnakeMathTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ModesScreenTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun displaysIndependentScoresAndModeActions() {
        rule.activity.setContent { SnakeMathTheme { ModesScreen(ModesUiState(120, 340), {}, {}, {}, {}) } }
        rule.onNodeWithText("CLASSIC APPLE").assertExists()
        rule.onNodeWithText("MDAS LEARNING").assertExists()
        rule.onNodeWithContentDescription("Classic best, 120").assertExists()
        rule.onNodeWithContentDescription("MDAS best, 340").assertExists()
        rule.onNodeWithText("PLAY CLASSIC").assertHasClickAction()
        rule.onNodeWithText("PLAY MDAS").assertHasClickAction()
    }

    @Test fun unavailableScoresAndSelectionDoNotStartGame() {
        val selections = mutableListOf<MdasDifficulty>()
        var starts = 0
        rule.activity.setContent {
            var level by remember { mutableStateOf(MdasDifficulty.EASY) }
            SnakeMathTheme { ModesScreen(ModesUiState(selectedDifficulty = level),
                { level = it; selections += it }, { starts++ }, { starts++ }, {}) }
        }
        rule.onNodeWithContentDescription("Classic best, unavailable").assertExists()
        rule.onNodeWithContentDescription("MDAS best, unavailable").assertExists()
        rule.onNodeWithText("Easy").assertIsSelected()
        rule.onNodeWithText("Selected").assertExists()
        rule.onNodeWithText("Hard").performScrollTo().performClick()
        rule.onNodeWithText("Hard").assertIsSelected()
        rule.onNodeWithText("Easy").assertIsNotSelected()
        rule.onNodeWithText("Medium").assertIsNotSelected()
        rule.runOnIdle {
            assertEquals(listOf(MdasDifficulty.HARD), selections)
            assertEquals(0, starts)
        }
    }
}
