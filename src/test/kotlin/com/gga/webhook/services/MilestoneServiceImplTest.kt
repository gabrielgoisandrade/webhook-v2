package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.MilestoneModel
import com.gga.webhook.models.vO.MilestoneVo
import com.gga.webhook.repositories.MilestoneRepository
import com.gga.webhook.services.impls.MilestoneServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
internal class MilestoneServiceImplTest {

    @MockBean
    private lateinit var milestoneRepository: MilestoneRepository

    private val milestoneServiceImpl: MilestoneServiceImpl by lazy { MilestoneServiceImpl(this.milestoneRepository) }

    private val expectedMilestone: MilestoneVo = PayloadBuilder().milestoneDto() convertTo MilestoneVo::class.java

    @Test
    @DisplayName("Deve retornar o milestone de determinada issue")
    fun getMilestone() {
        `when`(this.milestoneRepository.getMilestone())
            .thenReturn(expectedMilestone convertTo MilestoneModel::class.java)

        val milestoneVo: MilestoneVo? = this.milestoneServiceImpl.getMilestone()

        assertEquals(expectedMilestone, milestoneVo)
    }

}