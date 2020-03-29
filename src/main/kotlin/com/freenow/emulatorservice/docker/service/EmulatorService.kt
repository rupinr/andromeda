package com.freenow.emulatorservice.docker.service

import com.amihaiemil.docker.Container
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

    fun startEmulator(androidDevice: AndroidDevice) {
         dockerClient.containers()
                .create(androidDevice.containerName, DockerAndroidContainer.getV3Container(androidDevice)).start()

    }
}

