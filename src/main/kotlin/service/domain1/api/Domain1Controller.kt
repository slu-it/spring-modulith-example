package service.domain1.api

import org.springframework.web.bind.annotation.RestController
import service.domain1.business.Domain1Service

@RestController
class Domain1Controller(
    private val service: Domain1Service
) {
}
