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


fun test() {

	val tcp22 = ExposedPort.tcp(3306)
	val tcp23 = ExposedPort.tcp(3306)
	val portBindings = Ports()
	portBindings.bind(tcp22, Ports.Binding.bindPort(3306))
	// portBindings.bind(tcp23, Ports.Binding(11023))

	val docker: DockerClient = DockerClientBuilder.getInstance().build()
	val container = docker.createContainerCmd("mysql")
			.withEnv("MYSQL_ROOT_PASSWORD=corona")
			//.withExposedPorts(tcp22)
			.withHostConfig(HostConfig.newHostConfig()
					.withPortBindings(portBindings))
			.exec()
	println("Docker ID"+ container.id)
	docker.startContainerCmd(container.id).exec()
}
