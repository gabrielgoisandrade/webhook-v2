package com.gga.webhook.repositories

import com.gga.webhook.models.OwnerModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OwnerRepository : JpaRepository<OwnerModel, Long> {

    @Query("select c from OwnerModel c join RepositoryModel r on c.id = r.owner.id")
    fun getOwner(): OwnerModel?

}