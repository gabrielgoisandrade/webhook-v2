package com.gga.webhook.repositories

import com.gga.webhook.factories.BaseRepositoryTestFactory
import com.gga.webhook.models.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class IssueResponsibleRepositoryTest : BaseRepositoryTestFactory() {

    @Autowired
    private lateinit var responsibleRepository: IssueResponsibleRepository

    private val userModel: UserModel = this.model.user

    private val assigneeModel: AssigneeModel = this.model.assignee

    private val creatorModel: CreatorModel = this.model.creator

    private val milestoneModel: MilestoneModel = this.model.milestone.apply { this.creator = null }

    private val issueModel: IssueModel = this.model.issue.apply {
        this.assignee = null
        this.user = null
        this.milestone = null
    }

    private val assignees: AssigneesModel = this.model.assignees

    @Test
    fun saveResponsible() {
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

        val expectedAssignees: AssigneesModel = this.entityManager.merge(this.assignees)

        this.entityManager.persist(IssueResponsibleModel(issue = expectedIssue, assignees = expectedAssignees))

        this.responsibleRepository.findAll().first().also {
            assertThat(it.issue).isEqualTo(expectedIssue)
            assertThat(it.assignees).isEqualTo(expectedAssignees)
        }
    }

}