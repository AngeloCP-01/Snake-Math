package com.acp.snakemath.app.navigation

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.acp.snakemath.app.MainActivity
import org.junit.Rule
import org.junit.Test

class NavigationRecreationTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private fun click(label: String) { rule.onNodeWithText(label).performScrollTo().performClick() }

    private fun recreateFrom(start: String?) {
        rule.onNodeWithText("PLAY").performClick(); click("Hard")
        val staleBack = rule.onNodeWithText("Back to Home").performScrollTo().fetchSemanticsNode()
            .config[SemanticsActions.OnClick].action!!
        if (start != null) click(start)
        rule.activityRule.scenario.recreate()
        rule.waitForIdle()
        rule.runOnIdle { staleBack() }
        if (start != null) {
            rule.onNodeWithText("Gameplay is not available yet.").assertIsDisplayed()
            rule.onNodeWithText(if (start == "PLAY CLASSIC") "CLASSIC APPLE" else "MDAS LEARNING").assertExists()
            if (start == "PLAY MDAS") rule.onNodeWithText("Hard").assertExists()
            click("Back to modes")
        }
        rule.onNodeWithText("Hard").assertIsSelected()
    }
    @Test fun modesHardSurvivesRecreation() = recreateFrom(null)
    @Test fun classicAndUnderlyingHardSurviveRecreation() = recreateFrom("PLAY CLASSIC")
    @Test fun mdasHardAndUnderlyingSelectionSurviveRecreation() = recreateFrom("PLAY MDAS")
    @Test fun settingsSurvivesRecreation() {
        rule.onNodeWithText("Settings").performClick()
        rule.activityRule.scenario.recreate()
        rule.onNodeWithText("Settings will be available in a later update.").assertIsDisplayed()
        click("Back to Home")
        rule.onNodeWithText("PLAY").assertIsDisplayed()
    }
}
