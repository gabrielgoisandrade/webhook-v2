package com.gga.webhook.controllers

import com.gga.webhook.models.dTO.OwnerDto
import com.gga.webhook.services.impls.OwnerServiceImpl
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
@RequestMapping("/owner", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Owner controller", description = "Methods available for resource '/owner'.")
class OwnerController {

    @Autowired
    private lateinit var service: OwnerServiceImpl

    @GetMapping("/repository/{repository-name}")
    fun findOwnerByRepositoryName(@PathVariable("repository-name") repositoryName: String): ResponseEntity<OwnerDto> =
        this.service.findOwnerByRepositoryName(repositoryName).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(
                        methodOn(this@OwnerController::class.java).findOwnerByRepositoryName(repositoryName)
                    ).withSelfRel()
                )
            }

            ok(this)
        }

}
