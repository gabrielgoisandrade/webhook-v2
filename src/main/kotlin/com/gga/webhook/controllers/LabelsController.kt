package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.dTO.LabelsDto
import com.gga.webhook.services.impls.LabelsServiceImpl
import com.gga.webhook.utils.LinkUtil.Companion.configureLinks
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/labels", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Labels", description = "Ações disponíveis para _'/labels'_")
class LabelsController {

    @Autowired
    private lateinit var service: LabelsServiceImpl

    @GetMapping("/issue/{issue-number}")
    @Operation(
        operationId = "Obter Labels pelo número da Issue",
        description = "Retorna um conteúdo paginável contendo as **Labels** vinculadas a uma determinada **Issue**.",
        parameters = [
            Parameter(
                name = "issue-number",
                description = "Número da Issue que está vinculada com as Labels.",
                example = "30"
            ), Parameter(
                name = "page",
                description = "Informa qual página a ser mostrada (**0** representa a primeira página).",
                example = "0"
            ), Parameter(
                name = "limit",
                description = "Informa a quantidade de itens a serem mostrados (valores abaixo de 0 **não** são válidos).",
                example = "10"
            ), Parameter(
                name = "sort",
                description = "Informa a ordem em que os itens serão mostrados (crescente ou decrescente).",
                example = "asc"
            )
        ],
        responses = [
            ApiResponse(
                description = "Labels encontradas.",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = LabelsDto::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "    \"links\": [\n" +
                                        "        {\n" +
                                        "            \"rel\": \"self\",\n" +
                                        "            \"href\": \"https://some-domain/labels/issue/{issue-number}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        }\n" +
                                        "    ],\n" +
                                        "    \"content\": [\n" +
                                        "        {\n" +
                                        "            \"url\": \"https://someurl.com/\",\n" +
                                        "            \"name\": \"some-name\",\n" +
                                        "            \"color\": \"some-color\",\n" +
                                        "            \"default\": true,\n" +
                                        "            \"description\": \"some-description\",\n" +
                                        "            \"nodeId\": \"some-node-id\",\n" +
                                        "            \"links\": [\n" +
                                        "                {\n" +
                                        "                    \"rel\": \"self\",\n" +
                                        "                    \"href\": \"https://some-domain/labels/issue/{issue-number}\",\n" +
                                        "                    \"type\": \"GET\"\n" +
                                        "                }\n" +
                                        "            ]\n" +
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
                                        "  \"message\": \"There isn't any Labels related with this Issue.\",\n" +
                                        "  \"details\": \"uri=/labels/issue/{issue-number}\",\n" +
                                        "  \"timestamp\": \"2021-03-28T00:04:52.293-0300\"\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun findLabelsByIssueNumber(
        @PathVariable("issue-number") issueNumber: Int,
        @RequestParam(name = "page", defaultValue = "0") page: Int = 0,
        @RequestParam(name = "limit", defaultValue = "5") limit: Int = 5,
        @RequestParam(name = "sort", defaultValue = "asc") sort: String = "asc"
    ): ResponseEntity<CollectionModel<LabelsDto>> =
        this.service.findLabelsByIssueNumber(issueNumber, page, limit, sort).run {
            this.forEach {
                configureLinks(it.links) {
                    it.add(
                        linkTo(
                            methodOn(this@LabelsController::class.java)
                                .findLabelsByIssueNumber(issueNumber, page, limit, sort)
                        ).withSelfRel().withType("GET")
                    )
                }
            }

            val link: Link = linkTo(
                methodOn(this@LabelsController::class.java)
                    .findLabelsByIssueNumber(issueNumber, page, limit, sort)
            ).withSelfRel()

            ok(CollectionModel.of(this, link))
        }

}
