package com.acp.snakemath.core.model

data class GameEntryConfig(val mode: GameMode, val difficulty: MdasDifficulty?) {
    init {
        require((mode == GameMode.MDAS) == (difficulty != null)) {
            "Only MDAS requires a difficulty"
        }
    }
}

fun parseGameEntry(mode: String, difficulty: String?): GameEntryConfig? {
    val parsedMode = GameMode.entries.firstOrNull { it.name == mode } ?: return null
    val parsedDifficulty = difficulty?.let { value ->
        MdasDifficulty.entries.firstOrNull { it.name == value } ?: return null
    }
    if ((parsedMode == GameMode.MDAS) != (parsedDifficulty != null)) return null
    return GameEntryConfig(parsedMode, parsedDifficulty)
}
