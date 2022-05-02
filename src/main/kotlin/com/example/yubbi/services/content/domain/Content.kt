package com.example.yubbi.services.content.domain

import com.example.yubbi.common.domain.BaseTime
import com.example.yubbi.common.utils.ActiveStatus
import com.example.yubbi.common.utils.UploadStatus
import com.example.yubbi.services.category.domain.Category
import com.example.yubbi.services.content.controller.dto.request.AdminContentCreateRequestDto
import com.example.yubbi.services.content.controller.dto.request.AdminContentUpdateRequestDto
import com.example.yubbi.services.member.domain.Member
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "content")
class Content constructor() : BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private val contentId: Int? = null

    @Column(name = "title")
    private var title: String? = null

    @Column(name = "description", length = 500)
    private var description: String? = null

    @Column(name = "image_url", length = 500)
    private var imageUrl: String? = null

    @Column(name = "video_url", length = 500)
    private var videoUrl: String? = null

    @Column(name = "active_status")
    @Enumerated(value = EnumType.STRING)
    private var activeStatus: ActiveStatus? = null

    @Column(name = "display_start_date")
    private var displayStartDate: LocalDateTime? = null

    @Column(name = "display_end_date")
    private var displayEndDate: LocalDateTime? = null

    @Column(name = "priority")
    private var priority: Int? = null

    @Column(name = "upload_status")
    @Enumerated(value = EnumType.STRING)
    private var uploadStatus: UploadStatus? = null

    @Column(name = "is_deleted")
    private var isDeleted: Boolean? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "last_modifier")
    private var lastModifier: Member? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private var category: Category? = null

    constructor(
        title: String?,
        description: String?,
        imageUrl: String?,
        videoUrl: String?,
        activeStatus: ActiveStatus?,
        displayStartDate: LocalDateTime?,
        displayEndDate: LocalDateTime?,
        priority: Int?,
        uploadStatus: UploadStatus?,
        isDeleted: Boolean?,
        lastModifier: Member?
    ) : this() {
        this.title = title
        this.description = description
        this.imageUrl = imageUrl
        this.videoUrl = videoUrl
        this.activeStatus = activeStatus
        this.displayStartDate = displayStartDate
        this.displayEndDate = displayEndDate
        this.priority = priority
        this.uploadStatus = uploadStatus
        this.isDeleted = isDeleted
        this.lastModifier = lastModifier
        this.setCreatedAt(LocalDateTime.now())
        this.setLastModifiedAt(LocalDateTime.now())
    }

    fun getContentId(): Int? {
        return this.contentId
    }

    fun getTitle(): String? {
        return this.title
    }

    fun getDescription(): String? {
        return this.description
    }

    fun getImageUrl(): String? {
        return this.imageUrl
    }

    fun getVideoUrl(): String? {
        return this.videoUrl
    }

    fun getActiveStatus(): ActiveStatus? {
        return this.activeStatus
    }

    fun getDisplayStartDate(): LocalDateTime? {
        return this.displayStartDate
    }

    fun getDisplayEndDate(): LocalDateTime? {
        return this.displayEndDate
    }

    fun getPriority(): Int? {
        return this.priority
    }

    fun getUploadStatus(): UploadStatus? {
        return this.uploadStatus
    }

    fun getIsDeleted(): Boolean? {
        return this.isDeleted
    }

    fun getLastModifier(): Member? {
        return this.lastModifier
    }

    fun setIsDeletedAndDeletedAt(isDeleted: Boolean, member: Member) {
        this.isDeleted = isDeleted
        this.setDeletedAt(LocalDateTime.now())
        this.lastModifier = member
        setLastModifiedAt(LocalDateTime.now())
    }

    fun setImageUrlAndVideoUrlAndUploadStatus(imageUrl: String, videoUrl: String, uploadStatus: UploadStatus) {
        this.imageUrl = imageUrl
        this.videoUrl = videoUrl
        this.uploadStatus = uploadStatus
        setLastModifiedAt(LocalDateTime.now())
    }

    fun setCreateInformation(adminContentCreateRequestDto: AdminContentCreateRequestDto, category: Category, member: Member) {
        this.category = category
        this.title = adminContentCreateRequestDto.title
        this.description = adminContentCreateRequestDto.description
        this.activeStatus = ActiveStatus.IN_ACTIVE
        this.displayStartDate = adminContentCreateRequestDto.displayStartDate
        this.displayEndDate = adminContentCreateRequestDto.displayEndDate
        this.priority = adminContentCreateRequestDto.priority
        this.lastModifier = member
        this.isDeleted = false
        setLastModifiedAt(LocalDateTime.now())
    }

    fun setUploadStatusAndIsDeleted(uploadStatus: UploadStatus, isDeleted: Boolean) {
        this.isDeleted = isDeleted
        this.uploadStatus = uploadStatus
        setLastModifiedAt(LocalDateTime.now())
    }

    fun setUpdateInformation(adminContentUpdateRequestDto: AdminContentUpdateRequestDto, category: Category, member: Member) {
        this.category = category
        this.title = adminContentUpdateRequestDto.title
        this.description = adminContentUpdateRequestDto.description
        this.activeStatus = adminContentUpdateRequestDto.activeStatus
        this.displayStartDate = adminContentUpdateRequestDto.displayStartDate
        this.displayEndDate = adminContentUpdateRequestDto.displayEndDate
        this.priority = adminContentUpdateRequestDto.priority
        this.lastModifier = member
        setLastModifiedAt(LocalDateTime.now())
    }
}
