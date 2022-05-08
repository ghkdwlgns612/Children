package com.example.yubbi.services.member.service

import com.example.yubbi.common.exception.custom.ForbiddenException
import com.example.yubbi.common.exception.custom.NotFoundMemberException
import com.example.yubbi.common.exception.custom.NotMatchPasswordException
import com.example.yubbi.common.exception.custom.UnAuthorizedException
import com.example.yubbi.common.utils.Role
import com.example.yubbi.services.member.controller.dto.request.LoginRequestDto
import com.example.yubbi.services.member.controller.dto.response.LoginResponseDto
import com.example.yubbi.services.member.controller.dto.response.MyInfoResponseDto
import com.example.yubbi.services.member.domain.Member
import com.example.yubbi.services.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class MemberServiceImpl @Autowired constructor(private val memberRepository: MemberRepository) : MemberService {

    private val accessTokenDelimiter = "_"

    override fun login(request: LoginRequestDto): LoginResponseDto {
        val member = memberRepository.findByEmail(request.email)

        return if (member?.getEmail() == null) {
            throw NotFoundMemberException()
        } else if (member.getPassword() != request.password) {
            throw NotMatchPasswordException()
        } else {
            LoginResponseDto(member.getMemberId().toString() + accessTokenDelimiter + member.getRole())
        }
    }

    override fun getMyInfo(accessToken: String?): MyInfoResponseDto {
        if (accessToken == null) {
            throw UnAuthorizedException()
        }
        checkValidAccessToken(accessToken)

        val memberId = getMemberIdByAccessToken(accessToken)
        val member = memberRepository.findById(memberId).orElseThrow { NotFoundMemberException() }

        return MyInfoResponseDto(
            member.getEmail()!!,
            member.getName()!!,
            member.getRole()!!
        )
    }

    override fun getAdminMemberByAccessToken(accessToken: String?): Member {
        if (accessToken == null) {
            throw UnAuthorizedException()
        }
        checkValidAccessToken(accessToken)
        checkAdminRole(accessToken)

        val adminMemberId = getMemberIdByAccessToken(accessToken)

        return memberRepository.findById(adminMemberId).get()
    }

    private fun checkValidAccessToken(accessToken: String) {
        val split = accessToken.split(accessTokenDelimiter)
        if (split.size != 2) {
            throw UnAuthorizedException()
        }
        if (split[0].toIntOrNull() == null) {
            throw UnAuthorizedException()
        }
        if (split[1] != Role.MEMBER.name && split[1] != Role.ADMIN.name) {
            throw UnAuthorizedException()
        }
    }

    private fun checkAdminRole(accessToken: String) {
        val split = accessToken.split(accessTokenDelimiter)
        val memberId = split[0].toInt()
        val role = split[1]

        val member = memberRepository.findById(memberId)

        if (member.isEmpty || member.get().getRole() != Role.ADMIN || role != Role.ADMIN.name) {
            throw ForbiddenException()
        }
    }

    private fun getMemberIdByAccessToken(accessToken: String): Int {
        val indexOf = accessToken.indexOf(accessTokenDelimiter)
        return accessToken.substring(0, indexOf).toInt()
    }
}
