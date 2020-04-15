package com.freenow.andromeda.docker.controller

import com.freenow.andromeda.docker.model.DockerImage
import com.freenow.andromeda.docker.service.ImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("image")
class ImageController {

    @Autowired
    lateinit var imageService: ImageService



    @PutMapping(consumes =  [MediaType.APPLICATION_JSON_VALUE], produces =  [MediaType.APPLICATION_JSON_VALUE])
    fun pullImage(@RequestBody dockerImage: DockerImage) : ResponseEntity<Any> {
        return ResponseEntity.ok( imageService.pullImage(dockerImage))
    }

    @GetMapping(path = ["list"],produces =  [MediaType.APPLICATION_JSON_VALUE])
    fun getImages() : ResponseEntity<Any> {
        return ResponseEntity.ok( imageService.allImages())
    }
}