package com.freenow.emulatorservice.docker.service

import com.freenow.emulatorservice.docker.model.Container
import com.freenow.emulatorservice.docker.model.HostConfig
import com.freenow.emulatorservice.docker.model.HostIpPort
import com.freenow.emulatorservice.docker.utils.PortUtil
import com.google.gson.GsonBuilder
import java.io.StringReader
import javax.json.Json
import javax.json.JsonObject


object DockerAndroidContainer {

    fun getV3Container(androidDevice: AndroidDevice): JsonObject {


        val jsonString = GsonBuilder().serializeNulls().disableHtmlEscaping().create().toJson(Container(
                env = arrayListOf("EMULATOR_ARGS=-partition-size "+androidDevice.partitionSize+" -memory "+androidDevice.memomry+" -cache-size "+androidDevice.cacheSize+" -noaudio -no-boot-anim", "DEVICE="+androidDevice.deviceName),
                exposedPorts = mapOf("5555/tcp" to Any(), "6080/tcp" to Any()),
                image =  androidDevice.imageName,
                HostConfig = HostConfig(PortBindings = mapOf("5555/tcp" to arrayListOf(HostIpPort(HostPort = PortUtil.getAdbPort().toString())), "6080/tcp" to arrayListOf(HostIpPort(HostPort = PortUtil.getWebPort().toString()))))
        )
        )
        return Json.createReader(StringReader( jsonString)).readObject()
    }
}