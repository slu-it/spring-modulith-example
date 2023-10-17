package service.employee.api

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.noContent
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import service.employee.api.model.EmployeeRepresentation
import service.employee.api.model.toRepresentation
import service.employee.business.GetEmployeeFunction
import java.util.UUID

@RestController
@RequestMapping("/api/employees/{id}")
class GetEmployeeController(
    private val getEmployee: GetEmployeeFunction,
) {

    @GetMapping
    fun get(@PathVariable id: UUID): ResponseEntity<EmployeeRepresentation> =
        when (val employee = getEmployee(id)) {
            null -> noContent().build()
            else -> ok(employee.toRepresentation())
        }
}
