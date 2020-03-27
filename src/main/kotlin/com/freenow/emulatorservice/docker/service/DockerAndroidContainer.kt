package com.freenow.emulatorservice.docker.service

import javax.json.Json
import javax.json.JsonObject

object DockerAndroidContainer {

    fun getContainer() : JsonObject {

        val builder = Json.createObjectBuilder()


        builder.add("Env",Json.createArrayBuilder().add("EMULATOR_ARGS=-partition-size 2048 -memory 3076 -cache-size 1000 -noaudio -no-boot-anim")
                .add("DEVICE=Samsung Galaxy S10").build())
        builder.add("Privileged",true)
        builder.add("Image","budtmo/docker-android-x86-9.0")
        builder.add("HostConfig",
        Json.createObjectBuilder().add("PortBindings",Json.createObjectBuilder().add("5555/tcp",
                Json.createArrayBuilder().add(Json.createObjectBuilder().add("HostPort","5555").build()).build()).build()
        ).build())

        return builder.build()
    }

}