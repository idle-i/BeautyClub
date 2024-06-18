package com.example.disbeauty.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Review
import com.example.disbeauty.databinding.ItemReviewBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ReviewsAdapter(
    private val context: Context,
    private val reviews: List<Review>
) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_review, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review: Review = reviews[position]

        with(holder) {
            binding.nameLabel.text = review.userName
            binding.ratingLabel.text = review.rating.toString()

            if (review.message.isNullOrEmpty()) {
                binding.messageLabel.visibility = View.GONE
            } else {
                binding.messageLabel.visibility = View.VISIBLE
                binding.messageLabel.text = review.message
            }

            binding.dateLabel.text =
                SimpleDateFormat(
                    "dd MMM yyyy",
                    Locale.getDefault()
                ).format(review.time)
        }
    }

    override fun getItemCount() = reviews.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemReviewBinding = ItemReviewBinding.bind(itemView)
    }
}