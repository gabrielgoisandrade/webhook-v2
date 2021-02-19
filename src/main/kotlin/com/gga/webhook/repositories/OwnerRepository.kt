package com.gga.webhook.repositories

import com.gga.webhook.models.*
import org.springframework.data.jpa.repository.JpaRepository

interface OwnerRepository: JpaRepository<OwnerModel, Long>