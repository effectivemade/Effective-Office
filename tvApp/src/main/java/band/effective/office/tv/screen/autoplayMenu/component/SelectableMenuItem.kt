package band.effective.office.tv.screen.autoplayMenu.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R
import band.effective.office.tv.screen.autoplayMenu.ItemRes
import band.effective.office.tv.screen.menu.component.MenuItem
import band.effective.office.tv.ui.theme.robotoFontFamily

@Composable
fun SelectableMenuItem(
    modifier: Modifier = Modifier,
    res: ItemRes,
    defaultState: Boolean,
    onClick: () -> Unit,
    onFocus: (Boolean) -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    var isCheck by remember { mutableStateOf(defaultState) }
    var isFocus by remember { mutableStateOf(false) }
    MenuItem(modifier = modifier
        .alpha(if (!isCheck && !isFocus) 0.5f else 1f),
        onClick = {
            isCheck = !isCheck
            onCheckedChange(isCheck)
            onClick()
        }, onFocus = {
            isFocus = it
            onFocus(it)
        }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            when {
                isFocus && res.activeIcon != null -> Image(
                    modifier = Modifier.fillMaxHeight(0.5f),
                    painter = res.activeIcon,
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )

                !isFocus && res.icon != null -> Image(
                    modifier = Modifier.fillMaxHeight(0.5f),
                    painter = res.icon,
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )
            }
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = res.text,
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = robotoFontFamily()
            )
        }
        Row(
            modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.fillMaxHeight(0.5f),
                painter = painterResource(if (isCheck) R.drawable.checkbox_on else R.drawable.checkbox_off),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Preview
@Composable
fun SelectableMenuItemPreview() {
    Column(){
        SelectableMenuItem(modifier = Modifier
            .height(100.dp)
            .clip(shape = RoundedCornerShape(20.dp)),
            defaultState = false,
            res = ItemRes(
                text = stringResource(R.string.story_screen_title),
                icon = painterResource(R.drawable.icon_stories_orange),
                activeIcon = painterResource(R.drawable.icon_stories_white)
            ),
            onClick = { },
            onFocus = {},
            onCheckedChange = { })
        Spacer(modifier = Modifier.height(10.dp))
        SelectableMenuItem(modifier = Modifier
            .height(100.dp)
            .clip(shape = RoundedCornerShape(20.dp)),
            defaultState = true,
            res = ItemRes(
                text = stringResource(R.string.story_screen_title),
                icon = painterResource(R.drawable.icon_stories_orange),
                activeIcon = painterResource(R.drawable.icon_stories_white)
            ),
            onClick = { },
            onFocus = {},
            onCheckedChange = { })
    }
}