package band.effective.office.elevator.ui.authorization.authorization_finish.store

import com.arkivanov.mvikotlin.core.store.Store

interface AuthorizationFinishStore :
    Store<Nothing, AuthorizationFinishStore.State, Nothing> {

    data class State(
        val name: String,
        val post: String,
        val avatarUrl: String,
    )
}
