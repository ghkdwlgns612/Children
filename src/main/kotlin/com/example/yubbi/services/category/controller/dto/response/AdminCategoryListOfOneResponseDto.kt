package com.example.yubbi.services.category.controller.dto.response

import com.example.yubbi.common.utils.ActiveStatus
import java.time.LocalDateTime

data class AdminCategoryListOfOneResponseDto(
    val categoryId: Int,
    val title: String,
    val description: String,
    val activeStatus: ActiveStatus,
    val priority: Int,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime,
    val lastModifier: AdminCategoryModifierResponseDto
)
