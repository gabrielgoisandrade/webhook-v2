package com.gga.webhook.repositories

import com.gga.webhook.factories.BaseRepositoryTestFactory
import com.gga.webhook.models.AssigneesModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class AssigneesRepositoryTest : BaseRepositoryTestFactory() {

    @Autowired
    private lateinit var repository: AssigneesRepository

    private val assigneesModel: List<AssigneesModel> = listOf(this.model.assignees)

    @Test
    fun saveAssignees() {
        val expectedAssignees: MutableList<AssigneesModel> = mutableListOf()

        this.assigneesModel.forEach {
            this.entityManager.merge(it).also { savedAssignee: AssigneesModel ->
                expectedAssignees.add(savedAssignee)
            }
        }

        this.repository.findAll().also { assertThat(it).isEqualTo(expectedAssignees) }
    }

    @Test
    fun findByLogin() {
        val expectedAssignees: MutableList<AssigneesModel> = mutableListOf()

        this.assigneesModel.forEach {
            this.entityManager.merge(it).also { savedAssignee: AssigneesModel ->
                expectedAssignees.add(savedAssignee)
            }
        }

        val foundAssignees: MutableList<AssigneesModel> = mutableListOf()

        expectedAssignees.forEach { model: AssigneesModel ->
            this.repository.findByLogin(model.login).get().also { foundAssignees.add(it) }
        }

        assertThat(foundAssignees).isEqualTo(expectedAssignees)
    }

}