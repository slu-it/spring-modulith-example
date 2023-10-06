package service.domain2

import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.test.context.ActiveProfiles
import service.domain1.Domain1Accessor

@ActiveProfiles("test")
@MockkBean(Domain1Accessor::class)
@ApplicationModuleTest(extraIncludes = ["config"])
class Domain2ModuleTests {

    @Test
    fun `module can be initialized`() {
        // nothing to check, for now
    }
}
