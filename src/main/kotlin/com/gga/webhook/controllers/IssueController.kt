package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.dTO.IssueDto
import com.gga.webhook.models.vO.IssueVo
import com.gga.webhook.services.impls.IssueServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
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
import java.util.*

@RestController
@RequestMapping("/issue", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Issue controller", description = "Methods available for resource 'issue'.")
class IssueController {

    @Autowired
    private lateinit var issueServiceImpl: IssueServiceImpl

    @Operation(
        description = "Get Issue by number",
        responses = [
            ApiResponse(description = "Issue found.", responseCode = "200"),
            ApiResponse(
                description = "Issue not found.",
                responseCode = "404",
                content = [Content(schema = Schema(implementation = ApiError::class))]
            )
        ]
    )
    @GetMapping("/{number}")
    fun getIssueByNumber(@PathVariable("number") number: Int): ResponseEntity<HashSet<IssueDto>> =
        this.issueServiceImpl.getIssueByNumber(number).run { ok(this) }

    @Operation(
        description = "Return the issue of current payload",
        responses = [ApiResponse(description = "Issue found", responseCode = "200"),
            ApiResponse(
                description = "No issue found.",
                responseCode = "404",
                content = [Content(schema = Schema(implementation = ApiError::class))]
            )]
    )
    @GetMapping
    fun getIssue(): ResponseEntity<IssueVo> =
        this.issueServiceImpl.getIssue().run {
            this.add(linkTo(methodOn(this@IssueController::class.java).getIssue()).withSelfRel())

            this.add(linkTo(methodOn(UserController::class.java).getUser()).withRel("user"))

            this.add(linkTo(methodOn(AssigneeController::class.java).getAssignee()).withRel("assignee"))

            this.add(linkTo(methodOn(MilestoneController::class.java).getMilestone()).withRel("milestone"))

            ok(this)
        }

}