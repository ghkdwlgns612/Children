package com.example.yubbi.services.content.controller.dto.request

import com.example.yubbi.common.utils.ActiveStatus
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class AdminContentCreateRequestDto(
    val categoryId: Int,
    val title: String,
    val description: String,
    val activeStatus: ActiveStatus,
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val displayStartDate: LocalDateTime,
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val displayEndDate: LocalDateTime,
    val priority: Int

    // TODO : 이미지, 동영상 관련 추가
)
