package com.acp.snakemath.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import com.acp.snakemath.R
import com.acp.snakemath.core.ui.theme.SnakeMathTheme

@Composable
fun HomeLogo() {
    val description = stringResource(R.string.app_name)
    val colors = SnakeMathTheme.colors
    val stroke = with(LocalDensity.current) { 6.dp.toPx() }
    val extrusion = with(LocalDensity.current) { 4.dp.toPx() }
    val snake = stringResource(R.string.logo_snake)
    val math = stringResource(R.string.logo_math)
    val title = "$snake\n$math"
    val coloredTitle = buildAnnotatedString {
        withStyle(SpanStyle(brush = Brush.verticalGradient(listOf(colors.titleCreamTop, colors.titleCreamBottom)))) { append(snake) }
        append('\n')
        withStyle(SpanStyle(brush = Brush.verticalGradient(listOf(colors.titleLimeTop, colors.primaryMiddle, colors.primaryLower)))) { append(math) }
    }
    val style = SnakeMathTheme.typography.logo.copy(
        textAlign = TextAlign.Center,
        // Adjust this ratio to change the space between the two title baselines.
        lineHeight = 0.8.em,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.Both,
            mode = LineHeightStyle.Mode.Fixed,
        ),
    )
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.clearAndSetSemantics { contentDescription = description }.padding(horizontal = 20.dp, vertical = 4.dp)) {
        LogoLeaves(Modifier.matchParentSize())
        BasicText(title, softWrap = false,
            style = style.copy(color = colors.ink, drawStyle = Stroke(stroke),
                shadow = Shadow(colors.ink, Offset(0f, extrusion), 0f)))
        BasicText(coloredTitle, softWrap = false, style = style)
    }
}

@Preview(name = "Home logo", showBackground = true, backgroundColor = 0xFF023E21, widthDp = 390)
@Composable
private fun HomeLogoPreview() {
    SnakeMathTheme {
        Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
            HomeLogo()
        }
    }
}
