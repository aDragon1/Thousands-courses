package self.adragon.thousandscourses.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import self.adragon.thousandscourses.data.database.CourseDatabase
import self.adragon.thousandscourses.data.model.Course
import self.adragon.thousandscourses.data.repo.CourseRepository

class CourseViewModel(application: Application) : AndroidViewModel(application) {

    private val _courseStateFlow = MutableStateFlow<List<Course>>(emptyList())
    val courseStateFlow = _courseStateFlow.asStateFlow()

    private val courseRepository: CourseRepository
    private var byDescending = false

    init {
        val db = CourseDatabase.getDatabase(application)
        courseRepository = CourseRepository(db.courseDAO())

        viewModelScope.launch {
            val localCourses = courseRepository.getAll()
            if (localCourses.isNotEmpty()) _courseStateFlow.value = localCourses
            else {
                val courses = courseRepository.fetchCourseAPI()
                Log.d("mytag", "Api courses size - ${courses.size}")
                courseRepository.insertMany(courses)
                _courseStateFlow.value = courses
            }
        }
    }

    fun sortCourses() {
        val newItems = if (byDescending) _courseStateFlow.value.sortedBy { it.publishDate }
        else _courseStateFlow.value.sortedByDescending { it.publishDate }

        _courseStateFlow.value = newItems
        byDescending = !byDescending
    }
}