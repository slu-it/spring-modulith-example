package service

import org.slf4j.LoggerFactory.getLogger
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Declarables
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExternalizedEventsListener {

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

    @Bean
    fun skillBindings(skillEventsTopic: TopicExchange): Declarables {
        val queue = Queue("queues.events.skill", true)
        val binding = BindingBuilder
            .bind(queue)
            .to(skillEventsTopic)
            .with("*")

        return Declarables(queue, binding)
    }

    @RabbitListener(queues = ["queues.events.employee", "queues.events.skill"])
    fun handle(message: Message) {
        log.info("Message: ${message.body.toString(Charsets.UTF_8)}")
        log.info("Properties: ${message.messageProperties}")
    }
}
