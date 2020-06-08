package com.freenow.andromeda.docker.service

import com.amihaiemil.docker.Docker
import com.freenow.andromeda.docker.model.ConfigurationKeys
import com.freenow.andromeda.docker.model.CustomResponse
import com.freenow.andromeda.docker.model.RunningContainer
import com.freenow.andromeda.docker.utils.PortUtil
import com.freenow.andromeda.docker.utils.StringExtensions.trimQuotes
import com.freenow.andromeda.docker.utils.StringExtensions.trimSlash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import javax.json.JsonObject


private const val NAMES = "Names"
private const val NAME = "Name"
private const val IMAGE = "Image"
private const val HOST_CONFIG = "HostConfig"
private const val PORT_BINDINGS = "PortBindings"
private const val HOST_PORT = "HostPort"
private const val ADB_PORT = "5555/tcp"
private const val WEB_PORT = "6080/tcp"


private const val STATE = "State"
private const val HEALTH = "Health"
private const val STATUS = "Status"
private const val UNKNOWN = "unknown"
private const val STARTED_AT = "StartedAt"

private const val CONTAINER_LIMIT = 3
private const val DEFAULT_CI_URL = "https://bamboo.intapps.it/browse/"
private const val IMAGE_NAME = "budtmo/docker-android-x86-9.0"


private const val CLEANUP_INTERVAL = 1000L*3600*3
private const val THRESHOLD_FOR_CLEANUP = 1000L*3600*2.5

@Service
class DockerService {


    @Autowired
    lateinit var dockerClient: Docker

    @Autowired
    lateinit var configurationService: ConfigurationService

    fun startEmulator(androidDevice: AndroidDevice): CustomResponse {
        try{
            killContainerCommand(androidDevice.containerName)
        }
        catch (e: Exception) {
            // NO OP
        }
        return try {
            if (isRunningContainerLimitReached()) {
                throw Exception("Running container limit of " + configurationService.getConfigurationSafely(ConfigurationKeys.CONTAINER_LIMIT, CONTAINER_LIMIT.toString()) + " has been reached.")
            }
            val adbPort = PortUtil.getAdbPort()
            val webPort = PortUtil.getWebPort()
            val container = dockerClient.containers()
                    .create(androidDevice.containerName,
                            DockerAndroidContainer.getV3Container(AndroidDevice(
                                    imageName = getImageName(),
                                    deviceName = androidDevice.deviceName, containerName = androidDevice.containerName),
                                    adbPort = adbPort,
                                    webPort = webPort))
            container.start()
            CustomResponse(data = RunningContainer(
                    adbPort = adbPort,
                    webPort = webPort,
                    containerName = androidDevice.containerName,
                    runningDuration = "0 minutes",
                    jobUrl = getBambooUrl() + androidDevice.containerName
            ), message = "Wait for container to be healthy.")
        } catch (ex: Exception) {
            CustomResponse(error = ex.localizedMessage)
        }
    }

    fun isRunning(containerName: String): CustomResponse {
        isRunningContainerLimitReached()
        var healthStatus = ""
        healthStatus = try {
            val container = getRelevantContainers().first { it[NAMES]!!.asJsonArray()[0].toString().trimQuotes().trimSlash() == containerName }
            container.inspect()[STATE]!!.asJsonObject()[HEALTH]!!.asJsonObject()[STATUS]!!.toString()
        } catch (ex: Exception) {
            UNKNOWN
        }
        return CustomResponse(data = healthStatus.trimQuotes())
    }

    fun isRunningContainerLimitReached(): Boolean {
        val containers = getRelevantContainers()
        var size = 0
        for (container in containers) {
            size++
        }
        return size >= getMaxContainerCount()
    }

    fun getRunningContainerDetails(): CustomResponse {
        val list = getRelevantContainers().map {
            val containerData = it.inspect()
            val containerName = containerData[NAME]!!.toString().trimSlash().trimQuotes()
            RunningContainer(jobUrl = getBambooUrl() + containerName,
                    containerName = containerName,
                    runningDuration = containerData[STATE]!!.asJsonObject()[STARTED_AT].toString().trimQuotes().getRunningDuration()
                    , adbPort = getPort(containerData, ADB_PORT), webPort = getPort(containerData, WEB_PORT))
        }
        return CustomResponse(data = list)
    }


    private fun getPort(containerData: JsonObject, key: String): Int {
        return containerData[HOST_CONFIG]!!
                .asJsonObject()[PORT_BINDINGS]!!
                .asJsonObject()[key]!!
                .asJsonArray()[0].asJsonObject()[HOST_PORT]
                .toString()
                .trimQuotes()
                .toInt()
    }

    private fun String.getRunningDuration(): String {
        val seconds = this.getRunningDurationInSeconds()
        return String.format("%d Hours %02d Minutes and %02d Seconds", seconds / 3600, (seconds % 3600) / 60, (seconds % 60))
    }


    @Scheduled(fixedDelay = CLEANUP_INTERVAL)
    fun cleanContainer() {
        getRelevantContainers().filter { it.inspect().extractStartTimeFrom().getRunningDurationInSeconds() > THRESHOLD_FOR_CLEANUP }.forEach {
            it.kill()
            it.remove()
        }
    }

    private fun JsonObject.extractStartTimeFrom() =
         this[STATE]!!.asJsonObject()[STARTED_AT].toString().trimQuotes()

    private fun String.getRunningDurationInSeconds(): Long {
        val old = Instant.parse(this)
        val now = Instant.now()
        return Duration.between(old, now).seconds
    }

    fun killContainer(containerName: String): CustomResponse {
        return try {
            Thread {
                killContainerCommand(containerName)
            }.start()
            CustomResponse(data = "Container cleanup in progress. Running status will updated shortly")
        } catch (ex: NoSuchElementException) {
            CustomResponse(error = "Error in killing $containerName. Container not found")
        } catch (ex: Exception) {
            CustomResponse(error = "Error in killing $containerName. " + ex.localizedMessage)
        }
    }

    private fun killContainerCommand(containerName: String) {
        val container = getRelevantContainers().first { it[NAMES]!!.asJsonArray()[0].toString().trimQuotes().trimSlash() == containerName }
        container.kill()
        container.remove()
    }

    private fun getMaxContainerCount(): Int = configurationService.getConfigurationSafely(ConfigurationKeys.CONTAINER_LIMIT, CONTAINER_LIMIT.toString()).toInt()

    private fun getBambooUrl(): String = configurationService.getConfigurationSafely(ConfigurationKeys.CI_TOOL_URL, DEFAULT_CI_URL)

    private fun getImageName() = configurationService.getConfigurationSafely(ConfigurationKeys.DOCKER_IMAGE, IMAGE_NAME)

    private fun getRelevantContainers() = dockerClient.containers().filter { it[IMAGE]!!.toString().trimQuotes() == getImageName() }

}
