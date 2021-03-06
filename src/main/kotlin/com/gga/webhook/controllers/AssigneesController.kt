package com.gga.webhook.controllers

import com.gga.webhook.models.vO.AssigneesVo
import com.gga.webhook.services.impls.AssigneesServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.noContent
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/assignees", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Labels controller", description = "Methods available for resource '/assignees'")
class AssigneesController {

    @Autowired
    private lateinit var assigneesServiceImpl: AssigneesServiceImpl

    @GetMapping
    @Operation(
        description = "Get all assignees of current issue",
        responses = [
            ApiResponse(description = "Assignees found.", responseCode = "200"),
            ApiResponse(description = "No assignees for issue.", responseCode = "204")
        ]
    )
    fun getAssignees(): ResponseEntity<HashSet<AssigneesVo>> {
        val assignees: HashSet<AssigneesVo> = this.assigneesServiceImpl.getAssignees()

        if (assignees.isEmpty()) return noContent().build()

        assignees.forEach { it.add(linkTo(methodOn(this::class.java).getLabelById(it.id)).withSelfRel()) }

        return ok(assignees)
    }

    @GetMapping("/{id}")
    fun getLabelById(@PathVariable("id") id: Long): ResponseEntity<AssigneesVo> =
        this.assigneesServiceImpl.getAssigneeById(id).run {
            this.add(linkTo(methodOn(this@AssigneesController::class.java).getLabelById(this.id)).withSelfRel())

            ok(this)
        }

}