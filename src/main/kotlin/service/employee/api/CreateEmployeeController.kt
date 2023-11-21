package service.employee.api

import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import service.employee.api.model.EmployeeRepresentation
import service.employee.api.model.toRepresentation
import service.employee.business.CreateEmployeeFunction
import service.employee.business.model.Employee

@RestController
@RequestMapping("/api/employees")
class CreateEmployeeController(
    private val createEmployee: CreateEmployeeFunction,
) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun post(@RequestBody body: Request): EmployeeRepresentation {
        val data = body.toEmployeeData()
        val employee = createEmployee(data)
        return employee.toRepresentation()
    }

    data class Request(
        val firstName: String,
        val lastName: String,
    )

    private fun Request.toEmployeeData() =
        Employee.Data(
            firstName = firstName,
            lastName = lastName
        )
}
