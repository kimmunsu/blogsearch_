package com.kms.blogsearch.dto

enum class SortType(val kakao: String, val naver: String) {
    ACCURACY("ACCURACY", "sim"),
    RECENCY("RECENCY", "date"),
    ;
}
