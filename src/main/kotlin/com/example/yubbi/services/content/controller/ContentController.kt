package com.example.yubbi.services.content.controller

import com.example.yubbi.services.content.controller.dto.response.ContentListResponseDto
import com.example.yubbi.services.content.service.ContentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/contents")
class ContentController constructor(private val contentService: ContentService) {

    @GetMapping
    fun getContentList(@RequestParam categoryId: Int): ResponseEntity<ContentListResponseDto> {
        return ResponseEntity.ok().body(contentService.getContentList(categoryId))
    }
}
