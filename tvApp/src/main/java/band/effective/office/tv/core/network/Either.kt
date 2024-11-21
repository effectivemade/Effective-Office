package band.effective.office.tv.core.network

sealed class Either<out Error, out Data> {
    class Failure<out Error>(val error: Error) : Either<Error, Nothing>()
    class Success<out Data>(val data: Data) : Either<Nothing, Data>()
}

inline fun <Error, Data> Either<Error, Data>.process(
    withData: (Data) -> Unit,
    withError: (Error) -> Unit
) {
    when (this) {
        is Either.Success -> withData(this.data)
        is Either.Failure -> withError(this.error)
    }
}

inline fun <Data, Error, DataOut> Either<Error, Data>.map(
    success: (Data) -> DataOut,
): Either<Error, DataOut> {
    return when (this) {
        is Either.Success -> Either.Success(success(this.data))
        is Either.Failure -> Either.Failure(this.error)
    }
}

inline fun <Data, Error, DataOut, ErrorOut> Either<Error, Data>.map(
    success: (Data) -> DataOut,
    error: (Error) -> ErrorOut
): Either<ErrorOut, DataOut> {
    return when (this) {
        is Either.Success -> Either.Success(success(this.data))
        is Either.Failure -> Either.Failure(error(this.error))
    }
}

inline fun <Data, Error, Result> Either<Error, Data>.unpack(
    success: (Data) -> Result,
    error: (Error) -> Result
): Result {
    return when (this) {
        is Either.Success -> success(this.data)
        is Either.Failure -> error(this.error)
    }
}