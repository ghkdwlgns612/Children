package com.example.yubbi.services.faq.service

import com.example.yubbi.services.faq.repository.FaqRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FaqServiceImpl(private val faqRepository: FaqRepository) : FaqService
