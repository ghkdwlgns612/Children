package com.example.yubbi.services.content.controller

import com.example.yubbi.services.content.controller.dto.response.CategoryOfContentResponseDto
import com.example.yubbi.services.content.controller.dto.response.ContentListOfOneResponseDto
import com.example.yubbi.services.content.controller.dto.response.ContentListResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/contents")
class ContentController {

    @GetMapping
    fun getContentList(@RequestParam(required = false) categoryId: Int?): ResponseEntity<ContentListResponseDto> {

        val contents = listOf(
            ContentListOfOneResponseDto(
                1,
                CategoryOfContentResponseDto(1, "책읽는TV", "책읽는TV 상세설명"),
                "웃음이 퐁퐁퐁",
                "웃음이 퐁퐁퐁 상세설명",
                "https://image.yes24.com/goods/68746076/XL",
                "https://youtu.be/PQrfV_CtmP8",
                1
            ),
            ContentListOfOneResponseDto(
                2,
                CategoryOfContentResponseDto(1, "책읽는TV", "책읽는TV 상세설명"),
                "다도와 로봇센터",
                "다도와 로봇센터 상세설명",
                "https://www.greatbooks.co.kr/image/groupbook/4030",
                "https://youtu.be/QuW4aRgFhPQ",
                2
            ),
            ContentListOfOneResponseDto(
                3,
                CategoryOfContentResponseDto(1, "책읽는TV", "책읽는TV 상세설명"),
                "고릴라 코딱지",
                "고릴라 코딱지 상세설명",
                "https://image.yes24.com/momo/TopCate341/MidCate009/34081751.jpg",
                "https://youtu.be/SRc9Dnj8oc8",
                3
            )
        )

        return ResponseEntity.ok().body(ContentListResponseDto(contents))
    }
}
