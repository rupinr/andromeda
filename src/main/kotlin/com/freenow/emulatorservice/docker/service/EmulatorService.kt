package com.freenow.emulatorservice.docker.service

import com.amihaiemil.docker.Docker
import com.freenow.emulatorservice.docker.model.ConfigurationKeys
import com.freenow.emulatorservice.docker.model.CustomResponse
import com.freenow.emulatorservice.docker.model.RunningContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.NoSuchElementException



private const val NAMES = "Names"
private const val NAME = "Name"

private const val STATE = "State"
private const val HEALTH = "Health"
private const val STATUS = "Status"
private const val UNKNOWN ="unknown"
private const val STARTED_AT = "StartedAt"

//TODO, change to database
private const val CONTAINER_LIMIT = 1
private const val BAMBOO_JOB_URL ="https://bamboo.intapps.it/browse/ANP-EUTJ2-"

@Service
class EmulatorService {


    @Autowired
    lateinit var dockerClient: Docker

    @Autowired
    lateinit var configurationService: ConfigurationService

    fun startEmulator(androidDevice: AndroidDevice) : CustomResponse {
        return try{
            if(isRunningContainerLimitReached()){
                throw Exception("Running container limit of " +configurationService.getConfiguration(ConfigurationKeys.CONTAINER_LIMIT) +"has been reached.")
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
        return size >= configurationService.getConfiguration(ConfigurationKeys.CONTAINER_LIMIT).value.toInt()
    }

    fun getRunningContainerDetails() : CustomResponse {
        val containers = dockerClient.containers()
        val list = containers.map {
            val containerData =  it.inspect()
            val containerName = containerData[NAME]!!.toString().replace("/","").replace("\"","")
           RunningContainer (jobUrl = configurationService.getConfiguration(ConfigurationKeys.BAMBOO_URL).value+ containerName,
                   containerName  = containerName,
                   runningDuration = containerData[STATE]!!.asJsonObject()[STARTED_AT].toString().replace("\"","").getRunningDuration())
        }
        return CustomResponse(data = list)
    }

    private fun String.getRunningDuration(): String {
        val old = Instant.parse(this)
        val now = Instant.now()
        val seconds  = Duration.between(old,now).seconds
        return String.format("%d Hours %02d Minutes and %02d Seconds", seconds / 3600, (seconds % 3600) / 60, (seconds % 60))
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
