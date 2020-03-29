package com.freenow.emulatorservice.docker.model

data class RunningContainer(
        val adbPort: Int,
        val webPort: Int,
        val containerName: String,
        val runningDuration: String,
        val jobUrl: String
)