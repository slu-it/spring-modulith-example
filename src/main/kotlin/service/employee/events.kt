package service.employee

import org.jmolecules.event.types.DomainEvent
import java.time.Instant
import java.util.UUID

data class EmployeeCreated(
    override val id: UUID,
    override val timestamp: Instant,
    val employee: EmployeeDto,
) : EmployeeEvent

data class EmployeeDataUpdated(
    override val id: UUID,
    override val timestamp: Instant,
    val oldEmployee: EmployeeDto,
    val newEmployee: EmployeeDto,
) : EmployeeEvent {
    init {
        require(oldEmployee.id == newEmployee.id)
    }
}

data class EmployeeDeleted(
    override val id: UUID,
    override val timestamp: Instant,
    val employeeId: UUID,
) : EmployeeEvent

interface EmployeeEvent : DomainEvent {
    val id: UUID
    val timestamp: Instant
}
