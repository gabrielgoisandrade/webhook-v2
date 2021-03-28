package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.dTO.LicenseDto
import com.gga.webhook.services.impls.LicenseServiceImpl
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
@RequestMapping("/license", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "License", description = "Ações disponíveis para _'/license'_")
class LicenseController {

    @Autowired
    private lateinit var service: LicenseServiceImpl

    @GetMapping("/repository/{repository-name}")
    @Operation(
        operationId = "Obter License pelo nome do Repository",
        description = "Retorna uma **License** vinculado a um determinado **Repository**.",
        parameters = [
            Parameter(
                name = "repository-name",
                description = "Nome do Repository que está vinculado com a License.",
                example = "repo-name"
            )
        ],
        responses = [
            ApiResponse(
                description = "License encontrada.",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = LicenseDto::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "    \"key\": \"some-key\",\n" +
                                        "    \"name\": \"some-name\",\n" +
                                        "    \"url\": \"https://someurl.com/\",\n" +
                                        "    \"spdxId\": \"some-spdx-id\",\n" +
                                        "    \"nodeId\": \"some-node-id\",\n" +
                                        "    \"links\": [\n" +
                                        "        {\n" +
                                        "            \"rel\": \"self\",\n" +
                                        "            \"href\": \"https://some-domain/license/repository/{repository-name}\",\n" +
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
                                        "  \"message\": \"There isn't any Licenses related with this Repository.\",\n" +
                                        "  \"details\": \"uri=/license/repository/{repository-name}\",\n" +
                                        "  \"timestamp\": \"2021-03-28T00:04:52.293-0300\"\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun findLicenseByRepositoryName(@PathVariable("repository-name") repositoryName: String): ResponseEntity<LicenseDto> =
        this.service.findLicenseByRepositoryName(repositoryName).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(
                        methodOn(this@LicenseController::class.java).findLicenseByRepositoryName(repositoryName)
                    ).withSelfRel().withType("GET")
                )
            }

            ok(this)
        }

}