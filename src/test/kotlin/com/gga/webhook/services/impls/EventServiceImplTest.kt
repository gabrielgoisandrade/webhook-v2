package com.gga.webhook.services.impls

import com.gga.webhook.constants.MockValuesConstant.EVENT_ACTION
import com.gga.webhook.errors.exceptions.EventNotFoundException
import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.EventModel
import com.gga.webhook.models.dTO.EventDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import java.util.*

internal class EventServiceImplTest : BaseServiceImplTestFactory() {

    private val service: EventServiceImpl by lazy { EventServiceImpl(this.eventRepository) }

    private val expectedModel: EventModel = this.model.event

    private val expectedDto: EventDto = this.dto.eventDto()

    @Test
    fun saveEvent() {
        `when`(this.eventRepository.findByAction(anyString())).thenReturn(Optional.empty())

        `when`(this.eventRepository.save(any(EventModel::class.java))).thenReturn(this.expectedModel)

        this.service.saveEvent(this.expectedDto).also { assertThat(it).isEqualTo(this.expectedModel) }
    }

    @Test
    fun getExistingEvent() {
        `when`(this.eventRepository.findByAction(anyString())).thenReturn(Optional.of(this.expectedModel))

        verify(this.eventRepository, never()).save(any(EventModel::class.java))

        this.service.saveEvent(this.expectedDto).also { assertThat(it).isEqualTo(this.expectedModel) }
    }

    @Test
    fun findEventByAction() {
        `when`(this.eventRepository.findByAction(anyString())).thenReturn(Optional.of(this.expectedModel))

        this.service.findEventByAction(EVENT_ACTION).also { assertThat(it).isEqualTo(this.expectedDto) }
    }

    @Test
    fun throwErrorByEventActionNotFound() {
        `when`(this.eventRepository.findByAction(anyString()))
            .thenThrow(EventNotFoundException("Event with action '$EVENT_ACTION' not found"))

        assertThrows<EventNotFoundException> { this.service.findEventByAction(EVENT_ACTION) }.also {
            assertThat(it).isInstanceOf(EventNotFoundException::class.java)
                .hasMessage("Event with action '$EVENT_ACTION' not found")
        }
    }

}