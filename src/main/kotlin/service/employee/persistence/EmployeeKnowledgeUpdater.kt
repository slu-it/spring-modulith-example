package service.employee.persistence

import org.springframework.modulith.ApplicationModuleListener
import org.springframework.stereotype.Component
import service.skill.SkillDataUpdated
import service.skill.SkillDeleted

@Component
class EmployeeKnowledgeUpdater(
    private val repository: EmployeeRepository
) {

    @ApplicationModuleListener
    fun handle(event: SkillDataUpdated) {
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
            .forEach(repository::save)
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
            .forEach(repository::save)
    }
}