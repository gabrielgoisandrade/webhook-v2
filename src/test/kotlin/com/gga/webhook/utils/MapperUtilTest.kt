package com.gga.webhook.utils

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.models.LabelModel
import com.gga.webhook.models.SenderModel
import com.gga.webhook.models.dto.LabelDto
import com.gga.webhook.models.dto.SenderDto
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MapperUtilTest {

    private val builder = PayloadBuilder()

    @Test
    fun fromModelToDto() {
        val senderDto: SenderDto = this.builder.payload().sender

        val senderModel = SenderModel(
            login = "mock@mock.com",
            id = 10L,
            nodeId = "mockmock4123mock",
            avatarUrl = "mock",
            gravatarId = "mock4321mock",
            url = "https://mock.com",
            htmlUrl = "https://mock.com",
            followersUrl = "https://mock.com",
            followingUrl = "https://mock.com",
            gistsUrl = "https://mock.com/guist",
            starredUrl = "https://mock.com",
            subscriptionsUrl = "https://mock.com",
            organizationsUrl = "https://mock.com",
            reposUrl = "https://mock.com",
            eventsUrl = "https://mock.com",
            receivedEventsUrl = "https://mock.com",
            type = "mock"
        )

        val parseObjet: SenderDto = senderModel convertTo SenderDto::class.java

        assertEquals(senderDto, parseObjet)
    }

    @Test
    fun fromListDtoToListModel() {
        val labelsDto: Set<LabelDto> = this.builder.payload().issue.labels

        val labelsModel: Set<LabelModel> = setOf(
            LabelModel(
                id = 2L,
                nodeId = "mock431mock",
                url = "https://mock.com",
                name = "bug",
                color = "#C3C3C3",
                default = false,
                description = "mock"
            ),
            LabelModel(
                id = 2L,
                nodeId = "mock431mock",
                url = "https://mock.com",
                name = "bug",
                color = "#C3C3C3",
                default = false,
                description = "mock"
            )
        )

        val parseObjects: Set<LabelDto> = labelsModel convertTo LabelDto::class.java

        assertEquals(labelsDto, parseObjects)
    }

    @Test
    fun fromDtoToModel() {
        val senderDto: SenderDto = this.builder.payload().sender

        val senderModel = SenderModel(
            login = "mock@mock.com",
            id = 10L,
            nodeId = "mockmock4123mock",
            avatarUrl = "mock",
            gravatarId = "mock4321mock",
            url = "https://mock.com",
            htmlUrl = "https://mock.com",
            followersUrl = "https://mock.com",
            followingUrl = "https://mock.com",
            gistsUrl = "https://mock.com/guist",
            starredUrl = "https://mock.com",
            subscriptionsUrl = "https://mock.com",
            organizationsUrl = "https://mock.com",
            reposUrl = "https://mock.com",
            eventsUrl = "https://mock.com",
            receivedEventsUrl = "https://mock.com",
            type = "mock"
        )

        val parseObjet: SenderModel = senderDto convertTo SenderModel::class.java

        assertEquals(senderModel, parseObjet)
    }

    @Test
    fun fromListModelToListDto() {
        val labelsDto: Set<LabelDto> = this.builder.payload().issue.labels

        val labelsModel: Set<LabelModel> = setOf(
            LabelModel(
                id = 2L,
                nodeId = "mock431mock",
                url = "https://mock.com",
                name = "bug",
                color = "#C3C3C3",
                default = false,
                description = "mock"
            ),
            LabelModel(
                id = 2L,
                nodeId = "mock431mock",
                url = "https://mock.com",
                name = "bug",
                color = "#C3C3C3",
                default = false,
                description = "mock"
            )
        )

        val parseObjects: Set<LabelModel> = labelsDto convertTo LabelModel::class.java

        assertEquals(labelsModel, parseObjects)
    }

}