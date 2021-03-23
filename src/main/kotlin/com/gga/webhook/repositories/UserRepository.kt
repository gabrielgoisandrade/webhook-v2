package com.gga.webhook.repositories

import com.gga.webhook.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<UserModel, Long> {

    fun findByLogin(login: String): Optional<UserModel>

    @Query("SELECT I.user FROM IssueModel I where I.number = :number")
    fun findByIssueNumber(number: Int): Optional<UserModel>

}