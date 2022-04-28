package com.example.yubbi.services.content.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@AutoConfigureRestDocs
class ContentControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("카테고리 id가 주어지고, get방식으로 조회했을때, 응답이 200 Ok이고 contents필드에 content 목록이 담겨있는지 확인하는 테스트")
    fun getContentList_givenCategoryId_whenGetContentList_thenStatusOkAndExistContents() {

        // given
        val categoryId = 1

        // when
        val perform = mockMvc.perform(
            get("/contents")
                .queryParam("categoryId", categoryId.toString())
                .accept(MediaType.APPLICATION_JSON)
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("contents").isArray)
            .andExpect(jsonPath("contents[0].contentId").isNumber)
            .andExpect(jsonPath("contents[0].category.categoryId").isNumber)
            .andExpect(jsonPath("contents[0].category.title").isString)
            .andExpect(jsonPath("contents[0].category.description").isString)
            .andExpect(jsonPath("contents[0].title").isString)
            .andExpect(jsonPath("contents[0].description").isString)
            .andExpect(jsonPath("contents[0].imageUrl").isString)
            .andExpect(jsonPath("contents[0].videoUrl").isString)
            .andExpect(jsonPath("contents[0].priority").isNumber)
            .andDo(
                document(
                    "content-getContentList",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 컨텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON)
                    ),
                    requestParameters(
                        parameterWithName("categoryId").description(
                            "컨텐츠 목록 조회에 사용할 카테고리 아이디 (선택) +" + "\n" + "값이 없으면 모든 컨텐츠 목록을 조회한다."
                        )
                    ),
                    responseFields(
                        fieldWithPath("contents").description("컨텐츠 목록"),
                        fieldWithPath("contents[].contentId").description("컨텐츠 아이디"),
                        fieldWithPath("contents[].category").description("컨텐츠의 카테고리"),
                        fieldWithPath("contents[].category.categoryId").description("컨텐츠 카테고리의 아이디"),
                        fieldWithPath("contents[].category.title").description("컨텐츠 카테고리의 제목"),
                        fieldWithPath("contents[].category.description").description("컨텐츠 카테고리의 상세설명"),
                        fieldWithPath("contents[].title").description("컨텐츠 제목"),
                        fieldWithPath("contents[].description").description("컨텐츠 상세설명"),
                        fieldWithPath("contents[].imageUrl").description("컨텐츠 이미지 URL"),
                        fieldWithPath("contents[].videoUrl").description("컨텐츠 동영상 URL"),
                        fieldWithPath("contents[].priority").description("컨텐츠 우선순위")
                    )
                )
            )
    }
}
