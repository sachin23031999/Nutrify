package com.sachin.nutrify.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sachin.nutrify.R
import com.sachin.nutrify.ui.chat.ChatMessage
import com.sachin.nutrify.utils.Logger.Companion.d

class MessageAdapter(val currentUserId: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mChatMessage = mutableListOf<ChatMessage>()
    private var mCount = 0
    private val TAG = MessageAdapter::class.java.simpleName

    private val TYPE_SENT = 0
    private val TYPE_RECEIVED = 2

    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(msg: ChatMessage) {
            itemView.findViewById<TextView>(R.id.tvReceivedMessage).text = msg.message
            itemView.findViewById<TextView>(R.id.tvTms).text = msg.tms
        }

    }

    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(msg: ChatMessage) {
            itemView.findViewById<TextView>(R.id.tvReceivedMessage).text = msg.message
            itemView.findViewById<TextView>(R.id.tvTms).text = msg.tms
        }

    }
    private var mParent: ViewGroup? = null

    override fun getItemViewType(position: Int): Int {
        if(position < mChatMessage.size) {
            val msg = mChatMessage[position]
            return if (msg.senderId != currentUserId) TYPE_SENT else TYPE_RECEIVED
        }
        return -1
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        d(TAG, "onCreateViewHolder()")
        mParent = parent

        return if(viewType == TYPE_RECEIVED)
            ReceivedMessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_received_message, parent, false))
        else
            SentMessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_sent_message, parent, false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(position < mChatMessage.size) {
            val msg = mChatMessage[position]

            d(TAG, "onBindViewHolder(), " + msg.message)
            if (holder.itemViewType == TYPE_SENT) {
                (holder as SentMessageViewHolder).setData(msg)
            } else {
                (holder as ReceivedMessageViewHolder).setData(msg)
            }
        }

    }

    override fun getItemCount(): Int {
        mCount = mChatMessage.size
        return mChatMessage.size
    }

    fun addMessage(chatMessage: List<ChatMessage>) {
        chatMessage.sortedBy { it.tms }
        this.mChatMessage = chatMessage as MutableList<ChatMessage>

        notifyDataSetChanged()
    }
}