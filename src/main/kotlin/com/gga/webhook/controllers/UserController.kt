package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.models.vO.UserVo
import com.gga.webhook.services.impls.UserServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "User controller", description = "Methods available for resource 'user'")
class UserController {

    @Autowired
    private lateinit var userServiceImpl: UserServiceImpl

    @Operation(
        description = "Return the user of current issue",
        responses = [ApiResponse(description = "user found", responseCode = "200"),
            ApiResponse(
                description = "No user found.",
                responseCode = "404",
                content = [Content(schema = Schema(implementation = ApiError::class))]
            )]
    )
    @GetMapping
    fun getUser(): ResponseEntity<UserVo> = this.userServiceImpl.getUser().run {
        this.add(linkTo(methodOn(this@UserController::class.java).getUser()).withSelfRel())

        ok(this)
    }

}