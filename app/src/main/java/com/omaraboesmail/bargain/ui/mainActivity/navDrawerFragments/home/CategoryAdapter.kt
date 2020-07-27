package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.utils.NavigationFlow
import kotlinx.android.synthetic.main.category_item.view.*
import java.util.*

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryVH>() {

    private var data: List<Map<String, Any>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        return CategoryVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CategoryVH, position: Int) = holder.bind(data[position])
    fun swapData(data: List<Map<String, Any>>) {
        this.data = data
        notifyDataSetChanged()
    }

    class CategoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Map<String, Any>) = with(itemView) {
            categoryTxt.text = item["type"].toString()
            setOnClickListener {
                NavigationFlow(itemView.context).navigateToFragment(item["id"] as Int)
            }
        }
    }
}