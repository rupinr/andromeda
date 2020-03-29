package com.freenow.emulatorservice.docker.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class ConfigurationItem(

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        val id: Long =0,

        val key: ConfigurationKeys = ConfigurationKeys.NO_OP,

        val value: String = ""
)

enum class ConfigurationKeys{
    NO_OP, BAMBOO_URL,CONTAINER_LIMIT,DOCKER_IMAGE
}