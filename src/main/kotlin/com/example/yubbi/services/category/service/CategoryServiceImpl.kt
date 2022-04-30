package com.example.yubbi.services.category.service

import com.example.yubbi.services.category.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryServiceImpl(private val categoryRepository: CategoryRepository) : CategoryService
