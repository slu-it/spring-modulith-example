package service.employee.business.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.UUID

@Document("employees")
data class Employee(
    @Id val id: UUID,
    val data: Data,
    val knowledge: List<Knowledge>,
    val created: Instant,
    val lastModified: Instant,
) {
    data class Data(
        val firstName: String,
        val lastName: String,
    )
}
