package com.gga.webhook.utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import kotlin.random.Random

class RequestUtil {

    val randomId: Long = Random.nextLong()


    companion object {

        const val EVENT: String = "/event"

        const val ISSUE: String = "/issue"

        const val SENDER: String = "/sender"

        const val USER: String = "/user"

        const val ASSIGNEE: String = "/assignee"

        const val ASSIGNEES: String = "/assignees"

        const val LABELS: String = "/labels"

        const val LICENSE: String = "/license"

        const val OWNER: String = "/owner"

        const val REPOSITORY: String = "/repository"

        const val MILESTONE: String = "/milestone"

        const val CREATOR: String = "/creator"

        @JvmStatic
        fun getRequest(uri: String): MockHttpServletRequestBuilder = get(uri)
            .accept(APPLICATION_JSON_VALUE)

    }
}