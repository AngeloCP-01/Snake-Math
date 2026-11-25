package com.acp.snakemath.core.model

import org.junit.Assert.*
import org.junit.Test

class GameEntryConfigTest {
    @Test fun classicHasNoDifficulty() {
        assertEquals(GameEntryConfig(GameMode.CLASSIC, null), parseGameEntry("CLASSIC", null))
    }

    @Test fun mdasCarriesEachExactLevel() {
        for (level in MdasDifficulty.entries) {
            assertEquals(GameEntryConfig(GameMode.MDAS, level), parseGameEntry("MDAS", level.name))
        }
    }

    @Test fun invalidRoutesAreRejectedWithoutDefaults() {
        for ((mode, difficulty) in listOf(
            "CLASSIC" to "EASY", "MDAS" to null, "MDAS" to "", "MDAS" to "UNKNOWN",
            "" to null, "UNKNOWN" to null, "classic" to null, " CLASSIC" to null,
            "MDAS" to "EASY ", "MDAS" to "easy",
        )) assertNull("$mode/$difficulty", parseGameEntry(mode, difficulty))
    }

    @Test fun invalidConfigurationsCannotBeConstructed() {
        assertThrows(IllegalArgumentException::class.java) { GameEntryConfig(GameMode.CLASSIC, MdasDifficulty.EASY) }
        assertThrows(IllegalArgumentException::class.java) { GameEntryConfig(GameMode.MDAS, null) }
    }
}
