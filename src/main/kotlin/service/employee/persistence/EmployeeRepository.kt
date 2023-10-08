package service.employee.persistence

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Repository
import service.employee.business.Employee
import service.employee.business.Knowledge
import service.skill.SkillDeleted
import service.skill.SkillUpdated
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Repository
class EmployeeRepository {

    private val database = ConcurrentHashMap<UUID, Employee>()

    fun get(id: UUID): Employee? =
        database[id]

    fun upsert(employee: Employee) {
        database[employee.id] = employee
    }

    fun update(id: UUID, block: (Employee) -> Employee): Employee? {
        val employee = get(id) ?: return null
        val updatedEmployee = block(employee)
        upsert(updatedEmployee)
        return updatedEmployee
    }

    fun delete(id: UUID): Boolean =
        database.remove(id) != null

    @Async
    @EventListener
    fun handle(event: SkillUpdated) {
        val skillId = event.newSkill.id

        val updatedEmployees = database.values
            .filter { employee -> employee.hasKnowledge(skillId) }
            .map { employee ->
                val updatedKnowledge = employee.knowledge
                    .map { knowledge ->
                        if (knowledge.isOfSkill(skillId)) {
                            knowledge.copy(label = event.newSkill.label)
                        } else {
                            knowledge
                        }
                    }
                employee.copy(knowledge = updatedKnowledge)
            }

        updatedEmployees.forEach(::upsert)
    }

    private fun Employee.hasKnowledge(skillId: UUID) = knowledge.any { it.isOfSkill(skillId) }
    private fun Knowledge.isOfSkill(skillId: UUID) = this.skillId == skillId

    @Async
    @EventListener
    fun handle(event: SkillDeleted) {
        val skillId = event.skillId

        val updatedEmployees = database.values
            .filter { employee -> employee.hasKnowledge(skillId) }
            .map { employee ->
                val filteredKnowledge = employee.knowledge
                    .filter { knowledge -> !knowledge.isOfSkill(skillId) }
                employee.copy(knowledge = filteredKnowledge)
            }

        updatedEmployees.forEach(::upsert)
    }
}
