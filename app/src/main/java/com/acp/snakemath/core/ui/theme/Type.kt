package com.acp.snakemath.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.acp.snakemath.R

private val display = FontFamily(Font(R.font.baloo2_extra_bold, FontWeight.ExtraBold))
private val bodyFamily = FontFamily(Font(R.font.nunito_bold, FontWeight.Bold))

private fun displayStyle(size: Int, height: Int) = TextStyle(
    fontFamily = display, fontWeight = FontWeight.ExtraBold, fontSize = size.sp, lineHeight = height.sp,
    platformStyle = PlatformTextStyle(includeFontPadding = false),
    lineHeightStyle = LineHeightStyle(LineHeightStyle.Alignment.Center, LineHeightStyle.Trim.Both),
)

@Immutable
data class SnakeMathTypography(
    val logo: TextStyle = displayStyle(64, 60),
    val button: TextStyle = displayStyle(38, 40),
    val number: TextStyle = displayStyle(34, 36),
    val label: TextStyle = displayStyle(17, 20),
    val title: TextStyle = displayStyle(20, 24),
    val body: TextStyle = TextStyle(fontFamily = bodyFamily, fontWeight = FontWeight.Bold, fontSize = 17.sp, lineHeight = 22.sp),
)
