package com.gga.webhook.controllers

import com.gga.webhook.constants.ControllersConstants.ASSIGNEES_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.ASSIGNEE_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.LABELS_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.MILESTONE_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.USER_CONTROLLER
import com.gga.webhook.models.dTO.IssueDto
import com.gga.webhook.services.impls.IssueServiceImpl
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
@RequestMapping("/issue", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Issue controller", description = "Methods available for resource '/issue'.")
class IssueController {

    @Autowired
    private lateinit var service: IssueServiceImpl

    @GetMapping("/{issue-number}")
    fun findIssueByNumber(@PathVariable("issue-number") number: Int): ResponseEntity<IssueDto> =
        this.service.findIssueByNumber(number).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(methodOn(this@IssueController::class.java).findIssueByNumber(number)).withSelfRel(),
                    linkTo(methodOn(USER_CONTROLLER).findUserByIssueNumber(number)).withRel("user")
                )

                if (this.labels.isNotEmpty())
                    this.add(
                        linkTo(
                            methodOn(LABELS_CONTROLLER).findLabelsByIssueNumber(number)
                        ).withRel("labels")
                    )

                this.assignee?.let {
                    this.add(
                        linkTo(
                            methodOn(ASSIGNEE_CONTROLLER).findAssigneeByIssueNumber(number)
                        ).withRel("assignee")
                    )
                }

                if (this.assignees.isNotEmpty())
                    this.add(
                        linkTo(
                            methodOn(ASSIGNEES_CONTROLLER).findAssigneesByIssueNumber(number)
                        ).withRel("assignees")
                    )

                this.milestone?.let {
                    this.add(
                        linkTo(
                            methodOn(MILESTONE_CONTROLLER).findMilestoneByIssueNumber(number)
                        ).withRel("milestone")
                    )
                }

            }

            ok(this)
        }

}
