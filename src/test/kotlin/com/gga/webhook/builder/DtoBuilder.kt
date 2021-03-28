package com.gga.webhook.builder

import com.gga.webhook.models.dTO.*
import java.time.Instant

class DtoBuilder {

    companion object {
        private val INSTANT_MOCK: Instant = Instant.parse("2021-03-09T05:52:33.682985Z")
    }

    fun userDto(): UserDto = UserDto(
        "mock@mock.com",
        10L,
        "mockmock4123mock",
        "mock",
        "mock4321mock",
        "https://mock.com",
        "https://mock.com",
        "https://mock.com",
        "https://mock.com",
        "https://mock.com/guist",
        "https://mock.com",
        "https://mock.com",
        "https://mock.com",
        "https://mock.com",
        "https://mock.com",
        "https://mock.com",
        "https://mock.com/events"
    )

    fun labelsDto(): LabelsDto = LabelsDto(
        id = 1L,
        nodeId = "mock431mock",
        url = "https://mock.com",
        name = "bug",
        color = "#C3C3C3",
        default = false,
        description = "mock"
    )

    fun labels(): List<LabelsDto> = listOf(this.labelsDto())

    fun creatorDto(): CreatorDto = CreatorDto(
        login = "mock@mock.com",
        id = 10L,
        nodeId = "mockmock4123mock",
        avatarUrl = "mock",
        gravatarId = "mock4321mock",
        url = "https://mock.com",
        htmlUrl = "https://mock.com",
        followersUrl = "https://mock.com",
        followingUrl = "https://mock.com",
        gistsUrl = "https://mock.com/guist",
        starredUrl = "https://mock.com",
        subscriptionsUrl = "https://mock.com",
        organizationsUrl = "https://mock.com",
        reposUrl = "https://mock.com",
        eventsUrl = "https://mock.com",
        receivedEventsUrl = "https://mock.com",
        type = "https://mock.com/events"
    )

    fun milestoneDto(): MilestoneDto = MilestoneDto(
        url = "https://mock.com",
        htmlUrl = "https://mock.com",
        labelsUrl = "https://mock.com",
        id = 10L,
        nodeId = "mock241mock",
        number = 1,
        title = "mock",
        description = "mock",
        creator = this.creatorDto(),
        openIssues = 10,
        closedIssues = 5,
        state = "open",
        createdAt = INSTANT_MOCK,
        updatedAt = INSTANT_MOCK,
        dueOn = null,
        closedAt = null
    )

    fun assigneeDto(): AssigneeDto = AssigneeDto(
        login = "mock@mock.com",
        id = 1L,
        nodeId = "mockmock4123mock",
        avatarUrl = "mock",
        gravatarId = "mock4321mock",
        url = "https://mock.com",
        htmlUrl = "https://mock.com",
        followersUrl = "https://mock.com",
        followingUrl = "https://mock.com",
        gistsUrl = "https://mock.com/guist",
        starredUrl = "https://mock.com",
        subscriptionsUrl = "https://mock.com",
        organizationsUrl = "https://mock.com",
        reposUrl = "https://mock.com",
        eventsUrl = "https://mock.com",
        receivedEventsUrl = "https://mock.com",
        type = "https://mock.com/events"
    )

    fun assigneesDto(): AssigneesDto = AssigneesDto(
        login = "mock@mock.com",
        nodeId = "mockmock4123mock",
        avatarUrl = "mock",
        gravatarId = "mock4321mock",
        url = "https://mock.com",
        htmlUrl = "https://mock.com",
        followersUrl = "https://mock.com",
        followingUrl = "https://mock.com",
        gistsUrl = "https://mock.com/guist",
        starredUrl = "https://mock.com",
        subscriptionsUrl = "https://mock.com",
        organizationsUrl = "https://mock.com",
        reposUrl = "https://mock.com",
        eventsUrl = "https://mock.com",
        receivedEventsUrl = "https://mock.com",
        type = "https://mock.com/events"
    )

    fun assignees(): List<AssigneesDto> = listOf(this.assigneesDto())

    fun ownerDto(): OwnerDto = OwnerDto(
        login = "mock@mock.com",
        id = 10L,
        nodeId = "mockmock4123mock",
        avatarUrl = "mock",
        gravatarId = "mock4321mock",
        url = "https://mock.com",
        htmlUrl = "https://mock.com",
        followersUrl = "https://mock.com",
        followingUrl = "https://mock.com",
        gistsUrl = "https://mock.com/guist",
        starredUrl = "https://mock.com",
        subscriptionsUrl = "https://mock.com",
        organizationsUrl = "https://mock.com",
        reposUrl = "https://mock.com",
        eventsUrl = "https://mock.com",
        receivedEventsUrl = "https://mock.com",
        type = "mock"
    )

