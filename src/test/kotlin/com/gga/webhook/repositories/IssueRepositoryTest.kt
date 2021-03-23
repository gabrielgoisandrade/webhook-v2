package com.gga.webhook.repositories

import com.gga.webhook.factories.BaseRepositoryTestFactory
import com.gga.webhook.models.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class IssueRepositoryTest : BaseRepositoryTestFactory() {

    @Autowired
    private lateinit var repository: IssueRepository

    private val userModel: UserModel = this.model.user

    private val assigneeModel: AssigneeModel = this.model.assignee

    private val creatorModel: CreatorModel = this.model.creator

    private val milestoneModel: MilestoneModel = this.model.milestone.apply { this.creator = null }

    private val issueModel: IssueModel = this.model.issue.apply {
        this.assignee = null
        this.user = null
        this.milestone = null
    }

    @Test
    @DisplayName("Must save Issue")
    fun saveIssue() {
        val expectedUser: UserModel = this.entityManager.merge(this.userModel)

        val expectedAssignee: AssigneeModel = this.entityManager.merge(this.assigneeModel)

        val expectedCreator: CreatorModel = this.entityManager.merge(this.creatorModel)

        val expectedMilestone: MilestoneModel = this.entityManager.merge(this.milestoneModel.apply {
            this.creator = expectedCreator
        })

        val expectedIssue: IssueModel = this.entityManager.merge(this.issueModel.apply {
            this.assignee = expectedAssignee
            this.user = expectedUser
            this.milestone = expectedMilestone
        })

        this.repository.findById(expectedIssue.id).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedIssue)
            assertThat(it.get().assignee).isEqualTo(expectedAssignee)
            assertThat(it.get().user).isEqualTo(expectedUser)
            assertThat(it.get().milestone).isEqualTo(expectedMilestone)
        }

    }

    @Test
    @DisplayName("Must return the Issue by number")
    fun findByNumber() {
        val expectedUser: UserModel = this.entityManager.merge(this.userModel)

        val expectedAssignee: AssigneeModel = this.entityManager.merge(this.assigneeModel)

        val expectedCreator: CreatorModel = this.entityManager.merge(this.creatorModel)

        val expectedMilestone: MilestoneModel = this.entityManager.merge(this.milestoneModel.apply {
            this.creator = expectedCreator
        })

        val expectedIssue: IssueModel = this.entityManager.merge(this.issueModel.apply {
            this.assignee = expectedAssignee
            this.user = expectedUser
            this.milestone = expectedMilestone
        })

        this.repository.findByNumber(expectedIssue.number).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedIssue)
            assertThat(it.get().assignee).isEqualTo(expectedAssignee)
            assertThat(it.get().user).isEqualTo(expectedUser)
            assertThat(it.get().milestone).isEqualTo(expectedMilestone)
        }
    }

}