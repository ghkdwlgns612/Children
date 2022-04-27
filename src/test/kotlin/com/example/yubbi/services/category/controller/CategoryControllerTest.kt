package com.example.yubbi.services.category.controller

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
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@AutoConfigureRestDocs
class CategoryControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("카테고리 목록 get 방식으로 조회했을때, 응답이 200 Ok이고 categories필드에 category 목록이 담겨있는 확인하는 테스트")
    fun getCategoryList_whenGetCategoryList_thenStatusOkAndExistCategories() {

        // when
        val perform = mockMvc.perform(
            get("/categories")
                .accept(MediaType.APPLICATION_JSON)
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("categories").isArray)
            .andExpect(jsonPath("categories[0].categoryId").isNumber)
            .andExpect(jsonPath("categories[0].title").isString)
            .andExpect(jsonPath("categories[0].description").isString)
            .andExpect(jsonPath("categories[0].priority").isNumber)
            .andDo(
                document(
                    "category-getCategoryList",
                    preprocessRequest(Preprocessors.prettyPrint()),
                    preprocessResponse(Preprocessors.prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON)
                    ),
                    responseFields(
                        fieldWithPath("categories").description("카테고리 목록"),
                        fieldWithPath("categories[].categoryId").description("카테고리 아이디"),
                        fieldWithPath("categories[].title").description("카테고리 제목"),
                        fieldWithPath("categories[].description").description("카테고리 상세설명"),
                        fieldWithPath("categories[].priority").description("카테고리 우선순위")
                    )
                )
            )
    }
}
