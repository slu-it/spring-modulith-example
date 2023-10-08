package service.employee.business

import org.springframework.stereotype.Service
import org.springframework.util.IdGenerator
import service.employee.persistence.EmployeeRepository
import java.time.Clock
import java.util.UUID

@Service
class EmployeeService(
    private val repository: EmployeeRepository,
    private val publisher: EmployeeEventPublisher,
    private val idGenerator: IdGenerator,
    private val clock: Clock
) {

    fun get(id: UUID): Employee? =
        repository.get(id)

    fun create(data: Employee.Data): Employee {
        val id = idGenerator.generateId()
        val now = clock.instant()

        val employee = Employee(
            id = id,
            data = data,
            knowledge = emptyList(),
            created = now,
            lastModified = now
        )

        repository.upsert(employee)
        publisher.publishEmployeeCreated(employee)

        return employee
    }

    fun updateData(id: UUID, block: (Employee.Data) -> Employee.Data): Employee? {
        var oldEmployee: Employee? = null

        val updatedEmployee = repository.update(id) { employee ->
            oldEmployee = employee
            employee.copy(data = block(employee.data))
        }

        if (oldEmployee != null && updatedEmployee != null) {
            publisher.publishEmployeeDataUpdated(oldEmployee!!, updatedEmployee)
        }

        return updatedEmployee
    }

    fun delete(id: UUID) {
        if (repository.delete(id)) {
            publisher.publishEmployeeDeleted(id)
        }
    }
}
