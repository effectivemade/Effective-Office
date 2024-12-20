package band.effective.office.tv.repository.supernova

import band.effective.office.tv.domain.model.StoryDomainModel
import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity
import band.effective.office.tv.screen.ratings.supernova.model.SupernovaUserUi

data class Talent(
    val id: String,
    val score: Int
): StoryDomainModel()

fun List<Talent>.toUi(employees: List<EmployeeInfoEntity>) =
    mapNotNull { talent ->
        employees.find { talent.id == it.id}?.let { employee ->
            SupernovaUserUi(
                photoUrl = employee.photoUrl,
                name = employee.firstName.split(' ')[0],
                score = talent.score
            )
        }
    }