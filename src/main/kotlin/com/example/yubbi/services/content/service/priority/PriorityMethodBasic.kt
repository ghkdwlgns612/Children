package com.example.yubbi.services.content.service.priority

import com.example.yubbi.services.content.domain.Content
import org.springframework.stereotype.Component

@Component
class PriorityMethodBasic : PriorityMethod {
    override fun createContentPriority(priority: Int, categoryContents: List<Content>): Int {
        var changedPriority = priority
        if (changedPriority > categoryContents.size + 1) {
            changedPriority = categoryContents.size + 1
        } else {
            categoryContents.forEach() {
                if (changedPriority <= it.getPriority()!!) {
                    it.increasePriority()
                }
            }
        }
        return changedPriority
    }

    override fun updateContentPriority(oldPriority: Int?, updatePriority: Int, categoryContents: List<Content>): Int {
        var priority = updatePriority
        if (priority > categoryContents.size + 1) {
            priority = categoryContents.size + 1
        } else {
            if (oldPriority!! < updatePriority) {
                categoryContents.forEach { content ->
                    if (content.getPriority()!! in (oldPriority + 1)..updatePriority) {
                        content.decreasePriority()
                    }
                }
            } else if (oldPriority > updatePriority) {
                categoryContents.forEach { content ->
                    if (content.getPriority()!! in updatePriority until oldPriority) {
                        content.increasePriority()
                    }
                }
            }
        }
        return priority
    }

    override fun deleteContentPriority(categoryContents: List<Content>, deletedContent: Content) {
        categoryContents.forEach() { content ->
            if (deletedContent.getPriority()!! < content.getPriority()!!) {
                content.decreasePriority()
            }
        }
    }
}
