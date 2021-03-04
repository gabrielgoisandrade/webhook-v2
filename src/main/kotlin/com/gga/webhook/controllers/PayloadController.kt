package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.dTO.PayloadDto
import com.gga.webhook.models.vO.PayloadVo
import com.gga.webhook.services.PayloadServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payload", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Payload controller", description = "Methods available for resource 'payload'.")
class PayloadController {

    @Autowired
    private lateinit var payloadServiceImpl: PayloadServiceImpl

    @Operation(
        description = "Returns the payload by ID.",
        responses = [
            ApiResponse(description = "Payload found.", responseCode = "200"),
            ApiResponse(
                description = "Payload not found.",
                responseCode = "404",
                content = [Content(schema = Schema(implementation = ApiError::class))]
            )
        ]
    )
    @GetMapping("/{id}")
    fun findPayloadById(@PathVariable("id") id: Long): ResponseEntity<PayloadVo> =
        this.payloadServiceImpl.getPayloadById(id).run {
            this.add(
                linkTo(methodOn(this@PayloadController::class.java).findPayloadById(id)).withSelfRel(),
                linkTo(methodOn(SenderController::class.java).getSender()).withRel("sender")
            )
            ok(this)
        }

    @Operation(
        description = "Get all registered payloads.",
        responses = [ApiResponse(description = "Payloads found.", responseCode = "200")]
    )
    @GetMapping
    fun findAllPayloads(
        @RequestParam(value = "page", defaultValue = "10") page: Int,
        @RequestParam(value = "limit", defaultValue = "1") limit: Int,
        @RequestParam(value = "direction", defaultValue = "asc") direction: String,
    ): ResponseEntity<CollectionModel<PayloadVo>> =
        this.payloadServiceImpl.getAllPayloads(page, limit, direction).run {
            this.forEach {
                it.add(linkTo(methodOn(this@PayloadController::class.java).findPayloadById(it.id)).withSelfRel())
                it.add(linkTo(methodOn(SenderController::class.java).getSender()).withRel("sender"))
            }

            ok(CollectionModel.of(this))
        }

    @Operation(
        description = "Saves the new Issue opened or updated at GitHub.",
        responses = [ApiResponse(description = "Issue saved.", responseCode = "201")]
    )
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveIssue(@RequestBody payloadDto: PayloadDto): ResponseEntity<PayloadDto> =
        this.payloadServiceImpl.savePayload(payloadDto).run { status(HttpStatus.CREATED).body(this) }

}