package com.gga.webhook.services

import com.gga.webhook.models.EventModel
import com.gga.webhook.models.dTO.EventDto

interface EventService {

    fun saveEvent(event: EventModel): EventModel

    fun findEventByAction(action: String): EventDto

}