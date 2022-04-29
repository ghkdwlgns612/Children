package com.example.yubbi.services.member.repository

import com.example.yubbi.services.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Int> {
    fun findByEmail(email: String): Member?
}
