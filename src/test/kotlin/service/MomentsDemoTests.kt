package service

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestComponent
import org.springframework.context.annotation.Import
import org.springframework.context.event.EventListener
import org.springframework.modulith.moments.HourHasPassed
import org.springframework.modulith.moments.support.TimeMachine
import org.springframework.test.context.ActiveProfiles
import service.MomentsDemoTests.TestEventListener
import utils.InitializeWithContainerizedPostgreSQL
import java.time.Duration

@SpringBootTest
@ActiveProfiles("test")
@Import(TestEventListener::class)
@InitializeWithContainerizedPostgreSQL
class MomentsDemoTests(
    @Autowired private val timeMachine: TimeMachine
) {

    @TestComponent
    class TestEventListener {

        private val log = LoggerFactory.getLogger(javaClass)

        @EventListener
        fun abc(event: HourHasPassed) {
            log.info(event.time.toString())
        }
    }

    @Test
    fun `produce and log some moments events`() {
        timeMachine.shiftBy(Duration.ofMinutes(119) + Duration.ofSeconds(55))
    }
}
