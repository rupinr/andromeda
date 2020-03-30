package com.freenow.andromeda.docker.controller

import com.freenow.andromeda.docker.model.ConfigurationItem
import com.freenow.andromeda.docker.service.ConfigurationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("maintenance")
class MaintenanceController {

    @Autowired
    lateinit var configurationService: ConfigurationService

    @PostMapping(path = ["configuration"], consumes =  [MediaType.APPLICATION_JSON_VALUE], produces =  [MediaType.APPLICATION_JSON_VALUE])
    fun setConfig(@RequestBody configurationItem: ConfigurationItem) : ResponseEntity<Any> {
        configurationService.setConfiguration(configurationItem)
        return ResponseEntity.ok(configurationItem.key.name +" is set to " + configurationItem.value)
    }

    @GetMapping(path = ["configuration"], produces =  [MediaType.APPLICATION_JSON_VALUE])
    fun getConfigList() : ResponseEntity<Any> {
        configurationService.getConfigurations()
        return ResponseEntity.ok( configurationService.getConfigurations())
    }
}