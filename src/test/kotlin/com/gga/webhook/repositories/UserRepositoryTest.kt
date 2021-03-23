package com.gga.webhook.repositories

import com.gga.webhook.factories.BaseRepositoryTestFactory
import com.gga.webhook.models.UserModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class UserRepositoryTest : BaseRepositoryTestFactory() {

    @Autowired
    private lateinit var repository: UserRepository

    private val userModel: UserModel = this.model.user

    @Test
    @DisplayName("Must save User")
    fun saveUser() {
        val expectedUser: UserModel = this.entityManager.merge(this.userModel)

        this.repository.findById(expectedUser.id).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedUser)
        }
    }

    @Test
    @DisplayName("Must return the User by login")
    fun findByLogin() {
        val expectedUser: UserModel = this.entityManager.merge(this.userModel)

        this.repository.findByLogin(expectedUser.login).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedUser)
        }
    }

}