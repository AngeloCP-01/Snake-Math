package com.acp.snakemath.app.navigation

import org.junit.Assert.*
import org.junit.Test

class ShellRestorationPolicyTest {
    @Test fun sameNonemptyProcessRestores() { assertTrue(shouldRestoreShell("process-a", "process-a")) }
    @Test fun newOrMissingProcessStartsFresh() {
        assertFalse(shouldRestoreShell(null, "process-a"))
        assertFalse(shouldRestoreShell("", "process-a"))
        assertFalse(shouldRestoreShell("process-a", "process-b"))
        assertFalse(shouldRestoreShell("", ""))
    }
}
