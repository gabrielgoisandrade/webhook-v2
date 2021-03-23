package com.gga.webhook.services.impls

import com.gga.webhook.constants.MockValuesConstant.ISSUE_NUMBER
import com.gga.webhook.errors.exceptions.IssueNotFoundException
import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.UserModel
import com.gga.webhook.models.dTO.UserDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import java.util.*


internal class UserServiceImplTest : BaseServiceImplTestFactory() {

    private val service: UserServiceImpl by lazy { UserServiceImpl(this.userRepository) }

    private val expectedModel: UserModel = this.model.user

    private val expectedDto: UserDto = this.dto.userDto()

    @Test
    fun saveUser() {
        `when`(this.userRepository.findByLogin(anyString())).thenReturn(Optional.empty())

        `when`(this.userRepository.save(any(UserModel::class.java))).thenReturn(this.expectedModel)

        this.service.saveUser(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }
    }

    @Test
    fun getExistingUser() {
        `when`(this.userRepository.findByLogin(anyString())).thenReturn(Optional.of(this.expectedModel))

        this.service.saveUser(this.expectedModel).also { assertThat(it).isEqualTo(this.expectedModel) }

        verify(this.userRepository, never()).save(any(UserModel::class.java))
    }

    @Test
    fun findUserByIssueNumber() {
        `when`(this.userRepository.findByIssueNumber(anyInt())).thenReturn(Optional.of(this.expectedModel))

        this.service.findUserByIssueNumber(ISSUE_NUMBER).also {
            assertThat(it).isEqualTo(this.expectedDto)
        }
    }

    @Test
    fun throwErrorByIssueNumberNotFound() {
        `when`(this.userRepository.findByIssueNumber(anyInt()))
            .thenThrow(RelationNotFoundException("There isn't any User related with this Issue"))

        assertThrows<RelationNotFoundException> { this.service.findUserByIssueNumber(ISSUE_NUMBER) }.also {
            assertThat(it).isInstanceOf(RelationNotFoundException::class.java)
                .hasMessage("There isn't any User related with this Issue")
        }
    }

}
