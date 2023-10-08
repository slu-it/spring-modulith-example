package service.skill.business

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.util.IdGenerator
import service.skill.SkillCreated
import service.skill.SkillDeleted
import service.skill.SkillUpdated
import service.skill.toDto
import java.time.Clock
import java.time.Instant
import java.util.UUID

@Component
class SkillEventPublisher(
    private val publisher: ApplicationEventPublisher,
    private val idGenerator: IdGenerator,
    private val clock: Clock
) {

    fun publishSkillCreated(skill: Skill) {
        publish { eventId, timestamp ->
            SkillCreated(eventId, timestamp, skill.toDto())
        }
    }

    fun publishSkillUpdated(old: Skill, new: Skill) {
        check(old.id == new.id) { "The 'old' and 'new' objects need to reference the same skill!" }
        publish { eventId, timestamp ->
            SkillUpdated(eventId, timestamp, old.toDto(), new.toDto())
        }
    }

    fun publishSkillDeleted(id: UUID) {
        publish { eventId, timestamp ->
            SkillDeleted(eventId, timestamp, id)
        }
    }

    private fun publish(block: (UUID, Instant) -> Any) {
        publisher.publishEvent(block(idGenerator.generateId(), clock.instant()))
    }
}
