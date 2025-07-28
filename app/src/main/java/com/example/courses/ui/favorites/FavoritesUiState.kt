package com.example.courses.ui.favorites

sealed class FavoritesUiState {
    data object Initial : FavoritesUiState()
    data object CoursesLoading : FavoritesUiState()
    data object CoursesSuccess : FavoritesUiState()
    data class CoursesError(val message: String) : FavoritesUiState()
}