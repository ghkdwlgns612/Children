package com.example.yubbi.services.content.service.factory.upload.ifGetContent

import com.example.yubbi.common.utils.ActiveStatus
import com.example.yubbi.common.utils.UploadStatus
import com.example.yubbi.services.content.domain.Content
import com.example.yubbi.services.content.repository.ContentRepository
import com.example.yubbi.services.member.domain.Member

class SaveUploadMethod constructor(private val contentRepository: ContentRepository) : DefineUploadMethod {

    override fun getContent(contentId: Int?, member: Member): Content {
        return contentRepository.save(
            Content(
                null, null, null, null,
                ActiveStatus.IN_ACTIVE, null, null, null, UploadStatus.UPLOADING, true, member
            )
        )
    }
}
