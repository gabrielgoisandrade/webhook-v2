package com.gga.webhook.repositories

import com.gga.webhook.models.IssueModel
import org.springframework.data.jpa.repository.JpaRepository

interface IssueRepository: JpaRepository<IssueModel, Long>