package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.vO.CreatorVo
import com.gga.webhook.services.impls.CreatorServiceImpl
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
@RequestMapping("/creator", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Creator controller", description = "Methods available for resource 'creator'")
class CreatorController {

    @Autowired
    private lateinit var creatorServiceImpl: CreatorServiceImpl

    @Operation(
        description = "Return the creator of current milestone",
        responses = [ApiResponse(description = "creator found", responseCode = "200"),
            ApiResponse(
                description = "No creator found.",
                responseCode = "404",
                content = [Content(schema = Schema(implementation = ApiError::class))]
            )]
    )
    @GetMapping
    fun getCreator(): ResponseEntity<CreatorVo> = this.creatorServiceImpl.getCreator().run {
        this.add(linkTo(methodOn(this@CreatorController::class.java).getCreator()).withSelfRel())

        ok(this)
    }

}