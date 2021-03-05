package com.gga.webhook.controllers

import com.gga.webhook.models.vO.AssigneeVo
import com.gga.webhook.services.impls.AssigneeServiceImpl
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
@RequestMapping("/assignee", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Assignee controller", description = "Methods available for resource 'assignee'")
class AssigneeController {

    @Autowired
    private lateinit var assigneeServiceImpl: AssigneeServiceImpl

    @Operation(
        description = "Return the assignee of current issue",
        responses = [
            ApiResponse(description = "Assignee found", responseCode = "200"),
            ApiResponse(description = "The issue has no assignee", responseCode = "204"),
        ]
    )
    @GetMapping
    fun getAssignee(): ResponseEntity<AssigneeVo> = this.assigneeServiceImpl.getAssignee()?.run {
        this.add(linkTo(methodOn(this@AssigneeController::class.java).getAssignee()).withSelfRel())

        ok(this)
    } ?: noContent().build()

}