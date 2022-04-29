package com.example.yubbi.common.exception

class ErrorResponse(private val errorCode: ErrorCode) {
    var status: Int? = errorCode.status
    var message: String? = errorCode.message
}
