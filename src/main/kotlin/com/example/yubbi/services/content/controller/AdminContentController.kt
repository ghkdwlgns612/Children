package com.example.yubbi.services.content.controller

import com.example.yubbi.services.content.controller.dto.request.AdminContentCreateRequestDto
import com.example.yubbi.services.content.controller.dto.request.AdminContentUpdateRequestDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentCreateResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentDeleteResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentListResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentUpdateResponseDto
import com.example.yubbi.services.content.controller.dto.response.AdminContentUploadResponseDto
import com.example.yubbi.services.content.service.ContentService
import com.example.yubbi.services.member.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Validated
@RestController
@RequestMapping("/admin/contents")
class AdminContentController @Autowired constructor(private val contentService: ContentService, private val memberService: MemberService) {

    @GetMapping
    fun getContentList(
        @RequestParam categoryId: Int,
        @RequestHeader("Authorization") accessToken: String?
    ): ResponseEntity<AdminContentListResponseDto> {
        memberService.getAdminMemberByAccessToken(accessToken)
        return ResponseEntity.ok().body(contentService.getAdminContentList(categoryId))
    }

    @GetMapping("/{contentId}")
    fun getContent(
        @PathVariable contentId: Int,
        @RequestHeader("Authorization") accessToken: String?
    ): ResponseEntity<AdminContentResponseDto> {
        memberService.getAdminMemberByAccessToken(accessToken)
        return ResponseEntity.ok().body(contentService.getAdminContent(contentId))
    }

    @PostMapping("/upload")
    fun uploadContent(
        @RequestParam("image") imageFile: MultipartFile,
        @RequestParam("video") videoFile: MultipartFile,
        @RequestParam("contentId") contentId: Int?,
        @RequestHeader("Authorization") accessToken: String?
    ): ResponseEntity<AdminContentUploadResponseDto> {
        val member = memberService.getAdminMemberByAccessToken(accessToken)
        return ResponseEntity.ok().body(contentService.uploadContent(imageFile, videoFile, member, contentId))
    }

    @PostMapping
    fun createContent(
        @RequestBody adminContentCreateRequestDto: AdminContentCreateRequestDto,
        @RequestHeader("Authorization") accessToken: String?
    ): ResponseEntity<AdminContentCreateResponseDto> {
        val member = memberService.getAdminMemberByAccessToken(accessToken)
        return ResponseEntity.ok().body(contentService.createContent(adminContentCreateRequestDto, member))
    }

    @PutMapping("/{contentId}")
    fun updateContent(
        @PathVariable contentId: Int,
        @RequestBody adminContentUpdateRequestDto: AdminContentUpdateRequestDto,
        @RequestHeader("Authorization") accessToken: String?
    ): ResponseEntity<AdminContentUpdateResponseDto> {
        val member = memberService.getAdminMemberByAccessToken(accessToken)
        return ResponseEntity.ok().body(contentService.updateContent(adminContentUpdateRequestDto, member))
    }

    @DeleteMapping("/{contentId}")
    fun deleteContent(
        @PathVariable contentId: Int,
        @RequestHeader("Authorization") accessToken: String?
    ): ResponseEntity<AdminContentDeleteResponseDto> {
        val member = memberService.getAdminMemberByAccessToken(accessToken)
        return ResponseEntity.ok().body(contentService.deleteContent(contentId, member))
    }
}
