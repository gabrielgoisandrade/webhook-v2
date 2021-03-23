package com.gga.webhook.controllers

import com.gga.webhook.models.dTO.AssigneeDto
import com.gga.webhook.services.impls.AssigneeServiceImpl
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
@RequestMapping("/assignee", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Assignee controller", description = "Methods available for resource '/assignee'.")
class AssigneeController {

    @Autowired
    private lateinit var service: AssigneeServiceImpl

    @GetMapping("/issue/{issue-number}")
    fun findAssigneeByIssueNumber(@PathVariable("issue-number") issueNumber: Int): ResponseEntity<AssigneeDto> =
        this.service.findAssigneeByIssueNumber(issueNumber).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(methodOn(this@AssigneeController::class.java).findAssigneeByIssueNumber(issueNumber))
                        .withSelfRel()
                )
            }

            ok(this)
        }

}
