package com.acp.snakemath.app.navigation

import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.acp.snakemath.app.MainActivity
import com.acp.snakemath.core.ui.theme.SnakeMathTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/** Temporary review reproducer; the review controller removes this source after execution. */
class NavigationReviewTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun repeatedBackToHomeMustKeepHomeVisible() {
        lateinit var controller: TestNavHostController
        rule.activity.setContent {
            val context = LocalContext.current
            controller = remember {
                TestNavHostController(context).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
            }
            SnakeMathTheme { SnakeMathNavHost(controller) }
        }
        rule.runOnIdle { controller.navigate(Modes) }
        val back = rule.onNodeWithText("Back to Home").fetchSemanticsNode()
            .config[SemanticsActions.OnClick].action!!
        rule.runOnIdle { repeat(2) { back() } }
        rule.runOnIdle {
            assertTrue("Repeated Back removed Home: ${controller.currentDestination}",
                controller.currentDestination?.hasRoute<Home>() == true)
        }
    }
}
