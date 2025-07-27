package com.example.domain.repository
import com.example.domain.model.CourseData

interface CourseRepository {
    suspend fun getAllCourses(): Result<List<CourseData>>
}
