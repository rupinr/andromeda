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

//TODO, change to database
private const val CONTAINER_LIMIT = 1

@Service
class EmulatorService {


    @Autowired
    lateinit var dockerClient: Docker

    fun startEmulator(androidDevice: AndroidDevice) : CustomResponse {
        return try{
            if(isRunningContainerLimitReached()){
                throw Exception("Running container limit of $CONTAINER_LIMIT has been reached.")
            }
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
        isRunningContainerLimitReached()
        var healthStatus = ""
        healthStatus = try{
            val container = dockerClient.containers().first { it[NAMES]!!.asJsonArray()[0].toString()== "\"/$containerName\"" }
            container.inspect()[STATE]!!.asJsonObject()[HEALTH]!!.asJsonObject()[STATUS]!!.toString()
        }
        catch (ex: Exception) {
            UNKNOWN
        }
        return CustomResponse(data = healthStatus.replace("\"",""))
    }

    fun isRunningContainerLimitReached() : Boolean{
        val containers = dockerClient.containers()
        var size = 0
        for (container in containers) {
            size++
        }
        return size >= CONTAINER_LIMIT
    }

    fun killContainer(containerName: String) : CustomResponse {
        return try {
            val container = dockerClient.containers().first { it[NAMES]!!.asJsonArray()[0].toString()== "\"/$containerName\"" }
            container.kill()
            container.remove()
            CustomResponse(data = "Successfully killed and removed $containerName")
        }
        catch (ex: NoSuchElementException) {
            CustomResponse(error = "Error in killing $containerName. Container not found")
        }
        catch (ex: Exception) {
            CustomResponse(error = "Error in killing $containerName. "+ex.localizedMessage)
        }


    }
}

