package com.example.githubuserapidemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapidemo.R
import com.example.githubuserapidemo.databinding.CustomUserBinding
import com.example.githubuserapidemo.model.Users

class UserAdapter(
    private val context: Context,
    private var userList: List<Users>,
    val onUserBookMarked: (Int) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: CustomUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = CustomUserBinding.inflate(LayoutInflater.from(context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with(holder) {
            with(userList[position]) {

                binding.tvUserName.text = login
                Glide.with(itemView.context).load(avatar_url)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                    )
                    .into(binding.imgUser)
                binding.imgBookmark.setImageResource(R.drawable.ic_bookmark_outline)

                if (isBookmarked) {
                    binding.imgBookmark.setImageResource(R.drawable.ic_bookmark)
                }

                binding.imgBookmark.setOnClickListener {
                    onUserBookMarked(position)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun addUsers(data: List<Users>) {
        userList = data
        notifyItemRangeChanged(0, userList.size)
    }

    fun getLatestList(): List<Users> {
        return userList
    }
}