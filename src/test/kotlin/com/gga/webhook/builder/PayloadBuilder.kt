package com.gga.webhook.builder

import com.gga.webhook.models.dto.*
import java.time.Instant
import java.util.*

class PayloadBuilder {

    companion object {
        @JvmStatic
        private val INSTANT_MOCK: Instant = Instant.ofEpochMilli(Date().time)
    }

    private val userDto = UserDto()

    private val labels: HashSet<LabelDto> = hashSetOf(LabelDto())

    private val creatorDto = CreatorDto()

    private val milestoneDto = MilestoneDto(createdAt = INSTANT_MOCK, creator = creatorDto)

    private val assigneeDto = AssigneeDto()

    private val assignees: HashSet<AssigneesDto> = hashSetOf(AssigneesDto())

    private val ownerDto = OwnerDto()

    private val licenseDto = LicenseDto()

    private val repositoryDto = RepositoryDto(createdAt = INSTANT_MOCK, owner = ownerDto, license = licenseDto)

    private val senderDto = SenderDto()

    fun payloadBuilder(): PayloadDto = PayloadDto(
        action = "some action",
        issue = this.issue(),
        repository = this.repositoryDto,
        sender = this.senderDto
    )

    private fun issue(): IssueDto = IssueDto(
        createdAt = INSTANT_MOCK,
        user = this.userDto,
        labels = this.labels,
        milestone = this.milestoneDto,
        assignee = this.assigneeDto,
        assignees = this.assignees
    )

    fun issueWithoutLabels(): PayloadDto = this.payloadBuilder().run {
        this.issue.apply { this.labels = hashSetOf() }
        this
    }

    fun issueWithoutMilestone(): PayloadDto = this.payloadBuilder().run {
        this.issue.apply { this.milestone = null }
        this
    }

    fun issueWithoutAssignee(): PayloadDto = this.payloadBuilder().run {
        this.issue.apply { this.assignee = null }
        this
    }

    fun issueWithoutAssignees(): PayloadDto = this.payloadBuilder().run {
        this.issue.apply { this.assignees = hashSetOf() }
        this
    }

    fun repositoryWithoutLicense(): PayloadDto = this.payloadBuilder().run {
        this.repository.apply { this.license = null }
        this
    }

}