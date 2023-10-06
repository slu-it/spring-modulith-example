package service.domain2.business

import org.springframework.stereotype.Service
import service.domain1.Domain1Accessor
import service.domain2.Domain2Accessor
import service.domain2.persistence.Domain2Repository

@Service
class Domain2Service(
    private val repository: Domain2Repository,
    private val domain1: Domain1Accessor
) : Domain2Accessor {
}
