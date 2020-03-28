package com.freenow.emulatorservice.docker.model

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName


data class Container(

        @SerializedName("AttachStderr")
        val attachStderr: Boolean = false,

        @SerializedName("AttachStdin")
        val attachStdin: Boolean = false,

        @SerializedName("AttachStdout")
        val attachStdout: Boolean = false,

        @SerializedName("Cmd")
        val cmd: Any? = null,

        @SerializedName("Domainname")
        val domainName: String = "",

        @SerializedName("Entrypoint")
        val entryPoint: Any? = null,

        @SerializedName("Env")
        val env: ArrayList<String>,

        @SerializedName("ExposedPorts")
        val exposedPorts: Map<String, Any>,


        @SerializedName("Hostname")
        val hostname: String = "",

        @SerializedName("Image")
        val image: String,

        @SerializedName("Labels")
        val Labels: Any = Any(),

        @SerializedName("OnBuild")
        val OnBuild: Any? = null,

        @SerializedName("OpenStdin")
        val OpenStdin: Boolean = false,

        @SerializedName("StdinOnce")
        val StdinOnce: Boolean = false,

        @SerializedName("Tty")
        val Tty: Boolean = false,

        @SerializedName("User")
        val User: String = "",

        @SerializedName("Volumes")
        val Volumes: Any = Any(),

        @SerializedName("WorkingDir")
        val WorkingDir: String = "",

        @SerializedName("NetworkingConfig")
        val NetworkingConfig: EndpointsConfig = EndpointsConfig(),

        @SerializedName("HostConfig")
        val HostConfig: HostConfig
)

