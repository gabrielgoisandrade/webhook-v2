package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.helper.PageableHelper
import com.gga.webhook.models.UserModel
import com.gga.webhook.models.dTO.UserDto
import com.gga.webhook.repositories.UserRepository
import com.gga.webhook.services.UserService
import com.gga.webhook.utils.MapperUtil.Companion.convertTo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.EnableCaching
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@EnableCaching
class UserServiceImpl @Autowired constructor(
    private val repository: UserRepository
) : UserService {

    private val helper: PageableHelper<UserModel> = PageableHelper(this.repository)

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @CacheEvict("userByID", "userByIssueNumber", "userByLogin", "userByIssueId", allEntries = true)
    override fun saveUser(user: UserModel): UserModel {
        val userFound: Optional<UserModel> = this.repository.findByLogin(user.login)

        return if (userFound.isPresent) {
            this.log.info("User: Returning existing User.")

            userFound.get()
        } else {
            this.log.info("User: Saving new User.")

            this.repository.save(user)
        }
    }

    @Cacheable("userByIssueNumber")
    override fun findUserByIssueNumber(issueNumber: Int): UserDto =
        this.repository.findByIssueNumber(issueNumber).orElseThrow {
            RelationNotFoundException("There isn't any User related with this Issue")
        } convertTo UserDto::class.java

}
