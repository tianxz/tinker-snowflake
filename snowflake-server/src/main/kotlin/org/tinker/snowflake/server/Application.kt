package org.tinker.sfserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("org.tinker")
open class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}