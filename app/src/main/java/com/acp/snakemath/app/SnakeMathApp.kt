package com.acp.snakemath.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acp.snakemath.R

@Composable
fun SnakeMathApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .safeDrawingPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        BasicText(
            text = stringResource(R.string.app_name),
            style = TextStyle(color = Color.Black, fontSize = 20.sp, fontFamily = FontFamily.Default),
        )
        BasicText(
            text = stringResource(R.string.compose_ready),
            style = TextStyle(color = Color.Black, fontSize = 16.sp, fontFamily = FontFamily.Default),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SnakeMathAppPreview() {
    SnakeMathApp()
}
