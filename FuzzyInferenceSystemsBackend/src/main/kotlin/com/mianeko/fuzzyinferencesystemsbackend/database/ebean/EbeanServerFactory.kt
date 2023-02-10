package com.mianeko.fuzzyinferencesystemsbackend.database.ebean

import com.fasterxml.jackson.databind.ObjectMapper
import io.ebean.Database
import io.ebean.DatabaseFactory
import io.ebean.config.DatabaseConfig
import io.ebean.platform.postgres.PostgresPlatform
import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class EbeanServerFactory(
    val mapper: ObjectMapper,
    @Value("\${spring.datasource.url}") val url: String,
    @Value("\${spring.datasource.driver-class-name}") val driverClassName: String,
    @Value("\${spring.datasource.username}") val username: String,
    @Value("\${spring.datasource.password}") val password: String,
) : FactoryBean<Database> {
    override fun getObject(): Database = createDatabaseConfig()

    override fun getObjectType(): Class<*>? = Database::class.java

    override fun isSingleton(): Boolean = true

    fun createDatabaseConfig(): Database {
        val config = DatabaseConfig().also {
            it.addPackage("com.mianeko.fuzzyinferencesystemsbackend.database.entities")
            it.dataSource = DataSourceBuilder
                .create()
                .url(url)
                .driverClassName(driverClassName)
                .username(username)
                .password(password)
                .build()
            it.databasePlatform = PostgresPlatform()
            it.isExpressionNativeIlike = true
            it.objectMapper = mapper
            it.isDefaultServer = true
        }

        return DatabaseFactory.create(config)
    }

}