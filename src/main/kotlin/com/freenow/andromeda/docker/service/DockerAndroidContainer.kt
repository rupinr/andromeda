package com.freenow.andromeda.docker.service

import com.freenow.andromeda.docker.model.Container
import com.freenow.andromeda.docker.model.HostConfig
import com.freenow.andromeda.docker.model.HostIpPort
import com.google.gson.GsonBuilder
import java.io.StringReader
import javax.json.Json
import javax.json.JsonObject


object DockerAndroidContainer {

    fun getV3Container(androidDevice: AndroidDevice,adbPort: Int, webPort: Int): JsonObject {


        val jsonString = GsonBuilder().serializeNulls().disableHtmlEscaping().create().toJson(Container(
                env = arrayListOf("EMULATOR_ARGS=-partition-size "+androidDevice.partitionSize+" -memory "+androidDevice.memory+" -cache-size "+androidDevice.cacheSize+" -noaudio -no-boot-anim", "DEVICE="+androidDevice.deviceName),
                exposedPorts = mapOf("5555/tcp" to Any(), "6080/tcp" to Any()),
                image =  androidDevice.imageName,
                HostConfig = HostConfig(PortBindings = mapOf("5555/tcp" to arrayListOf(HostIpPort(HostPort = adbPort.toString())), "6080/tcp" to arrayListOf(HostIpPort(HostPort = webPort.toString()))))
        )
        )
        return Json.createReader(StringReader( jsonString)).readObject()
    }
}