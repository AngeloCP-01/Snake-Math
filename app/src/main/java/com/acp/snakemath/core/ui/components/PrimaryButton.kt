package com.acp.snakemath.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.acp.snakemath.R
import com.acp.snakemath.core.ui.theme.SnakeMathTheme

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val colors = SnakeMathTheme.colors
    val dims = SnakeMathTheme.dimens
    val interactions = remember { MutableInteractionSource() }
    val pressed by interactions.collectIsPressedAsState()
    Box(modifier.widthIn(max = dims.buttonWidth).fillMaxWidth()
        .padding(top = if (pressed) 4.dp else 0.dp)
        .background(colors.lip, CircleShape)
        .clickable(interactions, indication = null, role = Role.Button, onClick = onClick)
        .padding(bottom = if (pressed) 2.dp else 6.dp)) {
        Box(Modifier.fillMaxWidth().heightIn(min = dims.buttonHeight)
            .clip(CircleShape)
            .background(Brush.verticalGradient(0f to colors.primaryTop, .4f to colors.primaryMiddle,
                .75f to colors.primaryLower, 1f to colors.primaryBottom))
            .drawWithContent {
                drawRect(Brush.verticalGradient(listOf(Color.White.copy(alpha = .4f), Color.Transparent),
                    endY = size.height / 3), size = Size(size.width, size.height / 3))
                drawContent()
            }
            .padding(horizontal = dims.gutter, vertical = dims.smallGap), contentAlignment = Alignment.Center) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(dims.smallGap)) {
                Canvas(Modifier.size(28.dp)) {
                    val triangle = Path().apply {
                        moveTo(size.width * .2f, size.height * .17f)
                        quadraticTo(size.width * .2f, size.height * .07f, size.width * .3f, size.height * .13f)
                        lineTo(size.width * .85f, size.height * .44f)
                        quadraticTo(size.width * .95f, size.height * .5f, size.width * .85f, size.height * .56f)
                        lineTo(size.width * .3f, size.height * .87f)
                        quadraticTo(size.width * .2f, size.height * .93f, size.width * .2f, size.height * .83f)
                        close()
                    }
                    drawPath(triangle, colors.onPrimary)
                }
                BasicText(text, style = SnakeMathTheme.typography.button.copy(color = colors.onPrimary))
            }
        }
    }
}

@Preview(name = "PLAY button", showBackground = true, backgroundColor = 0xFF023E21, widthDp = 360)
@Composable
private fun PrimaryButtonPreview() {
    SnakeMathTheme {
        Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
            PrimaryButton(stringResource(R.string.play), onClick = {})
        }
    }
}
