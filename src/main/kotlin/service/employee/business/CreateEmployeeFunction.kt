package service.employee.business

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.IdGenerator
import service.employee.EmployeeCreated
import service.employee.business.model.Employee
import service.employee.persistence.EmployeeRepository
import service.employee.toDto
import java.time.Clock

@Service
class CreateEmployeeFunction(
    private val repository: EmployeeRepository,
    private val publisher: ApplicationEventPublisher,
    private val idGenerator: IdGenerator,
    private val clock: Clock
) {

    @Transactional
    operator fun invoke(data: Employee.Data): Employee {
        val id = idGenerator.generateId()
        val now = clock.instant()

        val employee = Employee(
            id = id,
            data = data,
            knowledge = emptyList(),
            created = now,
            lastModified = now
        )
        repository.save(employee)

        publishEmployeeCreated(employee)
        return employee
    }

    fun publishEmployeeCreated(employee: Employee) =
        publisher.publishEvent(EmployeeCreated(idGenerator.generateId(), clock.instant(), employee.toDto()))
}
