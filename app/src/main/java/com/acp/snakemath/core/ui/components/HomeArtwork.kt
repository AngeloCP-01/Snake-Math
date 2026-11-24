package com.acp.snakemath.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import com.acp.snakemath.core.ui.theme.SnakeMathTheme
import kotlin.math.cos
import kotlin.math.sin

/** Decorative native vectors; labels on their containing components provide semantics. */
@Composable
internal fun SettingsGear(modifier: Modifier) {
    val cream = SnakeMathTheme.colors.cream
    val panel = SnakeMathTheme.colors.panel
    Canvas(modifier.testTag("settings_gear")) {
        val radius = size.minDimension / 2
        val gear = Path().apply {
            repeat(32) { i ->
                val angle = i * Math.PI * 2 / 32
                val r = radius * if (i % 4 in 1..2) .96f else .76f
                val x = center.x + cos(angle).toFloat() * r
                val y = center.y + sin(angle).toFloat() * r
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
            close()
        }
        drawPath(gear, cream)
        drawCircle(panel, radius * .35f)
    }
}

@Composable
internal fun Trophy(modifier: Modifier) {
    val colors = SnakeMathTheme.colors
    Canvas(modifier.testTag("best_scores_trophy")) {
        val w = size.width
        val h = size.height
        val gold = Brush.verticalGradient(listOf(colors.trophyHighlight, colors.trophyGold, colors.trophyShade))
        val cup = Path().apply {
            moveTo(w * .25f, h * .1f); lineTo(w * .75f, h * .1f)
            cubicTo(w * .77f, h * .65f, w * .23f, h * .65f, w * .25f, h * .1f)
            close()
        }
        drawOval(gold, Offset(w * .07f, h * .17f), Size(w * .4f, h * .36f), style = Stroke(w * .07f))
        drawOval(gold, Offset(w * .53f, h * .17f), Size(w * .4f, h * .36f), style = Stroke(w * .07f))
        drawRect(gold, Offset(w * .44f, h * .5f), Size(w * .12f, h * .3f))
        drawRoundRect(gold, Offset(w * .28f, h * .8f), Size(w * .44f, h * .12f),
            androidx.compose.ui.geometry.CornerRadius(w * .04f))
        drawPath(cup, gold)
        drawPath(Path().apply {
            moveTo(w * .31f, h * .15f); lineTo(w * .42f, h * .15f)
            lineTo(w * .42f, h * .49f); quadraticTo(w * .3f, h * .45f, w * .31f, h * .15f)
        }, colors.trophyHighlight.copy(alpha = .5f))
    }
}

@Composable
internal fun LogoLeaves(modifier: Modifier) {
    val colors = SnakeMathTheme.colors
    Canvas(modifier) {
        fun cluster(x: Float, y: Float, angle: Float) {
            val leaf = size.width * .055f
            rotate(angle, Offset(x, y)) {
                val path = Path().apply {
                    moveTo(x, y)
                    quadraticTo(x - leaf, y - leaf * 1.3f, x, y - leaf * 2)
                    quadraticTo(x + leaf, y - leaf, x, y)
                    close()
                }
                drawPath(path, Brush.verticalGradient(listOf(colors.titleLimeTop, colors.leaf)))
                drawLine(colors.leafVein, Offset(x, y), Offset(x, y - leaf * 1.7f), leaf * .08f)
            }
        }
        cluster(size.width * -.02f, size.height * .33f, -35f)
        cluster(size.width * .01f, size.height * .32f, -75f)
        cluster(size.width * 1.02f, size.height * .35f, 40f)
        cluster(size.width * .99f, size.height * .38f, 80f)
        cluster(size.width * -.025f, size.height * .82f, -45f)
    }
}

@Preview(name = "Home vector artwork", widthDp = 320, heightDp = 240)
@Composable
private fun HomeArtworkPreview() {
    SnakeMathTheme {
        Column(Modifier.fillMaxSize().background(SnakeMathTheme.colors.forest).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                SettingsGear(Modifier.size(26.dp))
                Trophy(Modifier.size(48.dp))
            }
            LogoLeaves(Modifier.fillMaxWidth().height(120.dp))
        }
    }
}
