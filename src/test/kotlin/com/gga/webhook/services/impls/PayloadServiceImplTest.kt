package com.gga.webhook.services.impls

import com.gga.webhook.factories.BaseServiceImplTestFactory
import com.gga.webhook.models.*
import com.gga.webhook.models.dTO.EventDto
import com.gga.webhook.models.dTO.PayloadDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

internal class PayloadServiceImplTest : BaseServiceImplTestFactory() {

    private val payloadDto: PayloadDto = this.dto.payloadDto()

    @Autowired
    private lateinit var eventServiceImpl: EventServiceImpl

    @Autowired
    private lateinit var repositoryServiceImpl: RepositoryServiceImpl

    @Autowired
    private lateinit var senderServiceImpl: SenderServiceImpl

    @Autowired
    private lateinit var ownerServiceImpl: OwnerServiceImpl

    @Autowired
    private lateinit var licenseServiceImpl: LicenseServiceImpl

    @Autowired
    private lateinit var userServiceImpl: UserServiceImpl

    @Autowired
    private lateinit var assigneeServiceImpl: AssigneeServiceImpl

    @Autowired
    private lateinit var issueServiceImpl: IssueServiceImpl

    @Autowired
    private lateinit var creatorServiceImpl: CreatorServiceImpl

    @Autowired
    private lateinit var milestoneServiceImpl: MilestoneServiceImpl

    @Autowired
    private lateinit var labelsServiceImpl: LabelsServiceImpl

    @Autowired
    private lateinit var assigneesServiceImpl: AssigneesServiceImpl

    @Autowired
    private lateinit var issueResponsibleServiceImpl: IssueResponsibleServiceImpl

    @Autowired
    private lateinit var issueClassifierServiceImpl: IssueClassifierServiceImpl

    private val payloadServiceImpl: PayloadServiceImpl by lazy {
        PayloadServiceImpl(
            this.eventServiceImpl,
            this.repositoryServiceImpl,
            this.senderServiceImpl,
            this.ownerServiceImpl,
            this.creatorServiceImpl,
            this.milestoneServiceImpl,
            this.userServiceImpl,
            this.assigneeServiceImpl,
            this.issueServiceImpl,
            this.licenseServiceImpl,
            this.labelsServiceImpl,
            this.assigneesServiceImpl,
            this.issueResponsibleServiceImpl,
            this.issueClassifierServiceImpl
        )
    }

    @Test
    fun savePayload() {
        val expected: EventDto = this.dto.eventDto()

        val expectedEvent: EventModel = this.model.event

        val expectedSender: SenderModel = this.model.sender.apply { this.event = expectedEvent }

        `when`(this.eventRepository.findByAction(anyString())).thenReturn(Optional.empty())

        `when`(this.eventRepository.save(any(EventModel::class.java))).thenReturn(expectedEvent)

        `when`(this.ownerRepository.save(any(OwnerModel::class.java))).thenReturn(this.model.owner)

        `when`(this.userRepository.save(any(UserModel::class.java))).thenReturn(this.model.user)

        `when`(this.creatorRepository.save(any(CreatorModel::class.java))).thenReturn(this.model.creator)

        `when`(this.milestoneRepository.save(any(MilestoneModel::class.java))).thenReturn(this.model.milestone)

        `when`(this.assigneeRepository.save(any(AssigneeModel::class.java))).thenReturn(this.model.assignee)

        `when`(this.issueRepository.save(any(IssueModel::class.java))).thenReturn(this.model.issue)

        `when`(this.senderRepository.save(any(SenderModel::class.java))).thenReturn(expectedSender)

        `when`(this.labelsRepository.saveAll(anyList())).thenReturn(listOf(this.model.labels))

        `when`(this.assigneesRepository.saveAll(anyList())).thenReturn(listOf(this.model.assignees))

        this.payloadServiceImpl.savePayload(this.payloadDto).also { assertThat(it).isEqualTo(expected) }
    }

}