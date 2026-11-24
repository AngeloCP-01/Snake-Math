package com.acp.snakemath.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.acp.snakemath.R
import com.acp.snakemath.core.ui.theme.SnakeMathTheme

@Composable
fun BestScoresPanel(classicBest: Int?, mdasBest: Int?) {
    val colors = SnakeMathTheme.colors
    val dims = SnakeMathTheme.dimens
    val shape = RoundedCornerShape(dims.panelRadius)
    Row(Modifier.widthIn(max = dims.panelWidth).fillMaxWidth().testTag("best_scores_panel")
        .shadow(6.dp, shape, clip = false, ambientColor = Color.Black.copy(alpha = .3f),
            spotColor = Color.Black.copy(alpha = .3f))
        .background(colors.panel, shape).border(dims.border, colors.border, shape)
        .drawWithContent {
            drawContent()
            val inset = 4.dp.toPx()
            drawRoundRect(Color.Black.copy(alpha = .15f), Offset(inset, inset),
                Size((size.width - 2 * inset).coerceAtLeast(0f), (size.height - 2 * inset).coerceAtLeast(0f)),
                CornerRadius((dims.panelRadius.toPx() - inset).coerceAtLeast(0f)), style = Stroke(inset))
        }
        .padding(dims.gutter), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dims.smallGap)) {
        Trophy(Modifier.size(48.dp))
        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dims.smallGap)) {
            BasicText(stringResource(R.string.best_scores), style = SnakeMathTheme.typography.title.copy(color = colors.cream))
            ScoreRow(stringResource(R.string.classic_best), classicBest)
            ScoreRow(stringResource(R.string.mdas_best), mdasBest)
        }
    }
}

@Composable
private fun ScoreRow(label: String, score: Int?) {
    val description = if (score == null) stringResource(R.string.score_unavailable, label)
        else stringResource(R.string.score_description, label, score)
    Row(Modifier.fillMaxWidth().semantics(mergeDescendants = true) { contentDescription = description },
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        BasicText(label, Modifier.weight(1f), style = SnakeMathTheme.typography.label.copy(color = SnakeMathTheme.colors.cream))
        Spacer(Modifier.width(SnakeMathTheme.dimens.smallGap))
        BasicText(score?.toString() ?: stringResource(R.string.unavailable_value),
            style = SnakeMathTheme.typography.number.copy(color = SnakeMathTheme.colors.gold))
    }
}

@Preview(name = "Best scores – sample values", showBackground = true, backgroundColor = 0xFF023E21, widthDp = 320)
@Preview(name = "Best scores – enlarged text", showBackground = true, backgroundColor = 0xFF023E21, widthDp = 320, fontScale = 1.5f)
@Composable
private fun BestScoresPanelPreview() {
    SnakeMathTheme {
        Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
            BestScoresPanel(classicBest = 120, mdasBest = 340)
        }
    }
}

@Preview(name = "Best scores – unavailable", showBackground = true, backgroundColor = 0xFF023E21, widthDp = 320)
@Composable
private fun BestScoresPanelUnavailablePreview() {
    SnakeMathTheme {
        Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
            BestScoresPanel(classicBest = null, mdasBest = null)
        }
    }
}
