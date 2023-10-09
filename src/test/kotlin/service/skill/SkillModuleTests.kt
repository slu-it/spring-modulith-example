package service.skill

import org.junit.jupiter.api.Test
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.test.context.ActiveProfiles
import utils.InitializeWithContainers

@ActiveProfiles("test")
@InitializeWithContainers
@ApplicationModuleTest(extraIncludes = ["config"])
class SkillModuleTests {

    @Test
    fun `module can be initialized`() {
        // nothing to check, for now
    }
}
