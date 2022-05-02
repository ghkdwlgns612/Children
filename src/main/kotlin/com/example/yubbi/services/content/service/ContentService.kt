package com.example.yubbi.services.content.service

import com.example.yubbi.services.content.controller.dto.request.AdminContentCreateRequestDto
import com.example.yubbi.services.content.controller.dto.request.AdminContentUpdateRequestDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentCreateResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentDeleteResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentUpdateResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentUploadResponseDto
import org.springframework.web.multipart.MultipartFile

interface ContentService {
    // fun getContentList() : ContentListResponseDto //이 부분은 카테고리 완성 후 작성
    // fun getAdminContentList() : AdminContentListResponseDto //이 부분은 카테고리 완성 후 작성
    // fun getAdminContent() : AdminContentResponseDto //이 부분은 카테고리 완성 후 작성
    fun uploadContent(imageFile: MultipartFile, videoFile: MultipartFile): AdminContentUploadResponseDto
    fun createContent(adminContentCreateRequestDto: AdminContentCreateRequestDto): AdminContentCreateResponseDto // 수정자 생성
    fun updateContent(adminContentUpdateRequestDto: AdminContentUpdateRequestDto): AdminContentUpdateResponseDto // 수정자도 업로드 해줘야함
    fun deleteContent(contentId: Int): AdminContentDeleteResponseDto
}