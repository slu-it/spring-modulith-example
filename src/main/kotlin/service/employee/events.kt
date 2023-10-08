package service.employee

import service.Event
import java.time.Instant
import java.util.UUID

data class EmployeeCreated(
    override val id: UUID,
    override val timestamp: Instant,
    val employee: EmployeeDto,
) : Event

data class EmployeeDataUpdated(
    override val id: UUID,
    override val timestamp: Instant,
    val oldEmployee: EmployeeDto,
    val newEmployee: EmployeeDto,
) : Event {
    init {
        require(oldEmployee.id == newEmployee.id)
    }
}

data class EmployeeDeleted(
    override val id: UUID,
    override val timestamp: Instant,
    val employeeId: UUID,
) : Event
