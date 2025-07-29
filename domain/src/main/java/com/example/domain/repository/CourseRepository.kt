package com.example.domain.repository
import com.example.domain.model.CourseData
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    val courses : Flow<List<CourseData>>
    val favoriteCourses : Flow<List<CourseData>>

    suspend fun getAllCourses(): Result<List<CourseData>>
    suspend fun toggleFavorite(course: CourseData) : Result<Unit>
    suspend fun filterByDate() : Result<Unit>

}
