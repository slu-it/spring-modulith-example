package service.employee.api

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import service.employee.api.model.EmployeeRepresentation
import service.employee.api.model.toRepresentation
import service.employee.business.DeleteKnowledgeFunction
import java.util.UUID

@RestController
@RequestMapping("/api/employees/{employeeId}/knowledge/{skillId}")
class DeleteKnowledgeController(
    private val doDeleteKnowledge: DeleteKnowledgeFunction,
) {

    @DeleteMapping
    fun deleteKnowledge(
        @PathVariable employeeId: UUID,
        @PathVariable skillId: UUID
    ): ResponseEntity<EmployeeRepresentation> =
        when (val employee = doDeleteKnowledge(employeeId, skillId)) {
            null -> notFound().build()
            else -> ok(employee.toRepresentation())
        }
}
