package com.gga.webhook.repositories

import com.gga.webhook.factories.BaseRepositoryTestFactory
import com.gga.webhook.models.AssigneeModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class AssigneeRepositoryTest : BaseRepositoryTestFactory() {

    @Autowired
    private lateinit var repository: AssigneeRepository

    private val assigneeModel: AssigneeModel = this.model.assignee

    @Test
    fun saveAssignee() {
        val expectedAssignee: AssigneeModel = this.entityManager.merge(this.assigneeModel)

        this.repository.findById(expectedAssignee.id).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedAssignee)
        }
    }

    @Test
    fun findByLogin() {
        val expectedAssignee: AssigneeModel = this.entityManager.merge(this.assigneeModel)

        this.repository.findByLogin(expectedAssignee.login).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedAssignee)
        }
    }

}