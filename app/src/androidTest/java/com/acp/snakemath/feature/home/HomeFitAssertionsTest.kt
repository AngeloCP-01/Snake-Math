package com.acp.snakemath.feature.home

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acp.snakemath.app.MainActivity
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeFitAssertionsTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun containmentRejectsChildHiddenByClipping() {
        rule.activity.setContent {
            Box(Modifier.size(100.dp).clipToBounds().testTag("viewport")) {
                Box(Modifier.offset(y = 80.dp).size(50.dp).testTag("overflow"))
            }
        }
        val child = rule.onNodeWithTag("overflow")
        child.assertIsDisplayed() // The former partial-visibility check passes.
        val clipped = child.fetchSemanticsNode().boundsInRoot
        val clippedParent = rule.onNodeWithTag("viewport").fetchSemanticsNode().boundsInRoot
        assertTrue(clipped.bottom <= clippedParent.bottom) // The former bounds check also passes.
        rejects { assertFullyContained(child, rule.onNodeWithTag("viewport").getUnclippedBoundsInRoot()) }
    }

    @Test fun textCheckRejectsInternalOverflow() {
        rule.activity.setContent {
            BasicText("A long label that cannot fit", Modifier.size(40.dp, 20.dp).testTag("text"),
                style = TextStyle(fontSize = 20.sp), softWrap = false, maxLines = 1)
        }
        rejects { assertTextFits(rule.onNodeWithTag("text")) }
    }

    @Test fun separationRejectsOverlappingSiblings() {
        rule.activity.setContent {
            Box(Modifier.size(100.dp)) {
                Box(Modifier.size(60.dp, 40.dp).testTag("first"))
                Box(Modifier.offset(y = 20.dp).size(60.dp, 40.dp).testTag("second"))
            }
        }
        rejects { assertSeparatedAbove(rule.onNodeWithTag("first").getUnclippedBoundsInRoot(),
            rule.onNodeWithTag("second").getUnclippedBoundsInRoot()) }
    }

    private fun rejects(assertion: () -> Unit) {
        val failure = runCatching(assertion).exceptionOrNull()
        assertTrue("Fixture must be rejected by the assertion", failure is AssertionError)
    }
}
