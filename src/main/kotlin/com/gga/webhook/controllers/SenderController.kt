package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.helper.LinkHelper
import com.gga.webhook.models.dTO.SenderDto
import com.gga.webhook.services.impls.SenderServiceImpl
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
@RequestMapping("/sender", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Sender", description = "Ações disponíveis para _'/sender'_")
class SenderController : LinkHelper<SenderDto> {

    @Autowired
    private lateinit var service: SenderServiceImpl

    @GetMapping("/{sender-login}")
    @Operation(
        operationId = "Obter Sender pelo login",
        description = "Retorna um **Sender** que contém o login passado.",
        parameters = [
            Parameter(
                name = "sender-login",
                description = "Nome do Sender a ser retornado.",
                example = "user123"
            )
        ],
        responses = [
            ApiResponse(
                description = "Sender encontrado.",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = SenderDto::class),
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
                                        "      \"href\": \"https://some-domain/sender/{sender-login}\",\n" +
                                        "      \"type\": \"GET\"\n" +
                                        "    }\n" +
                                        "  ]\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            ), ApiResponse(
                description = "Sender não encontrado.",
                responseCode = "404",
                content = [
                    Content(
                        schema = Schema(implementation = ApiError::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "  \"message\": \"Sender '{login}' not found.\",\n" +
                                        "  \"details\": \"uri=/sender/{sender-login}\",\n" +
                                        "  \"timestamp\": \"2021-03-28T00:04:52.293-0300\"\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun findSenderByLogin(@PathVariable("sender-login") login: String): ResponseEntity<SenderDto> =
        this.service.findSenderByLogin(login).setLinks()

    @GetMapping("/event/{event-action}")
    @Operation(
        description = "Retorna um **Sender** relacionado a um determinado **Event**.",
        parameters = [
            Parameter(
                name = "event-action",
                description = "Action do Event que está vinculado com o Sender.",
                example = "opened"
            )
        ],
        responses = [
            ApiResponse(
                description = "Sender encontrado.",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = SenderDto::class),
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
                                        "      \"href\": \"https://some-domain/sender/event/{event-action}\",\n" +
                                        "      \"type\": \"GET\"\n" +
                                        "    }\n" +
                                        "  ]\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            ), ApiResponse(
                description = "Relação não encontrada",
                responseCode = "404",
                content = [
                    Content(
                        schema = Schema(implementation = ApiError::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "  \"message\": \"There isn't any Sender related with this Event.\",\n" +
                                        "  \"details\": \"uri=/sender/event/{event-action}\",\n" +
                                        "  \"timestamp\": \"2021-03-28T00:04:52.293-0300\"\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun findSenderByEventAction(@PathVariable("event-action") action: String): ResponseEntity<SenderDto> =
        this.service.findSenderByEventAction(action).setLinks()

    override fun SenderDto.setLinks(): ResponseEntity<SenderDto> {
        configureLinks(this.links) {
            this.add(
                linkTo(
                    methodOn(this@SenderController::class.java).findSenderByLogin(login)
                ).withSelfRel().withType("GET")
            )
        }

        return ok(this)
    }

}
