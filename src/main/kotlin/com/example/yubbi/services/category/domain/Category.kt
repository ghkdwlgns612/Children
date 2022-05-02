package com.example.yubbi.services.category.domain

import com.example.yubbi.common.domain.BaseTime
import com.example.yubbi.common.utils.ActiveStatus
import com.example.yubbi.services.category.controller.dto.request.AdminCategoryUpdateRequestDto
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
@Table(name = "category")
class Category constructor() : BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private val categoryId: Int? = null

    @Column(name = "title")
    private var title: String? = null

    @Column(name = "description", length = 2000)
    private var description: String? = null

    @Enumerated(value = EnumType.STRING)
    @Column(name = "active_status")
    private var activeStatus: ActiveStatus? = null

    @Column(name = "priority")
    private var priority: Int? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "last_modifier")
    private var lastModifier: Member? = null

    @Column(name = "is_deleted")
    private var isDeleted: Boolean? = false

    constructor(title: String, description: String, activeStatus: ActiveStatus, priority: Int, lastModifier: Member) : this() {
        this.title = title
        this.description = description
        this.activeStatus = activeStatus
        this.priority = priority
        this.lastModifier = lastModifier
        this.isDeleted = false
        this.setCreatedAt(LocalDateTime.now())
        this.setLastModifiedAt(LocalDateTime.now())
    }

    fun getCategoryId(): Int? {
        return this.categoryId
    }

    fun getTitle(): String? {
        return this.title
    }

    fun getDescription(): String? {
        return this.description
    }

    fun getActiveStatus(): ActiveStatus? {
        return this.activeStatus
    }

    fun getPriority(): Int? {
        return this.priority
    }

    fun getLastModifier(): Member? {
        return this.lastModifier
    }

    fun getIsDeleted(): Boolean? {
        return this.isDeleted
    }

    fun setIsDeleted(isDeleted: Boolean) {
        this.isDeleted = isDeleted
    }

    fun increasePriority() {
        this.priority = this.priority!! + 1
    }

    fun decreasePriority() {
        this.priority = this.priority!! - 1
    }

    fun setUpdateInformation(adminCategoryUpdateRequestDto: AdminCategoryUpdateRequestDto, modifier: Member) {
        this.title = adminCategoryUpdateRequestDto.title
        this.description = adminCategoryUpdateRequestDto.description
        this.activeStatus = adminCategoryUpdateRequestDto.activeStatus
        this.priority = adminCategoryUpdateRequestDto.priority
        this.lastModifier = modifier
        setLastModifiedAt(LocalDateTime.now())
    }
}
