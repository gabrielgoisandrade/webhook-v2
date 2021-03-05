package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.vO.LicenseVo
import com.gga.webhook.services.impls.LicenseServiceImpl
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
@RequestMapping("/license", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "License controller", description = "Methods available for resource 'license'.")
class LicenseController {

    @Autowired
    private lateinit var licenseServiceImpl: LicenseServiceImpl

    @Operation(
        description = "Return the license of current repository",
        responses = [ApiResponse(description = "License found", responseCode = "200"),
            ApiResponse(
                description = "The repository has no license",
                responseCode = "204",
                content = [Content(schema = Schema(implementation = ApiError::class))]
            )]
    )
    @GetMapping
    fun getLicense(): ResponseEntity<LicenseVo> = this.licenseServiceImpl.getLicense()?.run {
        this.add(linkTo(methodOn(this@LicenseController::class.java).getLicense()).withSelfRel())

        ok(this)
    } ?: ResponseEntity.noContent().build()

}
