package com.example.yubbi.services.faq.service

import com.example.yubbi.services.faq.controller.dto.request.AdminFaqCreateRequestDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqCreateResponseDto
import com.example.yubbi.services.faq.domain.Faq
import com.example.yubbi.services.faq.repository.FaqRepository
import com.example.yubbi.services.member.domain.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FaqServiceImpl(private val faqRepository: FaqRepository) : FaqService {
    override fun createFaq(adminFaqCreateRequestDto: AdminFaqCreateRequestDto, creator: Member): AdminFaqCreateResponseDto {

        val faq = Faq(
            adminFaqCreateRequestDto.question,
            adminFaqCreateRequestDto.answer,
            creator
        )

        val createdFaq = faqRepository.save(faq)

        return AdminFaqCreateResponseDto(createdFaq.getFaqId()!!)
    }
}
