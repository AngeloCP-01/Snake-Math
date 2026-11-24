package com.acp.snakemath.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.acp.snakemath.app.navigation.SnakeMathNavHost
import com.acp.snakemath.core.ui.theme.SnakeMathTheme

@Composable
fun SnakeMathApp() {
    SnakeMathTheme { SnakeMathNavHost(rememberNavController()) }
}
