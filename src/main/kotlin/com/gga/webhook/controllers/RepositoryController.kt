package com.gga.webhook.controllers

import com.gga.webhook.constants.ControllersConstants.LICENSE_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.OWNER_CONTROLLER
import com.gga.webhook.errors.ApiError
import com.gga.webhook.helper.LinkHelper
import com.gga.webhook.models.dTO.RepositoryDto
import com.gga.webhook.services.impls.RepositoryServiceImpl
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
@RequestMapping("/repository", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Repository", description = "Ações disponíveis para _'/repository'_")
class RepositoryController : LinkHelper<RepositoryDto> {

    @Autowired
    private lateinit var service: RepositoryServiceImpl

    @GetMapping("/{repository-name}")
    @Operation(
        operationId = "Obter Repository pelo nome",
        description = "Retorna um **Repository** que contém o nome passado.",
        parameters = [
            Parameter(
                name = "repository-name",
                description = "Nome do Repository a ser retornado.",
                example = "repo-name"
            )
        ],
        responses = [
            ApiResponse(
                description = "Repository encontrado.",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = RepositoryDto::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "    \"name\": \"some-name\",\n" +
                                        "    \"private\": true,\n" +
                                        "    \"description\": \"some-description\",\n" +
                                        "    \"fork\": true,\n" +
                                        "    \"url\": \"https://someurl.com/\",\n" +
                                        "    \"homepage\": \"some-homepage\",\n" +
                                        "    \"size\": 0,\n" +
                                        "    \"language\": \"some-language\",\n" +
                                        "    \"archived\": true,\n" +
                                        "    \"disabled\": true,\n" +
                                        "    \"forks\": 0,\n" +
                                        "    \"watchers\": 0,\n" +
                                        "    \"nodeId\": \"some-node-id\",\n" +
                                        "    \"fullName\": \"some-full-name\",\n" +
                                        "    \"htmlUrl\": \"https://someurl.com/\",\n" +
                                        "    \"forksUrl\": \"https://someurl.com/\",\n" +
                                        "    \"keysUrl\": \"https://someurl.com/\",\n" +
                                        "    \"collaboratorsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"teamsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"hooksUrl\": \"https://someurl.com/\",\n" +
                                        "    \"issueEventsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"eventsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"assigneesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"branchesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"tagsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"blobsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"gitTagsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"gitRefsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"treesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"statusesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"languagesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"stargazersUrl\": \"https://someurl.com/\",\n" +
                                        "    \"contributorsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"subscribersUrl\": \"https://someurl.com/\",\n" +
                                        "    \"subscriptionUrl\": \"https://someurl.com/\",\n" +
                                        "    \"commitsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"gitCommitsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"commentsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"issueCommentUrl\": \"https://someurl.com/\",\n" +
                                        "    \"contentsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"compareUrl\": \"https://someurl.com/\",\n" +
                                        "    \"mergesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"archiveUrl\": \"https://someurl.com/\",\n" +
                                        "    \"downloadsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"issuesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"pullsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"milestonesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"notificationsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"labelsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"releasesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"deploymentsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"createdAt\": \"2021-03-28T04:48:50.713Z\",\n" +
                                        "    \"updatedAt\": \"2021-03-28T04:48:50.713Z\",\n" +
                                        "    \"pushedAt\": \"2021-03-28T04:48:50.713Z\",\n" +
                                        "    \"gitUrl\": \"https://someurl.com/\",\n" +
                                        "    \"sshUrl\": \"https://someurl.com/\",\n" +
                                        "    \"cloneUrl\": \"https://someurl.com/\",\n" +
                                        "    \"svnUrl\": \"https://someurl.com/\",\n" +
                                        "    \"stargazersCount\": 0,\n" +
                                        "    \"watchersCount\": 0,\n" +
                                        "    \"hasIssues\": true,\n" +
                                        "    \"hasProjects\": true,\n" +
                                        "    \"hasDownloads\": true,\n" +
                                        "    \"hasWiki\": true,\n" +
                                        "    \"hasPages\": true,\n" +
                                        "    \"forksCount\": 0,\n" +
                                        "    \"mirrorUrl\": \"https://someurl.com/\",\n" +
                                        "    \"openIssuesCount\": 0,\n" +
                                        "    \"openIssues\": 0,\n" +
                                        "    \"defaultBranch\": \"soe-branch\",\n" +
                                        "    \"links\": [\n" +
                                        "        {\n" +
                                        "            \"rel\": \"self\",\n" +
                                        "            \"href\": \"https://some-domain/repository/{repository-name}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"owner\",\n" +
                                        "            \"href\": \"https://some-domain/owner/repository/{repository-name}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"license\",\n" +
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
                description = "Repository não encontrado.",
                responseCode = "404",
                content = [
                    Content(
                        schema = Schema(implementation = ApiError::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "  \"message\": \"Repository '{repository-name} not found.'\",\n" +
                                        "  \"details\": \"uri=/repository/{repository-name}\",\n" +
                                        "  \"timestamp\": \"2021-03-28T00:04:52.293-0300\"\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun findRepositoryByName(@PathVariable("repository-name") name: String): ResponseEntity<RepositoryDto> =
        this.service.findRepositoryByName(name).setLinks()

    @GetMapping("/event/{event-action}")
    @Operation(
        operationId = "Obter Repository pela action do Event",
        description = "Retorna um **Repository** relacionado a um determinado **Event**.",
        parameters = [
            Parameter(
                name = "event-action",
                description = "Action do Event que está vinculado com o Repository.",
                example = "opened"
            )
        ],
        responses = [
            ApiResponse(
                description = "Repository encontrado.",
                responseCode = "200",
                content = [
                    Content(
                        schema = Schema(implementation = RepositoryDto::class),
                        examples = [
                            ExampleObject(
                                value = "{\n" +
                                        "    \"name\": \"some-name\",\n" +
                                        "    \"private\": true,\n" +
                                        "    \"description\": \"some-description\",\n" +
                                        "    \"fork\": true,\n" +
                                        "    \"url\": \"https://someurl.com/\",\n" +
                                        "    \"homepage\": \"some-homepage\",\n" +
                                        "    \"size\": 0,\n" +
                                        "    \"language\": \"some-language\",\n" +
                                        "    \"archived\": true,\n" +
                                        "    \"disabled\": true,\n" +
                                        "    \"forks\": 0,\n" +
                                        "    \"watchers\": 0,\n" +
                                        "    \"nodeId\": \"some-node-id\",\n" +
                                        "    \"fullName\": \"some-full-name\",\n" +
                                        "    \"htmlUrl\": \"https://someurl.com/\",\n" +
                                        "    \"forksUrl\": \"https://someurl.com/\",\n" +
                                        "    \"keysUrl\": \"https://someurl.com/\",\n" +
                                        "    \"collaboratorsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"teamsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"hooksUrl\": \"https://someurl.com/\",\n" +
                                        "    \"issueEventsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"eventsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"assigneesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"branchesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"tagsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"blobsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"gitTagsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"gitRefsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"treesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"statusesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"languagesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"stargazersUrl\": \"https://someurl.com/\",\n" +
                                        "    \"contributorsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"subscribersUrl\": \"https://someurl.com/\",\n" +
                                        "    \"subscriptionUrl\": \"https://someurl.com/\",\n" +
                                        "    \"commitsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"gitCommitsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"commentsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"issueCommentUrl\": \"https://someurl.com/\",\n" +
                                        "    \"contentsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"compareUrl\": \"https://someurl.com/\",\n" +
                                        "    \"mergesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"archiveUrl\": \"https://someurl.com/\",\n" +
                                        "    \"downloadsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"issuesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"pullsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"milestonesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"notificationsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"labelsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"releasesUrl\": \"https://someurl.com/\",\n" +
                                        "    \"deploymentsUrl\": \"https://someurl.com/\",\n" +
                                        "    \"createdAt\": \"2021-03-28T04:48:50.713Z\",\n" +
                                        "    \"updatedAt\": \"2021-03-28T04:48:50.713Z\",\n" +
                                        "    \"pushedAt\": \"2021-03-28T04:48:50.713Z\",\n" +
                                        "    \"gitUrl\": \"https://someurl.com/\",\n" +
                                        "    \"sshUrl\": \"https://someurl.com/\",\n" +
                                        "    \"cloneUrl\": \"https://someurl.com/\",\n" +
                                        "    \"svnUrl\": \"https://someurl.com/\",\n" +
                                        "    \"stargazersCount\": 0,\n" +
                                        "    \"watchersCount\": 0,\n" +
                                        "    \"hasIssues\": true,\n" +
                                        "    \"hasProjects\": true,\n" +
                                        "    \"hasDownloads\": true,\n" +
                                        "    \"hasWiki\": true,\n" +
                                        "    \"hasPages\": true,\n" +
                                        "    \"forksCount\": 0,\n" +
                                        "    \"mirrorUrl\": \"https://someurl.com/\",\n" +
                                        "    \"openIssuesCount\": 0,\n" +
                                        "    \"openIssues\": 0,\n" +
                                        "    \"defaultBranch\": \"soe-branch\",\n" +
                                        "    \"links\": [\n" +
                                        "        {\n" +
                                        "            \"rel\": \"self\",\n" +
                                        "            \"href\": \"https://some-domain/repository/event/{event-action}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"owner\",\n" +
                                        "            \"href\": \"https://some-domain/owner/repository/{repository-name}\",\n" +
                                        "            \"type\": \"GET\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"rel\": \"license\",\n" +
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
                                        "  \"message\": \"There isn't any Repository related with this Event.'\",\n" +
                                        "  \"details\": \"uri=/repository/event/{event-action}\",\n" +
                                        "  \"timestamp\": \"2021-03-28T00:04:52.293-0300\"\n" +
                                        "}"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    fun findRepositoryByEventAction(@PathVariable("event-action") action: String): ResponseEntity<RepositoryDto> =
        this.service.findRepositoryByEventAction(action).setLinks()

    override fun RepositoryDto.setLinks(): ResponseEntity<RepositoryDto> {
        configureLinks(this.links) {
            this.add(
                linkTo(methodOn(this@RepositoryController::class.java).findRepositoryByName(name))
                    .withSelfRel().withType("GET"),

                linkTo(methodOn(OWNER_CONTROLLER).findOwnerByRepositoryName(name))
                    .withRel("owner").withType("GET")
            )

            this.license?.let {
                this.add(
                    linkTo(methodOn(LICENSE_CONTROLLER).findLicenseByRepositoryName(name))
                        .withRel("license").withType("GET")
                )
            }
        }

        return ok(this)
    }

}


