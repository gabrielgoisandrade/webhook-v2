package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.AssigneeModel
import com.gga.webhook.models.vO.AssigneeVo
import com.gga.webhook.repositories.AssigneeRepository
import com.gga.webhook.services.impls.AssigneeServiceImpl
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
internal class AssigneeServiceImplTest {

    @MockBean
    private lateinit var assigneeRepository: AssigneeRepository

    private val assigneeServiceImpl: AssigneeServiceImpl by lazy { AssigneeServiceImpl(this.assigneeRepository) }

    private val expectedAssignee: AssigneeVo = PayloadBuilder().assigneeDto() convertTo AssigneeVo::class.java

    @Test
    @DisplayName("Deve retornar o assignee de determinada issue")
    fun getAssignee() {
        `when`(this.assigneeRepository.getAssignee())
            .thenReturn(expectedAssignee convertTo AssigneeModel::class.java)

        val assigneeVo: AssigneeVo? = this.assigneeServiceImpl.getAssignee()

        assertEquals(expectedAssignee, assigneeVo)
    }

}