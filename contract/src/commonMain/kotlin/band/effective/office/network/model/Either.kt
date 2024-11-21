package band.effective.office.network.model

sealed interface Either<out ErrorType, out DataType> {
    data class Error<out ErrorType>(val error: ErrorType) : Either<ErrorType, Nothing>
    data class Success<out DataType>(val data: DataType) : Either<Nothing, DataType>
}