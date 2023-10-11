package service.employee.business

import org.jmolecules.event.types.DomainEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.util.IdGenerator
import service.employee.EmployeeCreated
import service.employee.EmployeeDataUpdated
import service.employee.EmployeeDeleted
import service.employee.toDto
import java.time.Clock
import java.time.Instant
import java.util.UUID

@Component
class EmployeeEventPublisher(
    private val publisher: ApplicationEventPublisher,
    private val idGenerator: IdGenerator,
    private val clock: Clock
) {

    fun publishEmployeeCreated(employee: Employee) =
        publish(EmployeeCreated(id(), now(), employee.toDto()))

    fun publishEmployeeDataUpdated(old: Employee, new: Employee) =
        publish(EmployeeDataUpdated(id(), now(), old.toDto(), new.toDto()))

    fun publishEmployeeDeleted(id: UUID) =
        publish(EmployeeDeleted(id(), now(), id))

    private fun id(): UUID = idGenerator.generateId()
    private fun now(): Instant = clock.instant()
    private fun publish(event: DomainEvent) = publisher.publishEvent(event)
}
