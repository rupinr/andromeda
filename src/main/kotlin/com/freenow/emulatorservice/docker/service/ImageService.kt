package com.freenow.emulatorservice.docker.service

import com.amihaiemil.docker.Docker
import com.freenow.emulatorservice.docker.model.CustomResponse
import com.freenow.emulatorservice.docker.model.DockerImage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

private const val REPO_TAGS="RepoTags"
@Service
class ImageService {


    @Autowired
    lateinit var dockerClient: Docker

    @Autowired
    lateinit var configurationService: ConfigurationService


    fun pullImage(dockerImage: DockerImage) : CustomResponse {
        Thread(Runnable {
            dockerClient.images().pull(dockerImage.imageName, dockerImage.tag)
        }).start()
        return CustomResponse(data = "Image pull in progress. Check /image/list for list")
    }

    fun allImages() : CustomResponse {
        val images = dockerClient.images()
                .filter{it[REPO_TAGS]!!.toString() !="null"}
                .map {
                val repoTag1 = it[REPO_TAGS]!!.asJsonArray()[0].toString()
                val repoTag2 = repoTag1.replace("\"", "").split(":")
                DockerImage(imageName = repoTag2[0], tag = repoTag2[1])
        }
        return CustomResponse(data = images)

    }

}
