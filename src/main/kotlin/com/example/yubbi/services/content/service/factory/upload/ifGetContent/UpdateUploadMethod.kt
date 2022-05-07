package com.example.yubbi.services.content.service.factory.upload.ifGetContent

import com.example.yubbi.common.exception.custom.NotFoundContentException
import com.example.yubbi.services.content.domain.Content
import com.example.yubbi.services.content.repository.ContentRepository
import com.example.yubbi.services.member.domain.Member

class UpdateUploadMethod constructor(private val contentRepository: ContentRepository) : DefineUploadMethod {

    override fun getContent(contentId: Int?, member: Member): Content {
        return contentRepository.findById(contentId!!).orElseThrow { NotFoundContentException() }
    }
}
