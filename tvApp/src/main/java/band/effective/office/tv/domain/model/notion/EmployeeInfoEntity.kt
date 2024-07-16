package band.effective.office.tv.domain.model.notion

import band.effective.office.tv.screen.eventStory.models.*
import band.effective.office.tv.utils.DateUtlils
import band.effective.office.tv.utils.atDayStart
import band.effective.office.tv.utils.isSameDay
import java.util.Calendar

class EmployeeInfoEntity(
    val firstName: String,
    val startDate: String,
    val nextBirthdayDate: String,
    val photoUrl: String,
    val employment: String,
)

fun List<EmployeeInfoEntity>.processEmployeeInfo(): List<EmployeeInfoUI> =
    map { employee ->
        listOfNotNull(
            if (employee.nextBirthdayDate.isNotBlank() && isYearCelebrationToday(employee.nextBirthdayDate)) {
                BirthdayUI(
                    employee.firstName,
                    employee.photoUrl,
                    employee.employment,
                )
            } else null,
            if (employee.startDate.isNotBlank()
                && isFirstOrThirdMonthCelebrationToday(
                    employee.startDate, employee.employment
                )) {
                MonthAnniversaryUI(
                    employee.firstName,
                    employee.photoUrl,
                    employee.employment,
                    DateUtlils.getYearsFromStartDate(employee.startDate),
                    DateUtlils.getMonthsFromStartDate(employee.startDate),
                ).run { if (yearsInCompany == 0) this else null }
            } else null,
            if (employee.startDate.isNotBlank() && isYearCelebrationToday(employee.startDate)) {
                AnnualAnniversaryUI(
                    employee.firstName,
                    employee.photoUrl,
                    employee.employment,
                    DateUtlils.getYearsFromStartDate(employee.startDate)
                ).run { if (yearsInCompany == 0) null else this }
            } else null,
            if (employee.startDate.isNotBlank()
                && isNewEmployeeToday(employee.startDate, employee.employment)
                ) {
                NewEmployeeUI(
                    employee.firstName,
                    employee.photoUrl,
                )
            } else null
        )
    }.flatten()

fun isYearCelebrationToday(date: String): Boolean {
    val dateInfo = date.split('-')
    val dayOfMonth = dateInfo[2].toInt()
    val monthNumber = dateInfo[1].toInt()
    val calendar = Calendar.getInstance()
    return (calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth
            && calendar.get(Calendar.MONTH) + 1 == monthNumber)
}

fun isFirstOrThirdMonthCelebrationToday(date: String, employment: String): Boolean {
    val dateInfo = date.split('-')
    val dayOfMonth = dateInfo[2].toInt()
    val monthNumber = dateInfo[1].toInt()
    val year = dateInfo[0].toInt()

    val calendar = Calendar.getInstance()
    val employeeStartDate = Calendar.getInstance()
    employeeStartDate.set(year, monthNumber - 1, dayOfMonth)

    val oneMonthCheck = (calendar.clone() as Calendar)
        .apply { add(Calendar.MONTH, -1) }
    val threeMonthsCheck = (calendar.clone() as Calendar)
        .apply { add(Calendar.MONTH, -3) }

    return oneMonthCheck.isSameDay(employeeStartDate) && employment == "Band"
            || threeMonthsCheck.isSameDay(employeeStartDate)
}

fun isNewEmployeeToday(date: String, employment: String): Boolean {
    if (employment != "Band") return false

    val dateInfo = date.split('-')
    val dayOfMonth = dateInfo[2].toInt()
    val monthNumber = dateInfo[1].toInt()
    val year = dateInfo[0].toInt()

    val employeeStartWorkingDay = Calendar.getInstance().atDayStart()
    employeeStartWorkingDay.set(year, monthNumber - 1, dayOfMonth)

    val endCheckDate = (employeeStartWorkingDay.clone() as Calendar)
    endCheckDate.add(Calendar.DAY_OF_MONTH, 7)

    return Calendar.getInstance() in employeeStartWorkingDay..<endCheckDate
}