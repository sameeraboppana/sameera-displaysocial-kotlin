package com.sameera.displaysocial.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sameera.displaysocial.activity.R
import com.sameera.displaysocial.model.User
import kotlinx.android.synthetic.main.user_item_layout.view.*

class UserSearchRecyclerAdapter(private val context: Context, var users: List<User>) :
    RecyclerView.Adapter<UserSearchRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvFullName.text = users[position].fullName
        holder.tvUserName.text = users[position].userName
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tvFullName = view.fullName
        val tvUserName = view.username
    }

}