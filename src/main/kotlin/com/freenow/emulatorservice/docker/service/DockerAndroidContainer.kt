package com.freenow.emulatorservice.docker.service

import com.freenow.emulatorservice.docker.model.Container
import com.freenow.emulatorservice.docker.model.HostConfig
import com.freenow.emulatorservice.docker.model.HostIpPort
import com.google.gson.GsonBuilder
import java.io.StringReader
import javax.json.Json
import javax.json.JsonObject
import javax.json.JsonReader


object DockerAndroidContainer {

    fun getV3Container(androidDevice: AndroidDevice): JsonObject {


        val jsonString = GsonBuilder().serializeNulls().create().toJson(Container(
                env = arrayListOf("EMULATOR_ARGS=-partition-size "+androidDevice.partitionSize+" -memory "+androidDevice.memomry+" -cache-size "+androidDevice.cacheSize+" -noaudio -no-boot-anim", "DEVICE="+androidDevice.deviceName),
                exposedPorts = mapOf("5555/tcp" to Any(), "6080/tcp" to Any()),
                image = androidDevice.imageName,
                HostConfig = HostConfig(PortBindings = mapOf("5555/tcp" to arrayListOf(HostIpPort(HostPort = "5561")), "6080/tcp" to arrayListOf(HostIpPort(HostPort = "6090"))))
        )
        )

        val jsonReader: JsonReader = Json.createReader(StringReader(jsonString))
        return jsonReader.readObject()
    }
}