package service.employee.business

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import service.employee.business.model.Employee
import service.employee.business.model.Knowledge
import service.skill.SkillAccessor
import java.util.UUID

@Service
class SetKnowledgeFunction(
    private val skills: SkillAccessor,
    private val updateEmployee: UpdateEmployeeFunction,
) {

    @Transactional
    operator fun invoke(employeeId: UUID, skillId: UUID, level: Int): Employee? {
        val skill = skills.getById(skillId) ?: return null
        return updateEmployee(employeeId) { employee ->
            var skillKnowledge = employee.knowledge.firstOrNull { it.skillId == skill.id }
            val otherKnowledge = employee.knowledge.filter { it.skillId != skill.id }

            skillKnowledge = skillKnowledge?.copy(level = level) ?: Knowledge(skill.id, skill.label, level)

            employee.copy(knowledge = otherKnowledge + skillKnowledge)
        }
    }
}
