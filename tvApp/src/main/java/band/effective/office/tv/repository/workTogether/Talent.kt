package band.effective.office.tv.repository.workTogether

import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity
import band.effective.office.tv.screen.supernova.model.SupernovaUserUi

data class Talent(
    val id: String,
    val score: Int
)

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