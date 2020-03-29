package com.freenow.emulatorservice.docker.repository

import com.freenow.emulatorservice.docker.model.ConfigurationItem
import com.freenow.emulatorservice.docker.model.ConfigurationKeys
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConfigurationItemRepository : JpaRepository<ConfigurationItem, Long > {

    fun findConfigurationItemByKey(key: ConfigurationKeys): ConfigurationItem
}