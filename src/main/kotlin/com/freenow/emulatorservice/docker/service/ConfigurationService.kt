package com.freenow.emulatorservice.docker.service

import com.freenow.emulatorservice.docker.model.ConfigurationItem
import com.freenow.emulatorservice.docker.model.ConfigurationKeys
import com.freenow.emulatorservice.docker.repository.ConfigurationItemRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Level
import java.util.logging.Logger

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