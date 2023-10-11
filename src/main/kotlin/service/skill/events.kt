package service.skill

import org.jmolecules.event.types.DomainEvent
import java.time.Instant
import java.util.UUID

data class SkillCreated(
    override val id: UUID,
    override val timestamp: Instant,
    val skill: SkillDto,
) : SkillEvent

data class SkillUpdated(
    override val id: UUID,
    override val timestamp: Instant,
    val oldSkill: SkillDto,
    val newSkill: SkillDto,
) : SkillEvent {
    init {
        require(oldSkill.id == newSkill.id)
    }
}

data class SkillDeleted(
    override val id: UUID,
    override val timestamp: Instant,
    val skillId: UUID,
) : SkillEvent

interface SkillEvent : DomainEvent {
    val id: UUID
    val timestamp: Instant
}
