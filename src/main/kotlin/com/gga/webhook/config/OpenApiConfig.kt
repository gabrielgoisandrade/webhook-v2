package com.gga.webhook.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Value("\${doc.info.name}")
    private lateinit var title: String

    @Value("\${doc.info.version}")
    private lateinit var version: String

    @Value("\${doc.info.description}")
    private lateinit var description: String

    @Value("\${doc.contact.url}")
    private lateinit var contactUrl: String

    @Value("\${doc.contact.name}")
    private lateinit var contactName: String

    @Value("\${doc.contact.email}")
    private lateinit var contactEmail: String

    @Value("\${doc.license.url}")
    private lateinit var licenseUrl: String

    @Value("\${doc.license.name}")
    private lateinit var licenseName: String

    @Value("\${doc.terms-of-service}")
    private lateinit var termsOfService: String

    @Value("\${doc.server.description}")
    private lateinit var serverDescription: String

    @Value("\${doc.server.url}")
    private lateinit var serverUrl: String

    private fun info(): Info = Info()
        .title(this.title)
        .description("## *${this.description}*")
        .version(this.version)
        .contact(this.contact())
        .license(this.license())
        .termsOfService(this.termsOfService)

    private fun contact(): Contact = Contact()
        .url(this.contactUrl)
        .name(this.contactName)
        .email(this.contactEmail)

    private fun license(): License = License().name(this.licenseName).url(this.licenseUrl)

    private fun server(): Server = Server().url(this.serverUrl).description(this.serverDescription)

    @Bean
    fun openApi(): OpenAPI = OpenAPI().info(this.info()).addServersItem(this.server())

}