package com.gga.webhook.services.impls

import com.gga.webhook.errors.exceptions.SenderNotFoundException
import com.gga.webhook.helper.FkHelper
import com.gga.webhook.helper.PageableHelper
import com.gga.webhook.models.SenderModel
import com.gga.webhook.models.dTO.SenderDto
import com.gga.webhook.repositories.SenderRepository
import com.gga.webhook.services.SenderService
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
class SenderServiceImpl @Autowired constructor(
    private val repository: SenderRepository
) : SenderService, FkHelper<SenderModel> {

    private val helper: PageableHelper<SenderModel> = PageableHelper(this.repository)

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    @CacheEvict("senderByLogin", "senderByEventAction", allEntries = true)
    override fun saveSender(sender: SenderModel) {
        val senderFound: Optional<SenderModel> = this.repository.findByLogin(sender.login)

        if (senderFound.isPresent) {
            this.collectAlterations(sender, senderFound.get())?.let {
                log.info("Sender: Saving alterations.")
                this.repository.save(it)
            } ?: log.info("Sender: No alterations found.")
        } else {
            log.info("Sender: Saving new Sender.")

            this.repository.save(sender)
        }
    }

    override fun collectAlterations(newResult: SenderModel, actualResult: SenderModel): SenderModel? {
        var updatedSender: SenderModel? = null

        if (actualResult.event!!.action != newResult.event!!.action) {
            log.info("Sender: Alterations found.")
            log.info("Sender: Updating foreign key 'EVENT_ID'.")

            updatedSender = actualResult.apply { this.event = newResult.event }
        }

        return updatedSender
    }

    @Cacheable("senderByLogin")
    override fun findSenderByLogin(login: String): SenderDto = this.repository.findByLogin(login).orElseThrow {
        SenderNotFoundException("Sender '$login' not found")
    } convertTo SenderDto::class.java

}
