package com.acp.snakemath.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acp.snakemath.R
import com.acp.snakemath.core.ui.theme.SnakeMathTheme

@Composable
fun PillButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null) {
    val colors = SnakeMathTheme.colors
    val dims = SnakeMathTheme.dimens
    Box(modifier.widthIn(min = 136.dp).heightIn(min = dims.touchMin).background(colors.panel, CircleShape)
        .border(dims.border, colors.border, CircleShape)
        .clickable(role = Role.Button, onClick = onClick)
        .padding(horizontal = dims.gutter, vertical = dims.smallGap), contentAlignment = Alignment.Center) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(dims.smallGap)) {
            icon?.invoke()
            BasicText(text, style = SnakeMathTheme.typography.label.copy(color = colors.cream))
        }
    }
}

@Preview(name = "Settings button", showBackground = true, backgroundColor = 0xFF023E21, widthDp = 240)
@Composable
private fun PillButtonPreview() {
    SnakeMathTheme {
        Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
            PillButton(stringResource(R.string.settings), onClick = {}, icon = { SettingsGear(Modifier.size(26.dp)) })
        }
    }
}
