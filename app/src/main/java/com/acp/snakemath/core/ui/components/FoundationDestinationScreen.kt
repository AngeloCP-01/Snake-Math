package com.acp.snakemath.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.acp.snakemath.R
import com.acp.snakemath.core.ui.theme.SnakeMathTheme

@Composable
fun FoundationDestinationScreen(title: String, message: String, onBack: () -> Unit, modifier: Modifier = Modifier) {
    ForestBackground {
        Column(modifier.fillMaxSize().safeDrawingPadding().verticalScroll(rememberScrollState())
            .padding(SnakeMathTheme.dimens.gutter), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SnakeMathTheme.dimens.gap, Alignment.CenterVertically)) {
            BasicText(title, style = SnakeMathTheme.typography.button.copy(color = SnakeMathTheme.colors.cream))
            BasicText(message, style = SnakeMathTheme.typography.body.copy(color = SnakeMathTheme.colors.cream))
            PillButton(stringResource(R.string.back_home), onBack)
        }
    }
}

@Preview(name = "Modes placeholder", widthDp = 390, heightDp = 844)
@Composable
private fun ModesDestinationPreview() {
    SnakeMathTheme {
        FoundationDestinationScreen(
            title = stringResource(R.string.modes),
            message = stringResource(R.string.modes_unavailable),
            onBack = {},
        )
    }
}

@Preview(name = "Settings placeholder", widthDp = 390, heightDp = 844)
@Composable
private fun SettingsDestinationPreview() {
    SnakeMathTheme {
        FoundationDestinationScreen(
            title = stringResource(R.string.settings),
            message = stringResource(R.string.settings_unavailable),
            onBack = {},
        )
    }
}
