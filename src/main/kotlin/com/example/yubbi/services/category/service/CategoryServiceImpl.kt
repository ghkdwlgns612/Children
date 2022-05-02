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

        var priority = adminCategoryCreateRequestDto.priority

        val categoryList = categoryRepository.findAllNotIsDeleted()

        if (priority > categoryList.size + 1) {
            priority = categoryList.size + 1
        } else {
            categoryList.forEach() {
                if (priority <= it.getPriority()!!) {
                    it.increasePriority()
                }
            }
        }

        val createdCategory = categoryRepository.save(
            Category(
                adminCategoryCreateRequestDto.title,
                adminCategoryCreateRequestDto.description,
                adminCategoryCreateRequestDto.activeStatus,
                priority,
                creator
            )
        )

        return AdminCategoryCreateResponseDto(createdCategory.getCategoryId()!!)
    }
}
