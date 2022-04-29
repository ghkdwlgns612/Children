package com.example.yubbi.common.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    fun generalExceptionHandler(e: Exception): ResponseEntity.BodyBuilder {
        return ResponseEntity.badRequest()
    }
}
