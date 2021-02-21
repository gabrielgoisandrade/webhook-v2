package com.gga.webhook.repositories

import com.gga.webhook.models.IssueModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface IssueRepository : JpaRepository<IssueModel, Long> {

    fun findIssueModelByNumber(@Param("number") number: Int): IssueModel?

}