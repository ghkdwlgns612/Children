package com.example.yubbi.services.content.controller.dto.response

data class ContentListOfOneResponseDto(
    val contentId: Int,
    val category: CategoryOfContentResponseDto,
    val title: String,
    val description: String,
    val imageUrl: String,
    val videoUrl: String,
    val priority: Int
)
