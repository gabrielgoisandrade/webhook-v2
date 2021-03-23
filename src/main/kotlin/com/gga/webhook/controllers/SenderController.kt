package com.gga.webhook.controllers

import com.gga.webhook.models.dTO.SenderDto
import com.gga.webhook.services.impls.SenderServiceImpl
import com.gga.webhook.utils.LinkUtil.Companion.configureLinks
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sender", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Sender controller", description = "Methods available for resource '/sender'.")
class SenderController {

    @Autowired
    private lateinit var service: SenderServiceImpl

    @GetMapping("{sender-login}")
    fun findSenderByLogin(@PathVariable("sender-login") login: String): ResponseEntity<SenderDto> =
        this.service.findSenderByLogin(login).run {
            configureLinks(this.links) {
                this.add(linkTo(methodOn(this@SenderController::class.java).findSenderByLogin(login)).withSelfRel())
            }

            ok(this)
        }

}
