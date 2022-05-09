package com.example.yubbi.common.exception

import com.example.yubbi.common.exception.custom.ForbiddenException
import com.example.yubbi.common.exception.custom.NotEnoughInfoUploadContentForCreateException
import com.example.yubbi.common.exception.custom.NotEnoughInfoUploadContentForUpdateException
import com.example.yubbi.common.exception.custom.NotFoundCategoryException
import com.example.yubbi.common.exception.custom.NotFoundContentException
import com.example.yubbi.common.exception.custom.NotFoundFaqException
import com.example.yubbi.common.exception.custom.NotFoundMemberException
import com.example.yubbi.common.exception.custom.NotMatchImageTypeException
import com.example.yubbi.common.exception.custom.NotMatchPasswordException
import com.example.yubbi.common.exception.custom.NotMatchVideoTypeException
import com.example.yubbi.common.exception.custom.UnAuthorizedException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(value = [Exception::class])
    fun generalExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse(ErrorCode.GENERAL_EXCEPTION))
    }

    @ExceptionHandler(
        value = [
            HttpMessageNotReadableException::class,
            MethodArgumentNotValidException::class,
            ConstraintViolationException::class,
        ]
    )
    fun badRequestExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(ErrorCode.BAD_REQUEST))
    }

    @ExceptionHandler(value = [NotMatchPasswordException::class])
    fun notMatchPasswordExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ErrorCode.NOT_MATCH_PASSWORD))
    }

    @ExceptionHandler(value = [NotFoundMemberException::class])
    fun notFoundMemberExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ErrorCode.NOT_FOUND_MEMBER))
    }

    @ExceptionHandler(value = [NotFoundCategoryException::class])
    fun notFoundCategoryExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ErrorCode.NOT_FOUND_CATEGORY))
    }

    @ExceptionHandler(value = [NotFoundFaqException::class])
    fun notFoundFaqExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ErrorCode.NOT_FOUND_FAQ))
    }

    @ExceptionHandler(value = [UnAuthorizedException::class])
    fun unAuthorizedExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e.message)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse(ErrorCode.UNAUTHORIZED))
    }

    @ExceptionHandler(value = [ForbiddenException::class])
    fun forbiddenExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e.message)
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse(ErrorCode.FORBIDDEN))
    }

    @ExceptionHandler(value = [NotFoundContentException::class])
    fun notFoundExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ErrorCode.NOT_FOUND_CONTENT))
    }
    @ExceptionHandler(value = [NotEnoughInfoUploadContentForCreateException::class])
    fun notEnoughInfoUploadContentForCreateExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(ErrorCode.NOT_ENOUGH_INFO_CREATE))
    }
    @ExceptionHandler(value = [NotEnoughInfoUploadContentForUpdateException::class])
    fun notEnoughInfoUploadContentForUpdateExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(ErrorCode.NOT_ENOUGH_INFO_UPDATE))
    }
    @ExceptionHandler(value = [NotMatchImageTypeException::class])
    fun notMatchImageTypeExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(ErrorCode.NOT_MATCH_IMAGE_TYPE))
    }
    @ExceptionHandler(value = [NotMatchVideoTypeException::class])
    fun notMatchVideoTypeExceptionHandler(e: Exception): ResponseEntity<ErrorResponse> {
        log.error(e.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(ErrorCode.NOT_MATCH_VIDEO_TYPE))
    }
}
