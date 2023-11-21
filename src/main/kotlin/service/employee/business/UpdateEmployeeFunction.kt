package service.employee.business

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import service.employee.business.model.Employee
import service.employee.persistence.EmployeeRepository
import java.util.UUID

@Service
class UpdateEmployeeFunction(
    private val repository: EmployeeRepository,
) {

    @Transactional
    operator fun invoke(id: UUID, block: (Employee) -> Employee): Employee? {
        val employee = repository.findByIdOrNull(id) ?: return null
        val updatedEmployee = block(employee)
        repository.save(updatedEmployee)
        return updatedEmployee
    }
}
