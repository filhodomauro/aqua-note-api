package com.filhodomauro.aquanote

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class AquaNoteApiApplication

fun main(args: Array<String>) {
    SpringApplication.run(AquaNoteApiApplication::class.java, *args)
}
