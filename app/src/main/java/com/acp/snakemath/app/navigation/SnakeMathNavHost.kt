package com.acp.snakemath.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.acp.snakemath.R
import com.acp.snakemath.core.ui.components.FoundationDestinationScreen
import com.acp.snakemath.feature.home.HomeScreen
import com.acp.snakemath.feature.home.HomeUiState

@Composable
fun SnakeMathNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = Home, modifier = modifier) {
        composable<Home> { entry ->
            HomeScreen(HomeUiState(),
                onPlay = {
                    if (navController.currentBackStackEntry == entry) {
                        navController.navigate(Modes) { launchSingleTop = true }
                    }
                },
                onSettings = {
                    if (navController.currentBackStackEntry == entry) {
                        navController.navigate(Settings) { launchSingleTop = true }
                    }
                })
        }
        composable<Modes> { entry ->
            FoundationDestinationScreen(stringResource(R.string.modes), stringResource(R.string.modes_unavailable),
                onBack = {
                    if (navController.currentBackStackEntry == entry) {
                        navController.popBackStack<Home>(inclusive = false)
                    }
                })
        }
        composable<Settings> { entry ->
            FoundationDestinationScreen(stringResource(R.string.settings), stringResource(R.string.settings_unavailable),
                onBack = {
                    if (navController.currentBackStackEntry == entry) {
                        navController.popBackStack<Home>(inclusive = false)
                    }
                })
        }
    }
}
