package com.gga.webhook.controllers

import com.gga.webhook.constants.ControllersConstants.CREATOR_CONTROLLER
import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.dTO.MilestoneDto
import com.gga.webhook.services.impls.MilestoneServiceImpl
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
@RequestMapping("/milestone", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Milestone", description = "Ações disponíveis para _'/milestone'_")
class MilestoneController {

    @Autowired
    private lateinit var service: MilestoneServiceImpl

    @GetMapping("/issue/{issue-number}")
    @Operation(
        operationId = "Obter Milestone pelo número da Issue",
        description = "Retorna um **Milestone** vinculado a uma determinada **Issue**.",
        parameters = [
            Parameter(
                name = "issue-number",
                description = "Número da Issue que está vinculada com o Milestone.",
                example = "30"
            )
        ],
        responses = [
            ApiResponse(
                description = "Milestone encontrado.",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = MilestoneDto::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "    \"url\": \"https://someurl.com/\",\n" +
                                        "    \"number\": 0,\n" +
                                        "    \"title\": \"some-title\",\n" +
                                        "    \"description\": \"some-description\",\n" +
                                        "    \"state\": \"some-state\",\n" +
                                        "    \"htmlUrl\": \"https://someurl.com/\",\n" +
                                        "    \"labelsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"nodeId\": \"some-node-id\",\n" +
                                        "    \"openIssues\": 0,\n" +
                                        "    \"closedIssues\": 0,\n" +
                                        "    \"createdAt\": \"2021-03-28T05:27:23.463Z\",\n" +
                                        "    \"updatedAt\": \"2021-03-28T05:27:23.463Z\",\n" +
                                        "    \"dueOn\": \"2021-03-28T05:27:23.463Z\",\n" +
                                        "    \"closedAt\": \"2021-03-28T05:27:23.463Z\",\n" +
                                        "    \"links\": [\n" +
                                        "        {\n" +
                                        "            \"rel\": \"self\",\n" +
                                        "            \"href\": \"https://some-domain/milestone/repository/{issue-number}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },{\n" +
                                        "            \"rel\": \"creator\",\n" +
                                        "            \"href\": \"https://some-domain/creator/milestone/{milestone-number}\",\n" +
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
                                        "  \"message\": \"There isn't any Milestone related with this Issue.\",\n" +
                                        "  \"details\": \"uri=/milestone/issue/{issue-number}\",\n" +
                                        "  \"timestamp\": \"2021-03-28T00:04:52.293-0300\"\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun findMilestoneByIssueNumber(@PathVariable("issue-number") issueNumber: Int): ResponseEntity<MilestoneDto> =
        this.service.findMilestoneByIssueNumber(issueNumber).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(
                        methodOn(this@MilestoneController::class.java).findMilestoneByIssueNumber(issueNumber)
                    ).withSelfRel().withType("GET"),

                    linkTo(
                        methodOn(CREATOR_CONTROLLER).findCreatorByMilestoneNumber(this.number)
                    ).withRel("creator").withType("GET")
                )
            }

            ok(this)
        }

}
