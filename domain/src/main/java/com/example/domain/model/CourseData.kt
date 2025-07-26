package com.example.domain.model

data class CourseData(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)

data class CourseResponse(
    val courses: List<CourseData>
)
