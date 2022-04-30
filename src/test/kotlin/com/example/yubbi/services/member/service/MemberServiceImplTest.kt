package com.example.yubbi.services.member.service

import com.example.yubbi.common.exception.custom.ForbiddenException
import com.example.yubbi.common.exception.custom.UnAuthorizedException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    lateinit var memberService: MemberService

    @Test
    @DisplayName("유효한 accessToken이 주어지고, getAdminMemberIdByAccessToken함수를 호출했을때, accessMemberId를 반환하는지 확인하는 테스트")
    fun getAdminMemberIdByAccessToken_givenValidAccessToken_whenCallFunction_thenReturnAdminMemberId() {
        // given
        val validAccessToken = "1_ADMIN"

        // when
        val adminMember = memberService.getAdminMemberByAccessToken(validAccessToken)

        // then
        assertThat(adminMember.getMemberId()).isEqualTo(1)
    }

    @Test
    @DisplayName("관리자 권한이 없는 accessToken이 주어지고, getAdminMemberIdByAccessToken함수를 호출했을때, ForbiddenException 예외가 발생하는지 확인하는 테스트")
    fun getAdminMemberIdByAccessToken_givenNotAdminAccessTokens_whenCallFunction_thenThrowForbiddenException() {

        // given
        val notAdminAccessToken = "3_MEMBER"

        // when & then
        assertThrows(ForbiddenException::class.java) {
            memberService.getAdminMemberByAccessToken(notAdminAccessToken)
        }
    }

    @ParameterizedTest(name = "잘못된 accessToken({0})이 주어지고, getAdminMemberIdByAccessToken함수를 호출했을때, UnAuthorizedException 예외가 발생하는지 확인하는 테스트")
    @NullAndEmptySource
    @ValueSource(strings = ["A_ADMIN", "1ADMIN", "1_WRONG", "WRONG"])
    fun getAdminMemberIdByAccessToken_givenInValidAccessTokens_whenCallFunction_thenThrowUnAuthorizedException(inValidAccessToken: String?) {

        // when & then
        assertThrows(UnAuthorizedException::class.java) {
            memberService.getAdminMemberByAccessToken(inValidAccessToken)
        }
    }
}
