package utils

import org.springframework.test.context.ContextConfiguration

@Retention
@Target(AnnotationTarget.CLASS)
@ContextConfiguration(initializers = [MongoDBInitializer::class, PostgreSQLInitializer::class])
annotation class InitializeWithContainers
