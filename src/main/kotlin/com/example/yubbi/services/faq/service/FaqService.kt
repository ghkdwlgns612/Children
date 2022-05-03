package com.example.yubbi.services.faq.service

import com.example.yubbi.services.faq.controller.dto.request.AdminFaqCreateRequestDto
import com.example.yubbi.services.faq.controller.dto.request.AdminFaqUpdateRequestDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqCreateResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqListResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqUpdateResponseDto
import com.example.yubbi.services.faq.controller.dto.response.FaqListResponseDto
import com.example.yubbi.services.member.domain.Member

interface FaqService {

    fun getFaqList(page: Int, size: Int, word: String?): FaqListResponseDto
    fun getAdminFaqList(page: Int, size: Int, word: String?): AdminFaqListResponseDto
    fun createFaq(adminFaqCreateRequestDto: AdminFaqCreateRequestDto, creator: Member): AdminFaqCreateResponseDto
    fun updateFaq(faqId: Int, adminFaqUpdateRequestDto: AdminFaqUpdateRequestDto, modifier: Member): AdminFaqUpdateResponseDto
}
