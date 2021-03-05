package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.vO.SenderVo
import com.gga.webhook.services.impls.SenderServiceImpl
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
@RequestMapping("/sender", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Sender controller", description = "Methods available for resource 'sender'")
class SenderController {

    @Autowired
    private lateinit var senderServiceImpl: SenderServiceImpl

    @Operation(
        description = "Return the sender of current payload",
        responses = [ApiResponse(description = "Sender found", responseCode = "200"),
            ApiResponse(
                description = "No sender found.",
                responseCode = "404",
                content = [Content(schema = Schema(implementation = ApiError::class))]
            )]
    )
    @GetMapping
    fun getSender(): ResponseEntity<SenderVo> = this.senderServiceImpl.getSender().run {
        this.add(linkTo(methodOn(this@SenderController::class.java).getSender()).withSelfRel())

        ok(this)
    }

}