package com.example.yubbi.services.faq.controller

import com.example.yubbi.services.faq.controller.dto.request.AdminFaqCreateRequestDto
import com.example.yubbi.services.faq.controller.dto.request.AdminFaqUpdateRequestDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqCreateResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqDeleteResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqListOfOneResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqListResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqModifierResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqUpdateResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class AdminFaqController {

    // FAQ목록조회
    @GetMapping("/admin/faqs")
    fun getFaqListController(
        @RequestParam page: Int,
        @RequestParam size: Int?,
        @RequestParam word: String?
    ): ResponseEntity<AdminFaqListResponseDto> {
        val faq1 = AdminFaqListOfOneResponseDto(
            1, "question1", "answer1",
            LocalDateTime.now(), LocalDateTime.now(), AdminFaqModifierResponseDto(1, "email1", "name1")
        )
        val faq2 = AdminFaqListOfOneResponseDto(
            2, "question2", "answer2",
            LocalDateTime.now(), LocalDateTime.now(), AdminFaqModifierResponseDto(2, "email2", "name2")
        )
        val faq3 = AdminFaqListOfOneResponseDto(
            3, "question3", "answer3",
            LocalDateTime.now(), LocalDateTime.now(), AdminFaqModifierResponseDto(3, "email3", "name3")
        )

        val faqList = listOf(faq1, faq2, faq3)

        return ResponseEntity.ok().body(AdminFaqListResponseDto(100, page, faqList))
    }

    // FAQ단건조회
    @GetMapping("/admin/faqs/{faqId}")
    fun getFaqController(@PathVariable faqId: Int): ResponseEntity<AdminFaqResponseDto> {
        val modifier = AdminFaqModifierResponseDto(1, "email", "name")

        return ResponseEntity.ok().body(
            AdminFaqResponseDto(
                faqId, "question", "answer",
                LocalDateTime.now(), LocalDateTime.now(), modifier
            )
        )
    }

    // FAQ등록
    @PostMapping("/admin/faqs")
    fun createFaqController(@RequestBody adminCreateRequestDto: AdminFaqCreateRequestDto): ResponseEntity<AdminFaqCreateResponseDto> {
        return ResponseEntity.ok().body(AdminFaqCreateResponseDto(1))
    }

    // FAQ수정
    @PutMapping("/admin/faqs/{faqId}")
    fun updateFaqController(
        @RequestBody adminUpdateRequestDto: AdminFaqUpdateRequestDto,
        @PathVariable faqId: Int
    ): ResponseEntity<AdminFaqUpdateResponseDto> {
        return ResponseEntity.ok().body(AdminFaqUpdateResponseDto(faqId))
    }

    // FAQ삭제
    @DeleteMapping("/admin/faqs/{faqId}")
    fun deleteFaqController(@PathVariable faqId: Int): ResponseEntity<AdminFaqDeleteResponseDto> {
        return ResponseEntity.ok().body(AdminFaqDeleteResponseDto(faqId))
    }
}
