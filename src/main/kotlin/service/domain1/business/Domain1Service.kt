package service.domain1.business

import org.springframework.stereotype.Service
import service.domain1.Domain1Accessor
import service.domain1.persistence.Domain1Repository

@Service
class Domain1Service(
    private val repository: Domain1Repository
) : Domain1Accessor {
}
