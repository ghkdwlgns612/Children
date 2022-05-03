package com.example.yubbi.services.faq.controller

import com.example.yubbi.services.faq.controller.dto.response.FaqListResponseDto
import com.example.yubbi.services.faq.service.FaqService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Min

@Validated
@RestController
class FaqController(private val faqService: FaqService) {

    // FAQ목록조회
    @GetMapping("/faqs")
    fun getFaqList(
        @Min(1) @RequestParam(defaultValue = "1") page: Int,
        @Min(1) @RequestParam(defaultValue = "10") size: Int,
        @RequestParam word: String?
    ): ResponseEntity<FaqListResponseDto> {

        return ResponseEntity.ok().body(faqService.getFaqList(page, size, word))
    }
}
