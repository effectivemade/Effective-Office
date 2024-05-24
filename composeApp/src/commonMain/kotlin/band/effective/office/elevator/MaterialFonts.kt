package band.effective.office.elevator

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import dev.icerock.moko.resources.compose.fontFamilyResource

@Composable
internal fun getDefaultFont(): FontFamily {
    return fontFamilyResource(MainRes.fonts.roboto_regular)
}

@Composable
internal fun getPromoFont(): FontFamily {
    return fontFamilyResource(MainRes.fonts.museocyrl_bold)
}