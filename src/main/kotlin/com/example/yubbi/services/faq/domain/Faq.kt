package com.example.yubbi.services.faq.domain

import com.example.yubbi.common.domain.BaseTime
import com.example.yubbi.services.member.domain.Member
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "faq")
class Faq constructor() : BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private val faqId: Int? = null

    @Column(name = "question", length = 1000)
    private var question: String? = null

    @Column(name = "answer", length = 1000)
    private var answer: String? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "last_modifier")
    private var lastModifier: Member? = null

    @Column(name = "is_deleted")
    private var isDeleted: Boolean? = false

    constructor(question: String, answer: String, lastModifier: Member) : this() {
        this.question = question
        this.answer = answer
        this.lastModifier = lastModifier
        this.isDeleted = false
        this.setCreatedAt(LocalDateTime.now())
        this.setLastModifiedAt(LocalDateTime.now())
    }

    fun getFaqId(): Int? {
        return this.faqId
    }

    fun getQuestion(): String? {
        return this.question
    }

    fun getAnswer(): String? {
        return this.answer
    }

    fun getLastModifier(): Member? {
        return this.lastModifier
    }

    fun getIsDeleted(): Boolean? {
        return this.isDeleted
    }

    fun setIsDeleted(isDeleted: Boolean) {
        this.isDeleted = isDeleted
    }
}
