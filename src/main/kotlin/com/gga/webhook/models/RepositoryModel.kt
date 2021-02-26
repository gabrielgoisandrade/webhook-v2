package com.gga.webhook.models

import java.io.Serializable
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "REPOSITORY")
data class RepositoryModel @JvmOverloads constructor(
        @Id
        @Column(name = "REPOSITORY_ID")
        var id: Long = 0,

        @Column(name = "NODE_ID", unique = true)
        var nodeId: String = "",

        @Column(name = "NAME")
        var name: String = "",

        @Column(name = "FULL_NAME")
        var fullName: String = "",

        @Column(name = "PRIVATE")
        var private: Boolean = false,

        @OneToOne(mappedBy = "repository")
        var owner: OwnerModel? = null,

        @Column(name = "HTML_URL")
        var htmlUrl: String = "",

        @Column(name = "DESCRIPTION")
        var description: String = "",

        @Column(name = "FORK")
        var fork: Boolean = false,

        @Column(name = "URL")
        var url: String = "",

        @Column(name = "FORKS_URL")
        var forksUrl: String = "",

        @Column(name = "KEYS_URL")
        var keysUrl: String = "",

        @Column(name = "COLLABORATORS_URL")
        var collaboratorsUrl: String = "",

        @Column(name = "TEAMS_URL")
        var teamsUrl: String = "",

        @Column(name = "HOOKS_URL")
        var hooksUrl: String = "",

        @Column(name = "ISSUE_EVENTS_URL")
        var issueEventsUrl: String = "",

        @Column(name = "EVENTS_URL")
        var eventsUrl: String = "",

        @Column(name = "ASSIGNEES_URL")
        var assigneesUrl: String = "",

        @Column(name = "BRANCHES_URL")
        var branchesUrl: String = "",

        @Column(name = "TAGS_URL")
        var tagsUrl: String = "",

        @Column(name = "BLOBS_URL")
        var blobsUrl: String = "",

        @Column(name = "GIT_TAGS_URL")
        var gitTagsUrl: String = "",

        @Column(name = "GIT_REFS_URL")
        var gitRefsUrl: String = "",

        @Column(name = "TREES_URL")
        var treesUrl: String = "",

        @Column(name = "STATUSES_URL")
        var statusesUrl: String = "",

        @Column(name = "LANGUAGES_URL")
        var languagesUrl: String = "",

        @Column(name = "STARGAZERS_URL")
        var stargazersUrl: String = "",

        @Column(name = "CONTRIBUTORS_URL")
        var contributorsUrl: String = "",

        @Column(name = "SUBSCRIBERS_URL")
        var subscribersUrl: String = "",

        @Column(name = "SUBSCRIPTION_URL")
        var subscriptionUrl: String = "",

        @Column(name = "COMMITS_URL")
        var commitsUrl: String = "",

        @Column(name = "GIT_COMMITS_URL")
        var gitCommitsUrl: String = "",

        @Column(name = "COMMENTS_URL")
        var commentsUrl: String = "",

        @Column(name = "ISSUE_COMMENT_URL")
        var issueCommentUrl: String = "",

        @Column(name = "CONTENTS_URL")
        var contentsUrl: String = "",

        @Column(name = "COMPARE_URL")
        var compareUrl: String = "",

        @Column(name = "MERGES_URL")
        var mergesUrl: String = "",

        @Column(name = "ARCHIVE_URL")
        var archiveUrl: String = "",

        @Column(name = "DOWNLOADS_URL")
        var downloadsUrl: String = "",

        @Column(name = "ISSUES_URL")
        var issuesUrl: String = "",

        @Column(name = "PULLS_URL")
        var pullsUrl: String = "",

        @Column(name = "MILESTONES_URL")
        var milestonesUrl: String = "",

        @Column(name = "NOTIFICATIONS_URL")
        var notificationsUrl: String = "",

        @Column(name = "LABELS_URL")
        var labelsUrl: String = "",

        @Column(name = "RELEASES_URL")
        var releasesUrl: String = "",

        @Column(name = "DEPLOYMENTS_URL")
        var deploymentsUrl: String = "",

        @Column(name = "CREATED_AT")
        var createdAt: Instant? = null,

        @Column(name = "UPDATED_AT")
        var updatedAt: Instant? = null,

        @Column(name = "PUSHED_AT")
        var pushedAt: Instant? = null,

        @Column(name = "GIT_URL")
        var gitUrl: String? = null,

        @Column(name = "SSH_URL")
        var sshUrl: String? = null,

        @Column(name = "CLONE_URL")
        var cloneUrl: String? = null,

        @Column(name = "SVN_URL")
        var svnUrl: String? = null,

        @Column(name = "HOMEPAGE")
        var homepage: String? = null,

        @Column(name = "SIZE")
        var size: Int = 0,

        @Column(name = "STARGAZERS_COUNT")
        var stargazersCount: Int = 0,

        @Column(name = "WATCHERS_COUNT")
        var watchersCount: Int = 0,

        @Column(name = "LANGUAGE")
        var language: String = "",

        @Column(name = "HAS_ISSUES")
        var hasIssues: Boolean = false,

        @Column(name = "HAS_PROJECTS")
        var hasProjects: Boolean = false,

        @Column(name = "HAS_DOWNLOADS")
        var hasDownloads: Boolean = false,

        @Column(name = "HAS_WIKI")
        var hasWiki: Boolean = false,

        @Column(name = "HAS_PAGES")
        var hasPages: Boolean = false,

        @Column(name = "FORKS_COUNT")
        var forksCount: Int = 0,

        @Column(name = "MIRROR_URL")
        var mirrorUrl: String? = null,

        @Column(name = "ARCHIVED")
        var archived: Boolean = false,

        @Column(name = "DISABLED")
        var disabled: Boolean = false,

        @Column(name = "OPEN_ISSUES_COUNT")
        var openIssuesCount: Int = 0,

        @OneToOne(mappedBy = "repository", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var license: LicenseModel? = null,

        @Column(name = "FORKS")
        var forks: Int = 0,

        @Column(name = "OPEN_ISSUES")
        var openIssues: Int = 0,

        @Column(name = "WATCHERS")
        var watchers: Int = 0,

        @Column(name = "DEFAULT_BRANCH")
        var defaultBranch: String = "",

        @OneToOne(mappedBy = "repository", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var payload: PayloadModel? = null
) : Serializable