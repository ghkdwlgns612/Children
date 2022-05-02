package com.example.yubbi.services.faq.service

import com.example.yubbi.services.faq.controller.dto.request.AdminFaqCreateRequestDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqCreateResponseDto
import com.example.yubbi.services.faq.controller.dto.response.FaqListResponseDto
import com.example.yubbi.services.member.domain.Member

interface FaqService {

    fun getFaqList(page: Int, size: Int, word: String?): FaqListResponseDto
    fun createFaq(adminFaqCreateRequestDto: AdminFaqCreateRequestDto, creator: Member): AdminFaqCreateResponseDto
}
