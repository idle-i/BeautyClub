package com.example.disbeauty.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Category
import com.example.disbeauty.databinding.ItemMainListBinding

class CategoriesAdapter(
    private val context: Context,
    private val categories: List<Category>,
    private val onSelect: (service: Category) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_main_list, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category: Category = categories[position]

        with(holder) {
            binding.titleLabel.text = category.name
            binding.subTitleLabel.visibility = View.GONE

            binding.root.setOnClickListener {
                onSelect(category)
            }
        }
    }

    override fun getItemCount() = categories.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMainListBinding = ItemMainListBinding.bind(itemView)
    }
}