    fun licenseDto(): LicenseDto = LicenseDto(
        key = "mock",
        name = "Mock 2.0",
        spdxId = "mockmock",
        url = "https://mock.com",
        nodeId = "mock423mock"
    )

    fun repositoryDto(): RepositoryDto = RepositoryDto(
        id = 10L,
        nodeId = "mock464mock",
        name = "mock",
        fullName = "mock",
        private = true,
        owner = this.ownerDto(),
        htmlUrl = "https://mock.com",
        description = "mock",
        fork = false,
        url = "https://mock.com",
        forksUrl = "https://mock.com",
        keysUrl = "https://mock.com",
        collaboratorsUrl = "https://mock.com",
        teamsUrl = "https://mock.com",
        hooksUrl = "https://mock.com",
        issueEventsUrl = "https://mock.com",
        eventsUrl = "https://mock.com",
        assigneesUrl = "https://mock.com",
        branchesUrl = "https://mock.com",
        tagsUrl = "https://mock.com",
        blobsUrl = "https://mock.com",
        gitTagsUrl = "https://mock.com",
        gitRefsUrl = "https://mock.com",
        treesUrl = "https://mock.com",
        statusesUrl = "https://mock.com",
        languagesUrl = "https://mock.com",
        stargazersUrl = "https://mock.com",
        contributorsUrl = "https://mock.com",
        subscribersUrl = "https://mock.com",
        subscriptionUrl = "https://mock.com",
        commitsUrl = "https://mock.com",
        gitCommitsUrl = "https://mock.com",
        commentsUrl = "https://mock.com",
        issueCommentUrl = "https://mock.com",
        contentsUrl = "https://mock.com",
        compareUrl = "https://mock.com",
        mergesUrl = "https://mock.com",
        archiveUrl = "https://mock.com",
        downloadsUrl = "https://mock.com",
        issuesUrl = "https://mock.com",
        pullsUrl = "https://mock.com",
        milestonesUrl = "https://mock.com",
        notificationsUrl = "https://mock.com",
        labelsUrl = "https://mock.com",
        releasesUrl = "https://mock.com",
        deploymentsUrl = "https://mock.com",
        createdAt = INSTANT_MOCK,
        updatedAt = INSTANT_MOCK,
        pushedAt = INSTANT_MOCK,
        gitUrl = "https://mock.com",
        sshUrl = "https://mock.com",
        cloneUrl = "https://mock.com",
        svnUrl = "https://mock.com",
        homepage = "https://mock.com",
        size = 5,
        stargazersCount = 1,
        watchersCount = 0,
        language = "Koltin",
        forksCount = 1,
        mirrorUrl = "https://mock.com",
        openIssuesCount = 10,
        license = this.licenseDto(),
        forks = 0,
        openIssues = 10,
        watchers = 0,
        defaultBranch = "mock",
    )

    fun senderDto(): SenderDto = SenderDto(
        login = "mock@mock.com",
        id = 10L,
        nodeId = "mockmock4123mock",
        avatarUrl = "mock",
        gravatarId = "mock4321mock",
        url = "https://mock.com",
        htmlUrl = "https://mock.com",
        followersUrl = "https://mock.com",
        followingUrl = "https://mock.com",
        gistsUrl = "https://mock.com/guist",
        starredUrl = "https://mock.com",
        subscriptionsUrl = "https://mock.com",
        organizationsUrl = "https://mock.com",
        reposUrl = "https://mock.com",
        eventsUrl = "https://mock.com",
        receivedEventsUrl = "https://mock.com",
        type = "mock"
    )

    fun payloadDto(): PayloadDto = PayloadDto(
        action = "some action",
        issue = this.issueDto(),
        repository = this.repositoryDto(),
        sender = this.senderDto()
    )

    fun eventDto(): EventDto = EventDto(action = "some action")

    fun issueDto(): IssueDto = IssueDto(
        url = "https://mock.com",
        repositoryUrl = "https://mock.com",
        labelsUrl = "https://mock.com",
        commentsUrl = "https://mock.com",
        eventsUrl = "https://mock.com",
        htmlUrl = "https://mock.com",
        id = 1L,
        nodeId = "mock756mock",
        number = 5,
        title = "mock",
        user = this.userDto(),
        labels = this.labels(),
        state = "open",
        locked = true,
        assignee = this.assigneeDto(),
        assignees = this.assignees(),
        milestone = this.milestoneDto(),
        comments = 9,
        createdAt = INSTANT_MOCK,
        updatedAt = null,
        closedAt = null
    )

    fun repositoryWithoutLicense(): RepositoryDto = this.payloadDto().run {
        this.repository!!.apply { this.license = null }
    }

}