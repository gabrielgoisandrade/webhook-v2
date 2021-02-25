package com.gga.webhook.repositories

import com.gga.webhook.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserModel, Long>