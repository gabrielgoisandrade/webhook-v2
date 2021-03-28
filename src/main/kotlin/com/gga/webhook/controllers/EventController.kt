package com.gga.webhook.controllers

import com.gga.webhook.constants.ControllersConstants.ISSUE_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.REPOSITORY_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.SENDER_CONTROLLER
import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.dTO.EventDto
import com.gga.webhook.services.impls.EventServiceImpl
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
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/event", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Event", description = "Ações disponíveis para _'/event'_")
class EventController {

    @Autowired
    private lateinit var service: EventServiceImpl

    @GetMapping("/{event-action}")
    @Operation(
        operationId = "Obter Event pela action",
        description = "Retorna um **Event** que contém determinada **action**.",
        parameters = [
            Parameter(
                name = "event-action",
                description = "Action do Event a ser retornado.",
                example = "opened"
            )
        ],
        responses = [
            ApiResponse(
                description = "Event encontrado.",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = EventDto::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "    \"action\": \"some-action\",\n" +
                                        "    \"links\": [\n" +
                                        "        {\n" +
                                        "            \"rel\": \"self\",\n" +
                                        "            \"href\": \"https://some-domain/event/{event-action}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"issue\",\n" +
                                        "            \"href\": \"https://some-domain/issue/event/{event-action}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"repository\",\n" +
                                        "            \"href\": \"https://some-domain/repository/event/{event-action}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"sender\",\n" +
                                        "            \"href\": \"https://some-domain/sender/event/{event-action}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        }\n" +
                                        "    ]\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            ), ApiResponse(
                description = "Event não encontrado.",
                responseCode = "404",
                content = [
                    Content(
                        schema = Schema(implementation = ApiError::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "  \"message\": \"Event with action '{event-action}' not found.\",\n" +
                                        "  \"details\": \"uri=/event/{event-action}\",\n" +
                                        "  \"timestamp\": \"2021-03-28T00:04:52.293-0300\"\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun findEventByAction(@PathVariable("event-action") action: String): ResponseEntity<EventDto> =
        this.service.findEventByAction(action).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(methodOn(this@EventController::class.java).findEventByAction(action))
                        .withSelfRel().withType("GET"),

                    linkTo(methodOn(ISSUE_CONTROLLER).findIssueByEventAction(action))
                        .withRel("issue").withType("GET"),

                    linkTo(methodOn(REPOSITORY_CONTROLLER).findRepositoryByEventAction(action))
                        .withRel("repository").withType("GET"),

                    linkTo(methodOn(SENDER_CONTROLLER).findSenderByEventAction(action))
                        .withRel("sender").withType("GET")
                )
            }

            ok(this)
        }

}