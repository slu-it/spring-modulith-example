package service.employee

import service.employee.business.Employee
import java.util.UUID

data class EmployeeDto(
    val id: UUID,
    val fistName: String,
    val lastName: String,
)

fun Employee.toDto(): EmployeeDto =
    EmployeeDto(
        id = id,
        fistName = data.fistName,
        lastName = data.lastName
    )
