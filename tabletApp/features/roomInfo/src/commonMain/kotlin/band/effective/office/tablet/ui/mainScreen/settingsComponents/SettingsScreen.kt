package band.effective.office.tablet.ui.mainScreen.settingsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.ui.mainScreen.mainScreen.uiComponents.LoadMainScreen
import band.effective.office.tablet.ui.mainScreen.settingsComponents.store.SettingsStore
import band.effective.office.tablet.ui.mainScreen.settingsComponents.uiComponents.ChooseButtonView
import band.effective.office.tablet.ui.mainScreen.settingsComponents.uiComponents.ExitButtonView
import band.effective.office.tablet.ui.mainScreen.settingsComponents.uiComponents.GridRooms
import band.effective.office.tablet.ui.mainScreen.settingsComponents.uiComponents.TitleView

@Composable
fun SettingsScreen(component: SettingsComponent) {
    val state by component.state.collectAsState()

    if (state.loading) {
        LoadMainScreen()
    } else {
        SettingsView(
            listRooms = state.rooms,
            nameCurrentRoom = state.currentName,
            onExitApp = { component.onIntent(SettingsStore.Intent.OnExitApp) },
            onChangeCurrentName = { name: String ->
                component.onIntent(SettingsStore.Intent.ChangeCurrentNameRoom(name))
            },
            onSaveData = {
                component.onIntent(SettingsStore.Intent.SaveData)
            }
        )
    }
}

@Composable
fun SettingsView(
    listRooms: List<String>,
    nameCurrentRoom: String,
    onExitApp: () -> Unit,
    onChangeCurrentName: (name: String) -> Unit,
    onSaveData: () -> Unit
) {
    val padding = 35.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),

        ) {
        ExitButtonView(
            modifier = Modifier
                .fillMaxHeight(0.15f)
                .padding(start = padding),
            onExitApp = onExitApp
        )
        TitleView(modifier = Modifier.padding(start = padding))
        Spacer(modifier = Modifier.height(15.dp))
        GridRooms(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = padding)
                .selectableGroup(),
            data = listRooms,
            currentName = nameCurrentRoom,
            onChangeCurrentName = onChangeCurrentName
        )
        Spacer(modifier = Modifier.height(80.dp))
        if (nameCurrentRoom.isNotEmpty()) {
            ChooseButtonView(
                modifier = Modifier
                    .fillMaxWidth(),
                nameRoom = nameCurrentRoom,
                onSaveData = onSaveData
            )
        }
    }
}