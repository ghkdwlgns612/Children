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

    @GetMapping
    fun getCategoryList(): ResponseEntity<CategoryListResponseDto> {

        return ResponseEntity.ok().body(categoryService.getCategoryList())
    }
}
