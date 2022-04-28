package com.example.yubbi.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalHandlerException {

    @ExceptionHandler(value = [Exception::class])
    fun generalExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse(ErrorCode.GENERAL_EXCEPTION))
    }
}
