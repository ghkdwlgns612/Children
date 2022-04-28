package com.example.yubbi.services.faq.controller.dto.response

import java.time.LocalDateTime

data class AdminFaqListOfOneResponseDto(
    val faqId: Int,
    val question: String,
    val answer: String,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime,
    val lastModifier: AdminFaqModifierResponseDto
)
