package service.employee.business

import java.time.Instant
import java.util.UUID

data class Employee(
    val id: UUID,
    val data: Data,
    val knowledge: List<Knowledge>,
    val created: Instant,
    val lastModified: Instant,
) {
    data class Data(
        val fistName: String,
        val lastName: String,
    )
}

data class Knowledge(
    val skillId: UUID,
    val label: String,
    val level: Int,
)
