package com.example.yubbi.services.category.controller.dto.request

import com.example.yubbi.common.utils.ActiveStatus

data class AdminCategoryCreateRequestDto(
    val title: String,
    val description: String,
    val activeStatus: ActiveStatus,
    val priority: Int
)
