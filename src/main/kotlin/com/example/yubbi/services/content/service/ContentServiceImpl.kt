package com.example.yubbi.services.content.service

import com.example.yubbi.common.exception.custom.NotFoundCategoryException
import com.example.yubbi.common.exception.custom.NotFoundContentException
import com.example.yubbi.common.utils.ActiveStatus
import com.example.yubbi.common.utils.UploadStatus
import com.example.yubbi.infra.aws_s3.S3Service
import com.example.yubbi.services.category.domain.Category
import com.example.yubbi.services.category.repository.CategoryRepository
import com.example.yubbi.services.content.controller.dto.request.AdminContentCreateRequestDto
import com.example.yubbi.services.content.controller.dto.request.AdminContentUpdateRequestDto
import com.example.yubbi.services.content.controller.dto.response.AdminCategoryOfContentResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentCreateResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentDeleteResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentListOfOneResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentListResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentModifierResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentUpdateResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentUploadResponseDto
import com.example.yubbi.services.content.controller.dto.response.CategoryOfContentResponseDto
import com.example.yubbi.services.content.controller.dto.response.ContentListOfOneResponseDto
import com.example.yubbi.services.content.controller.dto.response.ContentListResponseDto
import com.example.yubbi.services.content.domain.Content
import com.example.yubbi.services.content.repository.ContentRepository
import com.example.yubbi.services.content.service.factory.upload.ifGetContent.DefineUploadMethodFactory
import com.example.yubbi.services.content.service.priority.PriorityMethod
import com.example.yubbi.services.member.domain.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import javax.transaction.Transactional

