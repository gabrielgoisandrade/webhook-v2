package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.errors.exceptions.IssueNotFound
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.*

@RestControllerAdvice
class AdviceController {

    companion object {
        @JvmStatic
        private val TIMESTAMP: Date = Date()
    }

    @ExceptionHandler(IssueNotFound::class)
    fun handler(ex: IssueNotFound, request: WebRequest): ResponseEntity<ApiError> =
        ApiError(ex.message!!, request.getDescription(false), TIMESTAMP).run {
            status(HttpStatus.BAD_REQUEST).body(this)
        }

}