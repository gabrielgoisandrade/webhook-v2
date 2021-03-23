package com.gga.webhook.repositories

import com.gga.webhook.factories.BaseRepositoryTestFactory
import com.gga.webhook.models.EventModel
import com.gga.webhook.models.OwnerModel
import com.gga.webhook.models.RepositoryModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class RepositoryRepositoryTest : BaseRepositoryTestFactory() {

    @Autowired
    private lateinit var repository: RepositoryRepository

    private val eventModel: EventModel = this.model.event

    private val ownerModel: OwnerModel = this.model.owner

    private val repositoryModel: RepositoryModel = this.model.repository.apply { this.license = null }

    @Test
    @DisplayName("Must save Repository")
    fun saveRepository() {
        val expectedEvent: EventModel = this.entityManager.persist(this.eventModel)

        val expectedOwner: OwnerModel = this.entityManager.merge(this.ownerModel)

        val expectedRepository: RepositoryModel =
            this.entityManager.merge(this.repositoryModel.apply {
                this.event = expectedEvent
                this.owner = expectedOwner
            })

        this.repository.findById(expectedRepository.id).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedRepository)
            assertThat(it.get().event).isEqualTo(expectedEvent)
            assertThat(it.get().owner).isEqualTo(expectedOwner)
        }
    }

    @Test
    @DisplayName("Must return the Repository by name")
    fun findByNodeId() {
        val expectedEvent: EventModel = this.entityManager.persist(this.eventModel)

        val expectedOwner: OwnerModel = this.entityManager.merge(this.ownerModel)

        val expectedRepository: RepositoryModel =
            this.entityManager.merge(this.repositoryModel.apply {
                this.event = expectedEvent
                this.owner = expectedOwner
            })

        this.repository.findByName(expectedRepository.name).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedRepository)
            assertThat(it.get().event).isEqualTo(expectedEvent)
            assertThat(it.get().owner).isEqualTo(expectedOwner)
        }
    }

}