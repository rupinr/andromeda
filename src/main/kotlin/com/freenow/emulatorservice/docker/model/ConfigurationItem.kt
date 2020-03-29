package com.freenow.emulatorservice.docker.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class ConfigurationItem(

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        @JsonIgnore
        val id: Long =0,

        val key: ConfigurationKeys = ConfigurationKeys.NO_OP,

        var value: String = ""
)

enum class ConfigurationKeys{
    NO_OP, BAMBOO_URL,CONTAINER_LIMIT,DOCKER_IMAGE
}