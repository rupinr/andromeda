package com.freenow.emulatorservice.docker

import com.amihaiemil.docker.LocalDocker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.net.URI

@Configuration
class Configuration {

    @Bean
    fun provideDockerClient() = LocalDocker(File("/var/run/docker.sock"),"v1.38");
}