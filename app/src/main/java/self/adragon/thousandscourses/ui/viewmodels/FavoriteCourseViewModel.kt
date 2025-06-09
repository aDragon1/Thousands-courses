package self.adragon.thousandscourses.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import self.adragon.thousandscourses.data.database.CourseDatabase
import self.adragon.thousandscourses.data.model.Course
import self.adragon.thousandscourses.data.model.FavoriteCourse
import self.adragon.thousandscourses.data.repo.FavoriteCourseRepository

class FavoriteCourseViewModel(application: Application) : AndroidViewModel(application) {

    val favoriteCourseLiveData: MutableLiveData<List<FavoriteCourse>> = MutableLiveData()
    private val favoriteCourseRepository: FavoriteCourseRepository

    init {
        val db = CourseDatabase.getDatabase(application)
        favoriteCourseRepository = FavoriteCourseRepository(db.favoriteCourseDAO())
        viewModelScope.launch { favoriteCourseRepository.dropTable() }
        refreshLiveData()
    }

    private fun refreshLiveData() = CoroutineScope(Dispatchers.IO).launch {
        val items = favoriteCourseRepository.getAll()
        favoriteCourseLiveData.postValue(items)
    }

    fun insert(favoriteCourse: FavoriteCourse) = viewModelScope.launch {
        favoriteCourseRepository.insert(favoriteCourse)
        refreshLiveData()
    }

    fun remove(favoriteCourse: Course) = viewModelScope.launch {
        favoriteCourseRepository.remove(favoriteCourse)
        refreshLiveData()
    }
}