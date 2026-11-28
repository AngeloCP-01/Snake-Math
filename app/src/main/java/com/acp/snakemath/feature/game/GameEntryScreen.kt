package com.acp.snakemath.feature.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.acp.snakemath.R
import com.acp.snakemath.core.model.GameEntryConfig
import com.acp.snakemath.core.model.GameMode
import com.acp.snakemath.core.ui.components.ForestBackground
import com.acp.snakemath.core.ui.components.PillButton
import com.acp.snakemath.core.ui.theme.SnakeMathTheme
import com.acp.snakemath.feature.modes.labelResource

@Composable
fun GameEntryScreen(config: GameEntryConfig, onBackToModes: () -> Unit, onHome: () -> Unit,
    modifier: Modifier = Modifier) {
    ForestBackground {
        Column(modifier.fillMaxSize().safeDrawingPadding().verticalScroll(rememberScrollState())
            .padding(SnakeMathTheme.dimens.gutter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SnakeMathTheme.dimens.gap, Alignment.CenterVertically)) {
            Column(Modifier.fillMaxWidth().background(SnakeMathTheme.colors.panel,
                RoundedCornerShape(SnakeMathTheme.dimens.panelRadius)).padding(SnakeMathTheme.dimens.gutter),
                verticalArrangement = Arrangement.spacedBy(SnakeMathTheme.dimens.gap)) {
                BasicText(stringResource(if (config.mode == GameMode.CLASSIC) R.string.classic_mode else R.string.mdas_mode),
                    style = SnakeMathTheme.typography.title.copy(color = SnakeMathTheme.colors.cream))
                config.difficulty?.let { BasicText(stringResource(it.labelResource()),
                    style = SnakeMathTheme.typography.body.copy(color = SnakeMathTheme.colors.cream)) }
                BasicText(stringResource(R.string.gameplay_unavailable),
                    style = SnakeMathTheme.typography.body.copy(color = SnakeMathTheme.colors.cream))
            }
            PillButton(stringResource(R.string.back_modes), onBackToModes)
            PillButton(stringResource(R.string.home), onHome)
        }
    }
}
