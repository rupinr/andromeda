package com.freenow.emulatorservice.docker.service

import com.amihaiemil.docker.Docker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


private const val NAMES = "Names"

private const val STATE = "State"
private const val HEALTH = "Health"
private const val STATUS = "Status"
private const val UNKNOWN ="unknown"

@Service
class EmulatorService {


    @Autowired
    lateinit var dockerClient: Docker

    fun startEmulator(androidDevice: AndroidDevice) {
         dockerClient.containers()
                .create(androidDevice.containerName, DockerAndroidContainer.getV3Container(androidDevice)).start()
    }

    fun isRunning(containerName: String) : String {
        var healthStatus = ""
        try{
            val container = dockerClient.containers().first { it[NAMES]!!.asJsonArray()[0].toString()== "\"/$containerName\"" }
            healthStatus = container.inspect()[STATE]!!.asJsonObject()[HEALTH]!!.asJsonObject()[STATUS]!!.toString()
        }
        catch (ex: Exception) {
            healthStatus=UNKNOWN
        }
        return healthStatus.replace("\"","")
    }
}

