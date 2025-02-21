package band.effective.office.elevator.ui.authorization.no_booking

import com.arkivanov.decompose.ComponentContext

class NoBookingComponent(
    componentContext: ComponentContext,
    private val output: (Output) -> Unit,
) : ComponentContext by componentContext {

    fun onOutput(output: Output) {
        output(output)
    }

    sealed interface Output {
        data object OpenContentScreen: Output
    }
}
