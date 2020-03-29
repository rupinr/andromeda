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
        configurationItemRepository.save(ConfigurationItem(key = configurationItem.key, value = configurationItem.value))
    }

    fun getConfiguration(key: ConfigurationKeys) = configurationItemRepository.findConfigurationItemByKey(key).value

    fun getConfigurations() = configurationItemRepository.findAll()


}