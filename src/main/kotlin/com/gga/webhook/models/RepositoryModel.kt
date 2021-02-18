package com.gga.webhook.models

import org.springframework.data.jpa.repository.Temporal
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "REPOSITORY")
data class RepositoryModel @JvmOverloads constructor(
    @Id
    @Column(name = "REPOSITORY_ID")
    val id: Long = 0,

    @Column(name = "NODE_ID", unique = true)
    val nodeId: String,

    @Column(name = "NAME")
    val name: String,

    @Column(name = "FULL_NAME")
    val fullName: String,

    @Column(name = "PRIVATE")
    val private: Boolean = false,

    @OneToOne(mappedBy = "repository")
    val owner: OwnerModel? = null,

    @Column(name = "HTML_URL")
    val htmlUrl: String,

    @Column(name = "DESCRIPTION")
    val description: String,

    @Column(name = "FORK")
    val fork: Boolean = false,

    @Column(name = "URL")
    val url: String,

    @Column(name = "FORKS_URL")
    val forksUrl: String,

    @Column(name = "KEYS_URL")
    val keysUrl: String,

    @Column(name = "COLLABORATORS_URL")
    val collaboratorsUrl: String,

    @Column(name = "TEAMS_URL")
    val teamsUrl: String,

    @Column(name = "HOOKS_URL")
    val hooksUrl: String,

    @Column(name = "ISSUE_EVENTS_URL")
    val issueEventsUrl: String,

    @Column(name = "EVENTS_URL")
    val eventsUrl: String,

    @Column(name = "ASSIGNEES_URL")
    val assigneesUrl: String,

    @Column(name = "BRANCHES_URL")
    val branchesUrl: String,

    @Column(name = "TAGS_URL")
    val tagsUrl: String,

    @Column(name = "BLOBS_URL")
    val blobsUrl: String,

    @Column(name = "GIT_TAGS_URL")
    val gitTagsUrl: String,

    @Column(name = "GIT_REFS_URL")
    val gitRefsUrl: String,

    @Column(name = "TREES_URL")
    val treesUrl: String,

    @Column(name = "STATUSES_URL")
    val statusesUrl: String,

    @Column(name = "LANGUAGES_URL")
    val languagesUrl: String,

    @Column(name = "STARGAZERS_URL")
    val stargazersUrl: String,

    @Column(name = "CONTRIBUTORS_URL")
    val contributorsUrl: String,

    @Column(name = "SUBSCRIBERS_URL")
    val subscribersUrl: String,

    @Column(name = "SUBSCRIPTION_URL")
    val subscriptionUrl: String,

    @Column(name = "COMMITS_URL")
    val commitsUrl: String,

    @Column(name = "GIT_COMMITS_URL")
    val gitCommitsUrl: String,

    @Column(name = "COMMENTS_URL")
    val commentsUrl: String,

    @Column(name = "ISSUE_COMMENT_URL")
    val issueCommentUrl: String,

    @Column(name = "CONTENTS_URL")
    val contentsUrl: String,

    @Column(name = "COMPARE_URL")
    val compareUrl: String,

    @Column(name = "MERGES_URL")
    val mergesUrl: String,

    @Column(name = "ARCHIVE_URL")
    val archiveUrl: String,

    @Column(name = "DOWNLOADS_URL")
    val downloadsUrl: String,

    @Column(name = "ISSUES_URL")
    val issuesUrl: String,

    @Column(name = "PULLS_URL")
    val pullsUrl: String,

    @Column(name = "MILESTONES_URL")
    val milestonesUrl: String,

    @Column(name = "NOTIFICATIONS_URL")
    val notificationsUrl: String,

    @Column(name = "LABELS_URL")
    val labelsUrl: String,

    @Column(name = "RELEASES_URL")
    val releasesUrl: String,

    @Column(name = "DEPLOYMENTS_URL")
    val deploymentsUrl: String,

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.DATE)
    val createdAt: Date,

    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.DATE)
    val updatedAt: Date? = null,

    @Column(name = "PUSHED_AT")
    @Temporal(TemporalType.DATE)
    val pushedAt: Date? = null,

    @Column(name = "GIT_URL")
    val gitUrl: String? = null,

    @Column(name = "SSH_URL")
    val sshUrl: String? = null,

    @Column(name = "CLONE_URL")
    val cloneUrl: String? = null,

    @Column(name = "SVN_URL")
    val svnUrl: String? = null,

    @Column(name = "HOMEPAGE")
    val homepage: String? = null,

    @Column(name = "SIZE")
    val size: Int = 0,

    @Column(name = "STARGAZERS_COUNT")
    val stargazersCount: Int = 0,

    @Column(name = "WATCHERS_COUNT")
    val watchersCount: Int = 0,

    @Column(name = "LANGUAGE")
    val language: String,

    @Column(name = "HAS_ISSUES")
    val hasIssues: Boolean = false,

    @Column(name = "HAS_PROJECTS")
    val hasProjects: Boolean = false,

    @Column(name = "HAS_DOWNLOADS")
    val hasDownloads: Boolean = false,

    @Column(name = "HAS_WIKI")
    val hasWiki: Boolean = false,

    @Column(name = "HAS_PAGES")
    val hasPages: Boolean = false,

    @Column(name = "FORKS_COUNT")
    val forksCount: Int = 0,

    @Column(name = "MIRROR_URL")
    val mirrorUrl: String? = null,

    @Column(name = "ARCHIVED")
    val archived: Boolean = false,

    @Column(name = "DISABLED")
    val disabled: Boolean = false,

    @Column(name = "OPEN_ISSUES_COUNT")
    val openIssuesCount: Int = 0,

    @OneToOne(mappedBy = "repository")
    val license: LicenseModel? = null,

    @Column(name = "FORKS")
    val forks: Int = 0,

    @Column(name = "OPEN_ISSUES")
    val openIssues: Int = 0,

    @Column(name = "WATCHERS")
    val watchers: Int = 0,

    @Column(name = "DEFAULT_BRANCH")
    val defaultBranch: String,

    @OneToOne(mappedBy = "repository")
    val payload: PayloadModel? = null
) : Serializable