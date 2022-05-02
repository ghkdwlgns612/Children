package com.example.yubbi.services.category.repository

import com.example.yubbi.services.category.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CategoryRepository : JpaRepository<Category, Int> {

    @Query(
        "select c" +
            " from Category c" +
            " where c.activeStatus = 'ACTIVE'" +
            " and c.isDeleted = false"
    )
    fun findActiveCategories(): List<Category>
}
