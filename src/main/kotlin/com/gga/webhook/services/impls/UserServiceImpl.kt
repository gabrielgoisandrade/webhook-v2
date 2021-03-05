package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.UserNotFoundException
import com.gga.webhook.models.vO.UserVo
import com.gga.webhook.repositories.UserRepository
import com.gga.webhook.services.UserService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(private val userRepository: UserRepository) : UserService {

    override fun getUser(): UserVo = (this.userRepository.getUser()
        ?: throw UserNotFoundException("No user found.")) convertTo UserVo::class.java

}