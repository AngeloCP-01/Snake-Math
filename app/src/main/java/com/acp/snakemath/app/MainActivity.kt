package com.acp.snakemath.app

import android.os.Bundle
import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.acp.snakemath.app.navigation.shellProcessId
import com.acp.snakemath.app.navigation.shouldRestoreShell

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState.takeIf {
            shouldRestoreShell(it?.getString(SHELL_PROCESS_KEY), shellProcessId)
        })
        requestedOrientation = if (resources.configuration.smallestScreenWidthDp >= 600)
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.dark(
                android.graphics.Color.rgb(2, 62, 33),
            ),
        )
        setContent { SnakeMathApp() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SHELL_PROCESS_KEY, shellProcessId)
    }

    private companion object {
        const val SHELL_PROCESS_KEY = "com.acp.snakemath.shellProcessId"
    }
}
