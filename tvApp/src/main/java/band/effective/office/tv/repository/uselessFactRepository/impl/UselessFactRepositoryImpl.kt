package band.effective.office.tv.repository.uselessFactRepository.impl

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.network.uselessFact.UselessFactApi
import band.effective.office.tv.repository.uselessFactRepository.UselessFactRepository
import javax.inject.Inject

class UselessFactRepositoryImpl @Inject constructor(private val api: UselessFactApi) :
    UselessFactRepository {
    override suspend fun fact(): String =
        when (val fact = api.getFact()) {
            is Either.Success -> fact.data.text
            is Either.Failure -> ""
        }
}