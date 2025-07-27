package com.example.courses.ui.home

sealed class HomeUiState {
    data object Initial : HomeUiState()
    data object CoursesLoading : HomeUiState()
    data object CoursesSuccess : HomeUiState()
    data class CoursesError(val message: String) : HomeUiState()
}