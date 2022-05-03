package com.example.yubbi.services.faq.controller.dto.request

import javax.validation.constraints.NotBlank

data class AdminFaqUpdateRequestDto(
    @field:NotBlank
    val question: String,

    @field:NotBlank
    val answer: String
)
