package band.effective.office.elevator.utils

import band.effective.office.elevator.ui.employee.allEmployee.models.mappers.EmployeeCard

fun changeEmployeeShowedList(query: String, allEmployeesCards: List<EmployeeCard>): List<EmployeeCard> =
    allEmployeesCards.filter{ it.name.lowercase().contains(query.lowercase())}
