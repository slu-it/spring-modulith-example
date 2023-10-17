package service.employee.api

import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import service.employee.business.DeleteEmployeeFunction
import java.util.UUID

@RestController
@RequestMapping("/api/employees/{id}")
class DeleteEmployeeController(
    private val deleteEmployee: DeleteEmployeeFunction,
) {

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    fun delete(@PathVariable id: UUID) {
        deleteEmployee(id)
    }
}
