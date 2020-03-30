package com.freenow.emulatorservice.docker.controller

import com.freenow.emulatorservice.docker.service.AndroidDevice
import com.freenow.emulatorservice.docker.service.DockerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("emulator")
class EmulatorController {

    @Autowired
    lateinit var dockerService: DockerService

    @PostMapping(consumes =  [MediaType.APPLICATION_JSON_VALUE], produces =  [MediaType.APPLICATION_JSON_VALUE])
    fun startEmulator(@RequestBody androidDevice: AndroidDevice) : ResponseEntity<Any> {
        return ResponseEntity.ok(dockerService.startEmulator(androidDevice))
    }

    @GetMapping(path= ["{containerName}/health"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun status(@PathVariable("containerName") containerName: String) : ResponseEntity<Any> {
        return ResponseEntity.ok(dockerService.isRunning(containerName))
    }

    @DeleteMapping(path = ["{containerName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun killContainer(@PathVariable("containerName") containerName: String) : ResponseEntity<Any> {
        return ResponseEntity.ok(dockerService.killContainer(containerName))
    }

    @GetMapping(path= ["/running"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun running() : ResponseEntity<Any> {
        return ResponseEntity.ok(dockerService.getRunningContainerDetails())
    }
}