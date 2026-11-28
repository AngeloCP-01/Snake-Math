package com.acp.snakemath.feature.modes

import com.acp.snakemath.core.model.MdasDifficulty

data class ModesUiState(
    val classicBest: Int? = null,
    val mdasBest: Int? = null,
    val selectedDifficulty: MdasDifficulty = MdasDifficulty.EASY,
)
