package com.gga.webhook.services

import com.gga.webhook.models.UserModel
import com.gga.webhook.models.dTO.UserDto


interface UserService {

    fun saveUser(user: UserModel): UserModel

    fun findUserByIssueNumber(issueNumber: Int): UserDto

}
