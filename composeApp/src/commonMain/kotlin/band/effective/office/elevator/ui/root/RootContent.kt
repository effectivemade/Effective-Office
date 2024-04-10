package band.effective.office.elevator.ui.root

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import band.effective.office.elevator.components.LoadingIndicator
import band.effective.office.elevator.ui.authorization.AuthorizationScreen
import band.effective.office.elevator.ui.content.Content
import band.effective.office.elevator.ui.main.MainScreen
import band.effective.office.elevator.ui.root.RootComponent.*
import band.effective.office.elevator.ui.root.store.RootStore
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                RootStore.Label.UserAlreadySigned -> component.onOutput(
                    Output.OpenContent
                )

                RootStore.Label.UserNotSigned -> component.onOutput(Output.OpenAuthorizationFlow)
            }
        }
    }

    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is Child.AuthorizationChild -> AuthorizationScreen(child.component)
            is Child.ContentChild -> Content(child.component)
            is Child.Undefined -> LoadingIndicator()
        }
    }
}

@Composable
fun AnimatedChild(visible: () -> Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible(),
        enter = fadeIn(spring(stiffness = Spring.StiffnessHigh)) + scaleIn(spring(stiffness = Spring.StiffnessHigh)),
        exit = fadeOut() + scaleOut()
    ) {
        content()
    }
}
