package self.adragon.thousandscourses.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import self.adragon.thousandscourses.R
import self.adragon.thousandscourses.data.model.Course
import self.adragon.thousandscourses.data.model.FavoriteCourse
import self.adragon.thousandscourses.utils.Utils

class CourseDelegate(
    private val onMoreClick: (Course) -> Unit,
    private val onLikeClick: (Int, Course) -> Unit,
) : AdapterDelegate {


    class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.titleTextView)
        val description: TextView = view.findViewById(R.id.descriptionTextView)
        val rating: TextView = view.findViewById(R.id.ratingTextView)
        val date: TextView = view.findViewById(R.id.dateTextView)
        val price: TextView = view.findViewById(R.id.priceTextView)
        val more: ImageButton = view.findViewById(R.id.moreImageButton)
        val coverImage: ImageView = view.findViewById(R.id.coverImageButton)
        val likeImage: ImageView = view.findViewById(R.id.hasLikeImageView)
    }


    override fun isForViewType(item: Any) = item is Course || item is FavoriteCourse

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_adapter_item, parent, false)
        return CourseViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Any) {
        val course = if (item is FavoriteCourse) item.course else item as Course
        val vh = holder as CourseViewHolder

        vh.title.text = course.title
        vh.description.text = course.text
        vh.rating.text = "★ ${course.rate}"
        vh.date.text = Utils.formatDate(course.publishDate)
        vh.price.text = course.price

        vh.likeImage.setImageResource(
            if (course.hasLike) R.drawable.ic_bookmark_fill else R.drawable.ic_bookmark
        )

        vh.more.setOnClickListener { onMoreClick(course) }
        vh.likeImage.setOnClickListener {
            course.hasLike = !course.hasLike
            onLikeClick(holder.adapterPosition, course)
        }
    }
}