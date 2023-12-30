package com.kms.blogsearch.config

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory
import java.util.Properties

/**
 * batch module application properties load factory
 */
class PropertySourceFactory: PropertySourceFactory {
    override fun createPropertySource(name: String?, resource: EncodedResource): PropertySource<*> {
        val properties = loadYml(resource)
        val sourceName = name ?: resource.resource.filename
        return PropertiesPropertySource(sourceName, properties)
    }

    private fun loadYml(resource: EncodedResource): Properties {
        return YamlPropertiesFactoryBean().apply {
            this.setResources(resource.resource)
            this.afterPropertiesSet()
        }.getObject()!!
    }
}