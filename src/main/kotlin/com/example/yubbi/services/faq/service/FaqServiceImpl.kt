package com.example.yubbi.services.faq.service

import com.example.yubbi.common.exception.custom.NotFoundFaqException
import com.example.yubbi.services.faq.controller.dto.request.AdminFaqCreateRequestDto
import com.example.yubbi.services.faq.controller.dto.request.AdminFaqUpdateRequestDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqCreateResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqListOfOneResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqListResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqModifierResponseDto
import com.example.yubbi.services.faq.controller.dto.response.AdminFaqUpdateResponseDto
import com.example.yubbi.services.faq.controller.dto.response.FaqListOfOneResponseDto
import com.example.yubbi.services.faq.controller.dto.response.FaqListResponseDto
import com.example.yubbi.services.faq.domain.Faq
import com.example.yubbi.services.faq.repository.FaqRepository
import com.example.yubbi.services.member.domain.Member
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FaqServiceImpl(private val faqRepository: FaqRepository) : FaqService {

    @Transactional(readOnly = true)
    override fun getFaqList(page: Int, size: Int, word: String?): FaqListResponseDto {

        val pageRequest = PageRequest.of(page - 1, size, Sort.Direction.ASC, "faqId")

        val pageOfFaqs = if (word == null) {
            faqRepository.getPageOfFaqs(pageRequest)
        } else {
            faqRepository.getPageOfFaqsWithWord(pageRequest, word)
        }

        val faqListOfOneResponseDto = pageOfFaqs.map { faq ->
            FaqListOfOneResponseDto(
                faq.getFaqId()!!,
                faq.getQuestion()!!,
                faq.getAnswer()!!
            )
        }.toList()

        return FaqListResponseDto(pageOfFaqs.totalPages, page, faqListOfOneResponseDto)
    }

    override fun getAdminFaqList(page: Int, size: Int, word: String?): AdminFaqListResponseDto {
        val pageRequest = PageRequest.of(page - 1, size, Sort.Direction.ASC, "faqId")

        val pageOfFaqs = if (word == null) {
            faqRepository.getPageOfFaqs(pageRequest)
        } else {
            faqRepository.getPageOfFaqsWithWord(pageRequest, word)
        }

        val adminFaqListOfOneResponseDto = pageOfFaqs.map { faq ->

            val lastModifier = faq.getLastModifier()!!

            AdminFaqListOfOneResponseDto(
                faq.getFaqId()!!,
                faq.getQuestion()!!,
                faq.getAnswer()!!,
                faq.getCreatedAt()!!,
                faq.getLastModifiedAt()!!,
                AdminFaqModifierResponseDto(
                    lastModifier.getMemberId()!!,
                    lastModifier.getEmail()!!,
                    lastModifier.getName()!!
                )
            )
        }.toList()

        return AdminFaqListResponseDto(pageOfFaqs.totalPages, page, adminFaqListOfOneResponseDto)
    }

    override fun createFaq(adminFaqCreateRequestDto: AdminFaqCreateRequestDto, creator: Member): AdminFaqCreateResponseDto {

        val faq = Faq(
            adminFaqCreateRequestDto.question,
            adminFaqCreateRequestDto.answer,
            creator
        )

        val createdFaq = faqRepository.save(faq)

        return AdminFaqCreateResponseDto(createdFaq.getFaqId()!!)
    }

    override fun updateFaq(faqId: Int, adminFaqUpdateRequestDto: AdminFaqUpdateRequestDto, modifier: Member): AdminFaqUpdateResponseDto {
        val faq = faqRepository.findByIdNotIsDeleted(faqId).orElseThrow { NotFoundFaqException() }

        faq.setUpdateInformation(adminFaqUpdateRequestDto, modifier)
        return AdminFaqUpdateResponseDto(faq.getFaqId()!!)
    }
}
