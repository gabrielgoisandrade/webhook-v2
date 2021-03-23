package com.gga.webhook.controllers

import com.gga.webhook.models.dTO.CreatorDto
import com.gga.webhook.services.impls.CreatorServiceImpl
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
@RequestMapping("/creator", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Creator controller", description = "Methods available for resource '/creator'.")
class CreatorController {

    @Autowired
    private lateinit var service: CreatorServiceImpl

    @GetMapping("/milestone/{milestone-number}")
    fun findCreatorByMilestoneNumber(@PathVariable("milestone-number") milestoneNumber: Int): ResponseEntity<CreatorDto> =
        this.service.findCreatorByMilestoneNumber(milestoneNumber).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(
                        methodOn(this@CreatorController::class.java).findCreatorByMilestoneNumber(milestoneNumber)
                    ).withSelfRel()
                )
            }

            ok(this)
        }

}
