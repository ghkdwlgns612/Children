package com.example.yubbi.services.member.controller.dto.response

import com.example.yubbi.common.utils.Role

data class MyInfoResponseDto(
    val email: String,
    val name: String,
    val role: Role
)
