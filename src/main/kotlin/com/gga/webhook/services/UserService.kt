package com.gga.webhook.services

import com.gga.webhook.models.vO.UserVo

interface UserService {

    fun getUser(): UserVo

}