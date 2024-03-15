package com.faizikhwan.shimmerdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faizikhwan.shimmerdemo.databinding.RowUserListBinding

class UserListAdapter(
    private val context: Context,
    private var userList: List<User>
) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    inner class UserListViewHolder(
        val binding: RowUserListBinding
    ) : RecyclerView.ViewHolder(binding.root)

    fun swapData(newList: List<User>) {
        this.userList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding = RowUserListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserListViewHolder(binding)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        with(holder) {
            with(userList[position]) {
                Glide.with(context).load(iconUrl).into(binding.userIcon)
                binding.userName.text = name
                binding.userStatus.text = status
            }
        }
    }
}