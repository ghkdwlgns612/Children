package com.example.yubbi.services.member.domain

import com.example.yubbi.common.domain.BaseTime
import com.example.yubbi.common.utils.Role
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "member")
class Member constructor() : BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private val memberId: Int? = null

    @Column(name = "email", unique = true)
    private var email: String? = null

    @Column(name = "name")
    private var name: String? = null

    @Column(name = "password")
    private var password: String? = null

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private var role: Role? = null

    @Column(name = "is_deleted")
    private var isDeleted: Boolean? = null

    constructor(email: String, name: String, password: String, role: Role) : this() {
        this.email = email
        this.name = name
        this.password = password
        this.role = role
        this.setCreatedAt(LocalDateTime.now())
        this.setLastModifiedAt(LocalDateTime.now())
    }

    fun getMemberId(): Int? {
        return this.memberId
    }

    fun getEmail(): String? {
        return this.email
    }

    fun getName(): String? {
        return this.name
    }

    fun getPassword(): String? {
        return this.password
    }

    fun getRole(): Role? {
        return this.role
    }

    fun getIsDeleted(): Boolean? {
        return this.isDeleted
    }

    fun setIsDeleted(isDeleted: Boolean) {
        this.isDeleted = isDeleted
    }
}
