package band.effective.office.tablet.utils

import band.effective.office.network.model.Either


fun <ErrorType, DataType> Either<ErrorType, DataType>.unbox(
    errorHandler: (ErrorType) -> DataType,
    successHandler: ((DataType) -> DataType)? = null
): DataType =
    when (this) {
        is Either.Error -> errorHandler(error)
        is Either.Success -> successHandler?.invoke(data) ?: data
    }

fun <OldErrorType, oldDataType, ErrorType, DataType> Either<OldErrorType, oldDataType>.map(
    errorMapper: (OldErrorType) -> ErrorType,
    successMapper: (oldDataType) -> DataType,
): Either<ErrorType, DataType> =
    when (this) {
        is Either.Error -> Either.Error(errorMapper(error))
        is Either.Success -> Either.Success(successMapper(data))
    }

suspend fun <OldErrorType, oldDataType, ErrorType, DataType> Either<OldErrorType, oldDataType>.asyncMap(
    errorMapper: suspend (OldErrorType) -> ErrorType,
    successMapper: suspend (oldDataType) -> DataType,
): Either<ErrorType, DataType> =
    when (this) {
        is Either.Error -> Either.Error(errorMapper(error))
        is Either.Success -> Either.Success(successMapper(data))
    }