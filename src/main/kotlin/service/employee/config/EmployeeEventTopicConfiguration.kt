package service.employee.config

import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EmployeeEventTopicConfiguration {

    @Bean
    fun employeeEventsTopic() = TopicExchange("employee-events")
}
