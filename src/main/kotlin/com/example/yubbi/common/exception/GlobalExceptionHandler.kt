package com.example.yubbi.common.exception

import com.example.yubbi.common.exception.custom.ForbiddenException
import com.example.yubbi.common.exception.custom.NotFoundMemberException
import com.example.yubbi.common.exception.custom.NotMatchPasswordException
import com.example.yubbi.common.exception.custom.UnAuthorizedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    fun generalExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse(ErrorCode.GENERAL_EXCEPTION))
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class, MethodArgumentNotValidException::class])
    fun badRequestExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(ErrorCode.BAD_REQUEST))
    }

    @ExceptionHandler(value = [NotMatchPasswordException::class])
    fun notMatchPasswordExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ErrorCode.NOT_MATCH_PASSWORD))
    }

    @ExceptionHandler(value = [NotFoundMemberException::class])
    fun notFoundMemberExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ErrorCode.NOT_FOUND_MEMBER))
    }

    @ExceptionHandler(value = [UnAuthorizedException::class])
    fun unAuthorizedExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse(ErrorCode.UNAUTHORIZED))
    }

    @ExceptionHandler(value = [ForbiddenException::class])
    fun forbiddenExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse(ErrorCode.FORBIDDEN))
    }
}
