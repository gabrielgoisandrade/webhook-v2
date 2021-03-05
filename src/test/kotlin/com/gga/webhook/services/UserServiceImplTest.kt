package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.UserNotFoundException
import com.gga.webhook.models.UserModel
import com.gga.webhook.models.vO.UserVo
import com.gga.webhook.repositories.UserRepository
import com.gga.webhook.services.impls.UserServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
internal class UserServiceImplTest {

    @MockBean
    private lateinit var userRepository: UserRepository

    private val userServiceImpl: UserServiceImpl by lazy { UserServiceImpl(this.userRepository) }

    private val expectedUser: UserVo = PayloadBuilder().userDto() convertTo UserVo::class.java

    @Test
    @DisplayName("Deve retornar o user de determinada issue")
    fun getUser() {

        `when`(this.userRepository.getUser()).thenReturn(this.expectedUser convertTo UserModel::class.java)

        val user: UserVo = this.userServiceImpl.getUser()

        assertEquals(expectedUser, user)
    }

    @Test
    @DisplayName("Deve retornar um erro ao não retornar nenhum user de determinado payload")
    fun throwErrorByNoUserFound() {
        `when`(this.userRepository.getUser()).thenThrow(UserNotFoundException("No user found."))

        assertThrows<UserNotFoundException> { this.userServiceImpl.getUser() }.also {
            assertThat(it).isInstanceOf(UserNotFoundException::class.java).hasMessage("No user found.")
        }
    }

}