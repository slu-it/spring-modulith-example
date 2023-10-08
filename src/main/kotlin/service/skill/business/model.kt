package service.skill.business

import java.time.Instant
import java.util.UUID

data class Skill(
    val id: UUID,
    val data: Data,
    val created: Instant,
    val lastModified: Instant
) {
    data class Data(
        val label: String,
    )
}
