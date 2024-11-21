package band.effective.office.elevator.ui.employee.aboutEmployee.models

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.models.User

fun EmployeeInfo.toUIAbout(): User{
    return User(id = id, imageUrl = logoUrl, userName = name, post = post,
        phoneNumber = phoneNum, telegram = telegramProfile, email = eMail)
}