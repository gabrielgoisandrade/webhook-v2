package com.gga.webhook.utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class RequestUtil private constructor() {
    companion object {

        const val PAYLOAD: String = "/payload"

        const val ISSUE: String = "/issue"

        const val SENDER: String = "/sender"

        @JvmStatic
        fun postRequest(uri: String, body: String): MockHttpServletRequestBuilder = post(uri)
            .contentType(APPLICATION_JSON_VALUE)
            .accept(APPLICATION_JSON_VALUE)
            .content(body)

        @JvmStatic
        fun getRequest(uri: String): MockHttpServletRequestBuilder = get(uri)
            .accept(APPLICATION_JSON_VALUE)

        @JvmStatic
        fun <T> T.toJson(): String = ObjectMapper().writeValueAsString(this)

    }
}