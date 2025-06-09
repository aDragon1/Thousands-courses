package self.adragon.thousandscourses.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class KAdapter(
    initialItems: List<Any>,
    private val delegates: List<AdapterDelegate>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Any> = initialItems

    fun fillData(newItems: List<Any>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val viewType = delegates.indexOfFirst { it.isForViewType(items[position]) }
        if (viewType == -1)
            throw IllegalArgumentException("No delegate found for item at position $position: ${items[position]}")

        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegates[viewType].onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[getItemViewType(position)].onBindViewHolder(holder, items[position])
    }

    override fun getItemCount(): Int = items.size
}
