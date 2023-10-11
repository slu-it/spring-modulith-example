package service.employee

import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.test.context.ActiveProfiles
import service.skill.SkillAccessor
import utils.InitializeWithContainers

@ActiveProfiles("test")
@InitializeWithContainers
@MockkBean(SkillAccessor::class)
@ApplicationModuleTest(extraIncludes = ["config"])
class EmployeeModuleTests {

    @Test
    fun `module can be initialized`() {
        // nothing to check, for now
    }
}
