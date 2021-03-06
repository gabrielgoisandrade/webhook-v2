package com.gga.webhook.controllers

import com.gga.webhook.models.vO.MilestoneVo
import com.gga.webhook.services.impls.MilestoneServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.noContent
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/milestone", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Milestone controller", description = "Methods available for resource 'milestone'")
class MilestoneController {

    @Autowired
    private lateinit var milestoneServiceImpl: MilestoneServiceImpl

    @Operation(
        description = "Return the milestone of current issue",
        responses = [
            ApiResponse(description = "Milestone found", responseCode = "200"),
            ApiResponse(description = "The issue has no milestone", responseCode = "204"),
        ]
    )
    @GetMapping
    fun getMilestone(): ResponseEntity<MilestoneVo> = this.milestoneServiceImpl.getMilestone()?.run {
        this.add(
            linkTo(methodOn(this@MilestoneController::class.java).getMilestone()).withSelfRel(),
            linkTo(methodOn(CreatorController::class.java).getCreator()).withRel("creator")
        )

        ok(this)
    } ?: noContent().build()

}