package com.example.disbeauty.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Service
import com.example.disbeauty.databinding.ItemMainListBinding

class ServicesAdapter(
    private val context: Context,
    private val services: List<Service>,
    private val onSelect: (service: Service) -> Unit
) : RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_main_list, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service: Service = services[position]

        with(holder) {
            binding.titleLabel.text = service.name
//            binding.subTitleLabel.text = "${service.price} ₽"
            binding.subTitleLabel.visibility = View.GONE

            binding.root.setOnClickListener {
                onSelect(service)
            }
        }
    }

    override fun getItemCount() = services.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMainListBinding = ItemMainListBinding.bind(itemView)
    }
}