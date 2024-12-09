package band.effective.office.elevator.ui.profile.editProfile.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.useCase.GetUserUseCase
import band.effective.office.elevator.domain.useCase.UpdateUserUseCase
import band.effective.office.elevator.domain.validator.ExtendedUserInfoValidator
import band.effective.office.elevator.ui.profile.editProfile.store.ProfileEditStore.*
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import dev.icerock.moko.resources.StringResource
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
    private val validator: ExtendedUserInfoValidator by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): ProfileEditStore =
        object : ProfileEditStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ProfileEditStore",
            initialState = State.Loading,
            bootstrapper = coroutineBootstrapper {
                dispatch(Action.FetchUserInfo)
            },
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}


    private sealed interface Action {
        data object FetchUserInfo : Action
    }

    private sealed interface Msg {
        data class ProfileData(val user: User) : Msg

        data class PhoneError(val message: StringResource) : Msg
        data class NameError(val message: StringResource) : Msg
        data class PostError(val message: StringResource) : Msg
        data class TelegramError(val message: StringResource) : Msg

        data object ValidPhone: Msg
        data object ValidName: Msg
        data object ValidPost: Msg
        data object ValidTelegram: Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            val state = getState()

            when (intent) {
                Intent.BackInProfileClicked -> returnToProfile()
                is Intent.SaveChangesClicked -> {
                    when (state) {
                        State.Loading -> return
                        is State.Data -> saveChanges(state, intent)
                    }
                }
            }
        }

        private fun saveChanges(data: State.Data, intent: Intent.SaveChangesClicked) {
            scope.launch {
                val updatedUser = data.user.copy(
                    userName = intent.userName,
                    post = intent.post,
                    phoneNumber = intent.phoneNumber,
                    telegram = intent.telegram,
                )
                dispatch(Msg.ProfileData(user = updatedUser))
                val isPhoneNumberValid = checkPhoneNumber(intent.phoneNumber)
                val isUserValid = checkUserdata(intent.userName)
                val isPostValid = checkPost(intent.post)
                val isTelegramValid = checkTelegram(intent.telegram)

                if (isPhoneNumberValid && isUserValid && isPostValid && isTelegramValid) {
                    withContext(Dispatchers.Main) {
                        updateUserUseCase.execute(updatedUser).collect { user ->
                            when (user) {
                                is Either.Success -> {
                                    dispatch(Msg.ProfileData(user = updatedUser))
                                    publish(Label.SavedChange)
                                }
                                is Either.Error -> {
                                    publish(Label.ServerError)
                                }
                            }
                        }
                    }
                }
            }
        }

        private fun checkTelegram(telegram: String): Boolean {
            return when (val result = validator.checkTelegramNick(telegram)) {
                is ExtendedUserInfoValidator.Result.Invalid -> {
                    dispatch(Msg.TelegramError(result.message))
                    false
                }
                ExtendedUserInfoValidator.Result.Valid -> {
                    dispatch(Msg.ValidTelegram)
                    true
                }
            }
        }

        private fun checkPost(post: String): Boolean {
            return when (val result = validator.checkPost(post)) {
                is ExtendedUserInfoValidator.Result.Invalid -> {
                    dispatch(Msg.PostError(result.message))
                    false
                }
                ExtendedUserInfoValidator.Result.Valid -> {
                    dispatch(Msg.ValidPost)
                    true
                }
            }
        }

        private fun checkUserdata(userName: String): Boolean {
            return when (val result = validator.checkName(userName)) {
                is ExtendedUserInfoValidator.Result.Invalid -> {
                    dispatch(Msg.NameError(result.message))
                    false
                }
                ExtendedUserInfoValidator.Result.Valid -> {
                    dispatch(Msg.ValidName)
                    true
                }
            }
        }

        private fun checkPhoneNumber(phone: String): Boolean {
            return when (val result = validator.checkPhone(phone)) {
                is ExtendedUserInfoValidator.Result.Invalid -> {
                    dispatch(Msg.PhoneError(result.message))
                    false
                }
                ExtendedUserInfoValidator.Result.Valid -> {
                    dispatch(Msg.ValidPhone)
                    true
                }
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

        private fun returnToProfile() {
            publish(Label.ReturnedInProfile)
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State {
            return when (this) {
                State.Loading -> {
                    when(msg) {
                        is Msg.ProfileData -> State.Data(user = msg.user)
                        else -> this
                    }
                }
                is State.Data -> {
                    when (msg) {
                        is Msg.ProfileData -> State.Data(user = msg.user)
                        is Msg.NameError -> copy(nameError = msg.message)
                        is Msg.PhoneError -> copy(phoneNumberError = msg.message)
                        is Msg.PostError -> copy(postError = msg.message)
                        is Msg.TelegramError -> copy(telegramError = msg.message)
                        Msg.ValidName -> copy(nameError = null)
                        Msg.ValidPhone -> copy(phoneNumberError = null)
                        Msg.ValidPost -> copy(postError = null)
                        Msg.ValidTelegram -> copy(telegramError = null)
                    }
                }
            }
        }
    }
}
