package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.services.IssueServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/issue")
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
    @GetMapping("/{number}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getIssueByNumber(@PathVariable("number") number: Int) =
        this.issueServiceImpl.getIssueByNumber(number).run { ResponseEntity.ok(this) }

}