package band.effective.office.elevator.ui.authorization.authorization_finish.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor

class AuthorizationFinishStoreFactory(
    private val storeFactory: StoreFactory,
    private val name: String,
    private val post: String,
    private val avatarUrl: String,
) {

    fun create(): AuthorizationFinishStore =
        object : AuthorizationFinishStore,
            Store<Nothing, AuthorizationFinishStore.State, Nothing> by storeFactory.create(
                name = "Authorization finish",
                initialState = AuthorizationFinishStore.State(name, post, avatarUrl),
                bootstrapper = SimpleBootstrapper(Action.InitUser),
                executorFactory = { ExecutorImpl() },
            ) { }

    private sealed interface Action {
        data object InitUser : Action
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Nothing, Action, AuthorizationFinishStore.State, Nothing, Nothing>() {

        override fun executeIntent(
            intent: Nothing,
            getState: () -> AuthorizationFinishStore.State,
        ) = Unit


        override fun executeAction(
            action: Action,
            getState: () -> AuthorizationFinishStore.State
        ) = Unit
    }
}
