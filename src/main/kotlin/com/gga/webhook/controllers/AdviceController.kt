package com.gga.webhook.controllers

import com.gga.webhook.errors.ApiError
import com.gga.webhook.errors.exceptions.*
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

    @ExceptionHandler(AssigneeNotFoundException::class)
    fun handler(ex: AssigneeNotFoundException, request: WebRequest): ResponseEntity<ApiError> =
        ApiError(ex.message!!, request.getDescription(false), DATE).run {
            status(NOT_FOUND).body(this)
        }

    @ExceptionHandler(UserNotFoundException::class)
    fun handler(ex: UserNotFoundException, request: WebRequest): ResponseEntity<ApiError> =
        ApiError(ex.message!!, request.getDescription(false), DATE).run {
            status(NOT_FOUND).body(this)
        }

    @ExceptionHandler(CreatorNotFoundException::class)
    fun handler(ex: CreatorNotFoundException, request: WebRequest): ResponseEntity<ApiError> =
        ApiError(ex.message!!, request.getDescription(false), DATE).run {
            status(NOT_FOUND).body(this)
        }

    @ExceptionHandler(RepositoryNotFoundException::class)
    fun handler(ex: RepositoryNotFoundException, request: WebRequest): ResponseEntity<ApiError> =
        ApiError(ex.message!!, request.getDescription(false), DATE).run {
            status(NOT_FOUND).body(this)
        }

    @ExceptionHandler(OwnerNotFoundException::class)
    fun handler(ex: OwnerNotFoundException, request: WebRequest): ResponseEntity<ApiError> =
        ApiError(ex.message!!, request.getDescription(false), DATE).run {
            status(NOT_FOUND).body(this)
        }

    @ExceptionHandler(LabelNotFoundException::class)
    fun handler(ex: LabelNotFoundException, request: WebRequest): ResponseEntity<ApiError> =
        ApiError(ex.message!!, request.getDescription(false), DATE).run {
            status(NOT_FOUND).body(this)
        }

}