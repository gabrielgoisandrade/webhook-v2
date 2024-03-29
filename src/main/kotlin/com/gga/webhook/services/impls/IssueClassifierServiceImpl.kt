package com.gga.webhook.services.impls

import com.gga.webhook.models.IssueClassifierModel
import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.LabelsModel
import com.gga.webhook.repositories.IssueClassifierRepository
import com.gga.webhook.services.IssueClassifierService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class IssueClassifierServiceImpl @Autowired constructor(
    private val repository: IssueClassifierRepository
) : IssueClassifierService {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    override fun saveIssueClassifier(issue: IssueModel, labels: List<LabelsModel>) {
        labels.forEach {
            val classifier: Optional<IssueClassifierModel> = this.repository.findByIssueIdAndLabelsId(issue.id, it.id)

            if (classifier.isPresent) {
                this.log.info("Issue classifier: Same set found. Searching for alterations.")

                classifier.verifyAmbiguousClassifiers(issue, it)?.let { toSave: IssueClassifierModel ->
                    this.log.info("IssueClassifier: Saving new IssueClassifiers' sets.")

                    this.repository.save(toSave)
                } ?: this.log.info("Issue classifier: No alterations found.")
            } else {
                IssueClassifierModel(issue = issue, labels = it).also { toSave: IssueClassifierModel ->
                    this.log.info("IssueClassifier: Saving new IssueClassifier.")

                    this.repository.save(toSave)
                }
            }
        }
    }

    private fun Optional<IssueClassifierModel>.verifyAmbiguousClassifiers(
        newIssue: IssueModel,
        newLabel: LabelsModel
    ): IssueClassifierModel? =
        if (this.get().issue != newIssue || this.get().labels != newLabel)
            IssueClassifierModel(issue = newIssue, labels = newLabel)
        else
            null

}
