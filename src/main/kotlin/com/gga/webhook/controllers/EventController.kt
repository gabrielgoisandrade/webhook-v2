package com.gga.webhook.controllers

import com.gga.webhook.models.dto.IssueDto
import com.gga.webhook.models.dto.PayloadDto
import com.gga.webhook.services.EventService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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

    @PostMapping
    fun saveIssue(@RequestBody payloadDto: PayloadDto): ResponseEntity<PayloadDto> =
        this.eventService.savePayload(payloadDto).run { status(HttpStatus.CREATED).body(this) }

    @GetMapping("/{number}")
    fun getIssueByNumber(@PathVariable("number") number: Int): ResponseEntity<HashSet<IssueDto>> =
        this.eventService.getIssueByNumber(number).run { ok(this) }

}