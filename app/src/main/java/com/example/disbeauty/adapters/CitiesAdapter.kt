package com.example.disbeauty.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.disbeauty.R
import com.example.disbeauty.data.dto.City
import com.example.disbeauty.databinding.ItemCityBinding
import com.example.disbeauty.databinding.ItemMainListBinding

class CitiesAdapter(
    private val context: Context,
    private val cities: List<City>,
    private val onSelect: (service: City) -> Unit
) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_city, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city: City = cities[position]

        with(holder) {
            binding.titleLabel.text = city.name

            binding.root.setOnClickListener {
                onSelect(city)
            }
        }
    }

    override fun getItemCount() = cities.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemCityBinding = ItemCityBinding.bind(itemView)
    }
}