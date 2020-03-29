package com.freenow.emulatorservice.docker.service

import com.freenow.emulatorservice.docker.model.ConfigurationItem
import com.freenow.emulatorservice.docker.model.ConfigurationKeys
import com.freenow.emulatorservice.docker.repository.ConfigurationItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConfigurationService {

    @Autowired
    lateinit var configurationItemRepository: ConfigurationItemRepository

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

    fun getConfigurations() = configurationItemRepository.findAll()


}