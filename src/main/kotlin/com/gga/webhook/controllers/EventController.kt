package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.dto.PayloadDto
import com.gga.webhook.services.EventService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.*

@Tag(name = "Issue controller", description = "Methods available for '/issue'.")
@RestController
@RequestMapping("/issue")
class EventController {

    @Autowired
    private lateinit var eventService: EventService

    @Operation(
        description = "Saves the new Issue opened or updated at GitHub.",
        responses = [ApiResponse(description = "Issue saved.", responseCode = "201")]
    )
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun saveIssue(@RequestBody payloadDto: PayloadDto): ResponseEntity<PayloadDto> =
        this.eventService.savePayload(payloadDto).run { status(HttpStatus.CREATED).body(this) }

    @Operation(
        description = "Get Issue by number",
        responses = [
            ApiResponse(description = "Issue found.", responseCode = "200"),
            ApiResponse(
                description = "Issue not found.",
                responseCode = "404",
                content = [Content(schema = Schema(implementation = ApiError::class))]
            )
        ]
    )
    @GetMapping("/{number}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getIssueByNumber(@PathVariable("number") number: Int) =
        this.eventService.getIssueByNumber(number).run { ok(this) }

}