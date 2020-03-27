package com.freenow.emulatorservice

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.Ports
import com.github.dockerjava.core.DockerClientBuilder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class EmulatorServiceApplication

fun main(args: Array<String>) {
	 runApplication<EmulatorServiceApplication>(*args)
}
