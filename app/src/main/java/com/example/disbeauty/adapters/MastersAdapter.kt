package com.example.disbeauty.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.databinding.ItemMasterBinding

class MastersAdapter(
    private val context: Context,
    private val masters: List<Master>
) : RecyclerView.Adapter<MastersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_master, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val master: Master = masters[position]

        with(holder) {
            binding.nameLabel.text = master.name

            Glide
                .with(context)
                .load(master.avatar)
                .error(R.color.colorSecondary)
                .into(binding.avatarImage)
        }
    }

    override fun getItemCount() = masters.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMasterBinding = ItemMasterBinding.bind(itemView)
    }
}