data class HostConfig(
        @SerializedName("AutoRemove")
        val AutoRemove: Boolean = false,

        @SerializedName("Binds")
        val Binds: Any? = null,

        @SerializedName("BlkioDeviceReadBps")
        val BlkioDeviceReadBps: Any? = null,

        @SerializedName("BlkioDeviceReadIOps")
        val BlkioDeviceReadIOps: Any? = null,

        @SerializedName("BlkioDeviceWriteBps")
        val BlkioDeviceWriteBps: Any? = null,

        @SerializedName("BlkioDeviceWriteIOps")
        val BlkioDeviceWriteIOps: Any? = null,

        @SerializedName("BlkioWeight")
        val BlkioWeight: Int = 0,

        @SerializedName("BlkioWeightDevice")
        val BlkioWeightDevice: ArrayList<Any> = arrayListOf(),

        @SerializedName("CapAdd")
        val CapAdd: Any? = null,

        @SerializedName("CapDrop")
        val CapDrop: Any? = null,

        @SerializedName("Cgroup")
        val Cgroup: String = "",

        @SerializedName("CgroupParent")
        val CgroupParent: String = "",

        @SerializedName("ConsoleSize")
        val ConsoleSize: ArrayList<Int> = arrayListOf(0, 0),

        @SerializedName("ContainerIDFile")
        val ContainerIDFile: String = "",

        @SerializedName("CpuCount")
        val CpuCount: Int = 0,

        @SerializedName("CpuPercent")
        val CpuPercent: Int = 0,

        @SerializedName("CpuPeriod")
        val CpuPeriod: Int = 0,

        @SerializedName("CpuQuota")
        val CpuQuota: Int = 0,

        @SerializedName("CpuRealtimePeriod")
        val CpuRealtimePeriod: Int = 0,

        @SerializedName("CpuRealtimeRuntime")
        val CpuRealtimeRuntime: Int = 0,

        @SerializedName("CpuShares")
        val CpuShares: Int = 0,

        @SerializedName("CpusetCpus")
        val CpusetCpus: String = "",

        @SerializedName("CpusetMems")
        val CpusetMems: String = "",

        @SerializedName("DeviceCgroupRules")
        val DeviceCgroupRules: Any? = null,

        @SerializedName("Devices")
        val Devices: ArrayList<Any> = arrayListOf(),

        @SerializedName("DiskQuota")
        val DiskQuota: Int = 0,

        @SerializedName("Dns")
        val Dns: ArrayList<Any> = arrayListOf(),

        @SerializedName("DnsOptions")
        val DnsOptions: ArrayList<Any> = arrayListOf(),

        @SerializedName("DnsSearch")
        val DnsSearch: ArrayList<Any> = arrayListOf(),

        @SerializedName("ExtraHosts")
        val ExtraHosts: Any? = null,

        @SerializedName("GroupAdd")
        val GroupAdd: Any? = null,

        @SerializedName("IOMaximumBandwidth")
        val IOMaximumBandwidth: Int = 0,

        @SerializedName("IOMaximumIOps")
        val IOMaximumIOps: Int = 0,

        @SerializedName("IpcMode")
        val IpcMode: String = "",

        @SerializedName("Isolation")
        val Isolation: String = "",

        @SerializedName("KernelMemory")
        val KernelMemory: Int = 0,

        @SerializedName("Links")
        val Links: Any? = null,

        @SerializedName("MaskedPaths")
        val MaskedPaths: Any? = null,

        @SerializedName("Memory")
        val Memory: Int = 0,

        @SerializedName("MemoryReservation")
        val MemoryReservation: Int = 0,

        @SerializedName("MemorySwap")
        val MemorySwap: Int = 0,

        @SerializedName("MemorySwappiness")
        val MemorySwappiness: Int = -1,

        @SerializedName("NanoCpus")
        val NanoCpus: Int = 0,

        @SerializedName("NetworkMode")
        val NetworkMode: String = "default",

        @SerializedName("PidMode")
        val PidMode: String = "",

        @SerializedName("PidsLimit")
        val PidsLimit: Int = 0,

        @SerializedName("OomScoreAdj")
        val OomScoreAdj: Int = 0,

        @SerializedName("OomKillDisable")
        val OomKillDisable: Boolean = false,


        @SerializedName("SecurityOpt")
        val SecurityOpt: Any? = null,

        @SerializedName("Ulimits")
        val Ulimits: Any? = null,

        @SerializedName("VolumesFrom")
        val VolumesFrom: Any? = null,

        @SerializedName("UTSMode")
        val UTSMode: String = "",

        @SerializedName("UsernsMode")
        val UsernsMode: String = "",

        @SerializedName("VolumeDriver")
        val VolumeDriver: String = "",

        @SerializedName("ShmSize")
        val ShmSize: Int = 0,

        @SerializedName("Privileged")
        val Privileged: Boolean = true,

        @SerializedName("PublishAllPorts")
        val PublishAllPorts: Boolean = false,

        @SerializedName("ReadonlyRootfs")
        val ReadonlyRootfs: Boolean = false,

        @SerializedName("ReadonlyPaths")
        val ReadonlyPaths: Any? = null,

        @SerializedName("RestartPolicy")
        val RestartPolicy: RestartPolicy = RestartPolicy(),

        @SerializedName("LogConfig")
        val LogConfig: LogConfig = LogConfig(),

        @SerializedName("PortBindings")
        val PortBindings:  Map<String, ArrayList<HostIpPort>>
)


data class HostIpPort(
        @SerializedName("HostIp")
        val HostIp: String = "",

        @SerializedName("HostPort")
        val HostPort: String
)

data class LogConfig(
        @SerializedName("Config")
        val Config: Any = Any(),

        @SerializedName("Type")
        val Type: String = ""
)

data class Config(
        @SerializedName("Config")
        val Config: Any = Any()
)

data class RestartPolicy(
        @SerializedName("MaximumRetryCount")
        val MaximumRetryCount: Int = 0,

        @SerializedName("Name")
        val Name: String = "no"

)

data class EndpointsConfig(
        @SerializedName("EndpointsConfig")
        val EndpointsConfig: Any = Any()
)

//fun main() {
//
//    val gson = GsonBuilder().serializeNulls().create()
//
//    print(gson.toJson(Container(
//            env = arrayListOf("EMULATOR_ARGS=-partition-size 2048 -memory 3076 -cache-size 1000 -noaudio -no-boot-anim", "DEVICE=Samsung Galaxy S10"),
//            exposedPorts = mapOf("5555/tcp" to Any(), "6080/tcp" to Any()),
//            image = "budtmo/docker-android-x86-9.0",
//            HostConfig = HostConfig(PortBindings = mapOf("5555/tcp" to arrayListOf(HostIpPort(HostPort = "5561")),"6080/tcp" to arrayListOf(HostIpPort(HostPort = "6090"))))
//            )
//    ))
//}