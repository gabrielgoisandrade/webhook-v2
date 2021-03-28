package com.gga.webhook.services.impls

import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.IssueClassifierModel
import com.gga.webhook.models.IssueModel
import com.gga.webhook.models.LabelsModel
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

internal class IssueClassifierServiceImplTest : BaseServiceImplTestFactory() {

    private val service: IssueClassifierServiceImpl by lazy {
        IssueClassifierServiceImpl(this.issueClassifierRepository)
    }

    private val expectedIssue: IssueModel = this.model.issue

    private val expectedLabels: LabelsModel = this.model.labels

    private val expectedIssueClassifier: IssueClassifierModel =
        IssueClassifierModel(issue = this.expectedIssue, labels = this.expectedLabels)

    @Test
    fun saveIssueClassifier() {
        `when`(this.issueClassifierRepository.findByIssueIdAndLabelsId(anyLong(), anyLong()))
            .thenReturn(Optional.empty())

        `when`(this.issueClassifierRepository.save(any(IssueClassifierModel::class.java)))
            .thenReturn((this.expectedIssueClassifier))

        this.service.saveIssueClassifier(this.expectedIssue, listOf(this.expectedLabels))

        verify(this.issueClassifierRepository).save(any(IssueClassifierModel::class.java))
    }

    @Test
    fun doNotSave() {
        this.service.saveIssueClassifier(this.expectedIssue, emptyList())

        verify(this.issueClassifierRepository, never()).findByIssueIdAndLabelsId(anyLong(), anyLong())

        verify(this.issueClassifierRepository, never()).save(any(IssueClassifierModel::class.java))
    }

}