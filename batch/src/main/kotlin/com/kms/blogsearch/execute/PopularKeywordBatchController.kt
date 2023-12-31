package com.kms.blogsearch.execute

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/batch")
class PopularKeywordBatchController(private val popularKeywordBatchExecuteService: PopularKeywordBatchExecuteService) {

    /**
     * api execute
     */
    @PostMapping
    fun execute() {
        popularKeywordBatchExecuteService.execute()
    }

}