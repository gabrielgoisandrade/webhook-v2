package com.gga.webhook.constants

import com.gga.webhook.controllers.*

object ControllersConstants {

    val EVENT_CONTROLLER: Class<EventController> = EventController::class.java

    val ISSUE_CONTROLLER: Class<IssueController> = IssueController::class.java

    val REPOSITORY_CONTROLLER: Class<RepositoryController> = RepositoryController::class.java

    val SENDER_CONTROLLER: Class<SenderController> = SenderController::class.java

    val OWNER_CONTROLLER: Class<OwnerController> = OwnerController::class.java

    val CREATOR_CONTROLLER: Class<CreatorController> = CreatorController::class.java

    val MILESTONE_CONTROLLER: Class<MilestoneController> = MilestoneController::class.java

    val USER_CONTROLLER: Class<UserController> = UserController::class.java

    val LICENSE_CONTROLLER: Class<LicenseController> = LicenseController::class.java

    val LABELS_CONTROLLER: Class<LabelsController> = LabelsController::class.java

    val ASSIGNEES_CONTROLLER: Class<AssigneesController> = AssigneesController::class.java

    val ASSIGNEE_CONTROLLER: Class<AssigneeController> = AssigneeController::class.java
}