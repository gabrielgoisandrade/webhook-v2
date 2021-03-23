package com.gga.webhook.repositories

import com.gga.webhook.factories.BaseRepositoryTestFactory
import com.gga.webhook.models.OwnerModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class OwnerRepositoryTest : BaseRepositoryTestFactory() {

    @Autowired
    private lateinit var repository: OwnerRepository

    private val ownerModel: OwnerModel = this.model.owner

    @Test
    @DisplayName("Must save Owner")
    fun saveOwner() {
        val expectedOwner: OwnerModel = this.entityManager.merge(this.ownerModel)

        this.repository.findById(expectedOwner.id).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedOwner)
        }
    }

    @Test
    @DisplayName("Must return the Owner by login")
    fun findByLogin() {
        val expectedOwner: OwnerModel = this.entityManager.merge(this.ownerModel)

        this.repository.findByLogin(expectedOwner.login).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedOwner)
        }
    }

}