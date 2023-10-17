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
import service.employee.business.UpdateEmployeeDataFunction
import java.util.UUID

@RestController
@RequestMapping("/api/employees/{id}")
class UpdateEmployeeController(
    private val updateEmployeeData: UpdateEmployeeDataFunction,
) {

    @PutMapping
    fun put(
        @PathVariable id: UUID,
        @RequestBody body: Request
    ): ResponseEntity<EmployeeRepresentation> {
        val employee = updateEmployeeData(id) { currentData ->
            currentData.copy(firstName = body.firstName, lastName = body.lastName)
        }
        return when (employee) {
            null -> notFound().build()
            else -> ok(employee.toRepresentation())
        }
    }

    data class Request(
        val firstName: String,
        val lastName: String,
    )
}
