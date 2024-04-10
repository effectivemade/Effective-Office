package band.effective.office.tv.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R

val museoCyrl = FontFamily(Font(R.font.museocyrl))
val drukLCGWideMedium = FontFamily(Font(R.font.druktextwidelcg_medium))

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = robotoFontFamily(),
    body1 = TextStyle(
        fontFamily = interFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    h1 = TextStyle(
        fontFamily = robotoFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp
    ),
    h2 = TextStyle(
        fontFamily = robotoFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Color.Black
    ),
    h3 = TextStyle(
        fontFamily = robotoFontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
)

fun robotoFontFamily(): FontFamily =
    FontFamily(
        Font(R.font.roboto_black, weight = FontWeight.Normal),
        Font(R.font.roboto_bold, weight = FontWeight.Bold),
        Font(R.font.roboto_light, weight = FontWeight.Light),
        Font(R.font.roboto_thin, weight = FontWeight.Thin),
        Font(R.font.roboto_italic, weight = FontWeight.Normal, style = FontStyle.Italic)
    )

fun interFontFamily(): FontFamily =
    FontFamily(
        Font(R.font.inter_black),
        Font(R.font.inter_bold, weight = FontWeight.Bold),
        Font(R.font.inter_light, weight = FontWeight.Light),
        Font(R.font.inter_thin, weight = FontWeight.Thin)
    )