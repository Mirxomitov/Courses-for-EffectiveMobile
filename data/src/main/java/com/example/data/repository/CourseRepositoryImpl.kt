package com.example.data.repository

import com.example.domain.model.CourseData
import com.example.domain.repository.CourseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CourseRepositoryImpl : CourseRepository {
    private val _courses = MutableStateFlow<List<CourseData>>(emptyList())
    override val courses: StateFlow<List<CourseData>> = _courses.asStateFlow()

    override val favoriteCourses: StateFlow<List<CourseData>> =
        _courses.map { courses ->
            courses.filter { it.hasLike }
        }.stateIn(
            scope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    override suspend fun getAllCourses(): Result<List<CourseData>> {
        val fetched = simulateFetchingFromInternet()
        _courses.value = fetched

        return Result.success(_courses.value)
    }

    override suspend fun toggleFavorite(course: CourseData): Result<Unit> {
        val toggledCourse = _courses.value.map {
            if (it.id == course.id) {
                it.copy(hasLike = !it.hasLike)
            } else {
                it
            }
        }
        _courses.value = toggledCourse
        return Result.success(Unit)
    }

    override suspend fun filterByDate() {
        val sortedCourses = _courses.value.sortedBy { it.startDate }
        _courses.value = sortedCourses
    }

    private suspend fun simulateFetchingFromInternet(): List<CourseData> {
        delay(500)
        return listOf(
            CourseData(
                id = 100,
                title = "Java-разработчик с нуля",
                text = "Освойте backend-разработку и программирование на Java, фреймворки Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, собрав портфолио и став востребованным специалистом для любой IT компании.",
                price = "999",
                rate = "4.9",
                startDate = "2024-05-22",
                hasLike = false,
                publishDate = "2024-02-02",
                imageAsset = "image100.png"
            ),
            CourseData(
                id = 101,
                title = "3D-дженералист",
                text = "Освой профессию 3D-дженералиста и стань универсальным специалистом, который умеет создавать 3D-модели, текстуры и анимации, а также может строить карьеру в геймдеве, кино, рекламе или дизайне.",
                price = "12 000",
                rate = "3.9",
                startDate = "2024-09-10",
                hasLike = false,
                publishDate = "2024-01-20",
                imageAsset = "image101.png"
            ),
            CourseData(
                id = 102,
                title = "Python Advanced. Для продвинутых",
                text = "Вы узнаете, как разрабатывать гибкие и высокопроизводительные серверные приложения на языке Kotlin. Преподаватели на вебинарах покажут пример того, как разрабатывается проект маркетплейса: от идеи и постановки задачи – до конечного решения",
                price = "1 299",
                rate = "4.3",
                startDate = "2024-10-12",
                hasLike = true,
                publishDate = "2024-08-10",
                imageAsset = "image102.png"
            ),
            CourseData(
                id = 103,
                title = "Системный аналитик",
                text = "Освоите навыки системной аналитики с нуля за 9 месяцев. Будет очень много практики на реальных проектах, чтобы вы могли сразу стартовать в IT.",
                price = "1 199",
                rate = "4.5",
                startDate = "2024-04-15",
                hasLike = false,
                publishDate = "2024-01-13",
                imageAsset = "image103.png"
            ),
            CourseData(
                id = 104,
                title = "Аналитик данных",
                text = "В этом уроке вы узнаете, кто такой аналитик данных и какие задачи он решает. А главное — мы расскажем, чему вы научитесь по завершении программы обучения профессии «Аналитик данных».",
                price = "899",
                rate = "4.7",
                startDate = "2024-06-20",
                hasLike = false,
                publishDate = "2024-03-12",
                imageAsset = "image104.png"
            )
        )
    }
}
