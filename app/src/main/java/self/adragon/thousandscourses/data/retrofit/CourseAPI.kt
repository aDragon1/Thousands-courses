package self.adragon.thousandscourses.data.retrofit

import retrofit2.http.GET
import self.adragon.thousandscourses.data.model.Course
import self.adragon.thousandscourses.data.model.CourseResponse

interface CourseAPI {
    @GET("u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourses(): CourseResponse
}