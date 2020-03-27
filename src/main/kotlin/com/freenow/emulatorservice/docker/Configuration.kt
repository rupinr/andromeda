package com.freenow.emulatorservice.docker

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DockerClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {

    @Bean
    fun provideDockerClient(): DockerClient = DockerClientBuilder.getInstance().build()
}