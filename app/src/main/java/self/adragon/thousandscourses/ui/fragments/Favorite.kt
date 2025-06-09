package self.adragon.thousandscourses.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import self.adragon.thousandscourses.R
import self.adragon.thousandscourses.data.model.FavoriteCourse
import self.adragon.thousandscourses.ui.adapters.CourseDelegate
import self.adragon.thousandscourses.ui.adapters.KAdapter
import self.adragon.thousandscourses.ui.viewmodels.FavoriteCourseViewModel

class Favorite : Fragment(R.layout.fragment_favorite) {

    private lateinit var coursesRecyclerView: RecyclerView
    private lateinit var adapter: KAdapter
    private val favoriteCourseViewModel: FavoriteCourseViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coursesRecyclerView = view.findViewById(R.id.coursesRecyclerView)
        coursesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = KAdapter(
            emptyList(),
            listOf(
                CourseDelegate(
                    {},
                    onLikeClick = { pos, course ->
                        adapter.notifyItemChanged(pos)
                        if (course.hasLike)
                            favoriteCourseViewModel.insert(FavoriteCourse(course = course))
                        else favoriteCourseViewModel.remove(course)
                    })
            )
        )

        favoriteCourseViewModel.favoriteCourseLiveData.observe(viewLifecycleOwner) { courses ->
            Log.d("mytag", "Favorite courses size - ${courses.size}")
            adapter.fillData(courses)
            coursesRecyclerView.adapter = adapter
        }
    }
}