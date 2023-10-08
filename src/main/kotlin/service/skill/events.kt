package service.skill

import service.Event
import service.skill.business.Skill
import java.time.Instant
import java.util.UUID

data class SkillCreated(
    override val id: UUID,
    override val timestamp: Instant,
    val skill: SkillDto,
) : Event

data class SkillUpdated(
    override val id: UUID,
    override val timestamp: Instant,
    val oldSkill: SkillDto,
    val newSkill: SkillDto,
) : Event {
    init {
        require(oldSkill.id == newSkill.id)
    }
}

data class SkillDeleted(
    override val id: UUID,
    override val timestamp: Instant,
    val skillId: UUID,
) : Event
