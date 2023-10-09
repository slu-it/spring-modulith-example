package service.employee.persistence

import org.springframework.data.repository.findByIdOrNull
import org.springframework.modulith.ApplicationModuleListener
import org.springframework.stereotype.Repository
import service.employee.business.Employee
import service.skill.SkillDeleted
import service.skill.SkillUpdated
import java.util.UUID

@Repository
class EmployeeRepository(
    private val repository: InternalEmployeeRepository
) {

    fun get(id: UUID): Employee? =
        repository.findByIdOrNull(id)

    fun upsert(employee: Employee) {
        repository.save(employee)
    }

    fun update(id: UUID, block: (Employee) -> Employee): Employee? {
        val employee = get(id) ?: return null
        val updatedEmployee = block(employee)
        upsert(updatedEmployee)
        return updatedEmployee
    }

    fun delete(id: UUID): Boolean {
        val existed = repository.existsById(id)
        repository.deleteById(id)
        return existed
    }

    @ApplicationModuleListener
    fun handle(event: SkillUpdated) {
        val skillId = event.newSkill.id
        // brute force, could also be a database statement ...
        repository.findAllWithKnowledgeOfSkill(skillId)
            .map { employee ->
                val updatedKnowledge = employee.knowledge
                    .map { knowledge ->
                        if (knowledge.skillId == skillId) {
                            knowledge.copy(label = event.newSkill.label)
                        } else {
                            knowledge
                        }
                    }
                employee.copy(knowledge = updatedKnowledge)
            }
            .forEach(::upsert)
    }

    @ApplicationModuleListener
    fun handle(event: SkillDeleted) {
        val skillId = event.skillId
        // brute force, could also be a database statement ...
        repository.findAllWithKnowledgeOfSkill(skillId)
            .map { employee ->
                val filteredKnowledge = employee.knowledge
                    .filter { knowledge -> knowledge.skillId != skillId }
                employee.copy(knowledge = filteredKnowledge)
            }
            .forEach(::upsert)
    }
}
