package com.gga.webhook.controllers

import com.gga.webhook.models.dTO.UserDto
import com.gga.webhook.services.impls.UserServiceImpl
import com.gga.webhook.utils.LinkUtil.Companion.configureLinks
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "User controller", description = "Methods available for resource '/user'.")
class UserController {

    @Autowired
    private lateinit var service: UserServiceImpl

    @GetMapping("/issue/{issue-number}")
    fun findUserByIssueNumber(@PathVariable("issue-number") issueNumber: Int): ResponseEntity<UserDto> =
        this.service.findUserByIssueNumber(issueNumber).run {
            configureLinks(this.links) {
                this.add(
                    linkTo(methodOn(this@UserController::class.java).findUserByIssueNumber(issueNumber)).withSelfRel()
                )
            }

            ok(this)
        }

}
