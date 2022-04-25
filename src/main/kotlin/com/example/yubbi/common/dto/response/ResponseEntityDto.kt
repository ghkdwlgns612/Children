package com.example.yubbi.common.dto.response

class ResponseEntityDto<T>(val status: Int, val message: String, val data: T?)
