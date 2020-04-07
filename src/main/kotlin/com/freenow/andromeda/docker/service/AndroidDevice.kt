package com.freenow.andromeda.docker.service

data class AndroidDevice(
        val partitionSize: Int = 2048,
        val memory: Int = 3076,
        val cacheSize: Int = 1000,
        val deviceName: String,
        val imageName: String="",
        val containerName : String
)
