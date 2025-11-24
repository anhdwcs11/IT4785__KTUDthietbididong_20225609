package com.example.it4785_11_24_2025

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(private val apps: List<App>) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    companion object {
        private const val VIEW_TYPE_VERTICAL = 1
        private const val VIEW_TYPE_HORIZONTAL = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (apps[position].isVertical) VIEW_TYPE_VERTICAL else VIEW_TYPE_HORIZONTAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val layoutId = if (viewType == VIEW_TYPE_VERTICAL) R.layout.list_item_app else R.layout.list_item_app_horizontal
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]
        holder.appName.text = app.name
        holder.appIcon.setImageResource(app.icon)

        if (app.isVertical) {
            holder.appCategory?.text = app.category
            holder.appRating?.text = app.rating.toString()
            holder.appSize?.text = app.size
        }
    }

    override fun getItemCount() = apps.size

    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appIcon: ImageView = itemView.findViewById(R.id.app_icon)
        val appName: TextView = itemView.findViewById(R.id.app_name)
        // Nullable fields for vertical list
        val appCategory: TextView? = itemView.findViewById(R.id.app_category)
        val appRating: TextView? = itemView.findViewById(R.id.app_rating)
        val appSize: TextView? = itemView.findViewById(R.id.app_size)
    }
}