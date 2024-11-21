package band.effective.office.elevator.ui.models

sealed interface ElevatorState{
    object Below: ElevatorState
    object Goes: ElevatorState
    object Raised: ElevatorState
}