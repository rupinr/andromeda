package com.freenow.emulatorservice.docker.controller

import com.freenow.emulatorservice.docker.model.DockerImage
import com.freenow.emulatorservice.docker.service.AndroidDevice
import com.freenow.emulatorservice.docker.service.DockerService
import com.freenow.emulatorservice.docker.service.ImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("image")
class ImageController {

    @Autowired
    lateinit var imgageService: ImageService



    @PutMapping(consumes =  [MediaType.APPLICATION_JSON_VALUE], produces =  [MediaType.APPLICATION_JSON_VALUE])
    fun pullImage(@RequestBody dockerImage: DockerImage) : ResponseEntity<Any> {
        return ResponseEntity.ok( imgageService.pullImage(dockerImage))
    }

    @GetMapping(path = ["list"],produces =  [MediaType.APPLICATION_JSON_VALUE])
    fun getImages() : ResponseEntity<Any> {
        return ResponseEntity.ok( imgageService.allImages())
    }
}