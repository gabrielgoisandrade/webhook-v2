package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.dTO.OwnerDto
import com.gga.webhook.models.dTO.RepositoryDto
import com.gga.webhook.services.impls.OwnerServiceImpl
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
@RequestMapping("/owner", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Owner", description = "Ações disponíveis para _'/owner'_")
class OwnerController {

    @Autowired
    private lateinit var service: OwnerServiceImpl

    @GetMapping("/repository/{repository-name}")
    @Operation(
        operationId = "Obter Owner pelo nome do Repository",
        description = "Retorna um **Owner** vinculado a um determinado **Repository**.",
        parameters = [
            Parameter(
                name = "repository-name",
                description = "Nome do Repository que está vinculado com o Owner.",
                example = "repo-name"
            )
        ],
        responses = [
            ApiResponse(
                description = "Owner encontrado.",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = OwnerDto::class),
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
                                        "      \"href\": \"https://some-domain/owner/repository/{repository-name}\",\n" +
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
                                        "  \"message\": \"There isn't any Owner related with this Repository.\",\n" +
                                        "  \"details\": \"uri=/owner/repository/{repository-name}\",\n" +
                                        "  \"timestamp\": \"2021-03-28T00:04:52.293-0300\"\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun findOwnerByRepositoryName(@PathVariable("repository-name") repositoryName: String): ResponseEntity<OwnerDto> =
        this.service.findOwnerByRepositoryName(repositoryName).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(
                        methodOn(this@OwnerController::class.java).findOwnerByRepositoryName(repositoryName)
                    ).withSelfRel().withType("GET")
                )
            }

            ok(this)
        }

}
