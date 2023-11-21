package service.employee.business

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import service.employee.business.model.Employee
import java.util.UUID

@Service
class DeleteKnowledgeFunction(
    private val updateEmployee: UpdateEmployeeFunction,
) {

    @Transactional
    operator fun invoke(employeeId: UUID, skillId: UUID): Employee? {
        return updateEmployee(employeeId) { employee ->
            employee.copy(knowledge = employee.knowledge.filter { it.skillId != skillId })
        }
    }
}
