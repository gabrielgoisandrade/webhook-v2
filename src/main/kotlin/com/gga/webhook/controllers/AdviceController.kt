package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.errors.exceptions.InvalidDirectionException
import com.gga.webhook.errors.exceptions.IssueNotFoundException
import com.gga.webhook.errors.exceptions.PayloadNotFoundException
import com.gga.webhook.errors.exceptions.SenderNotFoundException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.*

@RestControllerAdvice
class AdviceController {

    companion object {
        private val DATE: Date = Date()
    }

    @ExceptionHandler(IssueNotFoundException::class)
    fun handler(ex: IssueNotFoundException, request: WebRequest): ResponseEntity<ApiError> =
        ApiError(ex.message!!, request.getDescription(false), DATE).run {
            status(NOT_FOUND).body(this)
        }

    @ExceptionHandler(PayloadNotFoundException::class)
    fun handler(ex: PayloadNotFoundException, request: WebRequest): ResponseEntity<ApiError> =
        ApiError(ex.message!!, request.getDescription(false), DATE).run {
            status(NOT_FOUND).body(this)
        }

    @ExceptionHandler(InvalidDirectionException::class)
    fun handler(ex: InvalidDirectionException, request: WebRequest): ResponseEntity<ApiError> =
        ApiError(ex.message!!, request.getDescription(false), DATE).run {
            status(BAD_REQUEST).body(this)
        }

    @ExceptionHandler(SenderNotFoundException::class)
    fun handler(ex: SenderNotFoundException, request: WebRequest): ResponseEntity<ApiError> =
        ApiError(ex.message!!, request.getDescription(false), DATE).run {
            status(NOT_FOUND).body(this)
        }

}