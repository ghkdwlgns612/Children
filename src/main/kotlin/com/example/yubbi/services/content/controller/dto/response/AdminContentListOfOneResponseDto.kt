package com.example.yubbi.services.content.controller.dto.response

import com.example.yubbi.common.utils.ActiveStatus
import com.example.yubbi.common.utils.UploadStatus
import java.time.LocalDateTime

data class AdminContentListOfOneResponseDto(
    val contentId: Int,
    val category: AdminCategoryOfContentResponseDto,
    val title: String,
    val description: String,
    val imageUrl: String,
    val videoUrl: String,
    val activeStatus: ActiveStatus,
    val displayStartDate: LocalDateTime,
    val displayEndDate: LocalDateTime,
    val priority: Int,
    val uploadStatus: UploadStatus,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime,
    val lastModifier: AdminContentModifierResponseDto
)
