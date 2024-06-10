package com.example.disbeauty.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.disbeauty.R
import com.example.disbeauty.data.TempData
import com.example.disbeauty.data.dto.Service
import com.example.disbeauty.databinding.ItemServicesChangeServiceBinding

class ServicesChangeServiceAdapter(
    private val context: Context,
    private val services: List<Service>,
    private val onSelect: (service: Service, isSelected: Boolean) -> Unit
) : RecyclerView.Adapter<ServicesChangeServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_services_change_service, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service: Service = services[position]

        with(holder) {
            binding.titleLabel.text = service.name
            binding.serviceCheckbox.isChecked =
                TempData.currentMaster.services?.contains(service.id) ?: false

            binding.root.setOnClickListener {
                binding.serviceCheckbox.isChecked = !binding.serviceCheckbox.isChecked

                onSelect(service, binding.serviceCheckbox.isChecked)
            }
        }
    }

    override fun getItemCount() = services.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemServicesChangeServiceBinding =
            ItemServicesChangeServiceBinding.bind(itemView)
    }
}