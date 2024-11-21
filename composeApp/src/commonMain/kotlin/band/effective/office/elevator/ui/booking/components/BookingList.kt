package band.effective.office.elevator.ui.booking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun WorkSpaceList(
    scrollState: LazyListState,
    workSpaces: List<WorkSpaceUI>,
    onClickOpenBookAccept: (WorkSpaceUI) -> Unit
) {
    when (workSpaces.isEmpty()) {
        true ->
            NoFreeWorkspaces()
        false ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.onBackground)
                    .padding(horizontal = 16.dp),
                state = scrollState
            ) {
                items(workSpaces) { workSpace ->
                    BookingCard(
                        workSpace = workSpace,
                        onClickOpenBookAccept = onClickOpenBookAccept
                    )
                }
            }
    }
}

@Composable
private fun NoFreeWorkspaces() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(MainRes.strings.no_free_place),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
    }
}

