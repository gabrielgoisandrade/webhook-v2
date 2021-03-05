package com.gga.webhook.repositories

import com.gga.webhook.models.AssigneeModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AssigneeRepository : JpaRepository<AssigneeModel, Long> {

    @Query("select a from AssigneeModel a join IssueModel i on a.id = i.assignee.id")
    fun getAssignee(): AssigneeModel?

}