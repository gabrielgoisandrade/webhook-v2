package com.gga.webhook.controllers

import com.gga.webhook.models.PayloadModel
import com.gga.webhook.models.dto.PayloadDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Issue controller", description = "Methods available for '/issue'.")
@RestController
@RequestMapping("/issue")
class EventController {

    @PostMapping
    fun saveIssue(@RequestBody payloadModel: PayloadDto): ResponseEntity<PayloadDto> {
        println(payloadModel)
        return status(HttpStatus.CREATED).body(payloadModel)
    }

}