@Suppress("NAME_SHADOWING")
@Service
@Transactional
class ContentServiceImpl @Autowired constructor(
    private val contentRepository: ContentRepository,
    private val s3Service: S3Service,
    private val categoryRepository: CategoryRepository,
    private val defineUploadMethodFactory: DefineUploadMethodFactory,
    private val priorityMethod: PriorityMethod
) : ContentService {
    override fun getContentList(categoryId: Int): ContentListResponseDto {
        val category = categoryRepository.findByIdNotIsDeleted(categoryId).orElseThrow { NotFoundCategoryException() }
        return makeContentListResponseDto(category.getContentList(), category)
    }

    override fun getAdminContentList(categoryId: Int): AdminContentListResponseDto {
        val category = categoryRepository.findByIdNotIsDeleted(categoryId).orElseThrow { NotFoundCategoryException() }
        return makeAdminContentListResponseDto(category.getContentList(), category)
    }

    override fun getAdminContent(contentId: Int): AdminContentResponseDto {
        val content = contentRepository.findByIdNotIsDeleted(contentId).orElseThrow { NotFoundContentException() }
        return makeAdminContentResponseDto(content)
    }

    override fun uploadContent(imageFile: MultipartFile?, videoFile: MultipartFile?, member: Member, contentId: Int?): AdminContentUploadResponseDto {
        val content = defineUploadMethodFactory.getDefineUploadMethod(contentId).getContent(contentId, member)

        val imageUrl = if (imageFile != null && !imageFile.isEmpty) {
            s3Service.upload(imageFile)
        } else {
            content.getImageUrl()!!
        }
        val videoUrl = if (videoFile != null && !videoFile.isEmpty) {
            s3Service.upload(videoFile)
        } else {
            content.getVideoUrl()!!
        }

        content.setImageUrlAndVideoUrlAndUploadStatus(imageUrl, videoUrl, UploadStatus.SUCCESS)

        return AdminContentUploadResponseDto(content.getContentId(), content.getUploadStatus())
    }

    override fun createContent(adminContentCreateRequestDto: AdminContentCreateRequestDto, member: Member): AdminContentCreateResponseDto {
        var priority = adminContentCreateRequestDto.priority
        val category = categoryRepository.findByIdNotIsDeleted(adminContentCreateRequestDto.categoryId).orElseThrow { NotFoundCategoryException() }
        val content = contentRepository.findById(adminContentCreateRequestDto.contentId).orElseThrow { NotFoundContentException() }
        val categoryContents = contentRepository.findAllByCategoryIdAndNotIsDeleted(adminContentCreateRequestDto.categoryId)

        priority = priorityMethod.createContentPriority(priority, categoryContents)

        content.setCreateInformation(adminContentCreateRequestDto, category, member, priority)

        return makeAdminContentCreateResponseDto(content)
    }

    override fun updateContent(adminContentUpdateRequestDto: AdminContentUpdateRequestDto, member: Member): AdminContentUpdateResponseDto {
        val content = contentRepository.findById(adminContentUpdateRequestDto.contentId).orElseThrow { NotFoundContentException() }
        val category = categoryRepository.findById(adminContentUpdateRequestDto.categoryId).orElseThrow { NotFoundCategoryException() }
        val categoryContents = contentRepository.findAllByCategoryIdAndNotIsDeleted(adminContentUpdateRequestDto.categoryId)
        val oldPriority = content.getPriority()
        var updatePriority = adminContentUpdateRequestDto.priority

        updatePriority = priorityMethod.updateContentPriority(oldPriority, updatePriority, categoryContents)

        content.setUpdateInformation(adminContentUpdateRequestDto, category, member, updatePriority)
        return makeAdminContentUpdateResponseDto(content)
    }

    override fun deleteContent(contentId: Int, member: Member): AdminContentDeleteResponseDto {
        val deletedContent = contentRepository.findById(contentId).orElseThrow { NotFoundContentException() }
        val categoryId = deletedContent.getCategory()!!.getCategoryId()
        val categoryContents = contentRepository.findAllByCategoryIdAndNotIsDeleted(categoryId!!)
        deletedContent.setIsDeletedAndDeletedAt(true, member)

        priorityMethod.deleteContentPriority(categoryContents, deletedContent)

        return AdminContentDeleteResponseDto(deletedContent.getContentId()!!)
    }

    private fun makeAdminContentCreateResponseDto(content: Content): AdminContentCreateResponseDto {
        return AdminContentCreateResponseDto(content.getContentId()!!)
    }

    private fun makeAdminContentUpdateResponseDto(content: Content): AdminContentUpdateResponseDto {
        return AdminContentUpdateResponseDto(content.getContentId()!!)
    }

    private fun makeAdminContentListResponseDto(contents: MutableList<Content>?, category: Category): AdminContentListResponseDto {
        val result = arrayListOf<AdminContentListOfOneResponseDto>()
        val categoryResponse = AdminCategoryOfContentResponseDto(category.getCategoryId()!!, category.getTitle()!!, category.getDescription()!!)

        for (content in contents!!) {
            val modifier = AdminContentModifierResponseDto(
                content.getLastModifier()!!.getMemberId()!!,
                content.getLastModifier()!!.getEmail()!!,
                content.getLastModifier()!!.getName()!!
            )
            if (content.getIsDeleted() == false && content.getUploadStatus() == UploadStatus.SUCCESS) {
                result.add(
                    AdminContentListOfOneResponseDto(
                        content.getContentId()!!,
                        categoryResponse,
                        content.getTitle()!!,
                        content.getDescription()!!,
                        content.getImageUrl()!!,
                        content.getVideoUrl()!!,
                        content.getActiveStatus()!!,
                        content.getDisplayStartDate()!!,
                        content.getDisplayEndDate()!!,
                        content.getPriority()!!,
                        content.getUploadStatus()!!,
                        content.getCreatedAt()!!,
                        content.getLastModifiedAt()!!,
                        modifier
                    )
                )
            }
        }
        return AdminContentListResponseDto(result)
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

    private fun makeAdminContentResponseDto(content: Content): AdminContentResponseDto {
        val category = AdminCategoryOfContentResponseDto(
            content.getCategory()!!.getCategoryId()!!,
            content.getCategory()!!.getTitle()!!,
            content.getCategory()!!.getDescription()!!
        )
        val modifier = AdminContentModifierResponseDto(
            content.getLastModifier()!!.getMemberId()!!,
            content.getLastModifier()!!.getEmail()!!,
            content.getLastModifier()!!.getName()!!
        )
        return AdminContentResponseDto(
            content.getContentId()!!,
            category,
            content.getTitle()!!,
            content.getDescription()!!,
            content.getImageUrl()!!,
            content.getVideoUrl()!!,
            content.getActiveStatus()!!,
            content.getDisplayStartDate()!!,
            content.getDisplayEndDate()!!,
            content.getPriority()!!,
            content.getUploadStatus()!!,
            content.getCreatedAt()!!,
            content.getLastModifiedAt()!!,
            modifier
        )
    }
}
