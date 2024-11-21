package band.effective.office.tv.screen.message.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R

@Composable
fun EmptyMessageScreen(modifier: Modifier, uselessFact: String = "") {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uselessFact != "") {
            Text(text = stringResource(id = R.string.no_messages_have_facts))
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = uselessFact,
                fontSize = 25.sp
            )
        } else {
            Text(text = stringResource(id = R.string.no_messages))
        }
    }

}