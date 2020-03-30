package com.freenow.andromeda.docker.model

data class CustomResponse(
        val data: Any? = null,
        val message: String?=null,
        val error: Any? =null
)