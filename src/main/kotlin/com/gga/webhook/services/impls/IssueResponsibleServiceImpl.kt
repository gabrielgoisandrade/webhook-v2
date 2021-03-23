package com.gga.webhook.services.impls

import com.gga.webhook.models.AssigneesModel
import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.IssueResponsibleModel
import com.gga.webhook.repositories.IssueResponsibleRepository
import com.gga.webhook.services.IssueResponsibleService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class IssueResponsibleServiceImpl @Autowired constructor(
    private val repository: IssueResponsibleRepository
) : IssueResponsibleService {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    override fun saveIssueResponsible(issue: IssueModel, assignees: List<AssigneesModel>) {
        if (assignees.isEmpty()) {
            this.log.info("IssueResponsible: No Assignees found. No actions needed.")
            return
        }

        assignees.forEach {
            val classifier: Optional<IssueResponsibleModel> =
                this.repository.findByIssueIdAndAssigneesId(issue.id, it.id)

            if (classifier.isPresent) {
                this.log.info("IssueResponsible: Same set found. Searching for alterations.")

                classifier.verifyAmbiguousResponsible(issue, it).also { toSave: IssueResponsibleModel ->
                    this.log.info("IssueResponsible: Saving new IssueResponsible's sets.")

                    this.repository.save(toSave)
                }
            } else {
                IssueResponsibleModel(issue = issue, assignees = it).also { toSave: IssueResponsibleModel ->
                    this.log.info("IssueResponsible: Saving new IssueResponsible.")

                    this.repository.save(toSave)
                }
            }
        }
    }

    private fun Optional<IssueResponsibleModel>.verifyAmbiguousResponsible(
        issue: IssueModel,
        assignees: AssigneesModel
    ): IssueResponsibleModel =
        IssueResponsibleModel(
            issue = if (this.get().issue == issue) this.get().issue else issue,
            assignees = if (this.get().assignees == assignees) this.get().assignees else assignees
        )

}
