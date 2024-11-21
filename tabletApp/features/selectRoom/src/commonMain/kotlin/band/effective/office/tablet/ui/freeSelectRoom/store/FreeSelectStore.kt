package band.effective.office.tablet.ui.freeSelectRoom.store

import com.arkivanov.mvikotlin.core.store.Store

interface FreeSelectStore : Store<FreeSelectStore.Intent, FreeSelectStore.State, FreeSelectStore.Label> {
    sealed interface Intent {
        object OnFreeSelectRequest : Intent
        object OnCloseWindowRequest : Intent
    }

    sealed interface Label {
        object Close : Label
    }

    data class State(
        val isLoad: Boolean,
        val isSuccess: Boolean
    ) {
        companion object {
            val defaultState =
                State(
                    isLoad = false,
                    isSuccess = true
                )
        }
    }
}