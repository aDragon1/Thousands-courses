package self.adragon.thousandscourses.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import self.adragon.thousandscourses.data.model.Course

@Dao
interface CourseDAO {
    @Query("SELECT * FROM courses")
    suspend fun getALl(): List<Course>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: Course)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(courses: List<Course>)
}