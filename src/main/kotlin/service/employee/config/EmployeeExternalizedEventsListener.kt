package service.employee.config

import org.slf4j.LoggerFactory.getLogger
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Declarables
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

// only exists for demonstrating that the messages are actually published to a topic

@Configuration
class EmployeeExternalizedEventsListener {

    private val log = getLogger(javaClass)

    @Bean
    fun employeeBindings(employeeEventsTopic: TopicExchange): Declarables {
        val queue = Queue("queues.events.employee", true)
        val binding = BindingBuilder
            .bind(queue)
            .to(employeeEventsTopic)
            .with("*")

        return Declarables(queue, binding)
    }

    @RabbitListener(queues = ["queues.events.employee"])
    fun handle(message: Message) {
        log.info("Message: ${message.body.toString(Charsets.UTF_8)}")
        log.info("Properties: ${message.messageProperties}")
    }
}
