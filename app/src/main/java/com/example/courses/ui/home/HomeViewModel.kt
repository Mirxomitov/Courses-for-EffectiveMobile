package com.example.courses.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courses.ui.home.HomeUiState
import com.example.domain.model.CourseData
import com.example.domain.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val courseRepository: CourseRepository,
) : ViewModel() {


    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Initial)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _courses = MutableStateFlow<List<CourseData>>(emptyList())
    val courses: StateFlow<List<CourseData>> = _courses.asStateFlow()

    init {
        viewModelScope.launch {
            courseRepository.getAllCourses().fold(
                onFailure = { e ->
                    _uiState.value = HomeUiState.CoursesError(e.message ?: "Unknown error")
                },
                onSuccess = { courseList ->
                    _courses.value = courseList
                    _uiState.value = HomeUiState.CoursesSuccess
                }
            )
        }
    }
}