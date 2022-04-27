package com.example.yubbi.services.category.controller

import com.example.yubbi.services.category.controller.dto.response.CategoryListOfOneResponseDto
import com.example.yubbi.services.category.controller.dto.response.CategoryListResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController {

    @GetMapping
    fun getCategoryList(): ResponseEntity<CategoryListResponseDto> {
        val categories = listOf(
            CategoryListOfOneResponseDto(1, "책읽는TV", "책읽는TV 상세설명", 2),
            CategoryListOfOneResponseDto(2, "영어유치원", "영어유치원 상세설명", 1)
        )

        return ResponseEntity.ok().body(CategoryListResponseDto(categories))
    }
}
