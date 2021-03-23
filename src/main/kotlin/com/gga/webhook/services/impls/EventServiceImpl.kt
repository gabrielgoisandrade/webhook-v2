package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.EventNotFoundException
import com.gga.webhook.helper.PageableHelper
import com.gga.webhook.models.EventModel
import com.gga.webhook.models.dTO.EventDto
import com.gga.webhook.repositories.EventRepository
import com.gga.webhook.services.EventService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.EnableCaching
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@EnableCaching
class EventServiceImpl @Autowired constructor(
    private val repository: EventRepository
) : EventService {

    private val helper: PageableHelper<EventModel> = PageableHelper(this.repository)

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @CacheEvict("eventByAction", "eventByID", allEntries = true)
    override fun saveEvent(event: EventDto): EventModel {
        val eventToSave: EventModel = event convertTo EventModel::class.java

        val eventFound: Optional<EventModel> = this.repository.findByAction(event.action)

        return if (eventFound.isPresent) {
            this.log.info("Event: Returning existing Event.")

            eventFound.get()
        } else {
            this.log.info("Event: Saving new Event.")

            this.repository.save(eventToSave)
        }
    }

    @Cacheable("eventByAction")
    override fun findEventByAction(action: String): EventDto = this.repository.findByAction(action).orElseThrow {
        EventNotFoundException("Event with action '$action' not found.")
    } convertTo EventDto::class.java

}