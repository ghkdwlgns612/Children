package com.example.yubbi.common.domain

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseTime constructor() {
    @Column(name = "created_at")
    private var createdAt: LocalDateTime? = null
    @Column(name = "last_modified_at")
    private var lastModifiedAt: LocalDateTime? = null
    @Column(name = "deleted_at")
    private var deletedAt: LocalDateTime? = null

    constructor(createdAt: LocalDateTime, lastModifiedAt: LocalDateTime, deletedAt: LocalDateTime) : this() {
        this.createdAt = createdAt
        this.lastModifiedAt = lastModifiedAt
        this.deletedAt = deletedAt
    }

    fun getCreatedAt(): LocalDateTime? {
        return this.createdAt
    }

    fun getLastModifiedAt(): LocalDateTime? {
        return this.lastModifiedAt
    }

    fun getDeletedAt(): LocalDateTime? {
        return this.deletedAt
    }

    fun setCreatedAt(createdAt: LocalDateTime) {
        this.createdAt = createdAt
    }

    fun setLastModifiedAt(lastModifiedAt: LocalDateTime) {
        this.lastModifiedAt = lastModifiedAt
    }

    fun setDeletedAt(deletedAt: LocalDateTime) {
        this.deletedAt = deletedAt
    }

    fun setLastModifiedAtAndDeletedAt(lastModifiedAt: LocalDateTime, deletedAt: LocalDateTime) {
        this.lastModifiedAt = lastModifiedAt
        this.deletedAt = deletedAt
    }
}
