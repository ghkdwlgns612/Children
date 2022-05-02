package com.example.yubbi.services.category.service

import com.example.yubbi.services.category.controller.dto.request.AdminCategoryCreateRequestDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryCreateResponseDto
import com.example.yubbi.services.category.domain.Category
import com.example.yubbi.services.category.repository.CategoryRepository
import com.example.yubbi.services.member.domain.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryServiceImpl(private val categoryRepository: CategoryRepository) : CategoryService {

    override fun createCategory(adminCategoryCreateRequestDto: AdminCategoryCreateRequestDto, creator: Member): AdminCategoryCreateResponseDto {

        val category = Category(
            adminCategoryCreateRequestDto.title,
            adminCategoryCreateRequestDto.description,
            adminCategoryCreateRequestDto.activeStatus,
            adminCategoryCreateRequestDto.priority,
            creator
        )

        // TODO: 우선순위 정리하는 로직 들어가야함

        val createdCategory = categoryRepository.save(category)

        return AdminCategoryCreateResponseDto(createdCategory.getCategoryId()!!)
    }
}
