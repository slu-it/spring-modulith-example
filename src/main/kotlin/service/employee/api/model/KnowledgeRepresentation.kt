package service.employee.api.model

import service.employee.business.model.Knowledge
import java.util.UUID

data class KnowledgeRepresentation(
    val id: UUID,
    val label: String,
    val level: Int,
)

fun Knowledge.toRepresentation(): KnowledgeRepresentation =
    KnowledgeRepresentation(
        id = skillId,
        label = label,
        level = level
    )
