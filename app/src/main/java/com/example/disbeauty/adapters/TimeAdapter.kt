package com.example.disbeauty.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.disbeauty.R
import com.example.disbeauty.databinding.ItemTimeBinding
import java.util.Calendar

class TimeAdapter(
    private val context: Context,
    private val startTime: Int,
    private val endTime: Int,
    private val appointments: List<Long>,
    private val calendar: Calendar,
    private val onSelect: (hour: Int) -> Unit
) : RecyclerView.Adapter<TimeAdapter.ViewHolder>() {

    private var selectedTime: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_time, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPosition: Int = holder.adapterPosition

        val hour: Int = (startTime + currentPosition)

        val calendarCopy: Calendar = Calendar.getInstance()
        calendarCopy.timeInMillis = calendar.timeInMillis
        calendarCopy.set(Calendar.HOUR_OF_DAY, hour)

        val busy: Boolean = appointments.contains(calendarCopy.timeInMillis)

        with(holder) {
            binding.root.apply {
                text = "$hour:00"

                paintFlags =
                    if (busy) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                setBackgroundResource(
                    if (selectedTime == currentPosition) R.drawable.shape_time_selected_bg
                    else R.drawable.shape_time_bg
                )

                setTextColor(
                    ColorStateList.valueOf(
                        context.getColor(
                            if (selectedTime == currentPosition) R.color.colorWhite
                            else R.color.colorText
                        )
                    )
                )

                if (!busy)
                    setOnClickListener {
                        selectedTime = currentPosition

                        notifyDataSetChanged()

                        onSelect(hour)
                    }
            }
        }
    }

    override fun getItemCount() = (endTime - startTime + 1)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemTimeBinding = ItemTimeBinding.bind(itemView)
    }
}