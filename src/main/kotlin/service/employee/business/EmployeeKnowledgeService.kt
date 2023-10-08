package service.employee.business

import org.springframework.stereotype.Service
import service.employee.persistence.EmployeeRepository
import service.skill.SkillsSpi
import java.util.UUID

@Service
class EmployeeKnowledgeService(
    private val skills: SkillsSpi,
    private val repository: EmployeeRepository,
) {

    fun setKnowledge(employeeId: UUID, skillId: UUID, level: Int): Employee? {
        val skill = skills.getById(skillId) ?: return null
        return repository.update(employeeId) { employee ->
            var skillKnowledge = employee.knowledge.firstOrNull { it.skillId == skill.id }
            val otherKnowledge = employee.knowledge.filter { it.skillId != skill.id }

            skillKnowledge = skillKnowledge?.copy(level = level) ?: Knowledge(skill.id, skill.label, level)

            employee.copy(knowledge = otherKnowledge + skillKnowledge)
        }
    }

    fun deleteKnowledge(employeeId: UUID, skillId: UUID): Employee? {
        return repository.update(employeeId) { employee ->
            employee.copy(knowledge = employee.knowledge.filter { it.skillId != skillId })
        }
    }
}
