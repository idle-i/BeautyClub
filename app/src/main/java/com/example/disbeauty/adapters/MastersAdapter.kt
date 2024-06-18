package com.example.disbeauty.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.disbeauty.R
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.databinding.ItemMasterBinding
import com.example.disbeauty.ui.reviews.ReviewsActivity

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

            if (master.rating != 0f) {
                binding.ratingLayout.visibility = View.VISIBLE
                binding.ratingLabel.text = master.rating.toString()
            }

            Glide
                .with(context)
                .load(master.avatar)
                .error(R.color.colorSecondary)
                .into(binding.avatarImage)

            binding.ratingLayout.setOnClickListener {
                val intent = Intent(context, ReviewsActivity::class.java)
                intent.putExtra("masterId", master.id)
                (context as? BaseActivity)?.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = masters.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMasterBinding = ItemMasterBinding.bind(itemView)
    }
}