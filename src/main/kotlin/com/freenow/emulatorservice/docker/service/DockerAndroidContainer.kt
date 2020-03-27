package com.freenow.emulatorservice.docker.service

import javax.json.Json
import javax.json.JsonObject

object DockerAndroidContainer {

    fun getContainer(): JsonObject {

        val containerBuilder = Json.createObjectBuilder()
        val envArray = Json.createArrayBuilder()
                .add("EMULATOR_ARGS=-partition-size 2048 -memory 3076 -cache-size 1000 -noaudio -no-boot-anim")
                .add("DEVICE=Samsung Galaxy S10").build()

        containerBuilder.add("Env", envArray)
        containerBuilder.add("Privileged", true)
        containerBuilder.add("Image", "budtmo/docker-android-x86-9.0")

        val portBindings = Json.createObjectBuilder()

        portBindings.add("5555/tcp", Json.createArrayBuilder().add(Json.createObjectBuilder()
                .add("HostPort", "5555").build()))
        portBindings.add("6080/tcp",Json.createArrayBuilder().add(Json.createObjectBuilder()
                .add("HostPort", "6080").build()))


        val hostConfig =  Json.createObjectBuilder().add("PortBindings", portBindings)
                .build()

        containerBuilder.add("HostConfig",hostConfig)
        return containerBuilder.build()
    }

}

fun main2() {
    print(DockerAndroidContainer.getContainer())
}