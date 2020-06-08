package com.freenow.andromeda

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
class AndromedaApplication

fun main(args: Array<String>) {
	 runApplication<AndromedaApplication>(*args)
}
