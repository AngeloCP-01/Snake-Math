package com.acp.snakemath.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acp.snakemath.R
import com.acp.snakemath.core.ui.components.*
import com.acp.snakemath.core.ui.theme.SnakeMathTheme

@Composable
fun HomeScreen(state: HomeUiState, onPlay: () -> Unit, onSettings: () -> Unit, modifier: Modifier = Modifier) {
    val baseDimens = SnakeMathTheme.dimens
    val baseType = SnakeMathTheme.typography
    ForestBackground {
        BoxWithConstraints(modifier.fillMaxSize().safeDrawingPadding().padding(baseDimens.gutter)) {
            val wide = maxWidth >= 600.dp && maxWidth > maxHeight
            val fontScale = LocalDensity.current.fontScale
            val compact = maxHeight < 640.dp || fontScale > 1.2f
            val tight = maxHeight < 560.dp && fontScale > 1.2f
            // Only the decorative title counter-scales with accessibility text size.
            // Labels and actions still use the user's font scale.
            val logoSize = minOf(64f, maxHeight.value * (if (compact) .14f else .18f) / 2 / fontScale,
                maxWidth.value / (4.6f * fontScale))
            val type = baseType.copy(
                logo = baseType.logo.copy(fontSize = logoSize.sp, lineHeight = (logoSize * .94f).sp),
                button = if (compact) baseType.button.copy(fontSize = 30.sp, lineHeight = 32.sp) else baseType.button,
                label = if (compact) baseType.label.copy(fontSize = (if (tight) 12 else 14).sp,
                    lineHeight = (if (tight) 16 else 18).sp) else baseType.label,
                title = if (compact) baseType.title.copy(fontSize = (if (tight) 12 else 14).sp,
                    lineHeight = (if (tight) 16 else 18).sp) else baseType.title,
                number = if (compact) baseType.number.copy(fontSize = (if (tight) 22 else 26).sp,
                    lineHeight = (if (tight) 24 else 28).sp) else baseType.number,
                body = if (compact) baseType.body.copy(fontSize = 15.sp, lineHeight = 18.sp) else baseType.body,
            )
            val dims = if (compact) baseDimens.copy(gutter = 8.dp, gap = 4.dp, smallGap = 4.dp,
                buttonHeight = 48.dp) else baseDimens.copy(gap = 12.dp)
            SnakeMathTheme(typography = type, dimens = dims) {
                if (wide) {
                    Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(baseDimens.gap)) {
                        Column(Modifier.weight(1f).fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(dims.gap, Alignment.CenterVertically)) {
                            HomeHeading()
                            HomeMascot(Modifier.weight(1f, fill = false))
                        }
                        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(dims.gap)) {
                            HomeActions(state, onPlay, onSettings)
                        }
                    }
                } else {
                    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(dims.gap, Alignment.CenterVertically)) {
                        HomeHeading()
                        // Controls measure first. Artwork uses only the remaining height.
                        HomeMascot(Modifier.weight(1f, fill = false))
                        HomeActions(state, onPlay, onSettings)
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeHeading() {
    HomeLogo()
    BasicText(stringResource(R.string.home_subtitle), style = SnakeMathTheme.typography.body.copy(
        color = SnakeMathTheme.colors.cream, textAlign = TextAlign.Center))
}

@Composable
private fun HomeMascot(modifier: Modifier) {
    Image(painterResource(R.drawable.snake_mascot_v1), null,
        modifier.widthIn(max = 300.dp).fillMaxWidth().heightIn(max = 240.dp).testTag("home_mascot"),
        contentScale = ContentScale.Fit)
}

@Composable
private fun HomeActions(state: HomeUiState, onPlay: () -> Unit, onSettings: () -> Unit) {
    PrimaryButton(stringResource(R.string.play), onPlay)
    BestScoresPanel(state.classicBest, state.mdasBest)
    PillButton(stringResource(R.string.settings), onSettings, icon = { SettingsGear(Modifier.size(26.dp)) })
}

@Preview(widthDp = 390, heightDp = 844)
@Preview(widthDp = 320, heightDp = 568)
@Preview(widthDp = 390, heightDp = 844, fontScale = 1.5f)
@Preview(widthDp = 1024, heightDp = 768)
@Composable
private fun HomePreview() {
    SnakeMathTheme { HomeScreen(HomeUiState(120, 340), {}, {}) }
}
