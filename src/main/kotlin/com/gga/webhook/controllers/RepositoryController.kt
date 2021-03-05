package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.vO.RepositoryVo
import com.gga.webhook.services.impls.RepositoryServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/repository", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Repository controller", description = "Methods available for resource 'repository'.")
class RepositoryController {

    @Autowired
    private lateinit var repositoryServiceImpl: RepositoryServiceImpl

    @Operation(
        description = "Return the repository of current payload",
        responses = [ApiResponse(description = "Repository found", responseCode = "200"),
            ApiResponse(
                description = "No repository found.",
                responseCode = "404",
                content = [Content(schema = Schema(implementation = ApiError::class))]
            )]
    )
    @GetMapping
    fun getRepository(): ResponseEntity<RepositoryVo> = this.repositoryServiceImpl.getRepository().run {
        this.add(linkTo(methodOn(this@RepositoryController::class.java).getRepository()).withSelfRel())

        this.add(linkTo(methodOn(OwnerController::class.java).getOwner()).withRel("owner"))

        this.add(linkTo(methodOn(LicenseController::class.java).getLicense()).withRel("license"))

        ok(this)
    }

}
