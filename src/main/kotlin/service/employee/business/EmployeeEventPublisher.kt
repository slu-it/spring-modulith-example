package service.employee.business

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

    fun publishEmployeeCreated(employee: Employee) {
        publish { eventId, timestamp ->
            EmployeeCreated(eventId, timestamp, employee.toDto())
        }
    }

    fun publishEmployeeDataUpdated(old: Employee, new: Employee) {
        check(old.id == new.id) { "The 'old' and 'new' objects need to reference the same employee!" }
        publish { eventId, timestamp ->
            EmployeeDataUpdated(eventId, timestamp, old.toDto(), new.toDto())
        }
    }

    fun publishEmployeeDeleted(id: UUID) {
        publish { eventId, timestamp ->
            EmployeeDeleted(eventId, timestamp, id)
        }
    }

    private fun publish(block: (UUID, Instant) -> Any) {
        publisher.publishEvent(block(idGenerator.generateId(), clock.instant()))
    }
}
