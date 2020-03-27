package com.freenow.emulatorservice.docker.controller

import com.amihaiemil.docker.Docker
import com.freenow.emulatorservice.docker.service.EmulatorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EmulatorController {

    @Autowired
    lateinit var dockerClient: Docker

    @Autowired
    lateinit var emulatorService: EmulatorService

    @GetMapping("hello")
    fun test() {
        print(dockerClient)
        emulatorService.startEmulator()
    }
}