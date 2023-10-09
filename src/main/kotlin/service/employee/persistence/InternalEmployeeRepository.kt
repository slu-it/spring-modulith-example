package service.employee.persistence

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import service.employee.business.Employee
import java.util.UUID
import java.util.stream.Stream

@Repository
interface InternalEmployeeRepository : MongoRepository<Employee, UUID> {

    @Query("{ 'knowledge.skillId' : ?0 }")
    fun findAllWithKnowledgeOfSkill(skillId: UUID): Stream<Employee>
}
