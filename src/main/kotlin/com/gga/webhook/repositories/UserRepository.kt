package com.gga.webhook.repositories

import com.gga.webhook.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<UserModel, Long> {

    @Query("select u from UserModel u join IssueModel i on u.id = i.user.id")
    fun getUser(): UserModel?

}