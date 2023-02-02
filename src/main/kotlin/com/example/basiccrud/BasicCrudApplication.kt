package com.example.basiccrud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing


@SpringBootApplication
@EnableJpaAuditing
class BasicCrudApplication

fun main(args: Array<String>) {
    runApplication<BasicCrudApplication>(*args)
}
