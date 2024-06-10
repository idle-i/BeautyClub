package com.example.disbeauty.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Category
import com.example.disbeauty.data.dto.Message
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.example.disbeauty.databinding.ItemChatMessageBinding
import com.example.disbeauty.databinding.ItemMainListBinding
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class ChatAdapter(
    private val context: Context,
    private val messages: List<Message>
) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_chat_message, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message: Message = messages[position]

        with(holder) {
            binding.outgoingMessage.visibility = View.GONE
            binding.incomingMessage.visibility = View.GONE
            binding.systemMessageLabel.visibility = View.GONE

            if (message.fromUserId == FirebaseInstances.auth.currentUser?.uid) {
                binding.outgoingMessage.visibility = View.VISIBLE
                binding.outgoingMessageLabel.text = message.text
                binding.outgoingMessageTime.text = SimpleDateFormat(
                    "HH:mm",
                    Locale.getDefault()
                ).format(message.timestamp)
            } else {
                binding.incomingMessage.visibility = View.VISIBLE
                binding.incomingMessageLabel.text = message.text
                binding.incomingMessageTime.text = SimpleDateFormat(
                    "HH:mm",
                    Locale.getDefault()
                ).format(message.timestamp)
            }
        }
    }

    override fun getItemCount() = messages.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemChatMessageBinding = ItemChatMessageBinding.bind(itemView)
    }
}