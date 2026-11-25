package com.acp.snakemath.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.acp.snakemath.R
import com.acp.snakemath.core.ui.components.FoundationDestinationScreen
import com.acp.snakemath.feature.home.HomeScreen
import com.acp.snakemath.feature.home.HomeUiState
import com.acp.snakemath.feature.modes.ModesScreen
import com.acp.snakemath.feature.modes.ModesUiState
import com.acp.snakemath.feature.game.GameEntryScreen
import com.acp.snakemath.core.model.MdasDifficulty
import com.acp.snakemath.core.model.parseGameEntry

@Composable
fun SnakeMathNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = Home, modifier = modifier) {
        composable<Home> { entry ->
            val isCurrent = currentEntryGuard(navController, entry)
            HomeScreen(HomeUiState(),
                onPlay = {
                    if (isCurrent()) {
                        navController.navigate(Modes) { launchSingleTop = true }
                    }
                },
                onSettings = {
                    if (isCurrent()) {
                        navController.navigate(Settings) { launchSingleTop = true }
                    }
                })
        }
        composable<Modes> { entry ->
            val isCurrent = currentEntryGuard(navController, entry)
            var selected by rememberSaveable { mutableStateOf(MdasDifficulty.EASY) }
            ModesScreen(ModesUiState(selectedDifficulty = selected),
                onDifficultySelected = { if (isCurrent()) selected = it },
                onPlayClassic = { if (isCurrent()) navController.navigate(Game("CLASSIC")) },
                onPlayMdas = { if (isCurrent()) navController.navigate(Game("MDAS", selected.name)) },
                onBackHome = {
                    if (isCurrent()) {
                        navController.popBackStack<Home>(inclusive = false)
                    }
                })
        }
        composable<Game> { entry ->
            val isCurrent = currentEntryGuard(navController, entry)
            val route = entry.toRoute<Game>()
            val config = parseGameEntry(route.mode, route.difficulty)
            if (config == null) {
                LaunchedEffect(entry) {
                    if (isCurrent()) {
                        if (!navController.popBackStack<Modes>(inclusive = false)) {
                            navController.navigate(Modes) { popUpTo<Home> { inclusive = false }; launchSingleTop = true }
                        }
                    }
                }
            } else {
                GameEntryScreen(config,
                    onBackToModes = { if (isCurrent()) navController.popBackStack<Modes>(inclusive = false) },
                    onHome = { if (isCurrent()) navController.popBackStack<Home>(inclusive = false) })
            }
        }
        composable<Settings> { entry ->
            val isCurrent = currentEntryGuard(navController, entry)
            FoundationDestinationScreen(stringResource(R.string.settings), stringResource(R.string.settings_unavailable),
                onBack = {
                    if (isCurrent()) {
                        navController.popBackStack<Home>(inclusive = false)
                    }
                })
        }
    }
}

/** Disposed compositions cannot issue actions, even if their entry is restored later. */
@Composable
private fun currentEntryGuard(controller: NavHostController, entry: NavBackStackEntry): () -> Boolean {
    val active = remember(entry) { booleanArrayOf(true) }
    DisposableEffect(entry) { onDispose { active[0] = false } }
    return { active[0] && controller.currentBackStackEntry === entry }
}
