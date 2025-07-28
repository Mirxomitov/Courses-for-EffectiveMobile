package com.example.courses.ui.favorites.adapter

import com.example.domain.model.CourseData

interface FavoriteCourseItemModel {
}

data class FavoriteCourseItemWithImage(
    val data: CourseData
) : FavoriteCourseItemModel

//class CourseItemWithoutImage(
//    val data: CourseData
//) : CourseItemModel