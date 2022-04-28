package com.example.yubbi.services.faq.controller.dto.response

data class AdminFaqListResponseDto(
    val totalPage: Int,
    val currentPage: Int,
    val faqs: List<AdminFaqListOfOneResponseDto>
)
