package com.example.yubbi.services.faq.controller.dto.response

data class FaqListResponseDto(
    val totalPage: Int,
    val currentPage: Int,
    val faqs: List<FaqListOfOneResponseDto>
)
