package com.example.yubbi.services.content.repository

import com.example.yubbi.services.content.domain.Content
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContentRepository : JpaRepository<Content, Int>
