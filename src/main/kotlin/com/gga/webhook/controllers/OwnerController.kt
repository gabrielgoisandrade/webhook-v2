package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.vO.OwnerVo
import com.gga.webhook.services.impls.OwnerServiceImpl
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
@RequestMapping("/owner", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Owner controller", description = "Methods available for resource 'owner'.")
class OwnerController {

    @Autowired
    private lateinit var ownerServiceImpl: OwnerServiceImpl

    @Operation(
        description = "Return the owner of current repository",
        responses = [ApiResponse(description = "Owner found", responseCode = "200"),
            ApiResponse(
                description = "No owner found.",
                responseCode = "404",
                content = [Content(schema = Schema(implementation = ApiError::class))]
            )]
    )
    @GetMapping
    fun getOwner(): ResponseEntity<OwnerVo> = this.ownerServiceImpl.getOwner().run {
        this.add(linkTo(methodOn(this@OwnerController::class.java).getOwner()).withSelfRel())

        ok(this)
    }

}
