package com.example.yubbi.services.category.controller

import com.example.yubbi.services.category.controller.dto.request.AdminCategoryCreateRequestDto
import com.example.yubbi.services.category.controller.dto.request.AdminCategoryUpdateRequestDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryCreateResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryDeleteResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryListResponseDto
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

@RestController
@RequestMapping("/admin/categories")
class AdminCategoryController(
    private val categoryService: CategoryService,
    private val memberService: MemberService
) {

    @GetMapping
    fun getCategoryList(@RequestHeader(HttpHeaders.AUTHORIZATION) accessToken: String?): ResponseEntity<AdminCategoryListResponseDto> {

        memberService.getAdminMemberByAccessToken(accessToken)

        return ResponseEntity.ok().body(categoryService.getAdminCategoryList())
    }

    @GetMapping("/{categoryId}")
    fun getCategory(
        @RequestHeader(HttpHeaders.AUTHORIZATION) accessToken: String?,
        @PathVariable categoryId: Int
    ): ResponseEntity<AdminCategoryResponseDto> {

        memberService.getAdminMemberByAccessToken(accessToken)

        return ResponseEntity.ok().body(categoryService.getAdminCategory(categoryId))
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
        @Validated @RequestBody adminCategoryUpdateRequestDto: AdminCategoryUpdateRequestDto,
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
