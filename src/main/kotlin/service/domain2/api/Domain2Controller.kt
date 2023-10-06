package service.domain2.api

import org.springframework.web.bind.annotation.RestController
import service.domain2.business.Domain2Service

@RestController
class Domain2Controller(
    private val service: Domain2Service
) {
}
