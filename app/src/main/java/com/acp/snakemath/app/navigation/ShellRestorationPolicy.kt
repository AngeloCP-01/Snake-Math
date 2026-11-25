package com.acp.snakemath.app.navigation

import java.util.UUID

internal val shellProcessId: String = UUID.randomUUID().toString()

fun shouldRestoreShell(savedProcessId: String?, currentProcessId: String): Boolean =
    !savedProcessId.isNullOrEmpty() && savedProcessId == currentProcessId
