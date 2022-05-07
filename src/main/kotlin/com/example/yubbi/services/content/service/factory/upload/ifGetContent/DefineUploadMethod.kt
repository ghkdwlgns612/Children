package com.example.yubbi.services.content.service.factory.upload.ifGetContent

import com.example.yubbi.services.content.domain.Content
import com.example.yubbi.services.member.domain.Member

interface DefineUploadMethod {
    fun getContent(contentId: Int?, member: Member): Content
}
