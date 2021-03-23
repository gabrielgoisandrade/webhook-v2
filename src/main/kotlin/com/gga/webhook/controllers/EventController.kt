package com.gga.webhook.controllers

import com.gga.webhook.models.dTO.EventDto
import com.gga.webhook.services.impls.EventServiceImpl
import com.gga.webhook.utils.LinkUtil.Companion.configureLinks
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/event", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Event controller", description = "Methods available for resource '/event'.")
class EventController {

    @Autowired
    private lateinit var service: EventServiceImpl

    @GetMapping("/{event-action}")
    fun findEventByAction(@PathVariable("event-action") action: String): ResponseEntity<EventDto> =
        this.service.findEventByAction(action).run {
            configureLinks(this.links) {
                this.add(linkTo(methodOn(this@EventController::class.java).findEventByAction(action)).withSelfRel())
            }

            ok(this)
        }

}