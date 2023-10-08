package service.employee.api

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import service.employee.business.Employee
import service.employee.business.EmployeeKnowledgeService
import java.util.UUID

@RestController
@RequestMapping("/api/employees")
class EmployeeKnowledgeController(
    private val service: EmployeeKnowledgeService
) {

    @PutMapping("/{employeeId}/knowledge/{skillId}")
    fun setKnowledge(
        @PathVariable employeeId: UUID,
        @PathVariable skillId: UUID,
        @RequestBody body: SetKnowledgeBody
    ): Employee? = service.setKnowledge(employeeId, skillId, body.level)

    @DeleteMapping("/{employeeId}/knowledge/{skillId}")
    fun deleteKnowledge(
        @PathVariable employeeId: UUID,
        @PathVariable skillId: UUID
    ): Employee? = service.deleteKnowledge(employeeId, skillId)

    data class SetKnowledgeBody(
        val level: Int
    )
}
