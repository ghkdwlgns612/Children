package com.example.yubbi.services.category.controller

import com.example.yubbi.common.utils.ActiveStatus
import com.example.yubbi.services.category.controller.dto.request.AdminCategoryCreateRequestDto
import com.example.yubbi.services.category.controller.dto.request.AdminCategoryUpdateRequestDto
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class AdminCategoryControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("accessToken이 주어지고, 카테고리목록을 get 방식으로 조회했을때, 응답이 200 Ok이고 categories필드에 category 목록이 담겨있는 확인하는 테스트")
    fun getCategoryList_givenAccessToken_whenGetCategoryList_thenStatusOkAndExistCategories() {

        // given
        val accessToken = "1_ADMIN"

        // when
        val perform = mockMvc.perform(
            get("/admin/categories")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("categories").isArray)
            .andExpect(jsonPath("categories[0].categoryId").isNumber)
            .andExpect(jsonPath("categories[0].title").isString)
            .andExpect(jsonPath("categories[0].description").isString)
            .andExpect(jsonPath("categories[0].activeStatus").value(ActiveStatus.ACTIVE.name))
            .andExpect(jsonPath("categories[0].priority").isNumber)
            .andExpect(jsonPath("categories[0].createdAt").isString)
            .andExpect(jsonPath("categories[0].lastModifiedAt").isString)
            .andExpect(jsonPath("categories[0].lastModifier.memberId").isNumber)
            .andExpect(jsonPath("categories[0].lastModifier.email").isString)
            .andExpect(jsonPath("categories[0].lastModifier.name").isString)
            .andDo(
                document(
                    "category-getCategoryList-admin",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인시 받은 accessToken")
                    ),
                    responseFields(
                        fieldWithPath("categories").description("카테고리 목록"),
                        fieldWithPath("categories[].categoryId").description("카테고리 아이디"),
                        fieldWithPath("categories[].title").description("카테고리 제목"),
                        fieldWithPath("categories[].description").description("카테고리 상세설명"),
                        fieldWithPath("categories[].activeStatus").description("카테고리 활성 상태 +" + "\n" + "( ACTIVE or IN_ACTIVE )"),
                        fieldWithPath("categories[].priority").description("카테고리 우선순위"),
                        fieldWithPath("categories[].createdAt").description("생성된 시간"),
                        fieldWithPath("categories[].lastModifiedAt").description("최종 수정된 시간"),
                        fieldWithPath("categories[].lastModifier").description("최종 수정한 관리자"),
                        fieldWithPath("categories[].lastModifier.memberId").description("괸리자 아이디"),
                        fieldWithPath("categories[].lastModifier.email").description("괸라자 이메일"),
                        fieldWithPath("categories[].lastModifier.name").description("관리자 이름")
                    )
                )
            )
    }

    @Test
    @DisplayName("accessToken과 categoryId가 주어지고, 카테고리를 get 방식으로 조회했을때, 응답이 200 Ok이고 해당 category가 반환되는지 확인하는 테스트")
    fun getCategory_givenAccessTokenAndCategoryId_whenGetCategory_thenStatusOkAndExistCategory() {
        // given
        val accessToken = "1_ADMIN"
        val categoryId = 1

        // when
        val perform = mockMvc.perform(
            get("/admin/categories/{categoryId}", categoryId)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("categoryId").isNumber)
            .andExpect(jsonPath("title").isString)
            .andExpect(jsonPath("description").isString)
            .andExpect(jsonPath("activeStatus").value(ActiveStatus.ACTIVE.name))
            .andExpect(jsonPath("priority").isNumber)
            .andExpect(jsonPath("createdAt").isString)
            .andExpect(jsonPath("lastModifiedAt").isString)
            .andExpect(jsonPath("lastModifier.memberId").isNumber)
            .andExpect(jsonPath("lastModifier.email").isString)
            .andExpect(jsonPath("lastModifier.name").isString)
            .andDo(
                document(
                    "category-getCategory-admin",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인시 받은 accessToken")
                    ),
                    pathParameters(
                        parameterWithName("categoryId").description("조회할 카테고리의 아이디")
                    ),
                    responseFields(
                        fieldWithPath("categoryId").description("카테고리 아이디"),
                        fieldWithPath("title").description("카테고리 제목"),
                        fieldWithPath("description").description("카테고리 상세설명"),
                        fieldWithPath("activeStatus").description("카테고리 활성 상태 +" + "\n" + "( ACTIVE or IN_ACTIVE )"),
                        fieldWithPath("priority").description("카테고리 우선순위"),
                        fieldWithPath("createdAt").description("생성된 시간"),
                        fieldWithPath("lastModifiedAt").description("최종 수정된 시간"),
                        fieldWithPath("lastModifier").description("최종 수정한 관리자"),
                        fieldWithPath("lastModifier.memberId").description("괸리자 아이디"),
                        fieldWithPath("lastModifier.email").description("괸라자 이메일"),
                        fieldWithPath("lastModifier.name").description("관리자 이름")
                    )
                )
            )
    }

    @Test
    @DisplayName("accessToken과 category 정보가 주어지고, 카테고리를 post 방식으로 생성했을때, 응답이 200 Ok이고 생성된 categoryId가 반환되는지 확인하는 테스트")
    fun createCategory_givenAccessTokenAndAdminCategoryCreateRequestDto_whenPostCategory_thenStatusOkAndExistCreatedCategoryId() {
        // given
        val accessToken = "1_ADMIN"
        val adminCategoryCreateRequestDto = AdminCategoryCreateRequestDto(
            "new 카테고리",
            "new 카테고리 상세설명",
            ActiveStatus.ACTIVE,
            5
        )

        // when
        val perform = mockMvc.perform(
            post("/admin/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(objectMapper.writeValueAsString(adminCategoryCreateRequestDto))
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("categoryId").isNumber)
            .andDo(
                document(
                    "category-createCategory-admin",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE)
                            .description("요청 메시지의 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인시 받은 accessToken")
                    ),
                    requestFields(
                        fieldWithPath("title").description("등록할 카테고리의 제목"),
                        fieldWithPath("description").description("등록할 카테고리의 상세설명"),
                        fieldWithPath("activeStatus").description("등록할 카테고리의 활성 상태 +" + "\n" + "( ACTIVE or IN_ACTIVE )"),
                        fieldWithPath("priority").description("등록할 카테고리의 우선순위"),
                    ),
                    responseFields(
                        fieldWithPath("categoryId").description("등록된 카테고리 아이디")
                    )
                )
            )
    }

    @Test
    @DisplayName("accessToken과 업데이트할 categoryId와 category 정보가 주어지고, 카테고리를 put 방식으로 업데이트했을때, 응답이 200 Ok이고 업데이트된 categoryId가 반환되는지 확인하는 테스트")
    fun updateCategory_givenAccessTokenAndCategoryIdAndAdminCategoryUpdateRequestDto_whenPutCategory_thenStatusOkAndExistUpdatedCategoryId() {
        // given
        val accessToken = "1_ADMIN"
        val categoryId = 1
        val adminCategoryUpdateRequestDto = AdminCategoryUpdateRequestDto(
            "update 카테고리",
            "update 카테고리 상세설명",
            ActiveStatus.IN_ACTIVE,
            3
        )

        // when
        val perform = mockMvc.perform(
            put("/admin/categories/{categoryId}", categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(objectMapper.writeValueAsString(adminCategoryUpdateRequestDto))
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("categoryId").value(categoryId))
            .andDo(
                document(
                    "category-updateCategory-admin",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE)
                            .description("요청 메시지의 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인시 받은 accessToken")
                    ),
                    pathParameters(
                        parameterWithName("categoryId").description("수정할 카테고리의 아이디")
                    ),
                    requestFields(
                        fieldWithPath("title").description("수정할 카테고리의 제목"),
                        fieldWithPath("description").description("수정할 카테고리의 상세설명"),
                        fieldWithPath("activeStatus").description("수정할 카테고리의 활성 상태 +" + "\n" + "( ACTIVE or IN_ACTIVE )"),
                        fieldWithPath("priority").description("수정할 카테고리의 우선순위"),
                    ),
                    responseFields(
                        fieldWithPath("categoryId").description("수정된 카테고리 아이디")
                    )
                )
            )
    }

    @Test
    @DisplayName("accessToken과 삭제할 categoryId가 주어지고, 카테고리를 delete 방식으로 삭제했을때, 응답이 200 Ok이고 삭제된 categoryId가 반환되는지 확인하는 테스트")
    fun deleteCategory_givenAccessTokenAndCategoryId_whenDeleteCategory_thenStatusOkAndExistDeletedCategoryId() {
        // given
        val accessToken = "1_ADMIN"
        val categoryId = 1

        // when
        val perform = mockMvc.perform(
            delete("/admin/categories/{categoryId}", categoryId)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("categoryId").value(categoryId))
            .andDo(
                document(
                    "category-deleteCategory-admin",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인시 받은 accessToken")
                    ),
                    pathParameters(
                        parameterWithName("categoryId").description("삭제할 카테고리의 아이디")
                    ),
                    responseFields(
                        fieldWithPath("categoryId").description("삭제된 카테고리 아이디")
                    )
                )
            )
    }
}
