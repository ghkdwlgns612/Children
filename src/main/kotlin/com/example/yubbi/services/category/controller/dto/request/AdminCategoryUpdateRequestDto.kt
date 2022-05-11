package com.example.yubbi.services.category.controller.dto.request

import com.example.yubbi.common.utils.ActiveStatus
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class AdminCategoryUpdateRequestDto(
    @field:NotBlank
    val title: String,

    @field:NotBlank
    val description: String,

    val activeStatus: ActiveStatus,

    @field:Min(1)
    var priority: Int
)
