package band.effective.office.elevator.ui.root

import band.effective.office.elevator.ui.authorization.AuthorizationComponent
import band.effective.office.elevator.ui.content.ContentComponent
import band.effective.office.elevator.ui.root.store.RootStore
import band.effective.office.elevator.ui.root.store.RootStoreImplFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow

class RootComponent internal constructor(
    componentContext: ComponentContext,
    private val storeFactory: DefaultStoreFactory,
    private val authorization: (ComponentContext, () -> Unit) -> AuthorizationComponent,
    private val content: (ComponentContext, () -> Unit) -> ContentComponent,
) : ComponentContext by componentContext {

    init {
        Napier.base(DebugAntilog())
    }

    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        childFactory = ::child,
        initialConfiguration = Config.Undefined
    )

    val childStack: Value<ChildStack<*, Child>> = stack

    private val rootStore =
        instanceKeeper.getStore {
            RootStoreImplFactory(
                storeFactory = storeFactory
            ).create()
        }

    val label: Flow<RootStore.Label> = rootStore.labels

    constructor(componentContext: ComponentContext, storeFactory: DefaultStoreFactory) : this(
        componentContext,
        storeFactory,
        authorization = { childContext, output ->
            AuthorizationComponent(
                childContext,
                storeFactory,
                openContentFlow = output
            )
        },
        content = { childContext, onSignOut ->
            ContentComponent(
                childContext,
                storeFactory,
                openAuthorizationFlow = onSignOut
            )
        },
    )

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Authorization -> Child.AuthorizationChild(authorization(componentContext){
                navigation.replaceAll(Config.Content)
            })

            is Config.Content -> Child.ContentChild(content(componentContext) {
                navigation.replaceAll(Config.Authorization)
            })

            Config.Undefined -> Child.Undefined
        }

    fun onOutput(output: Output) {
        println("Output")
        when (output) {
            Output.OpenContent -> navigation.replaceAll(Config.Content, onComplete = { println("navigation compli") })
            Output.OpenAuthorizationFlow -> navigation.replaceAll(Config.Authorization)
        }
    }

    sealed interface Output {
        object OpenContent : Output
        object OpenAuthorizationFlow : Output
    }

    sealed class Child {
        object Undefined : Child()
        class AuthorizationChild(val component: AuthorizationComponent) : Child()
        class ContentChild(val component: ContentComponent) : Child()
    }

    sealed class Config : Parcelable {
        @Parcelize
        object Undefined : Config()

        @Parcelize
        object Authorization : Config()

        @Parcelize
        object Content : Config()
    }
}
