package com.gga.webhook.repositories

import com.gga.webhook.factories.BaseRepositoryTestFactory
import com.gga.webhook.models.CreatorModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class CreatorRepositoryTest : BaseRepositoryTestFactory() {

    @Autowired
    private lateinit var repository: CreatorRepository

    private val creatorModel: CreatorModel = this.model.creator

    @Test
    @DisplayName("Must save Creator")
    fun saveCreator() {
        val expectedCreator: CreatorModel = this.entityManager.merge(this.creatorModel)

        this.repository.findById(expectedCreator.id).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedCreator)
        }
    }

    @Test
    @DisplayName("Must return the Creator by login")
    fun findByLogin() {
        val expectedCreator: CreatorModel = this.entityManager.merge(this.creatorModel)

        this.repository.findByLogin(expectedCreator.login).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedCreator)
        }
    }

}