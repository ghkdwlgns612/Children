package com.example.yubbi.common.exception

enum class ErrorCode(
    val status: Int,
    val message: String
) {
    GENERAL_EXCEPTION(500, "예상치 못한 에러입니다."),
    NOT_MATCH_PASSWORD(404, "비밀번호가 일치하지 않습니다."),
    NOT_FOUND_MEMBER(404, "존재하지 않은 이메일입니다."),
    UNAUTHORIZED(401, "인증이 필요합니다."),
    FORBIDDEN(403, "접근 권한이 없습니다."),
    BAD_REQUEST(400, "잘못된 요청입니다")
}
