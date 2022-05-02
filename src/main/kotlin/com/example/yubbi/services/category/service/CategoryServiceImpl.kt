package com.example.yubbi.services.category.service

import com.example.yubbi.services.category.controller.dto.request.AdminCategoryCreateRequestDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryCreateResponseDto
import com.example.yubbi.services.category.controller.dto.response.CategoryListOfOneResponseDto
import com.example.yubbi.services.category.controller.dto.response.CategoryListResponseDto
import com.example.yubbi.services.category.domain.Category
import com.example.yubbi.services.category.repository.CategoryRepository
import com.example.yubbi.services.member.domain.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryServiceImpl(private val categoryRepository: CategoryRepository) : CategoryService {

    @Transactional(readOnly = true)
    override fun getActiveCategoryList(): CategoryListResponseDto {

        val findAll = categoryRepository.findActiveCategories()

        val categoryListOfOneResponseDtoList = findAll.map { category ->
            CategoryListOfOneResponseDto(
                category.getCategoryId()!!,
                category.getTitle()!!,
                category.getDescription()!!,
                category.getPriority()!!
            )
        }.toList()

        return CategoryListResponseDto(categoryListOfOneResponseDtoList)
    }

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
