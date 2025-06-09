package self.adragon.thousandscourses.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_courses")
data class FavoriteCourse(
    @PrimaryKey(true)
    val favoriteCourseId: Int = 0,
    @Embedded val course: Course
)