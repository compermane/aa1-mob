package com.example.aa1_mob.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// --- REMOVA AS DEFINIÇÕES ANTIGAS E MANTENHA APENAS ESTAS NOVAS ---
// Certifique-se que estas definições NÃO ESTÃO DENTRO da função Aa1mobTheme
private val DarkColorScheme = darkColorScheme(
    primary = VoeBlueLight, // Ou uma cor escura que combine
    secondary = VoeBlueDark,
    tertiary = Pink80,
    background = Color(0xFF1C1B1F), // Fundo escuro padrão
    surface = Color(0xFF1C1B1F),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFE6E1E5),
    onSurface = Color(0xFFE6E1E5),
    error = Color(0xFFCF6679),
    onError = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = VoeBlueLight, // Azul claro do Figma
    secondary = VoeBlueDark, // Azul um pouco mais escuro
    tertiary = Pink40, // Pode manter ou ajustar
    background = VoeWhite, // Fundo branco
    surface = VoeWhite, // Superfícies brancas
    onPrimary = VoeTextBlack, // Texto sobre primary
    onSecondary = VoeTextBlack,
    onTertiary = VoeTextBlack,
    onBackground = VoeTextBlack, // Texto sobre fundo branco
    onSurface = VoeTextBlack, // Texto sobre superfícies brancas
    error = Color(0xFFB00020),
    onError = Color.White,

    // Cores específicas para o botão e campos de texto se desejar
    // O botão amarelo pode ser mapeado para MaterialTheme.colorScheme.tertiary, por exemplo,
    // e os OutlinedTextFields podem ter sua cor de fundo e borda ajustadas individualmente.
    // Ou, podemos apenas usar as cores diretamente nos componentes.
)

@Composable
fun Aa1mobTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Certifique-se de que sua Typography está definida
        content = content
    )
}