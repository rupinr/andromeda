package com.freenow.andromeda.docker.repository

import com.freenow.andromeda.docker.model.ConfigurationItem
import com.freenow.andromeda.docker.model.ConfigurationKeys
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ConfigurationItemRepository : JpaRepository<ConfigurationItem, Long > {

    fun findConfigurationItemByKey(key: ConfigurationKeys): Optional<ConfigurationItem>
}