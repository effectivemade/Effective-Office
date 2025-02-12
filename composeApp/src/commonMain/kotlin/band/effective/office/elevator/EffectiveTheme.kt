package band.effective.office.elevator

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

data class EffectiveThemeColors(
    val background: Background,
    val text: Text,
    val icon: Icon,
    val stroke: Stroke,
    val divider: Divider,
    val graph: Graph,
    val button: Button,
    val calendar: Calendar,
    val item: Item,
    val avatar: Avatar,
    val card: Card,
    val input: Input,
    val table: Table,
) {

    data class Background(
        val primary: Color,
        val secondary: Color,
        val tertiary: Color,
        val additional: Color,
    )

    data class Text(
        val primary: Color,
        val secondary: Color,
        val tertiary: Color,
        val accent: Color,
        val primaryEvent: Color,
        val error: Color,
        val additional: Color,
        val caption: Color,
        val optional: Color,
    )

    data class Icon(
        val primary: Color,
        val secondary: Color,
        val tertiary: Color,
        val accent: Color,
        val success: Color,
        val error: Color,
        val primaryEvent: Color,
        val optional: Color,
    )

    data class Stroke(
        val primary: Color,
        val error: Color,
        val accent: Color,
    )

    data class Divider(
        val primary: Color,
    )

    data class Graph(
        val orange: Color,
        val violet: Color,
    )

    data class Button(
        val primaryNormal: Color,
        val primaryPress: Color,
        val primaryDisable: Color,
        val iconNormal: Color,
        val iconPress: Color,
        val iconDisable: Color,
        val tertiaryNormal: Color,
        val tertiaryPress: Color,
        val actionNormalNormal: Color,
        val actionEditPress: Color,
        val actionDelete: Color,
        val timeNormal: Color,
        val timePress: Color,
        val timeActive: Color,
        val fullDayNormal: Color,
        val fullDayPress: Color,
        val repeatNormal: Color,
        val repeatPress: Color,
        val toggleOff: Color,
        val toggleOn: Color,
        val toggleNormal: Color,
    )

    data class Calendar(
        val date: Color,
    )

    data class Item(
        val colleagueNormal: Color,
        val colleagueRepeat: Color,
        val repeatNormal: Color,
        val repeatPress: Color,
    )

    data class Avatar(
        val default: Color,
    )

    data class Card(
        val normal: Color,
        val active: Color,
        val press: Color,
    )

    data class Input(
        val freeNormal: Color,
        val freePress: Color,
        val freeTyping: Color,
        val freeFill: Color,
        val lockNormal: Color,
        val lockPress: Color,
        val lockTyping: Color,
        val lockFill: Color,
        val lockDisable: Color,
        val searchNormal: Color,
        val searchPress: Color,
        val searchTyping: Color,
        val searchFill: Color,
    )

    data class Table(
        val tableAvailable: Color,
        val tableSelect: Color,
    )
}

data class EffectiveThemeTypography(
    val xlMedium: TextStyle,
    val lMedium: TextStyle,
    val mMedium: TextStyle,
    val sMedium: TextStyle,
    val xsMedium: TextStyle,
    val xsRegular: TextStyle,
)

@Composable
internal fun EffectiveTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (useDarkTheme) {
        EffectiveDarkColors
    } else {
        EffectiveLightColors
    }
    CompositionLocalProvider(
        LocalEffectiveThemeColors provides colors,
        LocalEffectiveThemeTypography provides effectiveTypography,
    ) {
        content()
    }
}

object EffectiveTheme {

    val colors: EffectiveThemeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalEffectiveThemeColors.current

    val typography: EffectiveThemeTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalEffectiveThemeTypography.current
}

val LocalEffectiveThemeColors = staticCompositionLocalOf<EffectiveThemeColors> {
    throw IllegalStateException("EffectiveTheme is not set properly")
}

val LocalEffectiveThemeTypography = staticCompositionLocalOf<EffectiveThemeTypography> {
    throw IllegalStateException("EffectiveTheme is not set properly")
}
