package com.example.yubbi.services.faq.repository

import com.example.yubbi.services.faq.domain.Faq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface FaqRepository : JpaRepository<Faq, Int> {

    @Query("select f from Faq f where f.isDeleted = false")
    fun getPageOfFaqs(pageable: Pageable): Page<Faq>

    @Query(
        "select f" +
            " from Faq f" +
            " where ( f.question like %:word% or f.answer like %:word% )" +
            " and f.isDeleted = false"
    )
    fun getPageOfFaqsWithWord(pageable: Pageable, word: String): Page<Faq>

    @Query("select f from Faq f where f.faqId = :faqId and f.isDeleted = false")
    fun findByIdNotIsDeleted(faqId: Int): Optional<Faq>
}
