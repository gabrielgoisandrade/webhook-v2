package com.gga.webhook.services

import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.LabelsModel

interface IssueClassifierService {

    fun saveIssueClassifier(issue: IssueModel, labels: HashSet<LabelsModel>)

}