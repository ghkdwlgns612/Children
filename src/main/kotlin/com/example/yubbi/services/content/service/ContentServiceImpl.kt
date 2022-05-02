package com.example.yubbi.services.content.service

import com.example.yubbi.common.utils.ActiveStatus
import com.example.yubbi.common.utils.UploadStatus
import com.example.yubbi.infra.aws_s3.S3Service
import com.example.yubbi.services.category.repository.CategoryRepository
import com.example.yubbi.services.content.controller.dto.request.AdminContentCreateRequestDto
import com.example.yubbi.services.content.controller.dto.request.AdminContentUpdateRequestDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentCreateResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentDeleteResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentUpdateResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentUploadResponseDto
import com.example.yubbi.services.content.domain.Content
import com.example.yubbi.services.content.repository.ContentRepository
import com.example.yubbi.services.member.domain.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
@Transactional
class ContentServiceImpl @Autowired constructor(
    private val contentRepository: ContentRepository,
    private val s3Service: S3Service,
    private val categoryRepository: CategoryRepository
) : ContentService {

    override fun uploadContent(imageFile: MultipartFile, videoFile: MultipartFile, member: Member): AdminContentUploadResponseDto {
        val content = contentRepository.save(
            Content(
                null, null, null, null,
                ActiveStatus.IN_ACTIVE, null, null, null, UploadStatus.UPLOADING, true, member
            )
        )

        val imageUrl = s3Service.upload(imageFile)
        val videoUrl = s3Service.upload(videoFile)
        content.setImageUrlAndVideoUrlAndUploadStatus(imageUrl, videoUrl, UploadStatus.SUCCESS)
        val updatedContent = contentRepository.save(content)
        return AdminContentUploadResponseDto(updatedContent.getContentId(), UploadStatus.UPLOADING)
    }

    override fun createContent(adminContentCreateRequestDto: AdminContentCreateRequestDto, member: Member): AdminContentCreateResponseDto {
        val content = contentRepository.findById(adminContentCreateRequestDto.contentId).orElseThrow() // 컨텐츠가 없을 경우 예외 발생

        val category = categoryRepository.findById(adminContentCreateRequestDto.categoryId).orElseThrow() // 카테고리가 없을 경우 예외 발생
        content.setCreateInformation(adminContentCreateRequestDto, category, member)
        val updatedContent = contentRepository.save(content)
        return makeAdminContentCreateResponseDto(updatedContent)
    }

    override fun updateContent(adminContentUpdateRequestDto: AdminContentUpdateRequestDto): AdminContentUpdateResponseDto {
        return AdminContentUpdateResponseDto(1)
    }

    override fun deleteContent(contentId: Int): AdminContentDeleteResponseDto {
        return AdminContentDeleteResponseDto(1)
    }

    private fun makeAdminContentCreateResponseDto(content: Content): AdminContentCreateResponseDto {
        return AdminContentCreateResponseDto(content.getContentId())
    }
}
