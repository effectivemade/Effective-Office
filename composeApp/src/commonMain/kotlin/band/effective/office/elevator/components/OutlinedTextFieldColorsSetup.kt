package band.effective.office.elevator.components

import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.textGrayColor

@Composable
fun OutlinedTextColorsSetup() = TextFieldDefaults.outlinedTextFieldColors(
//                    region::Border
    focusedBorderColor = EffectiveTheme.colors.stroke.accent,
    unfocusedBorderColor = EffectiveTheme.colors.stroke.primary,
    disabledBorderColor = EffectiveTheme.colors.stroke.primary,
    errorBorderColor = EffectiveTheme.colors.stroke.error,
//                    endregion

//                    region::Trailing icon
    trailingIconColor = ExtendedThemeColors.colors.blackColor,
    disabledTrailingIconColor = textGrayColor,
    errorTrailingIconColor = ExtendedThemeColors.colors.blackColor,
//                    endregion

//                    region::Leading icon
    disabledLeadingIconColor = textGrayColor,
//                    endregion

//                    region::Cursor colors
    cursorColor = ExtendedThemeColors.colors.trinidad_400,
    errorCursorColor = ExtendedThemeColors.colors.error,
//                    endregion
    textColor = Color.Black,
    disabledTextColor = Color.Black
)
