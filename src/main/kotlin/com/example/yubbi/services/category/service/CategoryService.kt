package com.example.yubbi.services.category.service

import com.example.yubbi.services.category.controller.dto.request.AdminCategoryCreateRequestDto
import com.example.yubbi.services.category.controller.dto.request.AdminCategoryUpdateRequestDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryCreateResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryDeleteResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryListResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryUpdateResponseDto
import com.example.yubbi.services.category.controller.dto.response.CategoryListResponseDto
import com.example.yubbi.services.member.domain.Member

interface CategoryService {

    fun createCategory(adminCategoryCreateRequestDto: AdminCategoryCreateRequestDto, creator: Member): AdminCategoryCreateResponseDto
    fun getCategoryList(): CategoryListResponseDto
    fun getAdminCategoryList(): AdminCategoryListResponseDto
    fun updateCategory(
        categoryId: Int,
        adminCategoryUpdateRequestDto: AdminCategoryUpdateRequestDto,
        modifier: Member
    ): AdminCategoryUpdateResponseDto

    fun deleteCategory(categoryId: Int, modifier: Member): AdminCategoryDeleteResponseDto
}
