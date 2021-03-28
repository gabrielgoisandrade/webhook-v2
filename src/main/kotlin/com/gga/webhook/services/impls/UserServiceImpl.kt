package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.RelationNotFoundException
import com.gga.webhook.helper.AlterationsHelper
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
) : UserService, AlterationsHelper<UserModel> {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @CacheEvict("userByID", "userByIssueNumber", "userByLogin", "userByIssueId", allEntries = true)
    override fun saveUser(user: UserModel): UserModel {
        val userFound: Optional<UserModel> = this.repository.findByLogin(user.login)

        return if (userFound.isPresent) {
            this.collectAlterations(user, userFound.get())?.let {
                log.info("User: Saving alterations.")
                this.repository.save(it)
            } ?: log.info("User: No alterations found.")

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

    override fun collectAlterations(newResult: UserModel, actualResult: UserModel): UserModel? {
        newResult.id = actualResult.id

        return if (actualResult != newResult) {
            log.info("User: Alterations found.")
            newResult
        } else {
            null
        }
    }

}
