package service.employee.business

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.IdGenerator
import service.employee.EmployeeDataUpdated
import service.employee.business.model.Employee
import service.employee.toDto
import java.time.Clock
import java.util.UUID

@Service
class UpdateEmployeeDataFunction(
    private val updateEmployee: UpdateEmployeeFunction,
    private val publisher: ApplicationEventPublisher,
    private val idGenerator: IdGenerator,
    private val clock: Clock
) {

    @Transactional
    operator fun invoke(id: UUID, block: (Employee.Data) -> Employee.Data): Employee? {
        var oldEmployee: Employee? = null

        val updatedEmployee = updateEmployee(id) { employee ->
            oldEmployee = employee
            employee.copy(data = block(employee.data))
        }

        if (oldEmployee != null && updatedEmployee != null) {
            publishEmployeeDataUpdated(oldEmployee!!, updatedEmployee)
        }

        return updatedEmployee
    }

    fun publishEmployeeDataUpdated(old: Employee, new: Employee) =
        publisher.publishEvent(EmployeeDataUpdated(idGenerator.generateId(), clock.instant(), old.toDto(), new.toDto()))
}
