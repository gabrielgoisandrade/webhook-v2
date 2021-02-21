package com.gga.webhook.services

import com.gga.webhook.builder.PayloadBuilder
import com.gga.webhook.repositories.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EventServiceTest {

    @Mock
    private lateinit var payloadRepository: PayloadRepository

    @Mock
    private lateinit var issueRepository: IssueRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var assigneeRepository: AssigneeRepository

    @Mock
    private lateinit var assigneesRepository: AssigneesRepository

    @Mock
    private lateinit var labelRepository: LabelRepository

    @Mock
    private lateinit var milestoneRepository: MilestoneRepository

    @Mock
    private lateinit var creatorRepository: CreatorRepository

    @Mock
    private lateinit var repositoryRepository: RepositoryRepository

    @Mock
    private lateinit var licenseRepository: LicenseRepository

    @Mock
    private lateinit var ownerRepository: OwnerRepository

    @Mock
    private lateinit var senderRepository: SenderRepository

    @InjectMocks
    private lateinit var eventService: EventService

    private val builder: PayloadBuilder = PayloadBuilder()

    @BeforeAll
    fun init() {
        openMocks(this)
    }

    @Test
    fun savePayload() {
    }

    @Test
    fun saveIssue() {
    }

    @Test
    fun saveUser() {
    }

    @Test
    fun saveAssignee() {
    }

    @Test
    fun saveAssignees() {
    }

    @Test
    fun saveLabel() {
    }

    @Test
    fun saveMilestone() {
    }

    @Test
    fun saveCreator() {
    }

    @Test
    fun saveRepository() {
    }

    @Test
    fun saveLicense() {
    }

    @Test
    fun saveOwner() {
    }

    @Test
    fun saveSender() {
    }

    @Test
    fun getIssueByNumber() {
    }

}