package com.acp.snakemath.app.navigation

import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.navigation.toRoute
import com.acp.snakemath.app.MainActivity
import com.acp.snakemath.core.ui.theme.SnakeMathTheme
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class ModeEntryNavigationTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private fun host(): TestNavHostController {
        lateinit var controller: TestNavHostController
        rule.activity.setContent {
            val context = LocalContext.current
            controller = remember { TestNavHostController(context).apply { navigatorProvider.addNavigator(ComposeNavigator()) } }
            SnakeMathTheme { SnakeMathNavHost(controller) }
        }
        rule.waitForIdle()
        rule.onNodeWithText("PLAY").performClick()
        return controller
    }
    private fun click(label: String) { rule.onNodeWithText(label).performScrollTo().performClick() }
    private fun action(label: String): () -> Boolean = rule.onNodeWithText(label).performScrollTo()
        .fetchSemanticsNode().config[SemanticsActions.OnClick].action!!

    @Test fun classicAndEveryMdasLevelCarryExactConfiguration() {
        val controller = host()
        click("PLAY CLASSIC")
        rule.onNodeWithText("Gameplay is not available yet.").assertIsDisplayed()
        rule.runOnIdle { assertEquals(Game("CLASSIC"), controller.currentBackStackEntry!!.toRoute<Game>()) }
        click("Back to modes")
        for ((label, value) in listOf("Easy" to "EASY", "Medium" to "MEDIUM", "Hard" to "HARD")) {
            click(label)
            rule.runOnIdle { assertTrue(controller.currentDestination!!.hasRoute<Modes>()) }
            click("PLAY MDAS")
            rule.runOnIdle { assertEquals(Game("MDAS", value), controller.currentBackStackEntry!!.toRoute<Game>()) }
            click("Back to modes")
            rule.onNodeWithText(label).assertIsSelected()
        }
    }

    @Test fun sameTurnSelectionAndMixedStartsUseFirstActionAndLatestLevel() {
        val controller = host()
        val hard = action("Hard")
        val start = action("PLAY MDAS")
        val classic = action("PLAY CLASSIC")
        rule.runOnIdle { hard(); start(); classic(); start() }
        rule.runOnIdle {
            assertEquals(Game("MDAS", "HARD"), controller.currentBackStackEntry!!.toRoute<Game>())
            assertEquals(1, controller.currentBackStack.value.count { it.destination.hasRoute<Game>() })
        }
    }

    @Test fun classicReturnRetainsHardAndHomeDiscardsIt() {
        host()
        click("Hard"); click("PLAY CLASSIC"); click("Back to modes")
        rule.onNodeWithText("Hard").assertIsSelected()
        click("PLAY MDAS"); click("Home")
        rule.onNodeWithText("PLAY").performClick()
        rule.onNodeWithText("Easy").assertIsSelected()
        click("Hard"); click("Back to Home")
        rule.onNodeWithText("PLAY").performClick()
        rule.onNodeWithText("Easy").assertIsSelected()
    }

    @Test fun departedEntryCallbacksCannotAffectNewerScreens() {
        val controller = host()
        val oldHard = action("Hard")
        val oldStart = action("PLAY CLASSIC")
        val oldBack = action("Back to Home")
        click("PLAY MDAS")
        val oldReturn = action("Back to modes")
        val oldHome = action("Home")
        click("Home")
        rule.onNodeWithText("Settings").performClick()
        rule.runOnIdle { oldHard(); oldStart(); oldBack(); oldReturn(); oldHome() }
        rule.runOnIdle { assertTrue(controller.currentDestination!!.hasRoute<Settings>()) }
    }

    @Test fun invalidTypedRoutesReturnToOneModesEntry() {
        val controller = host()
        for (route in listOf(Game("UNKNOWN"), Game("CLASSIC", "EASY"), Game("MDAS"), Game("MDAS", "UNKNOWN"))) {
            rule.runOnIdle { controller.navigate(route) }
            rule.waitForIdle()
            rule.runOnIdle {
                assertTrue(controller.currentDestination!!.hasRoute<Modes>())
                assertEquals(1, controller.currentBackStack.value.count { it.destination.hasRoute<Modes>() })
            }
        }
    }

    @Test fun systemBackTraversesGameModesHome() {
        host(); click("PLAY CLASSIC")
        rule.runOnUiThread { rule.activity.onBackPressedDispatcher.onBackPressed() }
        rule.onNodeWithText("Easy").assertIsSelected()
        rule.runOnUiThread { rule.activity.onBackPressedDispatcher.onBackPressed() }
        rule.onNodeWithText("PLAY").assertIsDisplayed()
    }

    @Test fun repeatedMixedReturnsKeepFirstTargetAndOriginalHome() {
        val controller = host()
        val staleHard = action("Hard")
        val start = action("PLAY CLASSIC")
        rule.runOnIdle { start(); staleHard() }
        val back = action("Back to modes")
        val home = action("Home")
        rule.runOnIdle { back(); home(); back() }
        rule.onNodeWithText("Easy").assertIsSelected()
        click("PLAY MDAS")
        val backAgain = action("Back to modes")
        val homeAgain = action("Home")
        rule.runOnIdle { homeAgain(); backAgain(); homeAgain() }
        rule.runOnIdle {
            assertTrue(controller.currentDestination!!.hasRoute<Home>())
            assertEquals(1, controller.currentBackStack.value.count { it.destination.hasRoute<Home>() })
            assertEquals(0, controller.currentBackStack.value.count { it.destination.hasRoute<Modes>() || it.destination.hasRoute<Game>() })
        }
    }
}
