package band.effective.office.elevator.ui.root.store

import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.data.database.DBSource
import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.useCase.SignInUseCase
import band.effective.office.elevator.ui.root.store.RootStore.Label
import band.effective.office.elevator.ui.root.store.RootStore.State
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class RootStoreImplFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val signInUseCase: SignInUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): RootStore =
        object : RootStore, Store<Nothing, State, Label> by storeFactory.create(
            name = "RootStoreImplStore",
            initialState = State(),
            bootstrapper = coroutineBootstrapper {
                launch { dispatch(Action.CheckUserAlreadySigned) }
            },
            executorFactory = ::ExecutorImpl
        ) {}

    private sealed interface Action {
        object CheckUserAlreadySigned : Action
    }

    private inner class ExecutorImpl : CoroutineExecutor<Nothing, Action, State, Nothing, Label>() {
        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                Action.CheckUserAlreadySigned -> checkUserAlreadySigned()
            }
        }

        private fun checkUserAlreadySigned() {
            scope.launch {
                signInUseCase
                    .signIn()
                    .collect { response ->
                        when (response) {
                            is Either.Error<*> -> {
                                publish(Label.UserNotSigned)
                            }

                            is Either.Success<*> -> {
                                publish(Label.UserAlreadySigned)
                            }
                        }
                    }
            }
        }
    }
}
