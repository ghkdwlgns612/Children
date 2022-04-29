package com.example.yubbi.services.member.service

import com.example.yubbi.common.exception.custom.NotFoundMemberException
import com.example.yubbi.common.exception.custom.NotMatchPasswordException
import com.example.yubbi.services.member.controller.dto.request.LoginRequestDto
import com.example.yubbi.services.member.controller.dto.response.LoginResponseDto
import com.example.yubbi.services.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class MemberServiceImpl @Autowired constructor(private val memberRepository: MemberRepository) : MemberService {

    override fun login(request: LoginRequestDto): LoginResponseDto {
        val member = memberRepository.findByEmail(request.email)

        return if (member?.getEmail() == null) {
            throw NotFoundMemberException()
        } else if (member.getPassword() != request.password) {
            throw NotMatchPasswordException()
        } else {
            LoginResponseDto(member.getMemberId().toString() + "_" + member.getRole())
        }
    }
}
