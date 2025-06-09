package self.adragon.thousandscourses.data.repo

import self.adragon.thousandscourses.data.database.dao.FavoriteCourseDAO
import self.adragon.thousandscourses.data.model.Course
import self.adragon.thousandscourses.data.model.FavoriteCourse

class FavoriteCourseRepository(private val courseDAO: FavoriteCourseDAO) {
    fun getAll() = courseDAO.getALl()
    suspend fun insert(course: FavoriteCourse) = courseDAO.insert(course)
    suspend fun remove(course: Course) = courseDAO.remove(course.baseId)
    suspend fun dropTable() = courseDAO.drop()
}