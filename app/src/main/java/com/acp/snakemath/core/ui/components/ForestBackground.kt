package com.acp.snakemath.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.acp.snakemath.R
import com.acp.snakemath.core.ui.theme.SnakeMathTheme

@Composable
fun ForestBackground(content: @Composable BoxScope.() -> Unit) {
    Box(Modifier.fillMaxSize().background(SnakeMathTheme.colors.forest)) {
        Image(painterResource(R.drawable.forest_background_v1), null,
            Modifier.matchParentSize(), contentScale = ContentScale.Crop)
        content()
    }
}

@Preview(name = "Forest – phone", widthDp = 390, heightDp = 844)
@Preview(name = "Forest – tablet", widthDp = 1024, heightDp = 768)
@Composable
private fun ForestBackgroundPreview() {
    SnakeMathTheme { ForestBackground {} }
}
