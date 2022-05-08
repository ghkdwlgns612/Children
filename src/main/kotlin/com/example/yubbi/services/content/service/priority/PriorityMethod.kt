package com.example.yubbi.services.content.service.priority

import com.example.yubbi.services.content.domain.Content

interface PriorityMethod {
    fun createContentPriority(priority: Int, categoryContents: List<Content>): Int
    fun updateContentPriority(oldPriority: Int?, updatePriority: Int, categoryContents: List<Content>): Int
    fun deleteContentPriority(categoryContents: List<Content>, deletedContent: Content)
}
