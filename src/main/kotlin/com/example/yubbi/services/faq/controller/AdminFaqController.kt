package com.example.yubbi.services.faq.controller

import com.example.yubbi.services.faq.controller.dto.request.AdminFaqCreateRequestDto
import com.example.yubbi.services.faq.controller.dto.request.AdminFaqUpdateRequestDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqCreateResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqDeleteResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqListResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqModifierResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqUpdateResponseDto
import com.example.yubbi.services.faq.service.FaqService
import com.example.yubbi.services.member.service.MemberService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.validation.constraints.Min

@Validated
@RestController
class AdminFaqController(
    private val faqService: FaqService,
    private val memberService: MemberService
) {

    // FAQ목록조회
    @GetMapping("/admin/faqs")
    fun getFaqListController(
        @RequestHeader(HttpHeaders.AUTHORIZATION) accessToken: String?,
        @Min(1) @RequestParam(defaultValue = "1") page: Int,
        @Min(1) @RequestParam(defaultValue = "10") size: Int,
        @RequestParam word: String?
    ): ResponseEntity<AdminFaqListResponseDto> {

        memberService.getAdminMemberByAccessToken(accessToken)

        return ResponseEntity.ok().body(faqService.getAdminFaqList(page, size, word))
    }

    // FAQ단건조회 // TODO : 구현 필요
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
    fun createFaq(
        @RequestHeader(HttpHeaders.AUTHORIZATION) accessToken: String?,
        @Validated @RequestBody adminFaqCreateRequestDto: AdminFaqCreateRequestDto
    ): ResponseEntity<AdminFaqCreateResponseDto> {

        val adminMember = memberService.getAdminMemberByAccessToken(accessToken)

        val adminFaqCreateResponseDto = faqService.createFaq(adminFaqCreateRequestDto, adminMember)
        return ResponseEntity.ok().body(adminFaqCreateResponseDto)
    }

    // FAQ수정
    @PutMapping("/admin/faqs/{faqId}")
    fun updateFaq(
        @RequestHeader(HttpHeaders.AUTHORIZATION) accessToken: String?,
        @Validated @RequestBody adminUpdateRequestDto: AdminFaqUpdateRequestDto,
        @PathVariable faqId: Int
    ): ResponseEntity<AdminFaqUpdateResponseDto> {

        val adminMember = memberService.getAdminMemberByAccessToken(accessToken)

        return ResponseEntity.ok().body(faqService.updateFaq(faqId, adminUpdateRequestDto, adminMember))
    }

    // FAQ삭제
    @DeleteMapping("/admin/faqs/{faqId}")
    fun deleteFaqController(
        @RequestHeader(HttpHeaders.AUTHORIZATION) accessToken: String?,
        @PathVariable faqId: Int
    ): ResponseEntity<AdminFaqDeleteResponseDto> {

        val adminMember = memberService.getAdminMemberByAccessToken(accessToken)

        return ResponseEntity.ok().body(faqService.deleteFaq(faqId, adminMember))
    }
}
