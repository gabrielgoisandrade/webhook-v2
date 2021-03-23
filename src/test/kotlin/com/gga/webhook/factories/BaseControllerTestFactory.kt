package com.gga.webhook.factories

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@AutoConfigureMockMvc
@ActiveProfiles("test")
abstract class BaseControllerTestFactory: BaseTest() {

    @Autowired
    protected lateinit var mockMvc: MockMvc

}