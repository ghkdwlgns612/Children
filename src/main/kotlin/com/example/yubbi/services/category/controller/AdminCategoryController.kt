package com.example.yubbi.services.category.controller

import com.example.yubbi.common.utils.ActiveStatus
import com.example.yubbi.services.category.controller.dto.request.AdminCategoryCreateRequestDto
import com.example.yubbi.services.category.controller.dto.request.AdminCategoryUpdateRequestDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryCreateResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryDeleteResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryListOfOneResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryListResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryModifierResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryUpdateResponseDto
import com.example.yubbi.services.category.service.CategoryService
import com.example.yubbi.services.member.service.MemberService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/admin/categories")
class AdminCategoryController(
    private val categoryService: CategoryService,
    private val memberService: MemberService
) {

    // TODO : 구현 필요
    @GetMapping
    fun getCategoryList(): ResponseEntity<AdminCategoryListResponseDto> {

        val categories = listOf(
            AdminCategoryListOfOneResponseDto(
                1,
                "책읽는TV",
                "책읽는TV 상세설명",
                ActiveStatus.ACTIVE,
                2,
                LocalDateTime.now(),
                LocalDateTime.now(),
                AdminCategoryModifierResponseDto(1, "admin@email.com", "admin")
            ),
            AdminCategoryListOfOneResponseDto(
                2,
                "영어유치원",
                "영어유치원 상세설명",
                ActiveStatus.ACTIVE,
                1,
                LocalDateTime.now(),
                LocalDateTime.now(),
                AdminCategoryModifierResponseDto(1, "admin@email.com", "admin")
            ),
            AdminCategoryListOfOneResponseDto(
                3,
                "누리학습",
                "누리학습 상세설명",
                ActiveStatus.IN_ACTIVE,
                3,
                LocalDateTime.now(),
                LocalDateTime.now(),
                AdminCategoryModifierResponseDto(1, "admin@email.com", "admin")
            )
        )

        return ResponseEntity.ok().body(AdminCategoryListResponseDto(categories))
    }

    // TODO : 구현 필요
    @GetMapping("/{categoryId}")
    fun getCategory(@PathVariable categoryId: Int): ResponseEntity<AdminCategoryResponseDto> {

        val category = AdminCategoryResponseDto(
            1,
            "책읽는TV",
            "책읽는TV 상세설명",
            ActiveStatus.ACTIVE,
            2,
            LocalDateTime.now(),
            LocalDateTime.now(),
            AdminCategoryModifierResponseDto(1, "admin@email.com", "admin")
        )

        return ResponseEntity.ok().body(category)
    }

    @PostMapping
    fun createCategory(
        @RequestHeader(HttpHeaders.AUTHORIZATION) accessToken: String?,
        @Validated @RequestBody adminCategoryCreateRequestDto: AdminCategoryCreateRequestDto
    ): ResponseEntity<AdminCategoryCreateResponseDto> {

        val adminMember = memberService.getAdminMemberByAccessToken(accessToken)

        val adminCategoryCreateResponseDto = categoryService.createCategory(adminCategoryCreateRequestDto, adminMember)
        return ResponseEntity.ok().body(adminCategoryCreateResponseDto)
    }

    @PutMapping("/{categoryId}")
    fun updateCategory(
        @PathVariable categoryId: Int,
        @RequestBody adminCategoryUpdateRequestDto: AdminCategoryUpdateRequestDto,
        @RequestHeader(HttpHeaders.AUTHORIZATION) accessToken: String?
    ): ResponseEntity<AdminCategoryUpdateResponseDto> {

        val adminMember = memberService.getAdminMemberByAccessToken(accessToken)

        return ResponseEntity.ok().body(categoryService.updateCategory(categoryId, adminCategoryUpdateRequestDto, adminMember))
    }

    @DeleteMapping("/{categoryId}")
    fun deleteCategory(
        @PathVariable categoryId: Int,
        @RequestHeader(HttpHeaders.AUTHORIZATION) accessToken: String?
    ): ResponseEntity<AdminCategoryDeleteResponseDto> {

        val adminMember = memberService.getAdminMemberByAccessToken(accessToken)

        return ResponseEntity.ok().body(categoryService.deleteCategory(categoryId, adminMember))
    }
}
