package service.skill.config

import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SkillEventTopicConfiguration {

    @Bean
    fun skillEventsTopic() = TopicExchange("skill-events")
}
