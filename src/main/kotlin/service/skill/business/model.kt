package service.skill.business

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.UUID

@Document("skills")
data class Skill(
    @Id val id: UUID,
    val data: Data,
    val created: Instant,
    val lastModified: Instant
) {
    data class Data(
        val label: String,
    )
}
