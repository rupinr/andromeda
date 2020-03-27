package com.freenow.emulatorservice.docker.service

import java.io.StringReader
import javax.json.Json
import javax.json.JsonObject
import javax.json.JsonReader


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
                .add("HostPort", "5556").build()))
        portBindings.add("6080/tcp",Json.createArrayBuilder().add(Json.createObjectBuilder()
                .add("HostPort", "6081").build()))


        val hostConfig =  Json.createObjectBuilder().add("PortBindings", portBindings)
                .build()

        containerBuilder.add("HostConfig",hostConfig)
        return containerBuilder.build()
    }

    fun getV2Container() : JsonObject {
        val jsonReader: JsonReader = Json.createReader(StringReader("{\"AttachStderr\":false,\"AttachStdin\":false,\"AttachStdout\":false,\"Cmd\":null,\"Domainname\":\"\",\"Entrypoint\":null,\"Env\":[\"EMULATOR_ARGS=-partition-size 2048 -memory 3076 -cache-size 1000 -noaudio -no-boot-anim\",\"DEVICE=Samsung Galaxy S10\"],\"ExposedPorts\":{\"5555/tcp\":{},\"6080/tcp\":{}},\"HostConfig\":{\"AutoRemove\":false,\"Binds\":null,\"BlkioDeviceReadBps\":null,\"BlkioDeviceReadIOps\":null,\"BlkioDeviceWriteBps\":null,\"BlkioDeviceWriteIOps\":null,\"BlkioWeight\":0,\"BlkioWeightDevice\":[],\"CapAdd\":null,\"CapDrop\":null,\"Cgroup\":\"\",\"CgroupParent\":\"\",\"ConsoleSize\":[0,0],\"ContainerIDFile\":\"\",\"CpuCount\":0,\"CpuPercent\":0,\"CpuPeriod\":0,\"CpuQuota\":0,\"CpuRealtimePeriod\":0,\"CpuRealtimeRuntime\":0,\"CpuShares\":0,\"CpusetCpus\":\"\",\"CpusetMems\":\"\",\"DeviceCgroupRules\":null,\"Devices\":[],\"DiskQuota\":0,\"Dns\":[],\"DnsOptions\":[],\"DnsSearch\":[],\"ExtraHosts\":null,\"GroupAdd\":null,\"IOMaximumBandwidth\":0,\"IOMaximumIOps\":0,\"IpcMode\":\"\",\"Isolation\":\"\",\"KernelMemory\":0,\"Links\":null,\"LogConfig\":{\"Config\":{},\"Type\":\"\"},\"MaskedPaths\":null,\"Memory\":0,\"MemoryReservation\":0,\"MemorySwap\":0,\"MemorySwappiness\":-1,\"NanoCpus\":0,\"NetworkMode\":\"default\",\"OomKillDisable\":false,\"OomScoreAdj\":0,\"PidMode\":\"\",\"PidsLimit\":0,\"PortBindings\":{\"5555/tcp\":[{\"HostIp\":\"\",\"HostPort\":\"5561\"}],\"6080/tcp\":[{\"HostIp\":\"\",\"HostPort\":\"6090\"}]},\"Privileged\":true,\"PublishAllPorts\":false,\"ReadonlyPaths\":null,\"ReadonlyRootfs\":false,\"RestartPolicy\":{\"MaximumRetryCount\":0,\"Name\":\"no\"},\"SecurityOpt\":null,\"ShmSize\":0,\"UTSMode\":\"\",\"Ulimits\":null,\"UsernsMode\":\"\",\"VolumeDriver\":\"\",\"VolumesFrom\":null},\"Hostname\":\"\",\"Image\":\"budtmo/docker-android-x86-9.0\",\"Labels\":{},\"NetworkingConfig\":{\"EndpointsConfig\":{}},\"OnBuild\":null,\"OpenStdin\":false,\"StdinOnce\":false,\"Tty\":false,\"User\":\"\",\"Volumes\":{},\"WorkingDir\":\"\"}"))
        val `object`: JsonObject = jsonReader.readObject()
        jsonReader.close()
        return `object`
    }

}

fun main2() {
    print(DockerAndroidContainer.getV2Container())
}