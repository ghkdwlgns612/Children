package com.example.yubbi.services.member.controller

import com.example.yubbi.services.member.controller.dto.LoginRequestDto
import com.example.yubbi.services.member.controller.dto.LoginResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController {

    @PostMapping("/login")
    fun login(@RequestBody loginRequestDto: LoginRequestDto): ResponseEntity<LoginResponseDto> {
        val loginResponseDto = LoginResponseDto("1_ADMIN")
        return ResponseEntity.ok().body(loginResponseDto)
    }
}
