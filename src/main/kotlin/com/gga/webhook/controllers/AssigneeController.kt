package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.dTO.AssigneeDto
import com.gga.webhook.services.impls.AssigneeServiceImpl
import com.gga.webhook.utils.LinkUtil.Companion.configureLinks
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
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

@RestController
@RequestMapping("/assignee", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Assignee", description = "Ações disponíveis para _'/assignee'_")
class AssigneeController {

    @Autowired
    private lateinit var service: AssigneeServiceImpl

    @GetMapping("/issue/{issue-number}")
    @Operation(
        operationId = "Obter Assignee pelo número da Issue",
        description = "Retorna um **Assignee** vinculado a uma determinada **Issue**.",
        parameters = [
            Parameter(
                name = "issue-number",
                description = "Número da Issue que está vinculada com o Assignee.",
                example = "30"
            )
        ],
        responses = [
            ApiResponse(
                description = "Assignee encontrado.",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = AssigneeDto::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "  \"login\": \"some-login\",\n" +
                                        "  \"url\": \"https://someurl.com/\",\n" +
                                        "  \"type\": \"some-type\",\n" +
                                        "  \"nodeId\": \"some-node-id\",\n" +
                                        "  \"avatarUrl\": \"https://someurl.com/\",\n" +
                                        "  \"gravatarId\": \"some-gravatar-id\",\n" +
                                        "  \"htmlUrl\": \"https://somehtmlurl.com/\",\n" +
                                        "  \"followersUrl\": \"https://someurl.com/\",\n" +
                                        "  \"followingUrl\": \"https://someurl.com/\",\n" +
                                        "  \"gistsUrl\": \"https://someurl.com/\",\n" +
                                        "  \"starredUrl\": \"https://someurl.com/\",\n" +
                                        "  \"subscriptionsUrl\": \"https://someurl.com/\",\n" +
                                        "  \"organizationsUrl\": \"https://someurl.com/\",\n" +
                                        "  \"reposUrl\": \"https://someurl.com/\",\n" +
                                        "  \"eventsUrl\": \"https://someurl.com/\",\n" +
                                        "  \"receivedEventsUrl\": \"https://someurl.com/\",\n" +
                                        "  \"siteAdmin\": true,\n" +
                                        "  \"links\": [\n" +
                                        "    {\n" +
                                        "      \"rel\": \"self\",\n" +
                                        "      \"href\": \"https://some-domain/assignee/issue/{issue-number}\",\n" +
                                        "      \"type\": \"GET\"\n" +
                                        "    }\n" +
                                        "  ]\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            ), ApiResponse(
                description = "Relação não encontrada.",
                responseCode = "404",
                content = [
                    Content(
                        schema = Schema(implementation = ApiError::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "  \"message\": \"There isn't any Assignee related with this Issue.\",\n" +
                                        "  \"details\": \"uri=/assignee/issue/{issue-number}\",\n" +
                                        "  \"timestamp\": \"2021-03-28T00:04:52.293-0300\"\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun findAssigneeByIssueNumber(@PathVariable("issue-number") issueNumber: Int): ResponseEntity<AssigneeDto> =
        this.service.findAssigneeByIssueNumber(issueNumber).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(
                        methodOn(this@AssigneeController::class.java).findAssigneeByIssueNumber(issueNumber)
                    ).withSelfRel().withType("GET")
                )
            }

            ok(this)
        }

}
