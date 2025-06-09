package self.adragon.thousandscourses.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import self.adragon.thousandscourses.R
import self.adragon.thousandscourses.data.model.FavoriteCourse
import self.adragon.thousandscourses.ui.adapters.CourseDelegate
import self.adragon.thousandscourses.ui.adapters.KAdapter
import self.adragon.thousandscourses.ui.viewmodels.CourseViewModel
import self.adragon.thousandscourses.ui.viewmodels.FavoriteCourseViewModel

class Home : Fragment(R.layout.fragment_home) {

    private lateinit var sortImageButton: ImageButton

    private lateinit var coursesRecyclerView: RecyclerView
    private lateinit var adapter: KAdapter

    private val courseViewModel: CourseViewModel by activityViewModels()
    private val favoriteCourseViewModel: FavoriteCourseViewModel by activityViewModels()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sortImageButton = view.findViewById(R.id.sortImageButton)
        sortImageButton.setOnClickListener {
            courseViewModel.sortCourses()
        }

        coursesRecyclerView = view.findViewById(R.id.coursesRecyclerView)
        coursesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = KAdapter(
            emptyList(), listOf(
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                courseViewModel.courseStateFlow.collect { courses ->
                    courses.filter { it.hasLike }
                        .forEach { favoriteCourseViewModel.insert(FavoriteCourse(course = it)) }
                    adapter.fillData(courses)
                    coursesRecyclerView.adapter = adapter
                }
            }

        }
    }
}