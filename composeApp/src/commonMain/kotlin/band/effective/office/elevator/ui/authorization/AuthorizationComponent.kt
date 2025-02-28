package band.effective.office.elevator.ui.authorization

import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.useCase.UpdateUserInfoUseCase
import band.effective.office.elevator.domain.validator.UserInfoValidator
import band.effective.office.elevator.ui.authorization.authorization_finish.AuthorizationFinishComponent
import band.effective.office.elevator.ui.authorization.authorization_google.AuthorizationGoogleComponent
import band.effective.office.elevator.ui.authorization.authorization_phone.AuthorizationPhoneComponent
import band.effective.office.elevator.ui.authorization.authorization_profile.AuthorizationProfileComponent
import band.effective.office.elevator.ui.authorization.authorization_telegram.AuthorizationTelegramComponent
import band.effective.office.elevator.ui.authorization.no_booking.NoBookingComponent
import band.effective.office.elevator.ui.authorization.store.AuthorizationStore
import band.effective.office.elevator.ui.authorization.store.AuthorizationStoreFactory
import band.effective.office.network.model.Either
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthorizationComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val openContentFlow: () -> Unit
) :
    ComponentContext by componentContext, KoinComponent {

    private val validator: UserInfoValidator = UserInfoValidator()
    private val navigation = StackNavigation<AuthorizationComponent.Config>()
    private val updateUserInfoUseCase: UpdateUserInfoUseCase by inject()

    private val authorizationStore =
        instanceKeeper.getStore {
            AuthorizationStoreFactory(
                storeFactory = storeFactory
            ).create()
        }

    private fun changePhoneNumber(phoneNumber: String) {
        authorizationStore.accept(AuthorizationStore.Intent.ChangePhoneNumber(phoneNumber))
    }

    private fun changeName(name: String) {
        authorizationStore.accept(AuthorizationStore.Intent.ChangeName(name))
    }

    private fun changePost(post: String) {
        authorizationStore.accept(AuthorizationStore.Intent.ChangePost(post))
    }

    private fun changeTelegramNick(telegramNick: String) {
        authorizationStore.accept(AuthorizationStore.Intent.ChangeTelegram(telegramNick))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<AuthorizationStore.State> = authorizationStore.stateFlow

    val label: Flow<AuthorizationStore.Label> = authorizationStore.labels

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(AuthorizationComponent.Config.GoogleAuth) },
        childFactory = ::child,
        handleBackButton = true
    )

    val childStack: Value<ChildStack<*, AuthorizationComponent.Child>> = stack

    private fun child(
        config: AuthorizationComponent.Config,
        componentContext: ComponentContext
    ): AuthorizationComponent.Child {
        return when (config) {
            is Config.GoogleAuth -> Child.GoogleAuthChild(
                AuthorizationGoogleComponent(
                    componentContext,
                    storeFactory,
                    ::googleAuthOutput
                )
            )

            is Config.PhoneAuth -> Child.PhoneAuthChild(
                AuthorizationPhoneComponent(
                    componentContext,
                    storeFactory,
                    validator,
                    state.value.userData.phoneNumber,
                    ::phoneAuthOutput,
                    ::changePhoneNumber
                )
            )

            is Config.ProfileAuth -> Child.ProfileAuthChild(
                AuthorizationProfileComponent(
                    componentContext,
                    storeFactory,
                    validator,
                    state.value.userData.userName,
                    state.value.userData.post,
                    ::profileAuthOutput,
                    ::changeName,
                    ::changePost
                )
            )

            is Config.TelegramAuth -> Child.TelegramAuthChild(
                AuthorizationTelegramComponent(
                    componentContext,
                    storeFactory,
                    validator,
                    state.value.userData.telegram,
                    ::telegramAuthOutput,
                    ::changeTelegramNick
                )
            )

            is Config.FinishAuth -> Child.FinishAuthChild(
                AuthorizationFinishComponent(
                    componentContext,
                    storeFactory,
                    state.value.userData.userName,
                    state.value.userData.post,
                    state.value.userData.imageUrl,
                    ::finishAuthOutput
                )
            )

            is Config.NoBooking -> Child.NoBookingChild(
                NoBookingComponent(componentContext, ::noBookingOutput)
            )
        }
    }

    private fun googleAuthOutput(output: AuthorizationGoogleComponent.Output) {
        when (output) {
            is AuthorizationGoogleComponent.Output.OpenAuthorizationPhoneScreen -> {
                authorizationStore.accept(AuthorizationStore.Intent.UpdateUserInfo(output.userData))
                navigation.replaceAll(
                    Config.PhoneAuth
                )
            }
        }
    }

    private fun phoneAuthOutput(output: AuthorizationPhoneComponent.Output) {
        when (output) {
            is AuthorizationPhoneComponent.Output.OpenProfileScreen -> navigation.bringToFront(
                Config.ProfileAuth
            )

            is AuthorizationPhoneComponent.Output.OpenGoogleScreen -> navigation.bringToFront(
                AuthorizationComponent.Config.GoogleAuth
            )
        }
    }

    private fun profileAuthOutput(output: AuthorizationProfileComponent.Output) {
        when (output) {
            is AuthorizationProfileComponent.Output.OpenPhoneScreen -> navigation.bringToFront(
                Config.PhoneAuth
            )

            is AuthorizationProfileComponent.Output.OpenTGScreen -> navigation.bringToFront(
                Config.TelegramAuth
            )
        }
    }

    private fun telegramAuthOutput(output: AuthorizationTelegramComponent.Output) {
        when (output) {
            is AuthorizationTelegramComponent.Output.OpenProfileScreen -> navigation.bringToFront(
                Config.ProfileAuth
            )

            // TODO (Artem Gruzdev) @Slivka you should replace this logic to storeFactory
            is AuthorizationTelegramComponent.Output.OpenFinishScreen -> {
                CoroutineScope(Dispatchers.IO).launch {
                    Napier.d {
                        "telegram: ${authorizationStore.state.userData.telegram}"
                    }
                    val response = updateUserInfoUseCase.execute(authorizationStore.state.userData)
                    response.collect { result ->
                        withContext(Dispatchers.Main) {
                            when (result) {
                                is Either.Success -> {
                                    navigation.bringToFront(Config.FinishAuth)
                                }

                                is Either.Error -> {
                                    println("error show content: ${result.error.error} ")
                                    navigation.bringToFront(Config.FinishAuth)
                                    //TODO show error
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun finishAuthOutput(output: AuthorizationFinishComponent.Output) {
        when (output) {
            AuthorizationFinishComponent.Output.OpenNoBookingScreen ->
                navigation.bringToFront(Config.NoBooking)
        }
    }

    private fun noBookingOutput(output: NoBookingComponent.Output) {
        when (output) {
            NoBookingComponent.Output.OpenContentScreen -> openContentFlow()
        }
    }

    sealed class Child {
        class GoogleAuthChild(val component: AuthorizationGoogleComponent) : Child()
        class PhoneAuthChild(val component: AuthorizationPhoneComponent) : Child()
        class ProfileAuthChild(val component: AuthorizationProfileComponent) : Child()
        class TelegramAuthChild(val component: AuthorizationTelegramComponent) : Child()
        class FinishAuthChild(val component: AuthorizationFinishComponent) : Child()
        class NoBookingChild(val component: NoBookingComponent): Child()
    }

    sealed class Config : Parcelable {
        @Parcelize
        object GoogleAuth : Config()

        @Parcelize
        object PhoneAuth : Config()

        @Parcelize
        object ProfileAuth : Config()

        @Parcelize
        object TelegramAuth : Config()

        @Parcelize
        object FinishAuth : Config()

        @Parcelize
        object NoBooking: Config()
    }
}
