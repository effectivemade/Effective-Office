package band.effective.office.tablet.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

private val darkColors = darkColors(
    primary = md_theme_dark_primary,
    secondary = md_theme_dark_secondary,
    background = md_theme_dark_background,
    surface = md_theme_dark_surface,
    onError = md_theme_dark_onError,
    onPrimary = md_theme_dark_onPrimary
)

val CustomDarkColors = CustomColorsPalette(
    elevationBackground = md_theme_dark_elevationBackground,
    mountainBackground = md_theme_dark_mountainBackground,
    busyStatus = md_theme_dark_busyStatus,
    freeStatus = md_theme_dark_freeStatus,
    onSuccess = md_theme_dark_onSuccess,
    secondaryButton = md_theme_dark_secondaryButton,
    primaryTextAndIcon = md_theme_dark_primaryTextAndIcon,
    secondaryTextAndIcon = md_theme_dark_secondaryTextAndIcon,
    tertiaryTextAndIcon = md_theme_dark_tertiaryTextAndIcon,
    pressedPrimaryButton = md_theme_dark_pressedPrimaryButton,
    disabledPrimaryButton = md_theme_dark_disabledPrimaryButton,
    loadingColor = md_theme_dark_surface
)

private val lightColors = lightColors(
    primary = md_theme_light_primary,
    secondary = md_theme_light_secondary,
    background = md_theme_light_background,
    surface = md_theme_light_surface,
    onError = md_theme_light_onError,
)

val CustomLightColors = CustomColorsPalette(
    elevationBackground = md_theme_light_elevationBackground,
    mountainBackground = md_theme_light_mountainBackground,
    busyStatus = md_theme_light_busyStatus,
    freeStatus = md_theme_light_freeStatus,
    onSuccess = md_theme_light_onSuccess,
    secondaryButton = md_theme_light_secondaryButton,
    primaryTextAndIcon = md_theme_light_primaryTextAndIcon,
    secondaryTextAndIcon = md_theme_light_secondaryTextAndIcon,
    tertiaryTextAndIcon = md_theme_light_tertiaryTextAndIcon,
    pressedPrimaryButton = md_theme_light_pressedPrimaryButton,
    disabledPrimaryButton = md_theme_light_disabledPrimaryButton,
    loadingColor = md_theme_light_surface
)

data class CustomColorsPalette (
    val elevationBackground: Color = Color.Unspecified,
    val mountainBackground: Color = Color.Unspecified,
    val busyStatus: Color = Color.Unspecified,
    val freeStatus: Color = Color.Unspecified,
    val onSuccess: Color = Color.Unspecified,
    val secondaryButton: Color = Color.Unspecified,
    val primaryTextAndIcon: Color = Color.Unspecified,
    val secondaryTextAndIcon: Color = Color.Unspecified,
    val tertiaryTextAndIcon: Color = Color.Unspecified,
    val pressedPrimaryButton: Color = Color.Unspecified,
    val disabledPrimaryButton: Color = Color.Unspecified,
    val parameterTitle: Color = Color.Unspecified,
    val loadingColor: Color = Color.Unspecified
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }

@Composable
fun AppTheme(
    useDarkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
   val colors = if (!useDarkTheme) {
        lightColors
    } else {
        darkColors
    }

    val customColorsPalette = if (!useDarkTheme) {
        CustomLightColors
    } else {
        CustomDarkColors
    }

    val typography = Typography(
        h1 = header1,
        h2 = header2,
        h3 = header3,
        h4 = header4,
        h5 = header5,
        h6 = header6
    )

    /* (Margarita Djinjolia)
    https://stackoverflow.com/questions/75544931/jetpack-compose-add-custom-dark-light-colors */
    CompositionLocalProvider(
        LocalCustomColorsPalette provides customColorsPalette
    ) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            content = {
                Surface(content = content)
            }
        )
    }
}

val Typography.h6_button: TextStyle
    get() = header6_button
val Typography.h7: TextStyle
    get() = header7

val Typography.h8: TextStyle
    get() = header8