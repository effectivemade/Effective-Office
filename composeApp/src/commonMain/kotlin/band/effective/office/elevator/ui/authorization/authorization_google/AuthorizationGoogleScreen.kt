package band.effective.office.elevator.ui.authorization.authorization_google

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.companyColor
import band.effective.office.elevator.components.GoogleSignInButton
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource


@Composable
fun AuthorizationGoogleScreen(component: AuthorizationGoogleComponent) {

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                is AuthorizationGoogleStore.Label.AuthorizationFailure -> showToast(label.message)
                is AuthorizationGoogleStore.Label.AuthorizationSuccess -> {
                    component.onOutput(
                        AuthorizationGoogleComponent.Output.OpenAuthorizationPhoneScreen(label.user)
                    )
                }
            }
        }
    }

    AuthorizationGoogleScreenContent(onEvent = component::onEvent)
}


@Composable
private fun AuthorizationGoogleScreenContent(onEvent: (AuthorizationGoogleStore.Intent) -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(MainRes.images.effective_logo),
                    contentDescription = "Effective logo",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(MainRes.strings.company_name),
                    color = companyColor,
                    style = MaterialTheme.typography.h4
                )
            }

            GoogleSignInButton(
                modifier = Modifier,
                onClick = { onEvent(AuthorizationGoogleStore.Intent.SignInButtonClicked) })
        }
    }
}

