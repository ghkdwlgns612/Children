package com.example.yubbi.services.category.repository

import com.example.yubbi.services.category.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface CategoryRepository : JpaRepository<Category, Int> {

    @Query("select c from Category c where c.activeStatus = 'ACTIVE' and c.isDeleted = false")
    fun findActiveCategories(): List<Category>

    @Query("select c from Category c where c.isDeleted = false")
    fun findAllNotIsDeleted(): List<Category>

    @Query("select c from Category c where c.categoryId = :categoryId and c.isDeleted = false")
    fun findByIdNotIsDeleted(categoryId: Int): Optional<Category>
}
