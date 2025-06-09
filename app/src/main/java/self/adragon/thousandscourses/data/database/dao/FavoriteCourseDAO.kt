package self.adragon.thousandscourses.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import self.adragon.thousandscourses.data.model.FavoriteCourse

@Dao
interface FavoriteCourseDAO {
    @Query("SELECT * FROM favorite_courses")
    fun getALl(): List<FavoriteCourse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: FavoriteCourse)

    @Query("DELETE FROM favorite_courses where baseId = :courseBaseID")
    suspend fun remove(courseBaseID: Int)

    @Query("DELETE FROM favorite_courses WHERE 1=1")
    suspend fun drop()
}