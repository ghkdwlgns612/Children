package com.example.yubbi.services.faq.controller

import com.example.yubbi.services.faq.controller.dto.response.FaqListOfOneResponseDto
import com.example.yubbi.services.faq.controller.dto.response.FaqListResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class FaqController {
    // FAQ목록조회
    @GetMapping("/faqs")
    fun getFaqListController(
        @RequestParam page: Int,
        @RequestParam size: Int?,
        @RequestParam word: String?
    ): ResponseEntity<FaqListResponseDto> {
        val faq1 = FaqListOfOneResponseDto(1, "question1", "answer1")
        val faq2 = FaqListOfOneResponseDto(2, "question2", "answer2")
        val faq3 = FaqListOfOneResponseDto(3, "question3", "answer3")

        val faqList = listOf(faq1, faq2, faq3)

        return ResponseEntity.ok().body(FaqListResponseDto(100, page, faqList))
    }
}
