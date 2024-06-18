package com.example.disbeauty.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Category
import com.example.disbeauty.data.dto.Service
import com.example.disbeauty.databinding.ItemServicesChangeCategoryBinding

class ServicesChangeCategoryAdapter(
    private val context: Context,
    private val categories: List<Category>,
    private val onSelect: (service: Service, isSelected: Boolean) -> Unit
) : RecyclerView.Adapter<ServicesChangeCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_services_change_category, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category: Category = categories[position]

        with(holder) {
            binding.titleLabel.text = category.name

            binding.servicesRecycler.layoutManager = LinearLayoutManager(context)

            binding.root.setOnClickListener {
                if (binding.arrowImage.rotation == 0f) {
                    binding.arrowImage.rotation = 90f

                    binding.servicesRecycler.adapter = ServicesChangeServiceAdapter(
                        context,
                        category.services ?: listOf(),
                        onSelect
                    )
                    binding.servicesRecycler.visibility = View.VISIBLE
                } else {
                    binding.arrowImage.rotation = 0f

                    binding.servicesRecycler.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount() = categories.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemServicesChangeCategoryBinding =
            ItemServicesChangeCategoryBinding.bind(itemView)
    }
}