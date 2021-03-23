package com.gga.webhook.factories

import com.gga.webhook.repositories.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
abstract class BaseServiceImplTestFactory : BaseTest() {

    @MockBean
    protected final lateinit var eventRepository: EventRepository

    @MockBean
    protected final lateinit var issueRepository: IssueRepository

    @MockBean
    protected final lateinit var userRepository: UserRepository

    @MockBean
    protected final lateinit var assigneeRepository: AssigneeRepository

    @MockBean
    protected final lateinit var milestoneRepository: MilestoneRepository

    @MockBean
    protected final lateinit var creatorRepository: CreatorRepository

    @MockBean
    protected final lateinit var repositoryRepository: RepositoryRepository

    @MockBean
    protected final lateinit var licenseRepository: LicenseRepository

    @MockBean
    protected final lateinit var ownerRepository: OwnerRepository

    @MockBean
    protected final lateinit var senderRepository: SenderRepository

    @MockBean
    protected final lateinit var assigneesRepository: AssigneesRepository

    @MockBean
    protected final lateinit var labelsRepository: LabelsRepository

    @MockBean
    protected final lateinit var issueClassifierRepository: IssueClassifierRepository

    @MockBean
    protected final lateinit var issueResponsibleRepository: IssueResponsibleRepository

}