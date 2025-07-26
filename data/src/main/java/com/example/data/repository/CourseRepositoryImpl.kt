package com.example.data.repository

import com.example.domain.model.CourseData
import com.example.domain.repository.CourseRepository


class CourseRepositoryImpl : CourseRepository {
    override suspend fun getAllCourses(): List<CourseData> {
        return listOf()
    }
}