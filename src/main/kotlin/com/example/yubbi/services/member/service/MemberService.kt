package com.example.yubbi.services.member.service

import com.example.yubbi.services.member.controller.dto.request.LoginRequestDto
import com.example.yubbi.services.member.controller.dto.response.LoginResponseDto
import com.example.yubbi.services.member.domain.Member

interface MemberService {
    fun login(request: LoginRequestDto): LoginResponseDto

    fun getAdminMemberByAccessToken(accessToken: String?): Member
}
