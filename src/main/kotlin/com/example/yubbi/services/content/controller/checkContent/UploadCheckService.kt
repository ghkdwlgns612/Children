package com.example.yubbi.services.content.controller.checkContent

import org.springframework.web.multipart.MultipartFile

interface UploadCheckService {
    fun checkImageAndVideo(contentId: Int?, imageFile: MultipartFile?, videoFile: MultipartFile?)
}
