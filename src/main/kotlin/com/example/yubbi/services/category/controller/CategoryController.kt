package com.example.yubbi.services.category.controller

import com.example.yubbi.services.category.controller.dto.response.CategoryListResponseDto
import com.example.yubbi.services.category.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController(private val categoryService: CategoryService) {

    // TODO : 구현 필요
    @GetMapping
    fun getCategoryList(): ResponseEntity<CategoryListResponseDto> {

        val categoryListResponseDto = categoryService.getActiveCategoryList()

        return ResponseEntity.ok().body(categoryListResponseDto)
    }
}
