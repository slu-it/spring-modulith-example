package service.skill.business

import org.springframework.stereotype.Service
import org.springframework.util.IdGenerator
import service.skill.persistence.SkillRepository
import java.time.Clock
import java.util.UUID

@Service
class SkillService(
    private val repository: SkillRepository,
    private val publisher: SkillEventPublisher,
    private val idGenerator: IdGenerator,
    private val clock: Clock
) {

    fun get(id: UUID): Skill? =
        repository.get(id)

    fun create(data: Skill.Data): Skill {
        val id = idGenerator.generateId()
        val now = clock.instant()

        val skill = Skill(
            id = id,
            data = data,
            created = now,
            lastModified = now
        )

        repository.upsert(skill)
        publisher.publishSkillCreated(skill)

        return skill
    }

    fun updateData(id: UUID, block: (Skill.Data) -> Skill.Data): Skill? {
        var oldSkill: Skill? = null

        val updatedSkill = repository.update(id) { skill ->
            oldSkill = skill
            skill.copy(data = block(skill.data))
        }
        
        if (oldSkill != null && updatedSkill != null) {
            publisher.publishSkillUpdated(oldSkill!!, updatedSkill)
        }

        return updatedSkill
    }

    fun delete(id: UUID) {
        if (repository.delete(id)) {
            publisher.publishSkillDeleted(id)
        }
    }
}
