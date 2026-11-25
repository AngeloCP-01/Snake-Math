package com.acp.snakemath.feature.modes

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acp.snakemath.R
import com.acp.snakemath.core.ui.components.*
import com.acp.snakemath.core.ui.theme.SnakeMathTheme
import com.acp.snakemath.core.model.MdasDifficulty

@Composable
fun ModesScreen(
    state: ModesUiState,
    onDifficultySelected: (MdasDifficulty) -> Unit,
    onPlayClassic: () -> Unit,
    onPlayMdas: () -> Unit,
    onBackHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ForestBackground {
        BoxWithConstraints(modifier.fillMaxSize().safeDrawingPadding()) {
            val twoColumns = maxWidth >= 840.dp
            Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(SnakeMathTheme.dimens.gutter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(SnakeMathTheme.dimens.gap)) {
                ModesText(stringResource(R.string.modes))
                BasicText(stringResource(R.string.choose_mode),
                    style = SnakeMathTheme.typography.button.copy(color = SnakeMathTheme.colors.cream))
                if (twoColumns) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(SnakeMathTheme.dimens.gap)) {
                        ModeCard(false, state, onDifficultySelected, onPlayClassic, Modifier.weight(1f))
                        ModeCard(true, state, onDifficultySelected, onPlayMdas, Modifier.weight(1f))
                    }
                } else {
                    ModeCard(false, state, onDifficultySelected, onPlayClassic)
                    ModeCard(true, state, onDifficultySelected, onPlayMdas)
                }
                PillButton(stringResource(R.string.back_home), onBackHome)
            }
        }
    }
}

@Composable
private fun ModeCard(mdas: Boolean, state: ModesUiState, select: (MdasDifficulty) -> Unit,
    start: () -> Unit, modifier: Modifier = Modifier) {
    val colors = SnakeMathTheme.colors
    val dims = SnakeMathTheme.dimens
    val shape = RoundedCornerShape(dims.panelRadius)
    Column(modifier.fillMaxWidth().background(colors.panel, shape).border(dims.border, colors.border, shape)
        .padding(dims.gutter), verticalArrangement = Arrangement.spacedBy(dims.smallGap)) {
        BasicText(if (mdas) "× ÷" else "●", Modifier.clearAndSetSemantics {},
            style = SnakeMathTheme.typography.number.copy(color = colors.lime))
        ModesText(stringResource(if (mdas) R.string.mdas_mode else R.string.classic_mode))
        ModesText(stringResource(if (mdas) R.string.mdas_mode_copy else R.string.classic_mode_copy))
        val label = stringResource(if (mdas) R.string.mdas_best else R.string.classic_best)
        val score = if (mdas) state.mdasBest else state.classicBest
        val description = if (score == null) stringResource(R.string.score_unavailable, label)
            else stringResource(R.string.score_description, label, score)
        BasicText("$label: ${score?.toString() ?: stringResource(R.string.unavailable_value)}",
            Modifier.semantics { contentDescription = description },
            style = SnakeMathTheme.typography.title.copy(color = colors.gold))
        if (mdas) {
            Column(Modifier.selectableGroup(), verticalArrangement = Arrangement.spacedBy(dims.smallGap)) {
                MdasDifficulty.entries.forEach { level ->
                    val selected = state.selectedDifficulty == level
                    Column(Modifier.fillMaxWidth().heightIn(min = dims.touchMin)
                        .border(dims.border, if (selected) colors.lime else colors.border, shape)
                        .selectable(selected, role = Role.RadioButton, onClick = { select(level) })
                        .padding(dims.smallGap)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(dims.smallGap)) {
                            ModesText(stringResource(level.labelResource()))
                            if (selected) ModesText(stringResource(R.string.selected_level))
                        }
                        ModesText(stringResource(when (level) {
                            MdasDifficulty.EASY -> R.string.easy_guidance
                            MdasDifficulty.MEDIUM -> R.string.medium_guidance
                            MdasDifficulty.HARD -> R.string.hard_guidance
                        }))
                    }
                }
            }
        }
        PrimaryButton(stringResource(if (mdas) R.string.play_mdas else R.string.play_classic), start)
    }
}

@Composable
private fun ModesText(text: String) {
    BasicText(text, style = SnakeMathTheme.typography.body.copy(color = SnakeMathTheme.colors.cream))
}

internal fun MdasDifficulty.labelResource(): Int = when (this) {
    MdasDifficulty.EASY -> R.string.easy
    MdasDifficulty.MEDIUM -> R.string.medium
    MdasDifficulty.HARD -> R.string.hard
}

@Preview(name = "Modes phone", widthDp = 390, heightDp = 844)
@Preview(name = "Modes tablet", widthDp = 1024, heightDp = 768)
@Preview(name = "Modes enlarged text", widthDp = 320, heightDp = 568, fontScale = 1.5f)
@Composable
private fun ModesPreview() {
    SnakeMathTheme { ModesScreen(ModesUiState(120, 340), {}, {}, {}, {}) }
}
