package com.freenow.emulatorservice.docker.model

data class RunningContainer(
        val containerName: String,
        val runningDuration: String,
        val jobUrl: String
)