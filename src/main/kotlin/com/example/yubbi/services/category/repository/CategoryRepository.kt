package com.example.yubbi.services.category.repository

import com.example.yubbi.services.category.domain.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Int>
