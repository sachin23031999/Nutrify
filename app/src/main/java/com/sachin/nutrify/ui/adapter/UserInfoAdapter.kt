package com.sachin.nutrify.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sachin.nutrify.R
import com.sachin.nutrify.model.FUser

interface UserInfoAdapterListener {
    fun onLayoutClicked(userInfo: FUser)
}

class UserInfoAdapter : RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder>() {

    private var mUserList = mutableListOf<FUser>()
    var mUserInfoAdapterListener: UserInfoAdapterListener? = null

    inner class UserInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_info_adapter, parent, false)
        return UserInfoViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        val userInfo = mUserList[position]
        val view = holder.itemView
        view.findViewById<RelativeLayout>(R.id.layoutAdapter)
            .setOnClickListener { mUserInfoAdapterListener?.onLayoutClicked(userInfo) }

        view.findViewById<TextView>(R.id.tvName).text = userInfo.firstName + " " + userInfo.lastName
        // view.tvDesc.text = userInfo.desc
        // view.tvExp.text = userInfo.experience
        // view.tvPrice.text = userInfo.price
        // view.tvRatings.text = userInfo.ratings.toString()
        // view.tvChatCount.text = userInfo.totalChatDone.toString()
    }

    override fun getItemCount() = mUserList.size

    fun updateUsers(users: MutableList<FUser>) {
        this.mUserList = users
        notifyDataSetChanged()
    }

    fun getUsers() = mUserList
}
