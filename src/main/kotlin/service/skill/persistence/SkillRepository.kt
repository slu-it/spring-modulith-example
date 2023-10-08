package service.skill.persistence

import org.springframework.stereotype.Repository
import service.skill.business.Skill
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Repository
class SkillRepository {

    private val database = ConcurrentHashMap<UUID, Skill>()

    fun get(id: UUID): Skill? =
        database[id]

    fun upsert(skill: Skill) {
        database[skill.id] = skill
    }

    fun update(id: UUID, block: (Skill) -> Skill): Skill? {
        val skill = get(id) ?: return null
        val updatedSkill = block(skill)
        upsert(updatedSkill)
        return updatedSkill
    }

    fun delete(id: UUID): Boolean =
        database.remove(id) != null
}
