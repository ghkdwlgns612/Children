package com.example.yubbi.services.member.controller

import com.example.yubbi.common.exception.ErrorCode
import com.example.yubbi.services.member.controller.dto.request.LoginRequestDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class MemberControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("로그인 요청데이터가 주어지고, post방식으로 로그인요청을 수행했을 때, 응답이 200 Ok이고 accessToken필드가 있는지 확인하는 테스트")
    fun login_givenLoginRequestDto_whenPostLogin_thenStatusOkAndExistAccessToken() {

        // given
        val loginRequestDto = LoginRequestDto("ghkdwlgns612@naver.com", "1234")

        // when
        val perform = mockMvc.perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto))
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("accessToken").exists())
            .andDo(
                document(
                    "member-login",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE)
                            .description("요청 메시지의 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON)
                    ),
                    requestFields(
                        fieldWithPath("email").description("인증을 위한 이메일 (필수)"),
                        fieldWithPath("password").description("인증을 위한 비밀번호 (필수)")
                    ),
                    responseFields(
                        fieldWithPath("accessToken").description("인증이 필요한 리소스 요청에 사용가능한 access 토큰")
                    )
                )
            )
    }

    @Test
    @DisplayName("DB존재하지 않은 이메일을 요청이 주어지고, post방식으로 로그인을 했을 때, Status와 Message필드가 일치하는지 확인하는 테스트")
    fun notFoundMemberException_givenNotExistEmailLoginRequestDto_whenPostLogin_thenEqualStatusAndMessageField() {
        // given
        val loginRequestDto = LoginRequestDto("lg@naver.com", "1234")

        // when
        val perform = mockMvc.perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto))
        )

        // then
        perform.andExpect(status().is4xxClientError)
            .andExpect(jsonPath("status").value(404))
            .andExpect(jsonPath("message").value("존재하지 않은 이메일입니다."))
            .andDo(
                document(
                    "member-login-notFoundMember",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                )
            )
    }

    @Test
    @DisplayName("잘못된 비밀번호가 주어지고 , post방식으로 로그인을 했을 때, Status와 Message필드가 일치하는지 확인하는 테스트")
    fun notMatchPasswordException_givenNotMatchPasswordLoginRequestDto_whenPostLogin_thenEqualStatusAndMessageField() {
        // given
        val loginRequestDto = LoginRequestDto("ghkdwlgns612@naver.com", "12345")

        // when
        val perform = mockMvc.perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto))
        )

        // then
        perform.andExpect(status().is4xxClientError)
            .andExpect(jsonPath("status").value(404))
            .andExpect(jsonPath("message").value("비밀번호가 일치하지 않습니다."))
            .andDo(
                document(
                    "member-me-notMatchPassword",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                )
            )
    }

    @Test
    @DisplayName("accessToken이 주어지고, get방식으로 내정보조회를 했을때, status는 200 Ok이고, 회원이 정보가 반환되는지 확인하는 테스트")
    fun getMyInfo_givenAccessToken_whenGetMe_thenStatus200OkAndMemberInfo() {
        // given
        val accessToken = "1_ADMIN"

        // when
        val perform = mockMvc.perform(
            get("/members/me")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        )

        // then
        perform.andExpect(status().isOk)
            .andExpect(jsonPath("email").value("intern@lguplus.co.kr"))
            .andExpect(jsonPath("name").value("intern"))
            .andExpect(jsonPath("role").value("ADMIN"))
            .andDo(
                document(
                    "member-me",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인시 받은 accessToken")
                    ),
                    responseFields(
                        fieldWithPath("email").description("회원의 이메일"),
                        fieldWithPath("name").description("회원의 이름"),
                        fieldWithPath("role").description("회원의 역할 +" + "\n" + "( ADMIN or MEMBER )"),
                    )
                )
            )
    }

    @Test
    @DisplayName("잘못된 accessToken이 주어지고, get방식으로 내정보조회를 했을때, status는 401인지 확인하는 테스트")
    fun getMyInfo_givenWrongAccessToken_whenGetMe_thenStatus401() {
        // given
        val accessToken = "1_ADM"

        // when
        val perform = mockMvc.perform(
            get("/members/me")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        )

        // then
        perform.andExpect(status().isUnauthorized)
            .andExpect(jsonPath("status").value(ErrorCode.UNAUTHORIZED.status))
            .andExpect(jsonPath("message").value(ErrorCode.UNAUTHORIZED.message))
            .andDo(
                document(
                    "member-me-wrongAccessToken",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                )
            )
    }

    @Test
    @DisplayName("accessToken(존재하지 않는 회원)이 주어지고, get방식으로 내정보조회를 했을때, status는 404인지 확인하는 테스트")
    fun getMyInfo_givenAccessTokenWithoutMember_whenGetMe_thenStatus404() {
        // given
        val accessToken = "100_ADMIN"

        // when
        val perform = mockMvc.perform(
            get("/members/me")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        )

        // then
        perform.andExpect(status().isNotFound)
            .andExpect(jsonPath("status").value(ErrorCode.NOT_FOUND_MEMBER.status))
            .andExpect(jsonPath("message").value(ErrorCode.NOT_FOUND_MEMBER.message))
            .andDo(
                document(
                    "member-me-notFoundMember",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                )
            )
    }
}
