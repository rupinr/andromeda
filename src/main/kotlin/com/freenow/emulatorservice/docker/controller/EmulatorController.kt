package com.freenow.emulatorservice.docker.controller

import com.amihaiemil.docker.Docker
import com.freenow.emulatorservice.docker.service.AndroidDevice
import com.freenow.emulatorservice.docker.service.EmulatorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class EmulatorController {

    @Autowired
    lateinit var emulatorService: EmulatorService

    @PostMapping("emulator")
    fun test(@RequestBody androidDevice: AndroidDevice) {
        emulatorService.startEmulator(androidDevice)
    }

    @GetMapping("state", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun status(@RequestParam("containerName") containerName: String) : ResponseEntity<Any> {
        return ResponseEntity.ok(emulatorService.isRunning(containerName))
    }
}