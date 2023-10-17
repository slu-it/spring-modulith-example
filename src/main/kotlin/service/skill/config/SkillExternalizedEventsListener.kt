package service.skill.config

import org.slf4j.LoggerFactory.getLogger
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

// only exists for demonstrating that the messages are actually published to a topic

@Configuration
class SkillExternalizedEventsListener {

    private val log = getLogger(javaClass)

    @Bean
    fun skillBindings(skillEventsTopic: TopicExchange): Declarables {
        val queue = Queue("queues.events.skill", true)
        val binding = BindingBuilder
            .bind(queue)
            .to(skillEventsTopic)
            .with("*")

        return Declarables(queue, binding)
    }

    @RabbitListener(queues = ["queues.events.skill"])
    fun handle(message: Message) {
        log.info("Message: ${message.body.toString(Charsets.UTF_8)}")
        log.info("Properties: ${message.messageProperties}")
    }
}
