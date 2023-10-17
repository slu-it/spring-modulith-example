package service.employee.business.model

import java.util.UUID

data class Knowledge(
    val skillId: UUID,
    val label: String,
    val level: Int,
)
