package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.errors.exceptions.IssueNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.*

@RestControllerAdvice
class AdviceController {

    @ExceptionHandler(IssueNotFoundException::class)
    fun handler(ex: IssueNotFoundException, request: WebRequest): ResponseEntity<ApiError> =
        ApiError(ex.message!!, request.getDescription(false), Date()).run {
            status(HttpStatus.NOT_FOUND).body(this)
        }

}