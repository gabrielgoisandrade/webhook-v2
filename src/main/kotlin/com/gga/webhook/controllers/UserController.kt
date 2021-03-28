package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.dTO.UserDto
import com.gga.webhook.services.impls.UserServiceImpl
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
@RequestMapping("/user", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "User", description = "Ações disponíveis para _'/user'_")
class UserController {

    @Autowired
    private lateinit var service: UserServiceImpl

    @GetMapping("/issue/{issue-number}")
    @Operation(
        operationId = "Obter User pelo número da Issue",
        description = "Retorna um **User** vinculado a uma determinada **Issue**.",
        parameters = [
            Parameter(
                name = "issue-number",
                description = "Número da Issue que está vinculada com o User.",
                example = "10"
            )
        ],
        responses = [
            ApiResponse(
                description = "User encontrado.",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = UserDto::class),
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
                                        "      \"href\": \"https://some-domain/user/issue/{issue-number}\",\n" +
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
                                        "  \"message\": \"There isn't User related with this Issue.\",\n" +
                                        "  \"details\": \"uri=/user/issue/{issue-number}\",\n" +
                                        "  \"timestamp\": \"2021-03-28T00:04:52.293-0300\"\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun findUserByIssueNumber(@PathVariable("issue-number") issueNumber: Int): ResponseEntity<UserDto> =
        this.service.findUserByIssueNumber(issueNumber).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(methodOn(this@UserController::class.java).findUserByIssueNumber(issueNumber))
                        .withSelfRel().withType("GET")
                )
            }

            ok(this)
        }

}
