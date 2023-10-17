package service.employee.api

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import service.employee.api.model.EmployeeRepresentation
import service.employee.api.model.toRepresentation
import service.employee.business.SetKnowledgeFunction
import java.util.UUID

@RestController
@RequestMapping("/api/employees/{employeeId}/knowledge/{skillId}")
class SetKnowledgeController(
    private val doSetKnowledge: SetKnowledgeFunction,
) {

    @PutMapping
    fun setKnowledge(
        @PathVariable employeeId: UUID,
        @PathVariable skillId: UUID,
        @RequestBody body: Request
    ): ResponseEntity<EmployeeRepresentation> =
        when (val employee = doSetKnowledge(employeeId, skillId, body.level)) {
            null -> notFound().build()
            else -> ok(employee.toRepresentation())
        }

    data class Request(
        val level: Int
    )
}
