package service.skill.persistence

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import service.skill.business.Skill
import java.util.UUID

@Repository
class SkillRepository(
    private val repository: InternalSkillRepository
) {

    fun get(id: UUID): Skill? =
        repository.findByIdOrNull(id)

    fun upsert(skill: Skill) {
        repository.save(skill)
    }

    fun update(id: UUID, block: (Skill) -> Skill): Skill? {
        val skill = get(id) ?: return null
        val updatedSkill = block(skill)
        upsert(updatedSkill)
        return updatedSkill
    }

    fun delete(id: UUID): Boolean {
        val existed = repository.existsById(id)
        repository.deleteById(id)
        return existed
    }
}
