package com.example.yubbi.services.category.repository

import com.example.yubbi.services.category.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface CategoryRepository : JpaRepository<Category, Int> {

    @Query("select c from Category c where c.activeStatus = 'ACTIVE' and c.isDeleted = false")
    fun findActiveCategories(): List<Category>

    @Query("select c from Category c where c.isDeleted = false")
    fun findAllNotIsDeleted(): List<Category>

    @Query("select count(c) from Category c where c.isDeleted = false")
    fun countAllNotIsDeleted(): Int

    @Query("select c from Category c join fetch c.lastModifier where c.isDeleted = false")
    fun findAllNotIsDeletedWithLastModifier(): List<Category>

    @Query("select c from Category c where c.categoryId = :categoryId and c.isDeleted = false")
    fun findByIdNotIsDeleted(categoryId: Int): Optional<Category>

    @Query("select c from Category c join fetch c.lastModifier where c.categoryId = :categoryId and c.isDeleted = false")
    fun findByIdNotIsDeletedWithLastModifier(categoryId: Int): Optional<Category>

    @Modifying
    @Query(
        "update Category c set c.priority = c.priority + 1" +
            " where c.priority >= :start and c.priority <= :end" +
            " and c.isDeleted = false"
    )
    fun bulkPriorityIncreaseInRange(start: Int, end: Int)

    @Modifying
    @Query(
        "update Category c set c.priority = c.priority - 1" +
            " where c.priority >= :start and c.priority <= :end" +
            " and c.isDeleted = false"
    )
    fun bulkPriorityDecreaseInRange(start: Int, end: Int)
}
