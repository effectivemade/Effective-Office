package band.effective.office.elevator.ui.authorization.authorization_google

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.companyTitleColor
import band.effective.office.elevator.components.GoogleSignInButton
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.orange60
import band.effective.office.elevator.purple60
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .blur(radius = 200.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / 4
            val topMargin = size.height / 5


            drawCircle(
                color = purple60,
                radius = radius,
                center = Offset(x = size.width / 2, y = topMargin)
            )


            drawCircle(
                color = orange60,
                radius = radius,
                center = Offset(x = size.width / 2, y = topMargin + radius * 2)
            )
        }
    }
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Image(
                    painterResource(MainRes.images.effective_logo),
                    contentDescription = "Effective logo",
                    modifier = Modifier.size(90.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(MainRes.strings.company_title_name),
                    color = companyTitleColor,
                    style = MaterialTheme.typography.h2


                )
                Text(
                    text = stringResource(MainRes.strings.company_subtitle_name),
                    color = companyTitleColor,
                    style = MaterialTheme.typography.h6
                )
            }

            GoogleSignInButton(
                modifier = Modifier,
                onClick = { onEvent(AuthorizationGoogleStore.Intent.SignInButtonClicked) })
        }
    }
}

