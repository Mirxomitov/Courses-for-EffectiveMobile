package com.example.courses.ui.home.adapter

import com.example.domain.model.CourseData

interface CourseItemModel {
}

data class CourseItemWithImage(
    val data: CourseData
) : CourseItemModel

//class CourseItemWithoutImage(
//    val data: CourseData
//) : CourseItemModel