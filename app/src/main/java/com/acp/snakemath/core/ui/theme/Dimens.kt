package com.acp.snakemath.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class SnakeMathDimens(
    val gutter: Dp = 16.dp,
    val gap: Dp = 16.dp,
    val smallGap: Dp = 8.dp,
    val touchMin: Dp = 48.dp,
    val buttonWidth: Dp = 296.dp,
    val buttonHeight: Dp = 64.dp,
    val panelWidth: Dp = 270.dp,
    val panelRadius: Dp = 16.dp,
    val border: Dp = 2.dp,
)
