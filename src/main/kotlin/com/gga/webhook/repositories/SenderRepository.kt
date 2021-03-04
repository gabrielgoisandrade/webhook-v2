package com.gga.webhook.repositories

import com.gga.webhook.models.SenderModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SenderRepository : JpaRepository<SenderModel, Long> {

    @Query("select s from SenderModel s join PayloadModel p on s.id = p.sender.id")
    fun getSender(): SenderModel?

}