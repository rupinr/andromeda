package com.freenow.emulatorservice.docker.service

data class AndroidDevice(
        val partitionSize: Int = 2048,
        val memomry: Int = 3076,
        val cacheSize: Int = 1000,
        val deviceName: String,
        val imageName: String = "budtmo/docker-android-x86-9.0"
)
