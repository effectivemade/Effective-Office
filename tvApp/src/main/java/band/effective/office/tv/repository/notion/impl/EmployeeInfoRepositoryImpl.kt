package band.effective.office.tv.repository.notion.impl

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity
import band.effective.office.tv.network.notion.EmployeeInfoRemoteDataSourceImpl
import band.effective.office.tv.repository.notion.EmployeeInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
@Deprecated("Let's use WorkTogether")
@Singleton
class EmployeeInfoRepositoryImpl @Inject constructor(private val employeeInfoRemoteDataSourceImpl: EmployeeInfoRemoteDataSourceImpl) :
    EmployeeInfoRepository {
    override fun latestEvents(): Flow<Either<String, List<EmployeeInfoEntity>>> = flow {
        val employeesInfo = employeeInfoRemoteDataSourceImpl.fetchLatestBirthdays()
        employeesInfo.collect { either ->
            when (either) {
                is Either.Success -> {
                    emit(Either.Success(either.data.map { employee ->
                        EmployeeInfoEntity(
                            employee.firstName.orEmpty(),
                            employee.startDate.orEmpty(),
                            employee.nextBirthdayDate.orEmpty(),
                            employee.photoUrl.orEmpty(),
                            isIntern = false,
                        )
                    }))
                }
                is Either.Failure -> {
                    emit(either)
                }
            }

        }

    }
}
