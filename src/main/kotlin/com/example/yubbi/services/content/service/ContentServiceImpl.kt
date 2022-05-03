package com.example.yubbi.services.content.service

import com.example.yubbi.common.utils.ActiveStatus
import com.example.yubbi.common.utils.UploadStatus
import com.example.yubbi.infra.aws_s3.S3Service
import com.example.yubbi.services.category.domain.Category
import com.example.yubbi.services.category.repository.CategoryRepository
import com.example.yubbi.services.content.controller.dto.request.AdminContentCreateRequestDto
import com.example.yubbi.services.content.controller.dto.request.AdminContentUpdateRequestDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentCreateResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentDeleteResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentUpdateResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentUploadResponseDto
import com.example.yubbi.services.content.controller.dto.response.CategoryOfContentResponseDto
import com.example.yubbi.services.content.controller.dto.response.ContentListOfOneResponseDto
import com.example.yubbi.services.content.controller.dto.response.ContentListResponseDto
import com.example.yubbi.services.content.domain.Content
import com.example.yubbi.services.content.repository.ContentRepository
import com.example.yubbi.services.member.domain.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class ContentServiceImpl @Autowired constructor(
    private val contentRepository: ContentRepository,
    private val s3Service: S3Service,
    private val categoryRepository: CategoryRepository
) : ContentService {
    override fun getContentList(categoryId: Int): ContentListResponseDto {
        val category = categoryRepository.findByIdNotIsDeleted(categoryId).orElseThrow()
        return makeContentListResponseDto(category.getContentList(), category)
    }

    private fun makeContentListResponseDto(contents: MutableList<Content>?, category: Category): ContentListResponseDto {
        val result = arrayListOf<ContentListOfOneResponseDto>()
        val categoryResponse = CategoryOfContentResponseDto(category.getCategoryId()!!, category.getTitle()!!, category.getDescription()!!)

        for (content in contents!!) {
            if (content.getIsDeleted() == false && content.getUploadStatus() == UploadStatus.SUCCESS &&
                content.getDisplayEndDate()!! > LocalDateTime.now() && content.getActiveStatus() == ActiveStatus.ACTIVE
            ) {
                result.add(
                    ContentListOfOneResponseDto(
                        content.getContentId()!!,
                        categoryResponse,
                        content.getTitle()!!,
                        content.getDescription()!!,
                        content.getImageUrl()!!,
                        content.getVideoUrl()!!,
                        content.getPriority()!!
                    )
                )
            }
        } // 카테고리 관련 컨텐츠 중 삭제되지 않았고 업로드 상태가 완료이며 전시 기간의 끝나는 날짜는 현재보다 커야하고 상태는 활성상태인 것만 사용자에게 노출

        return ContentListResponseDto(result)
    }

    override fun uploadContent(imageFile: MultipartFile, videoFile: MultipartFile, member: Member, contentId: Int?): AdminContentUploadResponseDto {
        val content = if (contentId == null) {
            contentRepository.save(
                Content(
                    null, null, null, null,
                    ActiveStatus.IN_ACTIVE, null, null, null, UploadStatus.UPLOADING, true, member
                )
            )
        } else {
            val findContent = contentRepository.findById(contentId).orElseThrow() // 컨텐츠가 없을 경우 예외 발생
            findContent.setUploadStatusAndIsDeleted(UploadStatus.UPLOADING, true)
            findContent
        }

        val imageUrl = s3Service.upload(imageFile)
        val videoUrl = s3Service.upload(videoFile)
        content.setImageUrlAndVideoUrlAndUploadStatus(imageUrl, videoUrl, UploadStatus.SUCCESS)

        return AdminContentUploadResponseDto(content.getContentId(), content.getUploadStatus())
    }

    override fun createContent(adminContentCreateRequestDto: AdminContentCreateRequestDto, member: Member): AdminContentCreateResponseDto {
        val content = contentRepository.findById(adminContentCreateRequestDto.contentId).orElseThrow() // 컨텐츠가 없을 경우 예외 발생
        val category = categoryRepository.findById(adminContentCreateRequestDto.categoryId).orElseThrow() // 카테고리가 없을 경우 예외 발생

        content.setCreateInformation(adminContentCreateRequestDto, category, member)
        return makeAdminContentCreateResponseDto(content)
    }

    override fun updateContent(adminContentUpdateRequestDto: AdminContentUpdateRequestDto, member: Member): AdminContentUpdateResponseDto {
        val content = contentRepository.findById(adminContentUpdateRequestDto.contentId).orElseThrow() // 컨텐츠가 없을 경우 예외 발생
        val category = categoryRepository.findById(adminContentUpdateRequestDto.categoryId).orElseThrow() // 카테고리가 없을 경우 예외 발생

        content.setUpdateInformation(adminContentUpdateRequestDto, category, member)
        return makeAdminContentUpdateResponseDto(content)
    }

    override fun deleteContent(contentId: Int, member: Member): AdminContentDeleteResponseDto {
        val content = contentRepository.findById(contentId).orElseThrow() // 컨텐츠가 없을 경우 예외 발생
        content.setIsDeletedAndDeletedAt(true, member)
        return AdminContentDeleteResponseDto(content.getContentId()!!)
    }

    private fun makeAdminContentCreateResponseDto(content: Content): AdminContentCreateResponseDto {
        return AdminContentCreateResponseDto(content.getContentId()!!)
    }

    private fun makeAdminContentUpdateResponseDto(content: Content): AdminContentUpdateResponseDto {
        return AdminContentUpdateResponseDto(content.getContentId()!!)
    }
}
