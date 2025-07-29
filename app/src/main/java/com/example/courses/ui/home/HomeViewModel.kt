package com.example.courses.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.CoursesLogger
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
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _courses = MutableStateFlow<List<CourseData>>(emptyList())
    val courses: StateFlow<List<CourseData>> = _courses.asStateFlow()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Initial)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeCourses()
        loadCourses()
    }

    fun onFavoriteClick(course: CourseData) {
        viewModelScope.launch {
            courseRepository.toggleFavorite(course).fold(
                onSuccess = {
                    CoursesLogger.d("Toggled favorite course}")
                },
                onFailure = {
                    _uiState.value = HomeUiState.CoursesError(it.message ?: "Unknown error")
                }
            )
        }
    }

    private fun observeCourses() {
        viewModelScope.launch {
            courseRepository.courses.collect { list ->
                CoursesLogger.d("Courses updated: observeCourses() called with ${list.size} courses")
                _courses.value = list
            }
        }
    }

    private fun loadCourses() {
        _uiState.value = HomeUiState.CoursesLoading

        viewModelScope.launch {
            val result = courseRepository.getAllCourses()
            result.fold(
                onSuccess = {
                    _uiState.value = HomeUiState.CoursesSuccess
                },
                onFailure = {
                    _uiState.value = HomeUiState.CoursesError(it.message ?: "Unknown error")
                }
            )
        }
    }

    fun filterByDate() {
        viewModelScope.launch {
            courseRepository.filterByDate().fold(
                onSuccess = {
                    CoursesLogger.d("Filter toggled successfully")
                },
                onFailure = {
                    _uiState.value = HomeUiState.CoursesError(it.message ?: "Unknown error")
                }
            )
        }
    }
}
