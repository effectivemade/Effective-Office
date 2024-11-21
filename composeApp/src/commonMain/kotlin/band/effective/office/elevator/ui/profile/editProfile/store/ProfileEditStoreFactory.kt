package band.effective.office.elevator.ui.profile.editProfile.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.useCase.GetUserUseCase
import band.effective.office.elevator.domain.useCase.UpdateUserUseCase
import band.effective.office.elevator.ui.models.validator.UserInfoValidator
import band.effective.office.elevator.ui.profile.editProfile.store.ProfileEditStore.*
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class ProfileEditStoreFactory(
    private val storeFactory: StoreFactory,
) : KoinComponent {

    private val getUserUseCase: GetUserUseCase by inject()
    private val updateUserUseCase: UpdateUserUseCase by inject()
    private val validator: UserInfoValidator = UserInfoValidator()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): ProfileEditStore =
        object : ProfileEditStore,
            Store<Intent, State, Label>
            by storeFactory.create(
                name = "ProfileEditStore",
                initialState = State(user = User.defaultUser),
                bootstrapper = coroutineBootstrapper {
                    dispatch(Action.FetchUserInfo)
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}


    private sealed interface Action {
        object FetchUserInfo : Action
    }

    private sealed interface Msg {
        data class ProfileData(val user: User) : Msg
        data class ErrorPhone(
            val errorPhone: Boolean
        ) : Msg

        data class ErrorName(
            val isNameError: Boolean
        ) : Msg

        data class ErrorPost(
            val isPostError: Boolean
        ) : Msg

        data class ErrorTelegram(
            val isTelegramError: Boolean
        ) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.BackInProfileClicked -> doReturnProfile()
                is Intent.SaveChangeClicked -> doSaveChange(getState(), intent)
            }
        }

        private fun doSaveChange(user: State, intent: Intent.SaveChangeClicked) {
            scope.launch  {
                val uptUser = User(
                    id = user.user.id,
                    imageUrl = user.user.imageUrl,
                    userName = intent.userName, post = intent.post,
                    phoneNumber = intent.phoneNumber,
                    telegram = intent.telegram,
                    email = user.user.email,
                )
                dispatch(Msg.ProfileData(user = uptUser))
                if (checkPhoneNumber(intent.phoneNumber)
                    && checkUserdata(userName = intent.userName)
                    && checkPost(
                        intent.post) && checkTelegram(intent.telegram)
                ) {
                    updateUserUseCase.execute(uptUser).collect{ user ->
                            withContext(Dispatchers.Main) {
                                when (user) {
                                    is Either.Success -> {
                                        dispatch(Msg.ProfileData(user = user.data))
                                        publish(Label.SavedChange)
                                    }

                                    is Either.Error -> {
                                      publish(Label.Error(MainRes.strings.server_error))
                                    }
                                }

                            }

                    }

                }
            }

        }

        private fun checkTelegram(telegram: String): Boolean {
            return if (validator.checkTelegramNick(telegram)) {
                dispatch(
                    Msg.ErrorTelegram(
                        isTelegramError = false
                    )
                )
                true
            } else {
                dispatch(
                    Msg.ErrorTelegram(
                        isTelegramError = true
                    )
                )
                publish(Label.Error(MainRes.strings.telegram_format_error))
                false
            }
        }

        private fun checkPost(post: String): Boolean {
            return if (validator.checkPost(post)) {
                dispatch(
                    Msg.ErrorPost(
                        isPostError = false
                    )
                )
                true
            } else {
                dispatch(
                    Msg.ErrorPost(
                        isPostError = true
                    )
                )
                publish(Label.Error(MainRes.strings.profile_post_format_error))
                false
            }
        }

        private fun checkUserdata(userName: String): Boolean {
            return if (validator.checkName(userName)) {
                dispatch(
                    Msg.ErrorName(
                        isNameError = false
                    )
                )
                true
            } else {
                dispatch(
                    Msg.ErrorName(
                        isNameError = true
                    )
                )
                publish(Label.Error(MainRes.strings.profile_name_format_error))
                false
            }
        }

        private fun checkPhoneNumber(phone: String): Boolean {
            return if (validator.checkPhone(phone)) {
                dispatch(
                    Msg.ErrorPhone(
                        errorPhone = false
                    )
                )
                true
            } else {
                dispatch(
                    Msg.ErrorPhone(
                        errorPhone = true
                    )
                )
                publish(Label.Error(MainRes.strings.number_format_error))
                false
            }

        }


        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                Action.FetchUserInfo -> fetchUserInfo()
            }
        }

        private fun fetchUserInfo() {
            scope.launch(Dispatchers.IO) {
                getUserUseCase.executeInFormat().collect { user ->
                    withContext(Dispatchers.Main) {
                        when (user) {
                            is Either.Success -> {
                                dispatch(Msg.ProfileData(user = user.data))
                            }

                            is Either.Error -> {
                                // TODO show error on UI
                                user.error.saveData?.let {
                                    dispatch(Msg.ProfileData(user = it))
                                }
                            }
                        }

                    }
                }
            }
        }

        private fun doReturnProfile() {
            publish(Label.ReturnedInProfile)
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                is Msg.ProfileData -> {
                    copy(
                        isData = true,
                        isLoading = false,
                        user = message.user
                    )
                }

                is Msg.ErrorPhone ->
                    copy(isErrorPhone = message.errorPhone)

                is Msg.ErrorName -> copy(isErrorName = message.isNameError)
                is Msg.ErrorPost -> copy(isErrorPost = message.isPostError)
                is Msg.ErrorTelegram -> copy(isErrorTelegram = message.isTelegramError)
            }
    }
}