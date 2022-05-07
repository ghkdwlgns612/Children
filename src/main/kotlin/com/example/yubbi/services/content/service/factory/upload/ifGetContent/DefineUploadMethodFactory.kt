package com.example.yubbi.services.content.service.factory.upload.ifGetContent

import com.example.yubbi.services.content.repository.ContentRepository
import org.springframework.stereotype.Component

@Component
class DefineUploadMethodFactory constructor(private val contentRepository: ContentRepository) {

    fun getDefineUploadMethod(contentId: Int?): DefineUploadMethod {
        return if (contentId == null) {
            SaveUploadMethod(contentRepository)
        } else {
            UpdateUploadMethod(contentRepository)
        }
    }
}
