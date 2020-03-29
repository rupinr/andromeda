package com.freenow.emulatorservice.docker.service

import com.amihaiemil.docker.Docker
import com.freenow.emulatorservice.docker.model.CustomResponse
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

    fun startEmulator(androidDevice: AndroidDevice) : CustomResponse {
        return try{
            val container =  dockerClient.containers()
                    .create(androidDevice.containerName, DockerAndroidContainer.getV3Container(androidDevice))
            container.start()
            CustomResponse(data = "Docker container "+container.containerId()+" is started. Wait for it to turn healthy before doing adb connect")
        }

        catch(ex: Exception) {
            CustomResponse(error = ex.localizedMessage)
        }
    }

    fun isRunning(containerName: String) : CustomResponse {
        var healthStatus = ""
        try{
            val container = dockerClient.containers().first { it[NAMES]!!.asJsonArray()[0].toString()== "\"/$containerName\"" }
            healthStatus = container.inspect()[STATE]!!.asJsonObject()[HEALTH]!!.asJsonObject()[STATUS]!!.toString()
        }
        catch (ex: Exception) {
            healthStatus=UNKNOWN
        }
        return CustomResponse(data = healthStatus.replace("\"",""))
    }
}

