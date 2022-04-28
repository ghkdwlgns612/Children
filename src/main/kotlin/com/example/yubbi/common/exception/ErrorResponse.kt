package com.example.yubbi.common.exception

class ErrorResponse(errorCode: ErrorCode) {
    private var status: Int? = null
    private var message: String? = null

    init {
        this.status = errorCode.status
        this.message = errorCode.message
    }
}
