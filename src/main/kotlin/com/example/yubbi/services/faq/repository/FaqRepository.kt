package com.example.yubbi.services.faq.repository

import com.example.yubbi.services.faq.domain.Faq
import org.springframework.data.jpa.repository.JpaRepository

interface FaqRepository : JpaRepository<Faq, Int>
