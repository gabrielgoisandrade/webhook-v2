package com.gga.webhook.controllers

import com.gga.webhook.constants.ControllersConstants.CREATOR_CONTROLLER
import com.gga.webhook.models.dTO.MilestoneDto
import com.gga.webhook.services.impls.MilestoneServiceImpl
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
@RequestMapping("/milestone", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Milestone controller", description = "Methods available for resource '/milestone'.")
class MilestoneController {

    @Autowired
    private lateinit var service: MilestoneServiceImpl

    @GetMapping("/issue/{issue-number}")
    fun findMilestoneByIssueNumber(@PathVariable("issue-number") issueNumber: Int): ResponseEntity<MilestoneDto> =
        this.service.findMilestoneByIssueNumber(issueNumber).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(
                        methodOn(this@MilestoneController::class.java).findMilestoneByIssueNumber(issueNumber)
                    ).withSelfRel(),

                    linkTo(
                        methodOn(CREATOR_CONTROLLER).findCreatorByMilestoneNumber(this.number)
                    ).withRel("creator")
                )
            }

            ok(this)
        }

}
