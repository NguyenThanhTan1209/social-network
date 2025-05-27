package com.example.socialnetworkapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = darkPrimaryColor,
    secondary = darkSecondaryColor,
    tertiary = darkTertiaryColor,
    background = darkBackgroundColor,
    surface = darkSurfaceColor,
    onPrimary = darkOnPrimary,
    onSecondary = darkOnSecondary,
    onTertiary = darkOnTertiary,
    onBackground = darkOnBackground,
    onSurface = darkOnSurface,
)

private val LightColorScheme = lightColorScheme(
    primary = lightPrimaryColor,
    secondary = lightSecondaryColor,
    tertiary = lightTertiaryColor,
    background = lightBackgroundColor,
    surface = lightSurfaceColor,
    onPrimary = lightOnPrimary,
    onSecondary = lightOnSecondary,
    onTertiary = lightOnTertiary,
    onBackground = lightOnBackground,
    onSurface = lightOnSurface,
)

@Composable
fun SocialNetworkAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color tắt mặc định để sử dụng màu tùy chỉnh
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}