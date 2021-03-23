package com.gga.webhook.repositories

import com.gga.webhook.factories.BaseRepositoryTestFactory
import com.gga.webhook.models.EventModel
import com.gga.webhook.models.SenderModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class SenderRepositoryTest : BaseRepositoryTestFactory() {

    @Autowired
    private lateinit var repository: SenderRepository

    private val eventModel: EventModel = this.model.event

    private val senderModel: SenderModel = this.model.sender

    @Test
    @DisplayName("Must save Sender")
    fun saveSender() {
        val expectedEvent: EventModel = this.entityManager.persist(this.eventModel)

        val expectedSender: SenderModel =
            this.entityManager.merge(this.senderModel.apply { this.event = expectedEvent })

        this.repository.findById(expectedSender.id).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedSender)
            assertThat(it.get().event).isEqualTo(expectedEvent)
        }
    }

    @Test
    @DisplayName("Must return the Sender by login")
    fun findByLogin() {
        val expectedEvent: EventModel = this.entityManager.persist(this.eventModel)

        val expectedSender: SenderModel =
            this.entityManager.merge(this.senderModel.apply { this.event = expectedEvent })

        this.repository.findByLogin(expectedSender.login).also {
            assertThat(it.isPresent).isTrue
            assertThat(it.get()).isEqualTo(expectedSender)
            assertThat(it.get().event).isEqualTo(expectedEvent)
        }
    }

}