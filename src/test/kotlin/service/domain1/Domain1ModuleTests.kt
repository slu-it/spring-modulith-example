package service.domain1

import org.junit.jupiter.api.Test
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@ApplicationModuleTest(extraIncludes = ["config"])
class Domain1ModuleTests {

    @Test
    fun `module can be initialized`() {
        // nothing to check, for now
    }
}
