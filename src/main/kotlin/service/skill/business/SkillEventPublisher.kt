package service.skill.business

import org.jmolecules.event.types.DomainEvent
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

    fun publishSkillCreated(skill: Skill) =
        publish(SkillCreated(id(), now(), skill.toDto()))

    fun publishSkillUpdated(old: Skill, new: Skill) =
        publish(SkillUpdated(id(), now(), old.toDto(), new.toDto()))

    fun publishSkillDeleted(id: UUID) =
        publish(SkillDeleted(id(), now(), id))

    private fun id(): UUID = idGenerator.generateId()
    private fun now(): Instant = clock.instant()
    private fun publish(event: DomainEvent) = publisher.publishEvent(event)
}
