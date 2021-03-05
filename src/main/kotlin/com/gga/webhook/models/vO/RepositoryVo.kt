package com.gga.webhook.models.vO

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable
import java.time.Instant

data class RepositoryVo @JvmOverloads constructor(
    @JsonProperty("id")
    var id: Long = 0,

    @JsonProperty("node_id")
    var nodeId: String = "",

    @JsonProperty("name")
    var name: String = "",

    @JsonProperty("full_name")
    var fullName: String = "",

    @JsonProperty("private")
    var private: Boolean = false,

    @JsonProperty("html_url")
    var htmlUrl: String = "",

    @JsonProperty("description")
    var description: String? = null,

    @JsonProperty("fork")
    var fork: Boolean = false,

    @JsonProperty("url")
    var url: String = "",

    @JsonProperty("forks_url")
    var forksUrl: String = "",

    @JsonProperty("keys_url")
    var keysUrl: String = "",

    @JsonProperty("collaborators_url")
    var collaboratorsUrl: String = "",

    @JsonProperty("teams_url")
    var teamsUrl: String = "",

    @JsonProperty("hooks_url")
    var hooksUrl: String = "",

    @JsonProperty("issue_events_url")
    var issueEventsUrl: String = "",

    @JsonProperty("events_url")
    var eventsUrl: String = "",

    @JsonProperty("assignees_url")
    var assigneesUrl: String = "",

    @JsonProperty("branches_url")
    var branchesUrl: String = "",

    @JsonProperty("tags_url")
    var tagsUrl: String = "",

    @JsonProperty("blobs_url")
    var blobsUrl: String = "",

    @JsonProperty("git_tags_url")
    var gitTagsUrl: String = "",

    @JsonProperty("git_refs_url")
    var gitRefsUrl: String = "",

    @JsonProperty("trees_url")
    var treesUrl: String = "",

    @JsonProperty("statuses_url")
    var statusesUrl: String = "",

    @JsonProperty("languages_url")
    var languagesUrl: String = "",

    @JsonProperty("stargazers_url")
    var stargazersUrl: String = "",

    @JsonProperty("contributors_url")
    var contributorsUrl: String = "",

    @JsonProperty("subscribers_url")
    var subscribersUrl: String = "",

    @JsonProperty("subscription_url")
    var subscriptionUrl: String = "",

    @JsonProperty("commits_url")
    var commitsUrl: String = "",

    @JsonProperty("git_commits_url")
    var gitCommitsUrl: String = "",

    @JsonProperty("comments_url")
    var commentsUrl: String = "",

    @JsonProperty("issue_comment_url")
    var issueCommentUrl: String = "",

    @JsonProperty("contents_url")
    var contentsUrl: String = "",

    @JsonProperty("compare_url")
    var compareUrl: String = "",

    @JsonProperty("merges_url")
    var mergesUrl: String = "",

    @JsonProperty("archive_url")
    var archiveUrl: String = "",

    @JsonProperty("downloads_url")
    var downloadsUrl: String = "",

    @JsonProperty("issues_url")
    var issuesUrl: String = "",

    @JsonProperty("pulls_url")
    var pullsUrl: String = "",

    @JsonProperty("milestones_url")
    var milestonesUrl: String = "",

    @JsonProperty("notifications_url")
    var notificationsUrl: String = "",

    @JsonProperty("labels_url")
    var labelsUrl: String = "",

    @JsonProperty("releases_url")
    var releasesUrl: String = "",

    @JsonProperty("deployments_url")
    var deploymentsUrl: String = "",

    @JsonProperty("created_at")
    var createdAt: Instant? = null,

    @JsonProperty("updated_at")
    var updatedAt: Instant? = null,

    @JsonProperty("pushed_at")
    var pushedAt: Instant? = null,

    @JsonProperty("git_url")
    var gitUrl: String? = null,

    @JsonProperty("ssh_url")
    var sshUrl: String? = null,

    @JsonProperty("clone_url")
    var cloneUrl: String? = null,

    @JsonProperty("svn_url")
    var svnUrl: String? = null,

    @JsonProperty("homepage")
    var homepage: String? = null,

    @JsonProperty("size")
    var size: Int = 0,

    @JsonProperty("stargazers_count")
    var stargazersCount: Int = 0,

    @JsonProperty("watchers_count")
    var watchersCount: Int = 0,

    @JsonProperty("language")
    var language: String = "",

    @JsonProperty("has_issues")
    var hasIssues: Boolean = false,

    @JsonProperty("has_projects")
    var hasProjects: Boolean = false,

    @JsonProperty("has_downloads")
    var hasDownloads: Boolean = false,

    @JsonProperty("has_wiki")
    var hasWiki: Boolean = false,

    @JsonProperty("has_pages")
    var hasPages: Boolean = false,

    @JsonProperty("forks_count")
    var forksCount: Int = 0,

    @JsonProperty("mirror_url")
    var mirrorUrl: String? = null,

    @JsonProperty("archived")
    var archived: Boolean = false,

    @JsonProperty("disabled")
    var disabled: Boolean = false,

    @JsonProperty("open_issues_count")
    var openIssuesCount: Int = 0,

    @JsonProperty("forks")
    var forks: Int = 0,

    @JsonProperty("open_issues")
    var openIssues: Int = 0,

    @JsonProperty("watchers")
    var watchers: Int = 0,

    @JsonProperty("default_branch")
    var defaultBranch: String = ""
) : Serializable, RepresentationModel<RepositoryVo>()