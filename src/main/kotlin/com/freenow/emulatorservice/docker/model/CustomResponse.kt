package com.freenow.emulatorservice.docker.model

data class CustomResponse(
        val data: Any? = null,
        val message: String?=null,
        val error: Any? =null
)