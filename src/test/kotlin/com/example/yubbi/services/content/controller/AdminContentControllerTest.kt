package com.example.yubbi.services.content.controller

import com.example.yubbi.common.exception.ErrorCode
import com.example.yubbi.common.utils.ActiveStatus
import com.example.yubbi.common.utils.UploadStatus
import com.example.yubbi.services.content.controller.dto.request.AdminContentCreateRequestDto
import com.example.yubbi.services.content.controller.dto.request.AdminContentUpdateRequestDto
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
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import javax.transaction.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class AdminContentControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("카테고리 ID가 주어지고, get방식으로 리스트를 조회했을 때, 응답이 200 Ok이고  contentList들이 잘 반환되는지 확인하는 테스트")
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
                            "컨텐츠 목록 조회에 사용할 카테고리 아이디"
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
                        fieldWithPath("contents[].priority").description("컨텐츠 우선순위"),
                    )
                )
            )
    }

    @Test
    @DisplayName("존재하지 않는 카테고리 ID가 주어지고, get방식으로 리스트를 조회했을 때, 응답이 404이고 메세지 필드가 존재하는지 확인하는 테스트")
    fun getContentList_givenNotExistCategoryId_whenGetContentList_thenStatus404AndExistMessageField() {
        // given
        val categoryId = 100

        // when
        val perform = mockMvc.perform(
            get("/contents")
                .queryParam("categoryId", categoryId.toString())
                .accept(MediaType.APPLICATION_JSON)
        )

        // then
        perform
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("status").value(ErrorCode.NOT_FOUND_CATEGORY.status))
            .andExpect(jsonPath("message").value(ErrorCode.NOT_FOUND_CATEGORY.message))
            .andDo(
                document(
                    "content-getContentList-notFoundCategory",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())
                )
            )
    }

    @Test
    @DisplayName("accessToken과 카테고리 id가 주어지고, get방식으로 조회했을때, 응답이 200 Ok이고 contents필드에 content 목록이 담겨있는지 확인하는 테스트")
    fun getAdminContentList_givenAccessTokenCategoryId_whenGetAdminContentList_thenStatusOkAndExistContents() {

        // given
        val accessToken = "1_ADMIN"
        val categoryId = 1

        // when
        val perform = mockMvc.perform(
            get("/admin/contents")
                .queryParam("categoryId", categoryId.toString())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
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
            .andExpect(jsonPath("contents[0].activeStatus").value(ActiveStatus.ACTIVE.name))
            .andExpect(jsonPath("contents[0].displayStartDate").isString)
            .andExpect(jsonPath("contents[0].displayEndDate").isString)
            .andExpect(jsonPath("contents[0].priority").isNumber)
            .andExpect(jsonPath("contents[0].uploadStatus").value(UploadStatus.SUCCESS.name))
            .andExpect(jsonPath("contents[0].createdAt").isString)
            .andExpect(jsonPath("contents[0].lastModifiedAt").isString)
            .andExpect(jsonPath("contents[0].lastModifier.memberId").isNumber)
            .andExpect(jsonPath("contents[0].lastModifier.email").isString)
            .andExpect(jsonPath("contents[0].lastModifier.name").isString)
            .andDo(
                document(
                    "content-getContentList-admin",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 컨텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인시 받은 accessToken")
                    ),
                    requestParameters(
                        parameterWithName("categoryId").description(
                            "컨텐츠 목록 조회에 사용할 카테고리 아이디"
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
                        fieldWithPath("contents[].activeStatus").description("컨텐츠 활성 상태 +" + "\n" + "( ACTIVE or IN_ACTIVE )"),
                        fieldWithPath("contents[].displayStartDate").description("컨텐츠 전시 시작 시간"),
                        fieldWithPath("contents[].displayEndDate").description("컨텐츠 전시 종료 시간"),
                        fieldWithPath("contents[].priority").description("컨텐츠 우선순위"),
                        fieldWithPath("contents[].uploadStatus").description("영상 업로드 상태 +" + "\n" + "( UPLOADING or SUCCESS or FAIL )"),
                        fieldWithPath("contents[].createdAt").description("생성된 시간"),
                        fieldWithPath("contents[].lastModifiedAt").description("최종 수정된 시간"),
                        fieldWithPath("contents[].lastModifier").description("최종 수정한 관리자"),
                        fieldWithPath("contents[].lastModifier.memberId").description("괸리자 아이디"),
                        fieldWithPath("contents[].lastModifier.email").description("괸라자 이메일"),
                        fieldWithPath("contents[].lastModifier.name").description("관리자 이름")
                    )
                )
            )
    }

    @Test
    @DisplayName("accessToken과 존재하지 않은 카테고리 id가 주어지고, get방식으로 조회했을때, 응답이 404이고 메세지 필드가 존재하는지 확인하는 테스트")
    fun getAdminContentList_givenAccessTokenNotExistCategoryId_whenGetAdminContentList_thenStatus404AndExistMessageField() {

        // given
        val accessToken = "1_ADMIN"
        val categoryId = 100

        // when
        val perform = mockMvc.perform(
            get("/admin/contents")
                .queryParam("categoryId", categoryId.toString())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        )

        // then
        perform
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("status").value(ErrorCode.NOT_FOUND_CATEGORY.status))
            .andExpect(jsonPath("message").value(ErrorCode.NOT_FOUND_CATEGORY.message))
            .andDo(
                document(
                    "content-getContentList-admin-notFoundCategory",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())
                )
            )
    }

    @Test
    @DisplayName("accessToken과 contentId가 주어지고, 컨텐츠를 get 방식으로 조회했을때, 응답이 200 Ok이고 해당 컨텐츠가 반환되는지 확인하는 테스트")
    fun getAdminContent_givenAccessTokenAndContentId_whenGetAdminContent_thenStatusOkAndExistContent() {
        // given
        val accessToken = "1_ADMIN"
        val contentId = 1

        // when
        val perform = mockMvc.perform(
            get("/admin/contents/{contentId}", contentId)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("contentId").isNumber)
            .andExpect(jsonPath("category.categoryId").isNumber)
            .andExpect(jsonPath("category.title").isString)
            .andExpect(jsonPath("category.description").isString)
            .andExpect(jsonPath("title").isString)
            .andExpect(jsonPath("description").isString)
            .andExpect(jsonPath("imageUrl").isString)
            .andExpect(jsonPath("videoUrl").isString)
            .andExpect(jsonPath("activeStatus").value(ActiveStatus.ACTIVE.name))
            .andExpect(jsonPath("displayStartDate").isString)
            .andExpect(jsonPath("displayEndDate").isString)
            .andExpect(jsonPath("priority").isNumber)
            .andExpect(jsonPath("uploadStatus").value(UploadStatus.SUCCESS.name))
            .andExpect(jsonPath("createdAt").isString)
            .andExpect(jsonPath("lastModifiedAt").isString)
            .andExpect(jsonPath("lastModifier.memberId").isNumber)
            .andExpect(jsonPath("lastModifier.email").isString)
            .andExpect(jsonPath("lastModifier.name").isString)
            .andDo(
                document(
                    "content-getContent-admin",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 컨텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인시 받은 accessToken")
                    ),
                    pathParameters(
                        parameterWithName("contentId").description("조회할 컨텐츠의 아이디")
                    ),
                    responseFields(
                        fieldWithPath("contentId").description("컨텐츠 아이디"),
                        fieldWithPath("category").description("컨텐츠의 카테고리"),
                        fieldWithPath("category.categoryId").description("컨텐츠 카테고리의 아이디"),
                        fieldWithPath("category.title").description("컨텐츠 카테고리의 제목"),
                        fieldWithPath("category.description").description("컨텐츠 카테고리의 상세설명"),
                        fieldWithPath("title").description("컨텐츠 제목"),
                        fieldWithPath("description").description("컨텐츠 상세설명"),
                        fieldWithPath("imageUrl").description("컨텐츠 이미지 URL"),
                        fieldWithPath("videoUrl").description("컨텐츠 동영상 URL"),
                        fieldWithPath("activeStatus").description("컨텐츠 활성 상태 +" + "\n" + "( ACTIVE or IN_ACTIVE )"),
                        fieldWithPath("displayStartDate").description("컨텐츠 전시 시작 시간"),
                        fieldWithPath("displayEndDate").description("컨텐츠 전시 종료 시간"),
                        fieldWithPath("priority").description("컨텐츠 우선순위"),
                        fieldWithPath("uploadStatus").description("영상 업로드 상태 +" + "\n" + "( UPLOADING or SUCCESS or FAIL )"),
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
    @DisplayName("accessToken과 존재하지 않는 contentId가 주어지고, 컨텐츠를 get 방식으로 조회했을때, 응답이 404이고 메세지 필드가 존재하는지 확인하는 테스트")
    fun getAdminContent_givenAccessTokenAndNotExistContentId_whenGetAdminContent_thenStatus404AndExistMessageField() {
        // given
        val accessToken = "1_ADMIN"
        val contentId = 100

        // when
        val perform = mockMvc.perform(
            get("/admin/contents/{contentId}", contentId)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        )

        // then
        perform
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("status").value(ErrorCode.NOT_FOUND_CONTENT.status))
            .andExpect(jsonPath("message").value(ErrorCode.NOT_FOUND_CONTENT.message))
            .andDo(
                document(
                    "content-getContent-admin-notFoundContent",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())
                )
            )
    }

    @Test
    @DisplayName("accessToken과 content 정보가 주어지고, 컨텐츠를 post방식으로 생성했을때, 응답이 200 Ok이고 생성된 contentId가 반환되는지 확인하는 테스트")
    fun createContent_givenAccessTokenAndAdminContentCreateRequestDto_whenPostContent_thenStatusOkAndExistContentId() {
        // given
        val accessToken = "1_ADMIN"
        val adminContentCreateRequestDto = AdminContentCreateRequestDto(
            10,
            1,
            "NewContentTitle",
            "NewContentDescription",
            ActiveStatus.ACTIVE,
            LocalDateTime.now(),
            LocalDateTime.now(),
            3
        )

        // when
        val perform = mockMvc.perform(
            post("/admin/contents")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(objectMapper.writeValueAsString(adminContentCreateRequestDto))
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("contentId").isNumber)
            .andDo(
                document(
                    "content-createContent-admin",
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
                        fieldWithPath("contentId").description("컨텐츠의 ID"),
                        fieldWithPath("categoryId").description("등록할 컨텐츠의 카테고리 아이디"),
                        fieldWithPath("title").description("등록할 컨텐츠의 제목"),
                        fieldWithPath("description").description("등록할 컨텐츠의 상세설명"),
                        fieldWithPath("activeStatus").description("등록할 컨텐츠 활성 상태 +" + "\n" + "( ACTIVE or IN_ACTIVE )"),
                        fieldWithPath("displayStartDate").description("등록할 컨텐츠의 전시 시작시간 +" + "\n" + "( yyyy-MM-dd'T'HH:mm:ss )"),
                        fieldWithPath("displayEndDate").description("등록할 컨텐츠의 전시 시작시간 +" + "\n" + "( yyyy-MM-dd'T'HH:mm:ss )"),
                        fieldWithPath("priority").description("동록할 컨텐츠의 우선순위"),
                    ),
                    responseFields(
                        fieldWithPath("contentId").description("등록된 컨텐츠 아이디")
                    )
                )
            )
    }

    @Test
    @DisplayName("accessToken과 존재하지 않는 contentId가 주어지고, 컨텐츠를 post방식으로 생성했을때, 응답이 404이고 메세지가 존재하는지 확인하는 테스트")
    fun createContent_givenNotExistContentId_whenPostContent_thenStatusNotFoundAndExistMessage() {
        // given
        val accessToken = "1_ADMIN"
        val adminContentCreateRequestDto = AdminContentCreateRequestDto(
            100,
            1,
            "NewContentTitle",
            "NewContentDescription",
            ActiveStatus.ACTIVE,
            LocalDateTime.now(),
            LocalDateTime.now(),
            3
        )

        val perform = mockMvc.perform(
            post("/admin/contents")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(objectMapper.writeValueAsString(adminContentCreateRequestDto))
        )

        perform
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("status").value(ErrorCode.NOT_FOUND_CONTENT.status))
            .andExpect(jsonPath("message").value(ErrorCode.NOT_FOUND_CONTENT.message))
            .andDo(
                document(
                    "content-createContent-admin-notFoundContent",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())
                )
            )
    }

    @Test
    @DisplayName("accessToken과 존재하지않는 categoryId가 주어지고, 컨텐츠를 post방식으로 생성했을때, 응답이 404이고 메세지가 존재하는지 확인하는 테스트")
    fun createContent_givenNotExistCategoryId_whenPostContent_thenStatusNotFoundAndExistMessage() {
        // given
        val accessToken = "1_ADMIN"
        val adminContentCreateRequestDto = AdminContentCreateRequestDto(
            10,
            100,
            "NewContentTitle",
            "NewContentDescription",
            ActiveStatus.ACTIVE,
            LocalDateTime.now(),
            LocalDateTime.now(),
            3
        )

        val perform = mockMvc.perform(
            post("/admin/contents")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(objectMapper.writeValueAsString(adminContentCreateRequestDto))
        )

        perform
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("status").value(ErrorCode.NOT_FOUND_CATEGORY.status))
            .andExpect(jsonPath("message").value(ErrorCode.NOT_FOUND_CATEGORY.message))
            .andDo(
                document(
                    "content-createContent-admin-notFoundCategory",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())
                )
            )
    }

    @Test
    @DisplayName("accessToken과 contentId와 content정보가 주어지고, 컨텐츠를 put방식으로 수정했을때, 응답이 200 Ok이고 수정된 contentId가 반환되는지 확인하는 테스트")
    fun updateContent_givenAccessTokenAndContentIdAndAdminContentUpdateRequestDto_whenPutUpdateContent_thenStatusOkAndExistUpdatedContentId() {
        // given
        val accessToken = "1_ADMIN"
        val contentId = 9
        val adminContentUpdateRequestDto = AdminContentUpdateRequestDto(
            9,
            1,
            "UpdateContentTitle",
            "UpdateContentDescription",
            ActiveStatus.ACTIVE,
            LocalDateTime.now(),
            LocalDateTime.now(),
            1
        )

        // when
        val perform = mockMvc.perform(
            put("/admin/contents/{contentId}", contentId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(objectMapper.writeValueAsString(adminContentUpdateRequestDto))
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("contentId").isNumber)
            .andDo(
                document(
                    "content-updateContent-admin",
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
                        fieldWithPath("contentId").description("컨텐츠의 ID"),
                        fieldWithPath("categoryId").description("등록할 컨텐츠의 카테고리 아이디"),
                        fieldWithPath("title").description("등록할 컨텐츠의 제목"),
                        fieldWithPath("description").description("등록할 컨텐츠의 상세설명"),
                        fieldWithPath("activeStatus").description("등록할 컨텐츠 활성 상태 +" + "\n" + "( ACTIVE or IN_ACTIVE )"),
                        fieldWithPath("displayStartDate").description("등록할 컨텐츠의 전시 시작시간 +" + "\n" + "( yyyy-MM-dd'T'HH:mm:ss )"),
                        fieldWithPath("displayEndDate").description("등록할 컨텐츠의 전시 시작시간 +" + "\n" + "( yyyy-MM-dd'T'HH:mm:ss )"),
                        fieldWithPath("priority").description("동록할 컨텐츠의 우선순위"),
                    ),
                    pathParameters(
                        parameterWithName("contentId").description("컨텐츠의 Id")
                    ),
                    responseFields(
                        fieldWithPath("contentId").description("등록된 컨텐츠 아이디")
                    )
                )
            )
    }

    @Test
    @DisplayName("accessToken과 존재하지 않는 contentId와 content정보가 주어지고, 컨텐츠를 put방식으로 수정했을때, 응답이 404 이고 메세지 필드가 존재하는지 확인하는 테스트")
    fun updateContent_givenAccessTokenAndNotExistContentIdAndAdminContentUpdateRequestDto_whenPutUpdateContent_thenStatus404AndExistMessage() {
        // given
        val accessToken = "1_ADMIN"
        val contentId = 100
        val adminContentUpdateRequestDto = AdminContentUpdateRequestDto(
            100,
            1,
            "UpdateContentTitle",
            "UpdateContentDescription",
            ActiveStatus.ACTIVE,
            LocalDateTime.now(),
            LocalDateTime.now(),
            1
        )

        // when
        val perform = mockMvc.perform(
            put("/admin/contents/{contentId}", contentId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(objectMapper.writeValueAsString(adminContentUpdateRequestDto))
        )

        // then
        perform
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("status").value(ErrorCode.NOT_FOUND_CONTENT.status))
            .andExpect(jsonPath("message").value(ErrorCode.NOT_FOUND_CONTENT.message))
            .andDo(
                document(
                    "content-updateContent-admin-notFoundContent",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())
                )
            )
    }

    @Test
    @DisplayName("accessToken과 존재하지 않는 categorytId와 content정보가 주어지고, 컨텐츠를 put방식으로 수정했을때, 응답이 404 이고 메세지 필드가 존재하는지 확인하는 테스트")
    fun updateContent_givenAccessTokenAndNotExistCategoryIdAndAdminContentUpdateRequestDto_whenPutUpdateContent_thenStatus404AndExistMessage() {
        // given
        val accessToken = "1_ADMIN"
        val contentId = 9
        val adminContentUpdateRequestDto = AdminContentUpdateRequestDto(
            9,
            100,
            "UpdateContentTitle",
            "UpdateContentDescription",
            ActiveStatus.ACTIVE,
            LocalDateTime.now(),
            LocalDateTime.now(),
            1
        )

        // when
        val perform = mockMvc.perform(
            put("/admin/contents/{contentId}", contentId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(objectMapper.writeValueAsString(adminContentUpdateRequestDto))
        )

        // then
        perform
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("status").value(ErrorCode.NOT_FOUND_CATEGORY.status))
            .andExpect(jsonPath("message").value(ErrorCode.NOT_FOUND_CATEGORY.message))
            .andDo(
                document(
                    "content-updateContent-admin-notFoundCategory",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())
                )
            )
    }

    @Test
    @DisplayName("accessToken과 삭제할 contentId가 주어지고, 컨텐츠를 delete 방식으로 삭제했을때, 응답이 200 Ok이고 삭제된 contentId가 반환되는지 확인하는 테스트")
    fun deleteContent_givenAccessTokenAndContentId_whenDeleteContent_thenStatusOkAndExistDeletedContentId() {
        // given
        val accessToken = "1_ADMIN"
        val contentId = 8

        // when
        val perform = mockMvc.perform(
            delete("/admin/contents/{contentId}", contentId)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        )

        // then
        perform
            .andExpect(status().isOk)
            .andExpect(jsonPath("contentId").value(contentId))
            .andDo(
                document(
                    "content-deleteContent-admin",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT)
                            .description("응답받을 콘텐츠 타입 +" + "\n" + MediaType.APPLICATION_JSON),
                        headerWithName(HttpHeaders.AUTHORIZATION)
                            .description("인증 정보 헤더 +" + "\n" + "로그인시 받은 accessToken")
                    ),
                    pathParameters(
                        parameterWithName("contentId").description("삭제할 컨텐츠의 아이디")
                    ),
                    responseFields(
                        fieldWithPath("contentId").description("삭제된 컨텐츠의 아이디")
                    )
                )
            )
    }

    @Test
    @DisplayName("accessToken과 존재하지 않는 contentId가 주어지고, 컨텐츠를 delete 방식으로 삭제했을때, 응답이 404이고 메세지 필드가 존재하는지 확인하는 테스트")
    fun deleteContent_givenAccessTokenAndNotExistContentId_whenDeleteContent_thenStatus404AndExistMessageField() {
        // given
        val accessToken = "1_ADMIN"
        val contentId = 100

        // when
        val perform = mockMvc.perform(
            delete("/admin/contents/{contentId}", contentId)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        )

        // then
        perform
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("status").value(ErrorCode.NOT_FOUND_CONTENT.status))
            .andExpect(jsonPath("message").value(ErrorCode.NOT_FOUND_CONTENT.message))
            .andDo(
                document(
                    "content-deleteContent-admin-notFoundContent",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())
                )
            )
    }
}
