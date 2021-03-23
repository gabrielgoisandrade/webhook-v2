package com.gga.webhook.controllers

import com.gga.webhook.models.dTO.LicenseDto
import com.gga.webhook.services.impls.LicenseServiceImpl
import com.gga.webhook.utils.LinkUtil.Companion.configureLinks
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/license", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "License controller", description = "Methods available for resource '/license'.")
class LicenseController {

    @Autowired
    private lateinit var service: LicenseServiceImpl

    @GetMapping("/repository/{repository-name}")
    fun findLicenseByRepositoryName(@PathVariable("repository-name") repositoryName: String): ResponseEntity<LicenseDto> =
        this.service.findLicenseByRepositoryName(repositoryName).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(
                        methodOn(this@LicenseController::class.java).findLicenseByRepositoryName(repositoryName)
                    ).withSelfRel()
                )
            }

            ok(this)
        }

}