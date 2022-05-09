package com.example.yubbi.services.content.repository

import com.example.yubbi.services.content.domain.Content
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ContentRepository : JpaRepository<Content, Int> {

    @Query("select c from Content c where c.contentId = :contentId and c.isDeleted = false")
    fun findByIdNotIsDeleted(contentId: Int): Optional<Content>

    @Query("select c from Content c where c.category.categoryId = :categoryId and c.isDeleted = false")
    fun findAllByCategoryIdAndNotIsDeleted(categoryId: Int): List<Content>

    @Query("select c from Content c join fetch c.lastModifier where c.category.categoryId = :categoryId and c.isDeleted = false")
    fun findAllByCategoryIdAndNotIsDeletedWithLastModifier(categoryId: Int): List<Content>
}
