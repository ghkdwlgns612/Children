package com.example.yubbi.services.category.service

import com.example.yubbi.common.exception.custom.NotFoundCategoryException
import com.example.yubbi.services.category.controller.dto.request.AdminCategoryCreateRequestDto
import com.example.yubbi.services.category.controller.dto.request.AdminCategoryUpdateRequestDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryCreateResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryDeleteResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryListOfOneResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryListResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryModifierResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryResponseDto
import com.example.yubbi.services.category.controller.dto.response.AdminCategoryUpdateResponseDto
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
    override fun getCategoryList(): CategoryListResponseDto {

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

    @Transactional(readOnly = true)
    override fun getAdminCategoryList(): AdminCategoryListResponseDto {
        val categoryList = categoryRepository.findAllNotIsDeletedWithLastModifier()

        val adminCategoryListOfOneResponseDto = categoryList.map { category ->

            val lastModifier = category.getLastModifier()!!

            AdminCategoryListOfOneResponseDto(
                category.getCategoryId()!!,
                category.getTitle()!!,
                category.getDescription()!!,
                category.getActiveStatus()!!,
                category.getPriority()!!,
                category.getCreatedAt()!!,
                category.getLastModifiedAt()!!,
                AdminCategoryModifierResponseDto(
                    lastModifier.getMemberId()!!,
                    lastModifier.getEmail()!!,
                    lastModifier.getName()!!
                )
            )
        }.toList()

        return AdminCategoryListResponseDto(adminCategoryListOfOneResponseDto)
    }

    @Transactional(readOnly = true)
    override fun getAdminCategory(categoryId: Int): AdminCategoryResponseDto {
        val category = categoryRepository.findByIdNotIsDeletedWithLastModifier(categoryId).orElseThrow { NotFoundCategoryException() }
        val lastModifier = category.getLastModifier()!!

        return AdminCategoryResponseDto(
            category.getCategoryId()!!,
            category.getTitle()!!,
            category.getDescription()!!,
            category.getActiveStatus()!!,
            category.getPriority()!!,
            category.getCreatedAt()!!,
            category.getLastModifiedAt()!!,
            AdminCategoryModifierResponseDto(
                lastModifier.getMemberId()!!,
                lastModifier.getEmail()!!,
                lastModifier.getName()!!
            )
        )
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

    override fun updateCategory(
        categoryId: Int,
        adminCategoryUpdateRequestDto: AdminCategoryUpdateRequestDto,
        modifier: Member
    ): AdminCategoryUpdateResponseDto {
        val category = categoryRepository.findByIdNotIsDeleted(categoryId).orElseThrow { NotFoundCategoryException() }
        val categoryList = categoryRepository.findAllNotIsDeleted()

        val oldPriority = category.getPriority()!!
        val updatePriority = adminCategoryUpdateRequestDto.priority

        if (oldPriority < updatePriority) {
            categoryList.forEach { category ->
                if (category.getPriority()!! in (oldPriority + 1)..updatePriority) {
                    category.decreasePriority()
                }
            }
        } else if (oldPriority > updatePriority) {
            categoryList.forEach { category ->
                if (category.getPriority()!! in updatePriority until oldPriority) {
                    category.increasePriority()
                }
            }
        }

        category.setUpdateInformation(adminCategoryUpdateRequestDto, modifier)
        return AdminCategoryUpdateResponseDto(category.getCategoryId()!!)
    }

    override fun deleteCategory(categoryId: Int, modifier: Member): AdminCategoryDeleteResponseDto {
        val deleteTargetCategory = categoryRepository.findByIdNotIsDeleted(categoryId).orElseThrow { NotFoundCategoryException() }
        deleteTargetCategory.setIsDeletedAndDeletedAt(true, modifier)

        val categoryList = categoryRepository.findAllNotIsDeleted()
        categoryList.forEach { category ->
            if (deleteTargetCategory.getPriority()!! < category.getPriority()!!) {
                category.decreasePriority()
            }
        }

        val deleteTargetContentList = deleteTargetCategory.getContentList()!!
        deleteTargetContentList.forEach { content ->
            if (content.getIsDeleted() == false) {
                content.setIsDeletedAndDeletedAt(true, modifier)
            }
        }

        return AdminCategoryDeleteResponseDto(deleteTargetCategory.getCategoryId()!!)
    }
}
