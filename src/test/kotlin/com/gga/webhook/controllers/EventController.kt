package com.gga.webhook.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest
@AutoConfigureMockMvc
internal class EventController {

    companion object {

        private const val ISSUE: String = "/issue"

    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun saveIssue() {
        val body: String = "{\n" +
                "  \"action\": \"closed\",\n" +
                "  \"issue\": {\n" +
                "    \"url\": \"https://api.github.com/repos/mock/webhook/issues/14\",\n" +
                "    \"repository_url\": \"https://api.github.com/repos/mock/webhook\",\n" +
                "    \"labels_url\": \"https://api.github.com/repos/mock/webhook/issues/14/labels{/name}\",\n" +
                "    \"comments_url\": \"https://api.github.com/repos/mock/webhook/issues/14/comments\",\n" +
                "    \"events_url\": \"https://api.github.com/repos/mock/webhook/issues/14/events\",\n" +
                "    \"html_url\": \"https://github.com/mock/webhook/issues/14\",\n" +
                "    \"id\": 782116486,\n" +
                "    \"node_id\": \"MDU6SXNzdWU3ODIxMTY0ODY=\",\n" +
                "    \"number\": 14,\n" +
                "    \"title\": \"teste\",\n" +
                "    \"user\": {\n" +
                "      \"login\": \"mock\",\n" +
                "      \"id\": 42354016,\n" +
                "      \"node_id\": \"MDQ6VXNlcjQyMzU0MDE2\",\n" +
                "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/42354016?v=4\",\n" +
                "      \"gravatar_id\": \"\",\n" +
                "      \"url\": \"https://api.github.com/users/mock\",\n" +
                "      \"html_url\": \"https://github.com/mock\",\n" +
                "      \"followers_url\": \"https://api.github.com/users/mock/followers\",\n" +
                "      \"following_url\": \"https://api.github.com/users/mock/following{/other_user}\",\n" +
                "      \"gists_url\": \"https://api.github.com/users/mock/gists{/gist_id}\",\n" +
                "      \"starred_url\": \"https://api.github.com/users/mock/starred{/owner}{/repo}\",\n" +
                "      \"subscriptions_url\": \"https://api.github.com/users/mock/subscriptions\",\n" +
                "      \"organizations_url\": \"https://api.github.com/users/mock/orgs\",\n" +
                "      \"repos_url\": \"https://api.github.com/users/mock/repos\",\n" +
                "      \"events_url\": \"https://api.github.com/users/mock/events{/privacy}\",\n" +
                "      \"received_events_url\": \"https://api.github.com/users/mock/received_events\",\n" +
                "      \"type\": \"User\",\n" +
                "      \"site_admin\": false\n" +
                "    },\n" +
                "    \"labels\": [\n" +
                "\n" +
                "    ],\n" +
                "    \"state\": \"closed\",\n" +
                "    \"locked\": false,\n" +
                "    \"assignee\": null,\n" +
                "    \"assignees\": [\n" +
                "\n" +
                "    ],\n" +
                "    \"milestone\": null,\n" +
                "    \"comments\": 0,\n" +
                "    \"created_at\": \"2021-01-08T13:24:40Z\",\n" +
                "    \"updated_at\": \"2021-02-17T04:31:24Z\",\n" +
                "    \"closed_at\": \"2021-02-17T04:31:24Z\",\n" +
                "    \"author_association\": \"OWNER\",\n" +
                "    \"active_lock_reason\": null,\n" +
                "    \"body\": \"\",\n" +
                "    \"performed_via_github_app\": null\n" +
                "  },\n" +
                "  \"repository\": {\n" +
                "    \"id\": 321581581,\n" +
                "    \"node_id\": \"MDEwOlJlcG9zaXRvcnkzMjE1ODE1ODE=\",\n" +
                "    \"name\": \"webhook\",\n" +
                "    \"full_name\": \"mock/webhook\",\n" +
                "    \"private\": true,\n" +
                "    \"owner\": {\n" +
                "      \"login\": \"mock\",\n" +
                "      \"id\": 42354016,\n" +
                "      \"node_id\": \"MDQ6VXNlcjQyMzU0MDE2\",\n" +
                "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/42354016?v=4\",\n" +
                "      \"gravatar_id\": \"\",\n" +
                "      \"url\": \"https://api.github.com/users/mock\",\n" +
                "      \"html_url\": \"https://github.com/mock\",\n" +
                "      \"followers_url\": \"https://api.github.com/users/mock/followers\",\n" +
                "      \"following_url\": \"https://api.github.com/users/mock/following{/other_user}\",\n" +
                "      \"gists_url\": \"https://api.github.com/users/mock/gists{/gist_id}\",\n" +
                "      \"starred_url\": \"https://api.github.com/users/mock/starred{/owner}{/repo}\",\n" +
                "      \"subscriptions_url\": \"https://api.github.com/users/mock/subscriptions\",\n" +
                "      \"organizations_url\": \"https://api.github.com/users/mock/orgs\",\n" +
                "      \"repos_url\": \"https://api.github.com/users/mock/repos\",\n" +
                "      \"events_url\": \"https://api.github.com/users/mock/events{/privacy}\",\n" +
                "      \"received_events_url\": \"https://api.github.com/users/mock/received_events\",\n" +
                "      \"type\": \"User\",\n" +
                "      \"site_admin\": false\n" +
                "    },\n" +
                "    \"html_url\": \"https://github.com/mock/webhook\",\n" +
                "    \"description\": null,\n" +
                "    \"fork\": false,\n" +
                "    \"url\": \"https://api.github.com/repos/mock/webhook\",\n" +
                "    \"forks_url\": \"https://api.github.com/repos/mock/webhook/forks\",\n" +
                "    \"keys_url\": \"https://api.github.com/repos/mock/webhook/keys{/key_id}\",\n" +
                "    \"collaborators_url\": \"https://api.github.com/repos/mock/webhook/collaborators{/collaborator}\",\n" +
                "    \"teams_url\": \"https://api.github.com/repos/mock/webhook/teams\",\n" +
                "    \"hooks_url\": \"https://api.github.com/repos/mock/webhook/hooks\",\n" +
                "    \"issue_events_url\": \"https://api.github.com/repos/mock/webhook/issues/events{/number}\",\n" +
                "    \"events_url\": \"https://api.github.com/repos/mock/webhook/events\",\n" +
                "    \"assignees_url\": \"https://api.github.com/repos/mock/webhook/assignees{/user}\",\n" +
                "    \"branches_url\": \"https://api.github.com/repos/mock/webhook/branches{/branch}\",\n" +
                "    \"tags_url\": \"https://api.github.com/repos/mock/webhook/tags\",\n" +
                "    \"blobs_url\": \"https://api.github.com/repos/mock/webhook/git/blobs{/sha}\",\n" +
                "    \"git_tags_url\": \"https://api.github.com/repos/mock/webhook/git/tags{/sha}\",\n" +
                "    \"git_refs_url\": \"https://api.github.com/repos/mock/webhook/git/refs{/sha}\",\n" +
                "    \"trees_url\": \"https://api.github.com/repos/mock/webhook/git/trees{/sha}\",\n" +
                "    \"statuses_url\": \"https://api.github.com/repos/mock/webhook/statuses/{sha}\",\n" +
                "    \"languages_url\": \"https://api.github.com/repos/mock/webhook/languages\",\n" +
                "    \"stargazers_url\": \"https://api.github.com/repos/mock/webhook/stargazers\",\n" +
                "    \"contributors_url\": \"https://api.github.com/repos/mock/webhook/contributors\",\n" +
                "    \"subscribers_url\": \"https://api.github.com/repos/mock/webhook/subscribers\",\n" +
                "    \"subscription_url\": \"https://api.github.com/repos/mock/webhook/subscription\",\n" +
                "    \"commits_url\": \"https://api.github.com/repos/mock/webhook/commits{/sha}\",\n" +
                "    \"git_commits_url\": \"https://api.github.com/repos/mock/webhook/git/commits{/sha}\",\n" +
                "    \"comments_url\": \"https://api.github.com/repos/mock/webhook/comments{/number}\",\n" +
                "    \"issue_comment_url\": \"https://api.github.com/repos/mock/webhook/issues/comments{/number}\",\n" +
                "    \"contents_url\": \"https://api.github.com/repos/mock/webhook/contents/{+path}\",\n" +
                "    \"compare_url\": \"https://api.github.com/repos/mock/webhook/compare/{base}...{head}\",\n" +
                "    \"merges_url\": \"https://api.github.com/repos/mock/webhook/merges\",\n" +
                "    \"archive_url\": \"https://api.github.com/repos/mock/webhook/{archive_format}{/ref}\",\n" +
                "    \"downloads_url\": \"https://api.github.com/repos/mock/webhook/downloads\",\n" +
                "    \"issues_url\": \"https://api.github.com/repos/mock/webhook/issues{/number}\",\n" +
                "    \"pulls_url\": \"https://api.github.com/repos/mock/webhook/pulls{/number}\",\n" +
                "    \"milestones_url\": \"https://api.github.com/repos/mock/webhook/milestones{/number}\",\n" +
                "    \"notifications_url\": \"https://api.github.com/repos/mock/webhook/notifications{?since,all,participating}\",\n" +
                "    \"labels_url\": \"https://api.github.com/repos/mock/webhook/labels{/name}\",\n" +
                "    \"releases_url\": \"https://api.github.com/repos/mock/webhook/releases{/id}\",\n" +
                "    \"deployments_url\": \"https://api.github.com/repos/mock/webhook/deployments\",\n" +
                "    \"created_at\": \"2020-12-15T07:04:36Z\",\n" +
                "    \"updated_at\": \"2020-12-16T22:09:06Z\",\n" +
                "    \"pushed_at\": \"2020-12-16T22:09:04Z\",\n" +
                "    \"git_url\": \"git://github.com/mock/webhook.git\",\n" +
                "    \"ssh_url\": \"git@github.com:mock/webhook.git\",\n" +
                "    \"clone_url\": \"https://github.com/mock/webhook.git\",\n" +
                "    \"svn_url\": \"https://github.com/mock/webhook\",\n" +
                "    \"homepage\": null,\n" +
                "    \"size\": 82,\n" +
                "    \"stargazers_count\": 0,\n" +
                "    \"watchers_count\": 0,\n" +
                "    \"language\": \"Kotlin\",\n" +
                "    \"has_issues\": true,\n" +
                "    \"has_projects\": true,\n" +
                "    \"has_downloads\": true,\n" +
                "    \"has_wiki\": true,\n" +
                "    \"has_pages\": false,\n" +
                "    \"forks_count\": 0,\n" +
                "    \"mirror_url\": null,\n" +
                "    \"archived\": false,\n" +
                "    \"disabled\": false,\n" +
                "    \"open_issues_count\": 0,\n" +
                "    \"license\": null,\n" +
                "    \"forks\": 0,\n" +
                "    \"open_issues\": 0,\n" +
                "    \"watchers\": 0,\n" +
                "    \"default_branch\": \"main\"\n" +
                "  },\n" +
                "  \"sender\": {\n" +
                "    \"login\": \"mock\",\n" +
                "    \"id\": 42354016,\n" +
                "    \"node_id\": \"MDQ6VXNlcjQyMzU0MDE2\",\n" +
                "    \"avatar_url\": \"https://avatars.githubusercontent.com/u/42354016?v=4\",\n" +
                "    \"gravatar_id\": \"\",\n" +
                "    \"url\": \"https://api.github.com/users/mock\",\n" +
                "    \"html_url\": \"https://github.com/mock\",\n" +
                "    \"followers_url\": \"https://api.github.com/users/mock/followers\",\n" +
                "    \"following_url\": \"https://api.github.com/users/mock/following{/other_user}\",\n" +
                "    \"gists_url\": \"https://api.github.com/users/mock/gists{/gist_id}\",\n" +
                "    \"starred_url\": \"https://api.github.com/users/mock/starred{/owner}{/repo}\",\n" +
                "    \"subscriptions_url\": \"https://api.github.com/users/mock/subscriptions\",\n" +
                "    \"organizations_url\": \"https://api.github.com/users/mock/orgs\",\n" +
                "    \"repos_url\": \"https://api.github.com/users/mock/repos\",\n" +
                "    \"events_url\": \"https://api.github.com/users/mock/events{/privacy}\",\n" +
                "    \"received_events_url\": \"https://api.github.com/users/mock/received_events\",\n" +
                "    \"type\": \"User\",\n" +
                "    \"site_admin\": false\n" +
                "  }\n" +
                "}"

    }

    private fun jsonParser(value: String): String = ObjectMapper().writeValueAsString(value)

    private fun httpGet(): MockHttpServletRequestBuilder = get(ISSUE)
        .accept(MediaType.APPLICATION_JSON)

    private fun httpPost(json: String): MockHttpServletRequestBuilder = post(ISSUE)
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json)

}