package com.sachin.nutrify.dev.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sachin.nutrify.dev.R
import com.sachin.nutrify.dev.model.FUser
import com.sachin.nutrify.dev.model.User
import com.sachin.nutrify.dev.model.UserInfo
import kotlinx.android.synthetic.main.user_info_adapter.view.*

interface UserInfoAdapterListener {
    fun onLayoutClicked(userInfo: FUser)
}

class UserInfoAdapter : RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder>() {

    private var mUserList = mutableListOf<FUser>()
    var mUserInfoAdapterListener: UserInfoAdapterListener? = null
    inner class UserInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_info_adapter, parent, false)
        return UserInfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        val userInfo = mUserList[position]
        val view = holder.itemView
        view.layoutAdapter.setOnClickListener { mUserInfoAdapterListener?.onLayoutClicked(userInfo) }

        view.tvName.text = userInfo.firstName + " " + userInfo.lastName
        //view.tvDesc.text = userInfo.desc
        //view.tvExp.text = userInfo.experience
        //view.tvPrice.text = userInfo.price
        //view.tvRatings.text = userInfo.ratings.toString()
        //view.tvChatCount.text = userInfo.totalChatDone.toString()
    }

    override fun getItemCount() = mUserList.size

    fun updateUsers(users: MutableList<FUser>) {
        this.mUserList = users
        notifyDataSetChanged()
    }

    fun getUsers() = mUserList

}