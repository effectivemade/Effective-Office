package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ExtendedTheme
import band.effective.office.elevator.getDefaultFont

@Composable
fun BottomDialog(modifier: Modifier, title: String) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(vertical = 24.dp, horizontal = 16.dp)
            .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .fillMaxWidth()
            .then(other = modifier)
    ) {
        Text(text = title, style = MaterialTheme.typography.h6, color = Color.Black)
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedIconButton(
                onClick = {

                },
                border = BorderStroke(1.dp, ExtendedTheme.colors.purple_heart_800),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = "done button",
                        tint = ExtendedTheme.colors.purple_heart_800,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Рабочее место",
                        color = ExtendedTheme.colors.purple_heart_800,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedIconButton(
                onClick = {

                },
                border = BorderStroke(1.dp, ExtendedTheme.colors.purple_heart_800),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = "done button",
                        tint = ExtendedTheme.colors.purple_heart_800,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Переговорка",
                        color = ExtendedTheme.colors.purple_heart_800,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = {

                },
                border = BorderStroke(1.dp, ExtendedTheme.colors.trinidad_700),
                modifier = Modifier.fillMaxWidth(0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            {
                Text(
                    text = "Сбросить",
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 19.5.sp,
                        fontFamily = getDefaultFont(),
                        fontWeight = FontWeight(500),
                        color = Color(0xFFC2410C),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ExtendedTheme.colors.trinidad_700,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            {
                Text(
                    text = "Ок",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 19.5.sp,
                        fontFamily = getDefaultFont(),
                        fontWeight = FontWeight(500),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    )
                )
            }
        }
    }
}