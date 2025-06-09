package self.adragon.thousandscourses.data.repo

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import self.adragon.thousandscourses.data.database.dao.CourseDAO
import self.adragon.thousandscourses.data.model.Course
import self.adragon.thousandscourses.data.retrofit.CourseAPI

class CourseRepository(private val courseDAO: CourseDAO) {
    private val baseURL = "https://drive.usercontent.google.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val courseAPI = retrofit.create(CourseAPI::class.java)

    suspend fun getAll() = courseDAO.getALl()

    suspend fun fetchCourseAPI(): List<Course> = try {
        courseAPI.getCourses().courses
    } catch (e: Exception) {
        Log.e("mytag", "Exception in CourseRepository.fetchCourseAPI()\n\n ${e.message}")
        emptyList()
    }

    suspend fun insertMany(courses: List<Course>) = courseDAO.insertMany(courses)
}