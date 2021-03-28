package com.gga.webhook.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Value("\${info.app.name}")
    private lateinit var title: String

    @Value("\${info.app.version}")
    private lateinit var version: String

    @Value("\${info.app.description}")
    private lateinit var description: String

    @Value("\${info.app.contact.github}")
    private lateinit var github: String

    @Value("\${info.app.contact.name}")
    private lateinit var contactName: String

    @Value("\${info.app.contact.email}")
    private lateinit var contactEmail: String

    @Value("\${info.app.license.url}")
    private lateinit var licenseUrl: String

    @Value("\${info.app.license.name}")
    private lateinit var licenseName: String

    private fun info(): Info = Info()
        .title(this.title)
        .description(this.description)
        .version(this.version)
        .contact(this.contact())
        .license(this.license())

    private fun contact(): Contact = Contact()
        .name(this.contactName)
        .email(this.contactEmail)
        .url(this.github)

    private fun license(): License = License().name(this.licenseName).url(this.licenseUrl)

    @Bean
    fun openApi(): OpenAPI = OpenAPI().info(this.info())

}