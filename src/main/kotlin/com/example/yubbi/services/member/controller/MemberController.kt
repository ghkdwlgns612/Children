package com.example.yubbi.services.member.controller

import com.example.yubbi.services.member.controller.dto.request.LoginRequestDto
import com.example.yubbi.services.member.controller.dto.response.LoginResponseDto
import com.example.yubbi.services.member.controller.dto.response.MyInfoResponseDto
import com.example.yubbi.services.member.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController @Autowired constructor(private val memberService: MemberService) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequestDto: LoginRequestDto): ResponseEntity<LoginResponseDto> {
        val response = memberService.login(loginRequestDto)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/members/me")
    fun getMyInfo(@RequestHeader("Authorization") accessToken: String?): ResponseEntity<MyInfoResponseDto> {
        return ResponseEntity.ok().body(memberService.getMyInfo(accessToken))
    }
}
