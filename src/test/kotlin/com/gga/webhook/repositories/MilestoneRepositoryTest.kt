package com.gga.webhook.repositories

import com.gga.webhook.factories.BaseRepositoryTestFactory
import com.gga.webhook.models.CreatorModel
import com.gga.webhook.models.MilestoneModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class MilestoneRepositoryTest : BaseRepositoryTestFactory() {

    @Autowired
    private lateinit var repository: MilestoneRepository

    private val creatorModel: CreatorModel = this.model.creator

    private val milestoneModel: MilestoneModel = this.model.milestone.apply { this.creator = null }

    @Test
    @DisplayName("Must save Milestone")
    fun saveMilestone() {
        val expectedCreator: CreatorModel = this.entityManager.merge(this.creatorModel)

        val expectedMilestone: MilestoneModel =
            this.entityManager.merge(this.milestoneModel.apply {
                this.creator = expectedCreator
            })

        this.repository.findById(expectedMilestone.id).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedMilestone)
            assertThat(it.get().creator).isEqualTo(expectedCreator)
        }
    }

    @Test
    @DisplayName("Must return the Milestone by nodeId")
    fun findByNodeId() {
        val expectedCreator: CreatorModel = this.entityManager.merge(this.creatorModel)

        val expectedMilestone: MilestoneModel =
            this.entityManager.merge(this.milestoneModel.apply {
                this.creator = expectedCreator
            })

        this.repository.findByNumber(expectedMilestone.number).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedMilestone)
            assertThat(it.get().creator).isEqualTo(expectedCreator)
        }
    }

}