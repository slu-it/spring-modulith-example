package service.employee

import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer.MethodName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.context.TestComponent
import org.springframework.boot.test.web.reactive.server.WebTestClientBuilderCustomizer
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.WebTestClient.RequestHeadersSpec
import service.employee.EmployeeModuleTests.WebTestClientCustomizer
import service.employee.api.model.EmployeeRepresentation
import service.employee.api.model.KnowledgeRepresentation
import service.skill.SkillAccessor
import service.skill.SkillDto
import utils.InitializeWithContainers
import java.util.UUID
import java.util.UUID.randomUUID

@ActiveProfiles("test")
@TestInstance(PER_CLASS)
@InitializeWithContainers
@MockkBean(SkillAccessor::class)
@Import(WebTestClientCustomizer::class)
@TestMethodOrder(MethodName::class)
@ApplicationModuleTest(extraIncludes = ["config"], webEnvironment = RANDOM_PORT)
class EmployeeModuleTests(
    @Autowired private val webTestClient: WebTestClient,
    @Autowired private val skillAccessor: SkillAccessor
) {

    @TestComponent
    class WebTestClientCustomizer : WebTestClientBuilderCustomizer {
        override fun customize(builder: WebTestClient.Builder) {
            builder.defaultHeaders { headers -> headers.setBasicAuth("user", "resu") }
        }
    }

    private val skillId = randomUUID()
    private val skillDto = SkillDto(skillId, "Kotlin")

    private lateinit var employeeId: UUID

    @BeforeEach
    fun setupMocks() {
        every { skillAccessor.getById(skillId) } returns skillDto
    }

    @Test
    fun `01 create employee`() {
        val employee = executeAndReturn<EmployeeRepresentation> {
            post()
                .uri("/api/employees")
                .contentType(APPLICATION_JSON)
                .bodyValue(
                    """
                    {
                      "firstName": "John",
                      "lastName": "Doe"
                    }
                    """
                )
        }

        with(employee!!) {
            firstName shouldBe "John"
            lastName shouldBe "Doe"
            knowledge shouldBe emptyList()
        }

        employeeId = employee.id
    }

    @Test
    fun `02 get employee`() {
        val employee = executeAndReturn<EmployeeRepresentation> {
            get().uri("/api/employees/$employeeId")
        }

        with(employee!!) {
            firstName shouldBe "John"
            lastName shouldBe "Doe"
            knowledge shouldBe emptyList()
        }
    }

    @Test
    fun `03 update employee`() {
        val employee = executeAndReturn<EmployeeRepresentation> {
            put()
                .uri("/api/employees/$employeeId")
                .contentType(APPLICATION_JSON)
                .bodyValue(
                    """
                    {
                      "firstName": "Johnathon",
                      "lastName": "Doer"
                    }
                    """
                )
        }

        with(employee!!) {
            firstName shouldBe "Johnathon"
            lastName shouldBe "Doer"
            knowledge shouldBe emptyList()
        }
    }

    @Test
    fun `04 set employee knowledge`() {
        val employee = executeAndReturn<EmployeeRepresentation> {
            put()
                .uri("/api/employees/$employeeId/knowledge/$skillId")
                .contentType(APPLICATION_JSON)
                .bodyValue("""{ "level": 10 }""")
        }

        with(employee!!) {
            knowledge shouldBe listOf(KnowledgeRepresentation(skillId, "Kotlin", 10))
        }
    }

    @Test
    fun `05 delete employee knowledge`() {
        val employee = executeAndReturn<EmployeeRepresentation> {
            delete().uri("/api/employees/$employeeId/knowledge/$skillId")
        }

        with(employee!!) {
            knowledge shouldBe emptyList()
        }
    }

    @Test
    fun `06 delete employee`() {
        val employee = executeAndReturn<EmployeeRepresentation> {
            delete().uri("/api/employees/$employeeId")
        }

        employee shouldBe null
    }

    @Test
    fun `07 get deleted employee`() {
        val employee = executeAndReturn<EmployeeRepresentation> {
            get().uri("/api/employees/$employeeId")
        }

        employee shouldBe null
    }

    private inline fun <reified T> executeAndReturn(block: WebTestClient.() -> RequestHeadersSpec<*>): T? =
        block(webTestClient)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .returnResult(T::class.java)
            .getResponseBody()
            .blockFirst()
}
