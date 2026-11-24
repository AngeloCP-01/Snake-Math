package com.acp.snakemath.core.ui.theme

import androidx.compose.runtime.*

private val LocalColors = staticCompositionLocalOf { SnakeMathColors() }
private val LocalTypography = staticCompositionLocalOf { SnakeMathTypography() }
private val LocalDimens = staticCompositionLocalOf { SnakeMathDimens() }

object SnakeMathTheme {
    val colors: SnakeMathColors @Composable get() = LocalColors.current
    val typography: SnakeMathTypography @Composable get() = LocalTypography.current
    val dimens: SnakeMathDimens @Composable get() = LocalDimens.current
}

@Composable
fun SnakeMathTheme(
    typography: SnakeMathTypography = SnakeMathTypography(),
    dimens: SnakeMathDimens = SnakeMathDimens(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides SnakeMathColors(),
        LocalTypography provides typography,
        LocalDimens provides dimens,
        content = content,
    )
}
