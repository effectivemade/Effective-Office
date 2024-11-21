package band.effective.office.tv.screen.load

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R
import band.effective.office.tv.screen.leaderIdEvents.components.TextWithCaptionAndIcon
import band.effective.office.tv.ui.theme.robotoFontFamily
import kotlinx.coroutines.delay

@Composable
fun LoadScreen(){
    var ticks by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while(true) {
            delay(500)
            ticks++
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextWithCaptionAndIcon(
            resourceId = R.drawable.logo,
            text = "effective",
            fontSize = 60.sp,
            iconSize = 50.dp
        )
        LoadCircle(modifier = Modifier.size(100.dp),ticks)
    }
}

@Composable
fun LoadScreen(text: String){
    var ticks by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while(true) {
            delay(500)
            ticks++
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = text,
            color = Color.White,
            fontSize = 50.sp,
            fontFamily = robotoFontFamily()
        )
        LoadCircle(modifier = Modifier.size(100.dp),ticks)
    }
}