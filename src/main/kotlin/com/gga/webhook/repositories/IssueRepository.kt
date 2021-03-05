package com.gga.webhook.repositories

import com.gga.webhook.models.IssueModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface IssueRepository : JpaRepository<IssueModel, Long> {

    fun findIssueModelByNumber(@Param("number") number: Int): Set<IssueModel>

    @Query("select i from IssueModel i join PayloadModel p on p.issue.id = i.id")
    fun getIssue(): IssueModel?

}