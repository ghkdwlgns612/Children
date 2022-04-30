package com.example.yubbi.services.content.service

import com.example.yubbi.common.utils.UploadStatus
import com.example.yubbi.services.content.controller.dto.request.AdminContentCreateRequestDto
import com.example.yubbi.services.content.controller.dto.request.AdminContentUpdateRequestDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentCreateResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentDeleteResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentUpdateResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentUploadResponseDto
import com.example.yubbi.services.content.repository.ContentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class ContentServiceImpl @Autowired constructor(private val contentRepository: ContentRepository) : ContentService {

    override fun uploadContent(imageFile: MultipartFile, videoFile: MultipartFile): AdminContentUploadResponseDto {
        return AdminContentUploadResponseDto(1, UploadStatus.UPLOADING)
    }

    override fun createContent(adminContentCreateRequestDto: AdminContentCreateRequestDto): AdminContentCreateResponseDto {
        return AdminContentCreateResponseDto(1)
    }

    override fun updateContent(adminContentUpdateRequestDto: AdminContentUpdateRequestDto): AdminContentUpdateResponseDto {
        return AdminContentUpdateResponseDto(1)
    }

    override fun deleteContent(contentId: Int): AdminContentDeleteResponseDto {
        return AdminContentDeleteResponseDto(1)
    }
}
