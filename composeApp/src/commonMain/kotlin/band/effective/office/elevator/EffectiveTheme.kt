package band.effective.office.elevator

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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
    val lMedium: TextStyle,
    val mMedium: TextStyle,
    val sMedium: TextStyle,
    val xsMedium: TextStyle,
    val xsRegular: TextStyle,
)

private val LightColors = EffectiveThemeColors(
    background = EffectiveThemeColors.Background(
        primary = neutral15,
        secondary = neutral20,
        tertiary = neutral15.copy(alpha = 0.8f),
        additional = neutral25,
    ),

    text = EffectiveThemeColors.Text(
        primary = neutral90,
        secondary = neutral45,
        tertiary = orange40,
        accent = orange60,
        primaryEvent = neutral10,
        error = red50,
        additional = neutral30,
        caption = neutral20,
        optional = purple40,
    ),

    icon = EffectiveThemeColors.Icon(
        primary = neutral90,
        secondary = neutral30,
        tertiary = neutral20,
        accent = orange60,
        success = green50,
        error = red50,
        primaryEvent = neutral10,
        optional = purple40,
    ),

    stroke = EffectiveThemeColors.Stroke(
        primary = neutral20,
        error = red50,
        accent = orange60,
    ),

    divider = EffectiveThemeColors.Divider(
        primary = neutral20,
    ),

    graph = EffectiveThemeColors.Graph(
        orange = orange50,
        violet = purple30,
    ),

    button = EffectiveThemeColors.Button(
        primaryNormal = orange60,
        primaryPress = orange50,
        primaryDisable = orange20,
        iconNormal = neutral10,
        iconPress = neutral15,
        iconDisable = neutral10,
        tertiaryNormal = neutral10,
        tertiaryPress = neutral15,
        actionNormalNormal = purple40,
        actionEditPress = purple30,
        actionDelete = red30,
        timeNormal = neutral5,
        timePress = neutral10,
        timeActive = neutral5,
        fullDayNormal = neutral5,
        fullDayPress = neutral10,
        repeatNormal = neutral5,
        repeatPress = neutral10,
        toggleOn = green50,
        toggleOff = neutral5,
        toggleNormal = neutral10,
    ),

    calendar = EffectiveThemeColors.Calendar(
        date = purple40,
    ),

    item = EffectiveThemeColors.Item(
        colleagueNormal = neutral10,
        colleagueRepeat = neutral5,
        repeatNormal = neutral5,
        repeatPress = neutral15,
    ),

    avatar = EffectiveThemeColors.Avatar(
        default = neutral15,
    ),

    card = EffectiveThemeColors.Card(
        normal = neutral10,
        active = neutral10,
        press = neutral15,
    ),

    input = EffectiveThemeColors.Input(
        freeNormal = neutral10,
        freePress = neutral15,
        freeTyping = neutral10,
        freeFill = neutral10,
        lockNormal = neutral15,
        lockPress = neutral10,
        lockTyping = neutral15,
        lockFill = neutral15,
        lockDisable = neutral10,
        searchNormal = neutral10,
        searchPress = neutral15,
        searchTyping = neutral10,
        searchFill = neutral10,
    ),

    table = EffectiveThemeColors.Table(
        tableAvailable = purple10,
        tableSelect = purple50,
    ),
)

private val DarkColors = EffectiveThemeColors(
    background = EffectiveThemeColors.Background(
        primary = neutral95,
        secondary = neutral90,
        tertiary = neutral95.copy(alpha = 0.8f),
        additional = neutral80,
    ),

    text = EffectiveThemeColors.Text(
        primary =neutral10 ,
        secondary = neutral50,
        tertiary = orange30,
        accent = orange50,
        primaryEvent = neutral10,
        error = red40,
        additional = neutral40,
        caption = neutral20,
        optional = purple30,
    ),

    icon = EffectiveThemeColors.Icon(
        primary = neutral10,
        secondary = neutral25,
        tertiary = neutral35,
        accent = orange50,
        success = green40,
        error = red40,
        primaryEvent = neutral10,
        optional = purple30,
    ),

    stroke = EffectiveThemeColors.Stroke(
        primary = neutral85,
        error = red40,
        accent = orange50,
    ),

    divider = EffectiveThemeColors.Divider(
        primary = neutral85,
    ),

    graph = EffectiveThemeColors.Graph(
        orange = orange40,
        violet = purple50,
    ),

    button = EffectiveThemeColors.Button(
        primaryNormal = orange50,
        primaryPress = orange40,
        primaryDisable = orange90,
        iconNormal = neutral95,
        iconPress = neutral90,
        iconDisable = neutral95,
        tertiaryNormal = neutral95,
        tertiaryPress = neutral90,
        actionNormalNormal = purple30,
        actionEditPress = purple20,
        actionDelete = red20,
        timeNormal = neutral90,
        timePress = neutral95,
        timeActive = neutral90,
        fullDayNormal = neutral90,
        fullDayPress = neutral95,
        repeatNormal = neutral90,
        repeatPress = neutral95,
        toggleOn = green40,
        toggleOff = neutral80,
        toggleNormal = neutral5,
    ),

    calendar = EffectiveThemeColors.Calendar(
        date = purple30,
    ),

    item = EffectiveThemeColors.Item(
        colleagueNormal = neutral95,
        colleagueRepeat = neutral90,
        repeatNormal = neutral90,
        repeatPress = neutral85,
    ),

    avatar = EffectiveThemeColors.Avatar(
        default = neutral90,
    ),

    card = EffectiveThemeColors.Card(
        normal = neutral95,
        active = neutral95,
        press = neutral90,
    ),

    input = EffectiveThemeColors.Input(
        freeNormal = neutral95,
        freePress = neutral90,
        freeTyping = neutral95,
        freeFill = neutral95,
        lockNormal = neutral90,
        lockPress = neutral95,
        lockTyping = neutral90,
        lockFill = neutral90,
        lockDisable = neutral95,
        searchNormal = neutral95,
        searchPress = neutral90 ,
        searchTyping = neutral95,
        searchFill = neutral95,
    ),

    table = EffectiveThemeColors.Table(
        tableAvailable = neutral85,
        tableSelect = purple40,
    )
)

@Composable
internal fun EffectiveTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (useDarkTheme) {
        DarkColors
    } else {
        LightColors
    }

    val typography = EffectiveThemeTypography(
        lMedium = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        ),
        mMedium = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            ),
        sMedium = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            ),
        xsMedium = TextStyle(
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            ),
        xsRegular = TextStyle(
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            )
    )

    CompositionLocalProvider(
        LocalEffectiveThemeColors provides colors,
        LocalEffectiveThemeTypography provides typography,
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
