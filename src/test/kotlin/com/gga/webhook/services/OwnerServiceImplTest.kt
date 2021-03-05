package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.errors.exceptions.OwnerNotFoundException
import com.gga.webhook.models.OwnerModel
import com.gga.webhook.models.vO.OwnerVo
import com.gga.webhook.repositories.OwnerRepository
import com.gga.webhook.services.impls.OwnerServiceImpl
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
internal class OwnerServiceImplTest {

    @MockBean
    private lateinit var ownerRepository: OwnerRepository

    private val ownerServiceImpl: OwnerServiceImpl by lazy { OwnerServiceImpl(this.ownerRepository) }

    private val expectedOwner: OwnerVo = PayloadBuilder().ownerDto() convertTo OwnerVo::class.java

    @Test
    @DisplayName("Deve retornar o owner de determinado payload")
    fun getOwner() {

        `when`(this.ownerRepository.getOwner()).thenReturn(this.expectedOwner convertTo OwnerModel::class.java)

        val owner: OwnerVo = this.ownerServiceImpl.getOwner()

        assertEquals(expectedOwner, owner)
    }

    @Test
    @DisplayName("Deve retornar um erro ao não retornar nenhum owner de determinado payload")
    fun throwErrorByNoOwnerFound() {
        `when`(this.ownerRepository.getOwner()).thenThrow(OwnerNotFoundException("No owner found."))

        assertThrows<OwnerNotFoundException> { this.ownerServiceImpl.getOwner() }.also {
            assertThat(it).isInstanceOf(OwnerNotFoundException::class.java).hasMessage("No owner found.")
        }
    }

}