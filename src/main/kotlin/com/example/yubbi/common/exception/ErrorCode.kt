package com.example.yubbi.common.exception

enum class ErrorCode(
    val status: Int,
    val message: String
) {
    GENERAL_EXCEPTION(500, "예상치 못한 에러입니다.")
}
