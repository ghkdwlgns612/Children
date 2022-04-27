package com.example.yubbi.services.category.controller.dto.response

data class CategoryListOfOneResponseDto(
    val categoryId: Int,
    val title: String,
    val description: String,
    val priority: Int
)
