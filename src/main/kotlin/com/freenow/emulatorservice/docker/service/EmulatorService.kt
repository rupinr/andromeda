package com.freenow.emulatorservice.docker.service

import com.amihaiemil.docker.Docker
import com.freenow.emulatorservice.emulator.Emulator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit
import javax.json.Json
import javax.json.JsonObject


@Service
class EmulatorService {


    @Autowired
    lateinit var dockerClient: Docker

    fun startEmulator(emulator: Emulator = Emulator("Samsung Galaxy S10")) {


        //dockerClient.images().pull("","")

       val container=  dockerClient.containers().create("Docker_Service",DockerAndroidContainer.getContainer())
        print(container.containerId())
        container.start()


    }
    }

