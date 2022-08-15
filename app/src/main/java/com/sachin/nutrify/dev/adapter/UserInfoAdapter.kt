package com.sachin.nutrify.dev.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sachin.nutrify.dev.R
import com.sachin.nutrify.dev.model.UserInfo

class UserInfoAdapter : RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder>() {

    private var mUserList = mutableListOf<UserInfo>()
    inner class UserInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_info_adapter, parent, false)
        return UserInfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {

    }

    override fun getItemCount() = mUserList.size

    fun updateUsers(users: MutableList<UserInfo>) {
        this.mUserList = users
        notifyDataSetChanged()
    }

    fun getUsers() = mUserList

}