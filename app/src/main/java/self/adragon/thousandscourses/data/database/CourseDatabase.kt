package self.adragon.thousandscourses.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import self.adragon.thousandscourses.data.database.dao.CourseDAO
import self.adragon.thousandscourses.data.database.dao.FavoriteCourseDAO
import self.adragon.thousandscourses.data.model.Course
import self.adragon.thousandscourses.data.model.FavoriteCourse

@Database(
    entities = [Course::class, FavoriteCourse::class],
    version = 2,
    exportSchema = true
)
abstract class CourseDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: CourseDatabase? = null
        private const val DB_NAME = "COURSES_TABLE.db"

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): CourseDatabase = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                CourseDatabase::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration().build()
            INSTANCE = instance
            instance
        }

        fun deleteDatabase(context: Context) {
            context.deleteDatabase(DB_NAME)
            INSTANCE = null
        }
    }

    abstract fun courseDAO(): CourseDAO
    abstract fun favoriteCourseDAO(): FavoriteCourseDAO
}