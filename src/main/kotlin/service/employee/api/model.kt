package service.employee.api

import service.employee.business.Employee
import service.employee.business.Knowledge
import java.util.UUID

data class EmployeeRepresentation(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val knowledge: List<KnowledgeRepresentation>
)

data class KnowledgeRepresentation(
    val id: UUID,
    val label: String,
    val level: Int,
)

fun Employee.toRepresentation(): EmployeeRepresentation =
    EmployeeRepresentation(
        id = id,
        firstName = data.firstName,
        lastName = data.lastName,
        knowledge = knowledge.map { it.toRepresentation() }
    )

fun Knowledge.toRepresentation(): KnowledgeRepresentation =
    KnowledgeRepresentation(
        id = skillId,
        label = label,
        level = level
    )
