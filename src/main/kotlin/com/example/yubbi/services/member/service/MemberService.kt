package com.example.yubbi.services.member.service

import com.example.yubbi.services.member.controller.dto.request.LoginRequestDto
import com.example.yubbi.services.member.controller.dto.response.LoginResponseDto

interface MemberService {
    fun login(request: LoginRequestDto): LoginResponseDto
}
