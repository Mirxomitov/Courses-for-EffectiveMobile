package com.example.courses.ui.favorites

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
class FavoritesViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _favoriteCourses = MutableStateFlow<List<CourseData>>(emptyList())
    val favoriteCourses: StateFlow<List<CourseData>> = _favoriteCourses.asStateFlow()

    private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Initial)
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        observeCourses()
    }

    fun onFavoriteClick(course: CourseData) {
        viewModelScope.launch {
            courseRepository.toggleFavorite(course).fold(
                onSuccess = {
                    CoursesLogger.d("Toggled favorite course}")
                },
                onFailure = {
                    _uiState.value = FavoritesUiState.CoursesError(it.message ?: "Unknown error")
                }
            )
        }
    }

    private fun observeCourses() {
        viewModelScope.launch {
            courseRepository.favoriteCourses.collect { list ->
                CoursesLogger.d("Courses updated: observeCourses() called with ${list.size} courses")
                _favoriteCourses.value = list
            }
        }
    }
}
