package service

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import utils.InitializeWithContainers

@SpringBootTest
@ActiveProfiles("test")
@InitializeWithContainers
class ApplicationTests {

    @Test
    fun `application can be initialized`() {
        // nothing to check, for now
    }
}
