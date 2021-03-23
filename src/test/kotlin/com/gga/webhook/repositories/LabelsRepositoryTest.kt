package com.gga.webhook.repositories

import com.gga.webhook.factories.BaseRepositoryTestFactory
import com.gga.webhook.models.LabelsModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class LabelsRepositoryTest : BaseRepositoryTestFactory() {

    @Autowired
    private lateinit var repository: LabelsRepository

    private val labelsModel: List<LabelsModel> = listOf(this.model.labels)

    @Test
    fun saveLabels() {
        val expectedLabels: MutableList<LabelsModel> = mutableListOf()

        this.labelsModel.forEach {
            this.entityManager.merge(it).also { labels: LabelsModel ->
                expectedLabels.add(labels)
            }
        }

        this.repository.findAll().also { assertThat(it).isEqualTo(expectedLabels) }
    }

    @Test
    fun findByName() {
        val expectedLabels: MutableList<LabelsModel> = mutableListOf()

        this.labelsModel.forEach {
            this.entityManager.merge(it).also { labels: LabelsModel ->
                expectedLabels.add(labels)
            }
        }

        val foundLabels: MutableList<LabelsModel> = mutableListOf()

        expectedLabels.forEach { model: LabelsModel ->
            this.repository.findByName(model.name).get().also { foundLabels.add(it) }
        }

        assertThat(foundLabels).isEqualTo(expectedLabels)
    }

}