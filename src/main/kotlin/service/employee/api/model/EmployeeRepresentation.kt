package service.employee.api.model

import service.employee.business.model.Employee
import java.util.UUID

data class EmployeeRepresentation(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val knowledge: List<KnowledgeRepresentation>
)

fun Employee.toRepresentation(): EmployeeRepresentation =
    EmployeeRepresentation(
        id = id,
        firstName = data.firstName,
        lastName = data.lastName,
        knowledge = knowledge.map { it.toRepresentation() }
    )
