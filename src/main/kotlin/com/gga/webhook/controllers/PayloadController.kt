package com.gga.webhook.controllers

import com.gga.webhook.constants.ControllersConstants.EVENT_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.ISSUE_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.REPOSITORY_CONTROLLER
import com.gga.webhook.constants.ControllersConstants.SENDER_CONTROLLER
import com.gga.webhook.models.dTO.EventDto
import com.gga.webhook.models.dTO.PayloadDto
import com.gga.webhook.services.impls.PayloadServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.parameters.RequestBody as Body

@RestController
@RequestMapping("/payload", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Payload", description = "Ações disponíveis para _'/payload'_")
class PayloadController {

    @Autowired
    private lateinit var service: PayloadServiceImpl

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        operationId = "Salvar Payload",
        description = "Salva o **Payload**, vindo diretamente do GitHub, ao abrir/modificar uma **Issue**.",
        requestBody = Body(
            description = "Objeto vindo diretamente do GitHub",
            content = [
                Content(
                    schema = Schema(implementation = PayloadDto::class),
                    examples = [
                        ExampleObject(
                            value = "{\n" +
                                    "  \"action\": \"some-action\",\n" +
                                    "  \"issue\": {\n" +
                                    "    \"url\": \"https://someurl.com/\",\n" +
                                    "    \"repository_url\": \"https://someurl.com/\",\n" +
                                    "    \"labels_url\": \"https://someurl.com/\",\n" +
                                    "    \"comments_url\": \"https://someurl.com/\",\n" +
                                    "    \"events_url\": \"https://someurl.com/\",\n" +
                                    "    \"html_url\": \"https://someurl.com/\",\n" +
                                    "    \"node_id\": \"some-node-id\",\n" +
                                    "    \"number\": 16,\n" +
                                    "    \"title\": \"some-title\",\n" +
                                    "    \"user\": {\n" +
                                    "      \"login\": \"some-login\",\n" +
                                    "      \"node_id\": \"some-node-id\",\n" +
                                    "      \"avatar_url\": \"https://someurl.com/\",\n" +
                                    "      \"gravatar_id\": \"some-gravatar-id\",\n" +
                                    "      \"url\": \"https://someurl.com/\",\n" +
                                    "      \"html_url\": \"https://someurl.com/\",\n" +
                                    "      \"followers_url\": \"https://someurl.com/\",\n" +
                                    "      \"following_url\": \"https://someurl.com/\",\n" +
                                    "      \"gists_url\": \"https://someurl.com/\",\n" +
                                    "      \"starred_url\": \"https://someurl.com/\",\n" +
                                    "      \"subscriptions_url\": \"https://someurl.com/\",\n" +
                                    "      \"organizations_url\": \"https://someurl.com/\",\n" +
                                    "      \"repos_url\": \"https://someurl.com/\",\n" +
                                    "      \"events_url\": \"https://someurl.com/\",\n" +
                                    "      \"received_events_url\": \"https://someurl.com/\",\n" +
                                    "      \"type\": \"some-type\",\n" +
                                    "      \"site_admin\": false\n" +
                                    "    },\n" +
                                    "    \"labels\": [\n" +
                                    "      {\n" +
                                    "        \"node_id\": \"some-node-id\",\n" +
                                    "        \"url\": \"https://someurl.com/\",\n" +
                                    "        \"name\": \"some-name\",\n" +
                                    "        \"color\": \"some-color\",\n" +
                                    "        \"default\": true,\n" +
                                    "        \"description\": \"some-description\"\n" +
                                    "      }\n" +
                                    "    ],\n" +
                                    "    \"state\": \"some-state\",\n" +
                                    "    \"locked\": false,\n" +
                                    "    \"assignee\": {\n" +
                                    "      \"login\": \"some-login\",\n" +
                                    "      \"node_id\": \"some-node-id\",\n" +
                                    "      \"avatar_url\": \"https://someurl.com/\",\n" +
                                    "      \"gravatar_id\": \"some-gravatar-id\",\n" +
                                    "      \"url\": \"https://someurl.com/\",\n" +
                                    "      \"html_url\": \"https://someurl.com/\",\n" +
                                    "      \"followers_url\": \"https://someurl.com/\",\n" +
                                    "      \"following_url\": \"https://someurl.com/\",\n" +
                                    "      \"gists_url\": \"https://someurl.com/\",\n" +
                                    "      \"starred_url\": \"https://someurl.com/\",\n" +
                                    "      \"subscriptions_url\": \"https://someurl.com/\",\n" +
                                    "      \"organizations_url\": \"https://someurl.com/\",\n" +
                                    "      \"repos_url\": \"https://someurl.com/\",\n" +
                                    "      \"events_url\": \"https://someurl.com/\",\n" +
                                    "      \"received_events_url\": \"https://someurl.com/\",\n" +
                                    "      \"type\": \"some-type\",\n" +
                                    "      \"site_admin\": false\n" +
                                    "    },\n" +
                                    "    \"assignees\": [\n" +
                                    "      {\n" +
                                    "        \"login\": \"some-login\",\n" +
                                    "        \"node_id\": \"some-node-id\",\n" +
                                    "        \"avatar_url\": \"https://someurl.com/\",\n" +
                                    "        \"gravatar_id\": \"some-gravatar-id\",\n" +
                                    "        \"url\": \"https://someurl.com/\",\n" +
                                    "        \"html_url\": \"https://someurl.com/\",\n" +
                                    "        \"followers_url\": \"https://someurl.com/\",\n" +
                                    "        \"following_url\": \"https://someurl.com/\",\n" +
                                    "        \"gists_url\": \"https://someurl.com/\",\n" +
                                    "        \"starred_url\": \"https://someurl.com/\",\n" +
                                    "        \"subscriptions_url\": \"https://someurl.com/\",\n" +
                                    "        \"organizations_url\": \"https://someurl.com/\",\n" +
                                    "        \"repos_url\": \"https://someurl.com/\",\n" +
                                    "        \"events_url\": \"https://someurl.com/\",\n" +
                                    "        \"received_events_url\": \"https://someurl.com/\",\n" +
                                    "        \"type\": \"some-type\",\n" +
                                    "        \"site_admin\": false\n" +
                                    "      }\n" +
                                    "    ],\n" +
                                    "    \"milestone\": {\n" +
                                    "      \"url\": \"https://someurl.com/\",\n" +
                                    "      \"html_url\": \"https://someurl.com/\",\n" +
                                    "      \"labels_url\": \"https://someurl.com/\",\n" +
                                    "      \"node_id\": \"some-node-id\",\n" +
                                    "      \"number\": 1,\n" +
                                    "      \"title\": \"some-title\",\n" +
                                    "      \"description\": null,\n" +
                                    "      \"creator\": {\n" +
                                    "        \"login\": \"some-login\",\n" +
                                    "        \"node_id\": \"some-node-id\",\n" +
                                    "        \"avatar_url\": \"https://someurl.com/\",\n" +
                                    "        \"gravatar_id\": \"some-gravatar-id\",\n" +
                                    "        \"url\": \"https://someurl.com/\",\n" +
                                    "        \"html_url\": \"https://someurl.com/\",\n" +
                                    "        \"followers_url\": \"https://someurl.com/\",\n" +
                                    "        \"following_url\": \"https://someurl.com/\",\n" +
                                    "        \"gists_url\": \"https://someurl.com/\",\n" +
                                    "        \"starred_url\": \"https://someurl.com/\",\n" +
                                    "        \"subscriptions_url\": \"https://someurl.com/\",\n" +
                                    "        \"organizations_url\": \"https://someurl.com/\",\n" +
                                    "        \"repos_url\": \"https://someurl.com/\",\n" +
                                    "        \"events_url\": \"https://someurl.com/\",\n" +
                                    "        \"received_events_url\": \"https://someurl.com/\",\n" +
                                    "        \"type\": \"some-type\",\n" +
                                    "        \"site_admin\": false\n" +
                                    "      },\n" +
                                    "      \"open_issues\": 1,\n" +
                                    "      \"closed_issues\": 1,\n" +
                                    "      \"state\": \"some-state\",\n" +
                                    "      \"created_at\": \"2021-02-17T05:29:54Z\",\n" +
                                    "      \"updated_at\": \"2021-02-17T05:38:06Z\",\n" +
                                    "      \"due_on\": null,\n" +
                                    "      \"closed_at\": null\n" +
                                    "    },\n" +
                                    "    \"comments\": 0,\n" +
                                    "    \"created_at\": \"2021-02-17T05:33:48Z\",\n" +
                                    "    \"updated_at\": \"2021-02-17T05:38:06Z\",\n" +
                                    "    \"closed_at\": \"2021-02-17T05:38:06Z\",\n" +
                                    "    \"author_association\": \"some-author\",\n" +
                                    "    \"active_lock_reason\": null,\n" +
                                    "    \"body\": \"some-body\",\n" +
                                    "    \"performed_via_github_app\": null\n" +
                                    "  },\n" +
                                    "  \"repository\": {\n" +
                                    "    \"node_id\": \"some-node-id\",\n" +
                                    "    \"name\": \"some-name\",\n" +
                                    "    \"full_name\": \"some-full-name\",\n" +
                                    "    \"private\": true,\n" +
                                    "    \"owner\": {\n" +
                                    "      \"login\": \"some-login\",\n" +
                                    "      \"node_id\": \"some-node-id\",\n" +
                                    "      \"avatar_url\": \"https://someurl.com/\",\n" +
                                    "      \"gravatar_id\": \"some-gravatar-id\",\n" +
                                    "      \"url\": \"https://someurl.com/\",\n" +
                                    "      \"html_url\": \"https://someurl.com/\",\n" +
                                    "      \"followers_url\": \"https://someurl.com/\",\n" +
                                    "      \"following_url\": \"https://someurl.com/\",\n" +
                                    "      \"gists_url\": \"https://someurl.com/\",\n" +
                                    "      \"starred_url\": \"https://someurl.com/\",\n" +
                                    "      \"subscriptions_url\": \"https://someurl.com/\",\n" +
                                    "      \"organizations_url\": \"https://someurl.com/\",\n" +
                                    "      \"repos_url\": \"https://someurl.com/\",\n" +
                                    "      \"events_url\": \"https://someurl.com/\",\n" +
                                    "      \"received_events_url\": \"https://someurl.com/\",\n" +
                                    "      \"type\": \"some-type\",\n" +
                                    "      \"site_admin\": false\n" +
                                    "    },\n" +
                                    "    \"html_url\": \"https://someurl.com/\",\n" +
                                    "    \"description\": \"some-description\",\n" +
                                    "    \"fork\": false,\n" +
                                    "    \"url\": \"https://someurl.com/\",\n" +
                                    "    \"forks_url\": \"https://someurl.com/\",\n" +
                                    "    \"keys_url\": \"https://someurl.com/\",\n" +
                                    "    \"collaborators_url\": \"https://someurl.com/\",\n" +
                                    "    \"teams_url\": \"https://someurl.com/\",\n" +
                                    "    \"hooks_url\": \"https://someurl.com/\",\n" +
                                    "    \"issue_events_url\": \"https://someurl.com/\",\n" +
                                    "    \"events_url\": \"https://someurl.com/\",\n" +
                                    "    \"assignees_url\": \"https://someurl.com/\",\n" +
                                    "    \"branches_url\": \"https://someurl.com/\",\n" +
                                    "    \"tags_url\": \"https://someurl.com/\",\n" +
                                    "    \"blobs_url\": \"https://someurl.com/\",\n" +
                                    "    \"git_tags_url\": \"https://someurl.com/\",\n" +
                                    "    \"git_refs_url\": \"https://someurl.com/\",\n" +
                                    "    \"trees_url\": \"https://someurl.com/\",\n" +
                                    "    \"statuses_url\": \"https://someurl.com/\",\n" +
                                    "    \"languages_url\": \"https://someurl.com/\",\n" +
                                    "    \"stargazers_url\": \"https://someurl.com/\",\n" +
                                    "    \"contributors_url\": \"https://someurl.com/\",\n" +
                                    "    \"subscribers_url\": \"https://someurl.com/\",\n" +
                                    "    \"subscription_url\": \"https://someurl.com/\",\n" +
                                    "    \"commits_url\": \"https://someurl.com/\",\n" +
                                    "    \"git_commits_url\": \"https://someurl.com/\",\n" +
                                    "    \"comments_url\": \"https://someurl.com/\",\n" +
                                    "    \"issue_comment_url\": \"https://someurl.com/\",\n" +
                                    "    \"contents_url\": \"https://someurl.com/\",\n" +
                                    "    \"compare_url\": \"https://someurl.com/\",\n" +
                                    "    \"merges_url\": \"https://someurl.com/\",\n" +
                                    "    \"archive_url\": \"https://someurl.com/\",\n" +
                                    "    \"downloads_url\": \"https://someurl.com/\",\n" +
                                    "    \"issues_url\": \"https://someurl.com/\",\n" +
                                    "    \"pulls_url\": \"https://someurl.com/\",\n" +
                                    "    \"milestones_url\": \"https://someurl.com/\",\n" +
                                    "    \"notifications_url\": \"https://someurl.com/\",\n" +
                                    "    \"labels_url\": \"https://someurl.com/\",\n" +
                                    "    \"releases_url\": \"https://someurl.com/\",\n" +
                                    "    \"deployments_url\": \"https://someurl.com/\",\n" +
                                    "    \"created_at\": \"2020-12-15T07:04:36Z\",\n" +
                                    "    \"updated_at\": \"2020-12-16T22:09:06Z\",\n" +
                                    "    \"pushed_at\": \"2020-12-16T22:09:04Z\",\n" +
                                    "    \"git_url\": \"https://someurl.com/\",\n" +
                                    "    \"ssh_url\": \"https://someurl.com/\",\n" +
                                    "    \"clone_url\": \"https://someurl.com/\",\n" +
                                    "    \"svn_url\": \"https://someurl.com/\",\n" +
                                    "    \"homepage\": \"some-homepage\",\n" +
                                    "    \"size\": 82,\n" +
                                    "    \"stargazers_count\": 0,\n" +
                                    "    \"watchers_count\": 0,\n" +
                                    "    \"language\": \"some-language\",\n" +
                                    "    \"has_issues\": true,\n" +
                                    "    \"has_projects\": true,\n" +
                                    "    \"has_downloads\": true,\n" +
                                    "    \"has_wiki\": true,\n" +
                                    "    \"has_pages\": false,\n" +
                                    "    \"forks_count\": 0,\n" +
                                    "    \"mirror_url\": null,\n" +
                                    "    \"archived\": false,\n" +
                                    "    \"disabled\": false,\n" +
                                    "    \"open_issues_count\": 1,\n" +
                                    "    \"license\": {\n" +
                                    "      \"key\": \"some-key\",\n" +
                                    "      \"name\": \"some-name\",\n" +
                                    "      \"spdx_id\": \"some-spdx-id\",\n" +
                                    "      \"url\": \"https://someurl.com/\",\n" +
                                    "      \"node_id\": \"some-node-id\"\n" +
                                    "    },\n" +
                                    "    \"forks\": 0,\n" +
                                    "    \"open_issues\": 1,\n" +
                                    "    \"watchers\": 0,\n" +
                                    "    \"default_branch\": \"some-branch\"\n" +
                                    "  },\n" +
                                    "  \"sender\": {\n" +
                                    "    \"login\": \"some-login\",\n" +
                                    "    \"node_id\": \"some-node-id\",\n" +
                                    "    \"avatar_url\": \"https://someurl.com/\",\n" +
                                    "    \"gravatar_id\": \"\",\n" +
                                    "    \"url\": \"https://someurl.com/\",\n" +
                                    "    \"html_url\": \"https://someurl.com/\",\n" +
                                    "    \"followers_url\": \"https://someurl.com/\",\n" +
                                    "    \"following_url\": \"https://someurl.com/\",\n" +
                                    "    \"gists_url\": \"https://someurl.com/\",\n" +
                                    "    \"starred_url\": \"https://someurl.com/\",\n" +
                                    "    \"subscriptions_url\": \"https://someurl.com/\",\n" +
                                    "    \"organizations_url\": \"https://someurl.com/\",\n" +
                                    "    \"repos_url\": \"https://someurl.com/\",\n" +
                                    "    \"events_url\": \"https://someurl.com/\",\n" +
                                    "    \"received_events_url\": \"https://someurl.com/\",\n" +
                                    "    \"type\": \"some-type\",\n" +
                                    "    \"site_admin\": false\n" +
                                    "  }\n" +
                                    "}"
                        )
                    ]
                )
            ]
        ),
        responses = [
            ApiResponse(
                description = "Dados salvos.",
                responseCode = "201",
                content = [
                    Content(
                        schema = Schema(implementation = EventDto::class),
                        examples = [ExampleObject(
                            value = "{\n" +
                                    "    \"action\": \"some-action\",\n" +
                                    "    \"links\": [\n" +
                                    "        {\n" +
                                    "            \"rel\": \"self\",\n" +
                                    "            \"href\": \"https://some-domain/payload/\",\n" +
                                    "            \"type\": \"POST\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"rel\": \"event\",\n" +
                                    "            \"href\": \"https://some-domain/event/{event-action}\",\n" +
                                    "            \"type\": \"GET\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"rel\": \"issue\",\n" +
                                    "            \"href\": \"https://some-domain/issue/{issue-number}\",\n" +
                                    "            \"type\": \"GET\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"rel\": \"repository\",\n" +
                                    "            \"href\": \"https://some-domain/repository/{repository-name}\",\n" +
                                    "            \"type\": \"GET\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"rel\": \"sender\",\n" +
                                    "            \"href\": \"https://some-domain/sender/{sender-login}\",\n" +
                                    "            \"type\": \"GET\"\n" +
                                    "        }\n" +
                                    "    ]\n" +
                                    "}"
                        )
                        ]
                    )
                ]
            )
        ]
    )
    fun savePayload(@RequestBody payloadDto: PayloadDto): ResponseEntity<EventDto> =
        this.service.savePayloadData(payloadDto).run {
            this.add(
                linkTo(methodOn(this@PayloadController::class.java).savePayload(payloadDto)).withSelfRel()
                    .withType("POST"),

                linkTo(methodOn(EVENT_CONTROLLER).findEventByAction(this.action)).withRel("event").withType("GET"),

                linkTo(methodOn(ISSUE_CONTROLLER).findIssueByNumber(payloadDto.issue!!.number)).withRel("issue")
                    .withType("GET"),

                linkTo(methodOn(REPOSITORY_CONTROLLER).findRepositoryByName(payloadDto.repository!!.name))
                    .withRel("repository").withType("GET"),

                linkTo(methodOn(SENDER_CONTROLLER).findSenderByLogin(payloadDto.sender!!.login)).withRel("sender")
                    .withType("GET")
            )

            status(HttpStatus.CREATED).body(this)
        }

}