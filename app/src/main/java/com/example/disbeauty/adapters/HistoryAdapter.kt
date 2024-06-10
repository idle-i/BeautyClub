package com.example.disbeauty.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Order
import com.example.disbeauty.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter(
    private val context: Context,
    private val orders: List<Order>,
    private val onSelect: (order: Order) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_history, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order: Order = orders[position]

        with(holder) {
            binding.serviceLabel.text = order.name
            binding.priceLabel.text = "${order.price} ₽"
            binding.namePhoneLabel.text = order.masterName

            binding.dateLabel.text =
                SimpleDateFormat(
                    "dd MMM HH:mm",
                    Locale.getDefault()
                ).format(order.time)

            binding.root.setOnClickListener {
                onSelect(order)
            }
        }
    }

    override fun getItemCount() = orders.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemHistoryBinding = ItemHistoryBinding.bind(itemView)
    }
}