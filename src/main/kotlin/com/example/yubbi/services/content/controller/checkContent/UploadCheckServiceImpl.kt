package com.example.yubbi.services.content.controller.checkContent

import com.example.yubbi.common.exception.custom.NotEnoughInfoUploadContentForCreateException
import com.example.yubbi.common.exception.custom.NotEnoughInfoUploadContentForUpdateException
import com.example.yubbi.common.exception.custom.NotMatchImageTypeException
import com.example.yubbi.common.exception.custom.NotMatchVideoTypeException
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class UploadCheckServiceImpl : UploadCheckService {

    private val separator = "/"

    override fun checkImageAndVideo(contentId: Int?, imageFile: MultipartFile?, videoFile: MultipartFile?) {
        if (contentId != null && (imageFile == null || imageFile.isEmpty) && (videoFile == null || videoFile.isEmpty)) {
            throw NotEnoughInfoUploadContentForUpdateException()
        }
        if (contentId == null && (imageFile == null || imageFile.isEmpty || videoFile == null || videoFile.isEmpty)) {
            throw NotEnoughInfoUploadContentForCreateException()
        }
        if (imageFile != null && imageFile.contentType!!.split(separator)[0] != "image") {
            throw NotMatchImageTypeException()
        }
        if (videoFile != null && videoFile.contentType!!.split(separator)[0] != "video") {
            throw NotMatchVideoTypeException()
        }
    }
}
