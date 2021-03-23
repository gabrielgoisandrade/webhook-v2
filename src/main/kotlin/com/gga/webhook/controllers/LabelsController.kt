package com.gga.webhook.controllers

import com.gga.webhook.models.dTO.LabelsDto
import com.gga.webhook.services.impls.LabelsServiceImpl
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
@RequestMapping("/labels", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Labels controller", description = "Methods available for resource '/labels'.")
class LabelsController {

    @Autowired
    private lateinit var service: LabelsServiceImpl

    @GetMapping("/issue/{issue-number}")
    fun findLabelsByIssueNumber(@PathVariable("issue-number") issueNumber: Int): ResponseEntity<List<LabelsDto>> =
        this.service.findLabelsByIssueNumber(issueNumber).run {
            this.forEach {
                configureLinks(it.links) {
                    it.add(
                        linkTo(
                            methodOn(this@LabelsController::class.java).findLabelsByIssueNumber(issueNumber)
                        ).withSelfRel()
                    )
                }
            }

            ok(this)
        }

}
