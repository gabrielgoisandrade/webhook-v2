package com.gga.webhook.utils

import com.gga.webhook.models.LabelModel
import com.gga.webhook.models.SenderModel
import com.gga.webhook.models.dto.LabelDto
import com.gga.webhook.models.dto.SenderDto
import com.gga.webhook.utils.MapperUtil.Companion.parseListObjets
import com.gga.webhook.utils.MapperUtil.Companion.parseObjet
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MapperUtilTest {

    @Test
    fun fromDtoToModel() {
        val senderDto = SenderDto(
            "mock",
            10L,
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            true
        )
        val senderModel = SenderModel(
            "mock",
            10L,
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            "mock",
            true
        )

        val parseObjet: SenderDto = parseObjet(senderModel, SenderDto::class.java)

        assertEquals(senderDto, parseObjet)
    }

    @Test
    fun fromListModelToListDto() {
        val labelsDto: Set<LabelDto> = setOf(
            LabelDto(10L, "mock", "mock", "mock", "mock", false, "mock"),
            LabelDto(11L, "mock", "mock", "mock", "mock", false, "mock")
        )

        val labelsModel: Set<LabelModel> = setOf(
            LabelModel(10L, "mock", "mock", "mock", "mock", false, "mock"),
            LabelModel(11L, "mock", "mock", "mock", "mock", false, "mock")
        )

        val parseObjects: Set<LabelDto> = parseListObjets(labelsModel, LabelDto::class.java)

        assertEquals(labelsDto, parseObjects)
    }

}