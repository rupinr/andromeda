package com.freenow.emulatorservice.docker.service

import com.freenow.emulatorservice.emulator.Emulator
import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.Ports
import com.github.dockerjava.core.DockerClientBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmulatorService {


    @Autowired
    lateinit var dockerClient: DockerClient

    fun startEmulator(emulator: Emulator = Emulator("Samsung Galaxy S10")) {
        val portBindings = Ports()
        portBindings.bind(ExposedPort.tcp(5555), Ports.Binding.bindPort(5555))

        val container= dockerClient.createContainerCmd("budtmo/docker-android-x86-9.0")
                .withEnv("EMULATOR_ARGS=\"-partition-size 2048 -memory 3076 -cache-size 1000 -noaudio -no-boot-anim\"")
                .withEnv("DEVICE="+"\""+emulator.name+"\"")
                .withHostConfig(HostConfig.newHostConfig()
                        .withPortBindings(portBindings)
                        .withPrivileged(true))
                .exec()
        dockerClient.startContainerCmd(container.id)
    }
}