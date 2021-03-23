package com.gga.webhook.controllers

import com.gga.webhook.constants.ControllersConstants.LICENSE_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.OWNER_CONTROLLER
import com.gga.webhook.models.dTO.RepositoryDto
import com.gga.webhook.services.impls.RepositoryServiceImpl
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
@RequestMapping("/repository", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Repository controller", description = "Methods available for resource '/repository'.")
class RepositoryController {

    @Autowired
    private lateinit var service: RepositoryServiceImpl

    @GetMapping("/{repository-name}")
    fun findRepositoryByName(@PathVariable("repository-name") name: String): ResponseEntity<RepositoryDto> =
        this.service.findRepositoryByName(name).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(
                        methodOn(this@RepositoryController::class.java).findRepositoryByName(name)
                    ).withSelfRel(),

                    linkTo(methodOn(OWNER_CONTROLLER).findOwnerByRepositoryName(name)).withRel("owner")
                )

                this.license?.let {
                    this.add(
                        linkTo(
                            methodOn(LICENSE_CONTROLLER).findLicenseByRepositoryName(name)
                        ).withRel("license")
                    )
                }
            }

            ok(this)
        }

}

