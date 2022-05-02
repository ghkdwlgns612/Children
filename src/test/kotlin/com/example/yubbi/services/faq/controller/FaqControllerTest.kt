package com.example.yubbi.services.faq.controller

import com.example.yubbi.common.exception.ErrorCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class FaqControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("페이지.사이즈.단어가 주어지고, GET방식으로 FAQ리스트를 조회했을 때, 응답이 200 Ok이고 FaqListResponseDto의 필드가 존재하는지 확인하는 테스트")
    fun getFaqList_givenPageAndSizeAndWord_whenGetFaqList_thenStatusOkAndExistFaqListResponseDto() {
        // given
        val queryParam = LinkedMultiValueMap<String, String>()
        queryParam.add("page", "1")
        queryParam.add("size", "3")
        queryParam.add("word", "hello")

        // when
        val perform = mockMvc.perform(
            get("/faqs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .params(queryParam)
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("totalPage").isNumber)
            .andExpect(jsonPath("currentPage").isNumber)
            .andExpect(jsonPath("faqs").isArray)
            .andDo(
                document(
                    "faq-getFaqList",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    HeaderDocumentation.requestHeaders(
                        HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE)
                            .description("요청 메시지의 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        HeaderDocumentation.headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON)
                    ),
                    requestParameters(
                        parameterWithName("page").description("페이지 넘버 (선택) +" + "\n" + "(기본값 1,  1이상의 숫자)"),
                        parameterWithName("size").description("페이지 당 FAQ갯수 (선택) +" + "\n" + "(기본값 10,  1이상의 숫자)"),
                        parameterWithName("word").description("검색할 단어 (선택) +" + "\n" + "(질문 또는 답변에 해당 단어가 있으면 검색된다.)")
                    ),
                    responseFields(
                        fieldWithPath("totalPage").description("전체 페이지 수"),
                        fieldWithPath("currentPage").description("현재 페이지"),
                        fieldWithPath("faqs[].faqId").description("FAQ의 Id"),
                        fieldWithPath("faqs[].question").description("FAQ의 질문"),
                        fieldWithPath("faqs[].answer").description("FAQ의 질문에 대한 답변"),
                    )
                )
            )
    }

    @Test
    @DisplayName("잘못된 페이지.사이즈.단어가 주어지고, GET방식으로 FAQ리스트를 조회했을 때, 응답이 400 BadRequest인지 확인하는 테스트")
    fun getFaqList_givenWrongPageAndSizeAndWord_whenGetFaqList_thenStatusBadRequest() {
        // given
        val queryParam = LinkedMultiValueMap<String, String>()
        queryParam.add("page", "-1")
        queryParam.add("size", "3")
        queryParam.add("word", "hello")

        // when
        val perform = mockMvc.perform(
            get("/faqs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .params(queryParam)
        )

        // then
        perform
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("status").value(ErrorCode.BAD_REQUEST.status))
            .andExpect(jsonPath("message").value(ErrorCode.BAD_REQUEST.message))
            .andDo(
                document(
                    "faq-getFaqList-badRequest",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())
                )
            )
    }
}
