package com.acp.snakemath.app.navigation

import kotlinx.serialization.Serializable

@Serializable data object Home
@Serializable data object Modes
@Serializable data object Settings
@Serializable data class Game(val mode: String, val difficulty: String? = null)
