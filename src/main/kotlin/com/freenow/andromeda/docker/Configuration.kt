package com.freenow.andromeda.docker

import com.amihaiemil.docker.LocalDocker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
class Configuration {

    @Bean
    fun provideDockerClient() = LocalDocker(File("/var/run/docker.sock"),"v1.38");
}