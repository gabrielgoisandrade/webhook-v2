package com.gga.webhook.controllers

import com.gga.webhook.models.vO.LabelsVo
import com.gga.webhook.services.impls.LabelsServiceImpl
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
@RequestMapping("/labels", produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Labels controller", description = "Methods available for resource '/labels'")
class LabelsController {

    @Autowired
    private lateinit var labelsServiceImpl: LabelsServiceImpl

    @GetMapping
    @Operation(
        description = "Get all labels of current issue",
        responses = [
            ApiResponse(description = "Labels found.", responseCode = "200"),
            ApiResponse(description = "No labels for issue.", responseCode = "204")
        ]
    )
    fun getLabels(): ResponseEntity<HashSet<LabelsVo>> {
        val labels: HashSet<LabelsVo> = this.labelsServiceImpl.getLabels()

        if (labels.isEmpty()) return noContent().build()

        labels.forEach { it.add(linkTo(methodOn(this::class.java).getLabelById(it.id)).withSelfRel()) }

        return ok(labels)
    }

    @GetMapping("/{id}")
    fun getLabelById(@PathVariable("id") id: Long): ResponseEntity<LabelsVo> =
        this.labelsServiceImpl.getLabelById(id).run {
            this.add(linkTo(methodOn(this@LabelsController::class.java).getLabelById(this.id)).withSelfRel())

            ok(this)
        }

}