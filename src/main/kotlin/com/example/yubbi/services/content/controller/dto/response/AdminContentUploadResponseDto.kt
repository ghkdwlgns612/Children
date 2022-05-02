package com.example.yubbi.services.content.controller.dto.response

import com.example.yubbi.common.utils.UploadStatus

data class AdminContentUploadResponseDto(
    val contentId: Int?,
    val uploadStatus: UploadStatus?
)
