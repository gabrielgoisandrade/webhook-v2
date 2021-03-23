package com.gga.webhook.repositories

import com.gga.webhook.factories.BaseRepositoryTestFactory
import com.gga.webhook.models.EventModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class EventRepositoryTest : BaseRepositoryTestFactory() {

    @Autowired
    private lateinit var repository: EventRepository

    private val eventModel: EventModel = this.model.event

    @Test
    @DisplayName("Must save Event")
    fun saveEvent() {
        val expectedEvent: EventModel = this.entityManager.persist(this.eventModel)

        this.repository.findById(expectedEvent.id).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedEvent)
        }
    }

    @Test
    @DisplayName("Must return the Event by action")
    fun findByAction() {
        val expectedEvent: EventModel = this.entityManager.persist(this.eventModel)

        this.repository.findByAction(expectedEvent.action).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedEvent)
        }
    }

}