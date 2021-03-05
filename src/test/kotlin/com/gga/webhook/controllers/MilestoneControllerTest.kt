package com.gga.webhook.controllers

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.vO.MilestoneVo
import com.gga.webhook.services.impls.MilestoneServiceImpl
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import com.gga.webhook.utils.RequestUtil.Companion.MILESTONE
import com.gga.webhook.utils.RequestUtil.Companion.getRequest
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class MilestoneControllerTest {

    @MockBean
    private lateinit var milestoneServiceImpl: MilestoneServiceImpl

    @Autowired
    private lateinit var milestoneController: MilestoneController

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val expectedMilestone: MilestoneVo = PayloadBuilder().milestoneDto() convertTo MilestoneVo::class.java

    @Test
    @DisplayName("GET -> Deve retornar o milestone com as mesmas propriedades que o esperado")
    fun verifyMilestoneBody() {
        `when`(this.milestoneServiceImpl.getMilestone()).thenReturn((this.expectedMilestone))

        this.milestoneController.getMilestone().also { assertEquals(expectedMilestone, it.body!!) }
    }

    @Test
    @DisplayName("GET -> Deve retornar o milestone com o HATEOAS configurado")
    fun verifyMilestoneLink() {
        given(this.milestoneServiceImpl.getMilestone()).willReturn((this.expectedMilestone))

        this.mockMvc.perform(getRequest(MILESTONE))
            .andExpect(status().isOk)
            .andExpect(jsonPath("links").isNotEmpty)
            .andExpect(jsonPath("links[1]['rel']").value("creator"))
            .andExpect(jsonPath("links", hasSize<Int>(2)))
    }

    @Test
    @DisplayName("GET -> Deve status code 204 por não haver milestone na issue")
    fun getNullMilestone() {
        given(this.milestoneServiceImpl.getMilestone()).willReturn(null)

        this.mockMvc.perform(getRequest(MILESTONE)).andExpect(status().isNoContent)
    }

}