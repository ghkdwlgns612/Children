package com.example.yubbi.services.category.controller.dto.request

import com.example.yubbi.common.utils.ActiveStatus

data class AdminCategoryUpdateRequestDto(
    val title: String,
    val description: String,
    val activeStatus: ActiveStatus,
    val priority: Int
)
