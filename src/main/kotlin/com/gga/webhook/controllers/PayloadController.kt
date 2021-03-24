package com.gga.webhook.controllers

import com.gga.webhook.constants.ControllersConstants.EVENT_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.ISSUE_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.REPOSITORY_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.SENDER_CONTROLLER
import com.gga.webhook.models.dTO.EventDto
import com.gga.webhook.models.dTO.PayloadDto
import com.gga.webhook.services.impls.PayloadServiceImpl
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payload", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Payload controller", description = "Methods available for resource '/payload'.")
class PayloadController {

    @Autowired
    private lateinit var service: PayloadServiceImpl

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun savePayload(@RequestBody payloadDto: PayloadDto): ResponseEntity<EventDto> =
        this.service.savePayloadData(payloadDto).run {
            this.add(
                linkTo(methodOn(EVENT_CONTROLLER).findEventByAction(this.action)).withSelfRel(),
                linkTo(methodOn(ISSUE_CONTROLLER).findIssueByNumber(payloadDto.issue!!.number)).withRel("issue"),
                linkTo(methodOn(REPOSITORY_CONTROLLER).findRepositoryByName(payloadDto.repository!!.name))
                    .withRel("repository"),
                linkTo(methodOn(SENDER_CONTROLLER).findSenderByLogin(payloadDto.sender!!.login)).withRel("sender")
            )

            status(HttpStatus.CREATED).body(this)
        }

}