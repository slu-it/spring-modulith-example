package service.employee.business

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.IdGenerator
import service.employee.EmployeeDeleted
import service.employee.persistence.EmployeeRepository
import java.time.Clock
import java.util.UUID

@Service
class DeleteEmployeeFunction(
    private val repository: EmployeeRepository,
    private val publisher: ApplicationEventPublisher,
    private val idGenerator: IdGenerator,
    private val clock: Clock
) {

    @Transactional
    operator fun invoke(id: UUID) {
        if (repository.existsById(id)) {
            repository.deleteById(id)
            publishEmployeeDeleted(id)
        }
    }

    fun publishEmployeeDeleted(id: UUID) =
        publisher.publishEvent(EmployeeDeleted(idGenerator.generateId(), clock.instant(), id))
}
