package com.gga.webhook.controllers

import com.gga.webhook.models.dTO.AssigneesDto
import com.gga.webhook.services.impls.AssigneesServiceImpl
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
@RequestMapping("/assignees", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Assignees controller", description = "Methods available for resource '/assignees'.")
class AssigneesController {

    @Autowired
    private lateinit var service: AssigneesServiceImpl

    @GetMapping("/issue/{issue-number}")
    fun findAssigneesByIssueNumber(@PathVariable("issue-number") issueNumber: Int): ResponseEntity<List<AssigneesDto>> =
        this.service.findAssigneesByIssueNumber(issueNumber).run {
            this.forEach {
                configureLinks(it.links) {
                    it.add(
                        linkTo(
                            methodOn(this@AssigneesController::class.java).findAssigneesByIssueNumber(issueNumber)
                        ).withSelfRel()
                    )
                }
            }

            ok(this)
        }

}
