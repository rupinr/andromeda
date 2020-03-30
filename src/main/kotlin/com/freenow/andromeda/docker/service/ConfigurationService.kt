package com.freenow.andromeda.docker.service

import com.freenow.andromeda.docker.model.ConfigurationItem
import com.freenow.andromeda.docker.model.ConfigurationKeys
import com.freenow.andromeda.docker.repository.ConfigurationItemRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConfigurationService {

    @Autowired
    lateinit var configurationItemRepository: ConfigurationItemRepository

    private val logger = KotlinLogging.logger {}

    fun setConfiguration( configurationItem: ConfigurationItem) {
        val existingConfigurationItem = configurationItemRepository.findConfigurationItemByKey(configurationItem.key)

        if(existingConfigurationItem.isPresent) {
            existingConfigurationItem.get().value = configurationItem.value
            configurationItemRepository.save(existingConfigurationItem.get())
        }
        else {
            configurationItemRepository.save(ConfigurationItem(key = configurationItem.key, value = configurationItem.value))
        }

    }

    fun getConfiguration(key: ConfigurationKeys) = configurationItemRepository.findConfigurationItemByKey(key)

    fun getConfigurations(): MutableList<ConfigurationItem> = configurationItemRepository.findAll()

    fun getConfigurationSafely(key: ConfigurationKeys, defaultValue: String): String {
        var configuration = defaultValue
        try {
            if( getConfiguration(key).isPresent ){
                configuration = getConfiguration(key).get().value
            }
        }
        catch (ex: Exception) {
            logger.error { "Unable to get $key" }
        }
        return configuration
    }

}