package com.acp.snakemath.app.navigation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.SemanticsActions
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.acp.snakemath.core.ui.theme.SnakeMathTheme
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import com.acp.snakemath.app.MainActivity
import org.junit.Rule
import org.junit.Test

class SnakeMathNavigationTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun repeatedModesBackRetainsHome() = assertRepeatedBack("PLAY")
    @Test fun repeatedSettingsBackRetainsHome() = assertRepeatedBack("Settings")
    @Test fun staleSettingsActionCannotStackAfterModes() = assertMixedActions("PLAY", "Settings")
    @Test fun stalePlayActionCannotStackAfterSettings() = assertMixedActions("Settings", "PLAY")

    private fun controlledHost(): TestNavHostController {
        lateinit var controller: TestNavHostController
        rule.activity.setContent {
            val context = LocalContext.current
            controller = remember { TestNavHostController(context).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            } }
            SnakeMathTheme { SnakeMathNavHost(controller) }
        }
        rule.waitForIdle()
        return controller
    }

    private fun clickAction(label: String): () -> Boolean {
        val node = rule.onNodeWithText(label)
        if (label == "Back to Home") node.performScrollTo()
        return node.fetchSemanticsNode().config[SemanticsActions.OnClick].action!!
    }

    private fun assertRepeatedBack(label: String) {
        val controller = controlledHost()
        rule.onNodeWithText(label).performClick()
        val back = clickAction("Back to Home")
        rule.runOnIdle { repeat(3) { back() } }
        rule.runOnIdle { assertTrue("Repeated Back must retain Home", controller.currentDestination?.hasRoute<Home>() == true) }
        rule.onNodeWithText("PLAY").assertIsDisplayed()
    }

    private fun assertMixedActions(first: String, stale: String) {
        val controller = controlledHost()
        val firstAction = clickAction(first)
        val staleAction = clickAction(stale)
        rule.runOnIdle { firstAction(); staleAction(); firstAction() }
        rule.runOnIdle {
            assertTrue("First navigation must win", if (first == "PLAY") controller.currentDestination?.hasRoute<Modes>() == true
                else controller.currentDestination?.hasRoute<Settings>() == true)
            assertEquals(1, controller.currentBackStack.value.count { it.destination.hasRoute<Modes>() || it.destination.hasRoute<Settings>() })
        }
        rule.onNodeWithText("Back to Home").performScrollTo().performClick()
        rule.runOnIdle { assertTrue(controller.currentDestination?.hasRoute<Home>() == true) }
    }

    @Test fun staleModesBackCannotPopSettings() {
        val controller = controlledHost()
        rule.onNodeWithText("PLAY").performClick()
        val staleBack = clickAction("Back to Home")
        rule.onNodeWithText("Back to Home").performScrollTo().performClick()
        rule.onNodeWithText("Settings").performClick()
        rule.runOnIdle { staleBack() }
        rule.runOnIdle { assertTrue(controller.currentDestination?.hasRoute<Settings>() == true) }
    }

    @Test fun homeIsStartAndRapidActionsKeepOneTarget() {
        lateinit var controller: TestNavHostController
        rule.activity.setContent {
            val context = LocalContext.current
            controller = remember { TestNavHostController(context).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            } }
            SnakeMathTheme { SnakeMathNavHost(controller) }
        }
        rule.runOnIdle { assertTrue(controller.currentDestination!!.hasRoute<Home>()) }
        for (label in listOf("PLAY", "Settings")) {
            rule.onNodeWithText(label)
            val click = rule.onNodeWithText(label).fetchSemanticsNode().config[SemanticsActions.OnClick].action!!
            rule.runOnIdle { repeat(3) { click() } }
            rule.runOnIdle {
                assertTrue(if (label == "PLAY") controller.currentDestination!!.hasRoute<Modes>()
                    else controller.currentDestination!!.hasRoute<Settings>())
                assertEquals(1, controller.currentBackStack.value.count {
                    if (label == "PLAY") it.destination.hasRoute<Modes>() else it.destination.hasRoute<Settings>()
                })
            }
            rule.onNodeWithText("Back to Home").performScrollTo().performClick()
            rule.runOnIdle { assertTrue(controller.currentDestination!!.hasRoute<Home>()) }
        }
    }

    @Test fun playOpensModesAndBackReturnsHome() {
        rule.onNodeWithText("PLAY").performClick()
        rule.onNodeWithText("Modes").assertIsDisplayed()
        rule.onNodeWithText("Back to Home").performScrollTo().performClick()
        rule.onNodeWithText("PLAY").assertIsDisplayed()
    }

    @Test fun settingsOpensSettingsAndSystemBackReturnsHome() {
        rule.onNodeWithText("Settings").performClick()
        rule.onNodeWithText("Settings will be available in a later update.").assertIsDisplayed()
        rule.runOnUiThread { rule.activity.onBackPressedDispatcher.onBackPressed() }
        rule.onNodeWithText("PLAY").assertIsDisplayed()
    }
}
