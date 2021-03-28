package com.gga.webhook.controllers

import com.gga.webhook.constants.ControllersConstants.ASSIGNEES_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.ASSIGNEE_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.LABELS_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.MILESTONE_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.USER_CONTROLLER
import com.gga.webhook.errors.ApiError
import com.gga.webhook.helper.LinkHelper
import com.gga.webhook.models.dTO.IssueDto
import com.gga.webhook.services.impls.IssueServiceImpl
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
@RequestMapping("/issue", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Issue", description = "Ações disponíveis para _'/issue'_")
class IssueController : LinkHelper<IssueDto> {

    @Autowired
    private lateinit var service: IssueServiceImpl

    @GetMapping("/{issue-number}")
    @Operation(
        operationId = "Obter Issue pelo número",
        description = "Retorna uma **Issue** que contém o número passado.",
        parameters = [
            Parameter(
                name = "issue-number",
                description = "Número da Issue a ser retornada.",
                example = "30"
            )
        ],
        responses = [
            ApiResponse(
                description = "Issue encontrada.",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = IssueDto::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "    \"url\": \"https://someurl.com/\",\n" +
                                        "    \"number\": 0,\n" +
                                        "    \"title\": \"some-title\",\n" +
                                        "    \"state\": \"some-state\",\n" +
                                        "    \"locked\": true,\n" +
                                        "    \"comments\": 0,\n" +
                                        "    \"body\": \"some-body\",\n" +
                                        "    \"repositoryUrl\": \"https://someurl.com/\",\n" +
                                        "    \"labelsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"commentsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"eventsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"htmlUrl\": \"https://someurl.com/\",\n" +
                                        "    \"nodeId\": \"some-node-id\",\n" +
                                        "    \"createdAt\": \"2021-03-28T04:57:48.421Z\",\n" +
                                        "    \"updatedAt\": \"2021-03-28T04:57:48.421Z\",\n" +
                                        "    \"closedAt\": \"2021-03-28T04:57:48.421Z\",\n" +
                                        "    \"authorAssociation\": \"string\",\n" +
                                        "    \"activeLockReason\": \"string\",\n" +
                                        "    \"performedViaGithubApp\": \"some-app\",\n" +
                                        "    \"links\": [\n" +
                                        "        {\n" +
                                        "            \"rel\": \"self\",\n" +
                                        "            \"href\": \"https://some-domain/issue/{issue-number}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"labels\",\n" +
                                        "            \"href\": \"https://some-domain/labels/issue/{issue-number}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"assignee\",\n" +
                                        "            \"href\": \"https://some-domain/assignee/issue/{issue-number}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"assignees\",\n" +
                                        "            \"href\": \"https://some-domain/assignees/issue/{issue-number}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"milestone\",\n" +
                                        "            \"href\": \"https://some-domain/milestone/issue/{issue-number}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        }\n" +
                                        "    ]\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            ), ApiResponse(
                description = "Issue não encontrada.",
                responseCode = "404",
                content = [
                    Content(
                        schema = Schema(implementation = ApiError::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "  \"message\": \"Issue #{issue-number} not found.'\",\n" +
                                        "  \"details\": \"uri=/issue/{issue-number}\",\n" +
                                        "  \"timestamp\": \"2021-03-28T00:04:52.293-0300\"\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun findIssueByNumber(@PathVariable("issue-number") number: Int): ResponseEntity<IssueDto> =
        this.service.findIssueByNumber(number).setLinks()

    @GetMapping("/event/{event-action}")
    @Operation(
        operationId = "Obter Issue pela action do Event",
        description = "Retorna uma **Issue** relacionada a um determinado **Event**.",
        parameters = [
            Parameter(
                name = "event-action",
                description = "Action do Event que está vinculado com a Issue.",
                example = "opened"
            )
        ],
        responses = [
            ApiResponse(
                description = "Issue encontrada.",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = IssueDto::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "    \"url\": \"https://someurl.com/\",\n" +
                                        "    \"number\": 0,\n" +
                                        "    \"title\": \"some-title\",\n" +
                                        "    \"state\": \"some-state\",\n" +
                                        "    \"locked\": true,\n" +
                                        "    \"comments\": 0,\n" +
                                        "    \"body\": \"some-body\",\n" +
                                        "    \"repositoryUrl\": \"https://someurl.com/\",\n" +
                                        "    \"labelsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"commentsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"eventsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"htmlUrl\": \"https://someurl.com/\",\n" +
                                        "    \"nodeId\": \"some-node-id\",\n" +
                                        "    \"createdAt\": \"2021-03-28T04:57:48.421Z\",\n" +
                                        "    \"updatedAt\": \"2021-03-28T04:57:48.421Z\",\n" +
                                        "    \"closedAt\": \"2021-03-28T04:57:48.421Z\",\n" +
                                        "    \"authorAssociation\": \"string\",\n" +
                                        "    \"activeLockReason\": \"string\",\n" +
                                        "    \"performedViaGithubApp\": \"some-app\",\n" +
                                        "    \"links\": [\n" +
                                        "        {\n" +
                                        "            \"rel\": \"self\",\n" +
                                        "            \"href\": \"https://some-domain/issue/event/{event-action}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"labels\",\n" +
                                        "            \"href\": \"https://some-domain/labels/issue/{issue-number}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"assignee\",\n" +
                                        "            \"href\": \"https://some-domain/assignee/issue/{issue-number}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"assignees\",\n" +
                                        "            \"href\": \"https://some-domain/assignees/issue/{issue-number}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"milestone\",\n" +
                                        "            \"href\": \"https://some-domain/milestone/issue/{issue-number}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        }\n" +
                                        "    ]\n" +
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
                                        "  \"message\": \"There isn't any Issue related with this Event.'\",\n" +
                                        "  \"details\": \"uri=/issue/event/{event-action}\",\n" +
                                        "  \"timestamp\": \"2021-03-28T00:04:52.293-0300\"\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun findIssueByEventAction(@PathVariable("event-action") action: String): ResponseEntity<IssueDto> =
        this.service.findIssueByEventAction(action).setLinks()

    override fun IssueDto.setLinks(): ResponseEntity<IssueDto> {
        configureLinks(this.links) {
            this.add(
                linkTo(methodOn(this@IssueController::class.java).findIssueByNumber(number))
                    .withSelfRel().withType("GET"),
                linkTo(methodOn(USER_CONTROLLER).findUserByIssueNumber(number)).withRel("user")
                    .withType("GET")
            )

            if (this.labels.isNotEmpty())
                this.add(
                    linkTo(methodOn(LABELS_CONTROLLER).findLabelsByIssueNumber(number))
                        .withRel("labels").withType("GET")
                )

            this.assignee?.let {
                this.add(
                    linkTo(methodOn(ASSIGNEE_CONTROLLER).findAssigneeByIssueNumber(number))
                        .withRel("assignee").withType("GET")
                )
            }

            if (this.assignees.isNotEmpty())
                this.add(
                    linkTo(
                        methodOn(ASSIGNEES_CONTROLLER).findAssigneesByIssueNumber(number)
                    ).withRel("assignees").withType("GET")
                )

            this.milestone?.let {
                this.add(
                    linkTo(
                        methodOn(MILESTONE_CONTROLLER).findMilestoneByIssueNumber(number)
                    ).withRel("milestone").withType("GET")
                )
            }
        }

        return ok(this)
    }

}
