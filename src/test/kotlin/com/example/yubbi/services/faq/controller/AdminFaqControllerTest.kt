package com.example.yubbi.services.faq.controller

import com.example.yubbi.common.exception.ErrorCode
import com.example.yubbi.services.faq.controller.dto.request.AdminFaqCreateRequestDto
import com.example.yubbi.services.faq.controller.dto.request.AdminFaqUpdateRequestDto
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class AdminFaqControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("페이지.사이즈.단어.액세스토큰이 주어지고, GET방식으로 FAQ리스트를 조회했을 때, 응답이 200 Ok이고 AdminFaqListResponseDto의 필드가 존재하는지 확인하는 테스트")
    fun getFaqList_givenPageAndSizeAndWordAndToken_whenGetFaqList_thenStatusOkAndExistAdminFaqListResponseDto() {
        // given
        val accessToken = "1_ADMIN"
        val queryParam = LinkedMultiValueMap<String, String>()
        queryParam.add("page", "1")
        queryParam.add("size", "5")
        queryParam.add("word", "hello")

        // when
        val perform = mockMvc.perform(
            get("/admin/faqs")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .params(queryParam)
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("totalPage").isNumber)
            .andExpect(jsonPath("currentPage").isNumber)
            .andExpect(jsonPath("faqs").isArray)
            .andExpect(jsonPath("faqs[0].faqId").isNumber)
            .andExpect(jsonPath("faqs[0].question").isString)
            .andExpect(jsonPath("faqs[0].answer").isString)
            .andExpect(jsonPath("faqs[0].createdAt").isString)
            .andExpect(jsonPath("faqs[0].lastModifiedAt").isString)
            .andExpect(jsonPath("faqs[0].lastModifier.memberId").isNumber)
            .andExpect(jsonPath("faqs[0].lastModifier.email").isString)
            .andExpect(jsonPath("faqs[0].lastModifier.name").isString)
            .andDo(
                document(
                    "faq-getFaqList-admin",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인 시 받은 accessToken")

                    ),
                    requestParameters(
                        parameterWithName("page").description("페이지 넘버"),
                        parameterWithName("size").description("페이지 당 FAQ갯수"),
                        parameterWithName("word").description("포함하는 단어")
                    ),
                    responseFields(
                        fieldWithPath("totalPage").description("Word를 포함하고 있는 FAQ페이지 수"),
                        fieldWithPath("currentPage").description("현재 선택한 페이지 수"),
                        fieldWithPath("faqs[0].faqId").description("FAQ의 Id"),
                        fieldWithPath("faqs[0].question").description("FAQ의 질문"),
                        fieldWithPath("faqs[0].answer").description("FAQ의 질문에 대한 답변"),
                        fieldWithPath("faqs[0].createdAt").description("FAQ 작성되어진 시간"),
                        fieldWithPath("faqs[0].lastModifiedAt").description("최근 FAQ가 수정된 시간"),
                        fieldWithPath("faqs[0].lastModifier.memberId").description("수정자 Id"),
                        fieldWithPath("faqs[0].lastModifier.email").description("수정자 이메일"),
                        fieldWithPath("faqs[0].lastModifier.name").description("수정자 이름")
                    )
                )
            )
    }

    @Test
    @DisplayName("FaqId와 토큰이 주어지고, GET방식으로 FAQ를 조회했을 때, 응답이 200 Ok이고 AdminFaqResponseDto의 필드가 존재하는지 확인하는 테스트")
    fun getFaq_givenFaqId_whenGetFaq_thenStatusOkAndExistAdminFaqResponseDto() {
        // given
        val id = 1
        val accessToken = "1_ADMIN"
        // when
        val perform = mockMvc.perform(
            get("/admin/faqs/{faqId}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("faqId").isNumber)
            .andExpect(jsonPath("question").isString)
            .andExpect(jsonPath("answer").isString)
            .andExpect(jsonPath("createdAt").isString)
            .andExpect(jsonPath("lastModifiedAt").isString)
            .andExpect(jsonPath("lastModifier.memberId").isNumber)
            .andExpect(jsonPath("lastModifier.email").isString)
            .andExpect(jsonPath("lastModifier.name").isString)
            .andDo(
                document(
                    "faq-getFaq-admin",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인 시 받은 accessToken")
                    ),
                    pathParameters(
                        parameterWithName("faqId").description("FAQ의 Id")
                    ),
                    responseFields(
                        fieldWithPath("faqId").description("FAQ의 Id"),
                        fieldWithPath("question").description("FAQ의 질문"),
                        fieldWithPath("answer").description("FAQ의 질문에 대한 답변"),
                        fieldWithPath("createdAt").description("FAQ 작성되어진 시간"),
                        fieldWithPath("lastModifiedAt").description("최근 FAQ가 수정된 시간"),
                        fieldWithPath("lastModifier.memberId").description("수정자 Id"),
                        fieldWithPath("lastModifier.email").description("수정자 이메일"),
                        fieldWithPath("lastModifier.name").description("수정자 이름")
                    )
                )
            )
    }

    @Test
    @DisplayName("AdminCreateRequestDto가 주어지고, POST방식으로 FAQ를 생성했을 때, 응답이 200 Ok이고 AdminCreateResponseDto의 필드가 존재하는지 확인하는 테스트")
    fun createFaq_givenAdminCreateRequestDto_whenPostCreateFaq_thenStatusOkAndExistAdminCreateResponseDto() {
        // given
        val request = AdminFaqCreateRequestDto("question", "answer")
        val accessToken = "1_ADMIN"

        // when
        val perform = mockMvc.perform(
            post("/admin/faqs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(objectMapper.writeValueAsString(request))
        )

        // then
        perform.andExpect(status().isOk)
            .andExpect(jsonPath("faqId").isNumber)
            .andDo(
                document(
                    "faq-createFaq-admin",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE)
                            .description("요청 메시지의 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인 시 받은 accessToken")
                    ),
                    requestFields(
                        fieldWithPath("question").description("등록할 FAQ의 질문 (필수)"),
                        fieldWithPath("answer").description("등록할 FAQ의 답변 (필수)")
                    ),
                    responseFields(
                        fieldWithPath("faqId").description("등록되어진 FAQ의 Id")
                    )
                )
            )
    }

    @Test
    @DisplayName("잘못된(필수값 누락) AdminCreateRequestDto가 주어지고, POST방식으로 FAQ를 생성했을 때, 응답이 400 Bad Request인지 확인하는 테스트")
    fun createFaq_givenWrongAdminCreateRequestDto_whenPostCreateFaq_thenStatusBadRequest() {
        // given
        val wrongAdminFaqCreateRequestDto = AdminFaqCreateRequestDto("", "answer")
        val accessToken = "1_ADMIN"

        // when
        val perform = mockMvc.perform(
            post("/admin/faqs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(objectMapper.writeValueAsString(wrongAdminFaqCreateRequestDto))
        )

        // then
        perform.andExpect(status().isBadRequest)
            .andExpect(jsonPath("status").value(ErrorCode.BAD_REQUEST.status))
            .andExpect(jsonPath("message").value(ErrorCode.BAD_REQUEST.message))
            .andDo(
                document(
                    "faq-createFaq-admin-badRequest",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())
                )
            )
    }

    @Test
    @DisplayName("AdminUpdateRequestDto가 주어지고, PUT방식으로 FAQ를 수정했을 때, 응답이 200 Ok이고 AdminUpdateResponseDto의 필드가 존재하는지 확인하는 테스트")
    fun updateFaqAdmin_givenFaqIdAndAdminUpdateRequestDto_whenPutUpdateFaq_thenStatusOkAndExistAdminUpdateResponseDto() {
        // given
        val request = AdminFaqUpdateRequestDto("question", "answer")
        val id = 1
        val accessToken = "1_ADMIN"

        // when
        val perform = mockMvc.perform(
            put("/admin/faqs/{faqId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(objectMapper.writeValueAsString(request))
        )

        // then
        perform.andExpect(status().isOk)
            .andExpect(jsonPath("faqId").isNumber)
            .andDo(
                document(
                    "faq-update-admin",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE)
                            .description("요청 메시지의 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인 시 받은 accessToken")
                    ),
                    requestFields(
                        fieldWithPath("question").description("수정할 FAQ 질문"),
                        fieldWithPath("answer").description("수정할 FAQ 질문에 대한 답변")
                    ),
                    pathParameters(
                        parameterWithName("faqId").description("FAQ의 Id")
                    ),
                    responseFields(
                        fieldWithPath("faqId").description("수정되어진 FAQ의 Id")
                    )
                )
            )
    }

    @Test
    @DisplayName("FaqId가 주어지고, DELETE방식으로 FAQ를 삭제했을 때, 응답이 200 Ok이고 AdminDeleteResponseDto의 필드가 존재하는지 확인하는 테스트")
    fun deleteFaqAdmin_givenFaqId_whenDeleteDeleteFaq_thenStatusOkAndExistAdminDeleteResponseDto() {
        // given
        val id = 1
        val accessToken = "1_ADMIN"

        // when
        val perform = mockMvc.perform(
            delete("/admin/faqs/{faqId}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        )

        // then
        perform.andExpect(status().isOk)
            .andExpect(jsonPath("faqId").isNumber)
            .andDo(
                document(
                    "faq-delete-admin",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인 시 받은 accessToken")
                    ),
                    pathParameters(
                        parameterWithName("faqId").description("FAQ의 Id")
                    ),
                    responseFields(
                        fieldWithPath("faqId").description("삭제된 FAQ의 Id")
                    )
                )
            )
    }
}
