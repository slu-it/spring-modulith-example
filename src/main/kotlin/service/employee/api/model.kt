package service.employee.api

import service.employee.business.Employee
import service.employee.business.Knowledge
import java.util.UUID

data class EmployeeRepresentation(
    val id: UUID,
    val fistName: String,
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
        fistName = data.fistName,
        lastName = data.lastName,
        knowledge = knowledge.map { it.toRepresentation() }
    )

fun Knowledge.toRepresentation(): KnowledgeRepresentation =
    KnowledgeRepresentation(
        id = skillId,
        label = label,
        level = level
    )
