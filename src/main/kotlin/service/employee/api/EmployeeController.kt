package service.employee.api

import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import service.employee.business.Employee
import service.employee.business.EmployeeService
import java.util.UUID

@RestController
@RequestMapping("/api/employees")
class EmployeeController(
    private val service: EmployeeService
) {

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): EmployeeRepresentation? =
        service.get(id)?.toRepresentation()

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@RequestBody body: Employee.Data): EmployeeRepresentation =
        service.create(body).toRepresentation()

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody body: Employee.Data): EmployeeRepresentation? =
        service.updateData(id, { body })?.toRepresentation()

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    fun delete(@PathVariable id: UUID) {
        service.delete(id)
    }
}
