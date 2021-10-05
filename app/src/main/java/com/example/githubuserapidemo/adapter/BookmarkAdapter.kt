package com.example.githubuserapidemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapidemo.R
import com.example.githubuserapidemo.databinding.CustomUserBinding
import com.example.githubuserapidemo.model.BookmarkedUsers

class BookmarkAdapter(
    private val context: Context,
    private var userList: List<BookmarkedUsers>, val onItemUnBookMarked: (BookmarkedUsers) -> Unit
) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    inner class BookmarkViewHolder(val binding: CustomUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = CustomUserBinding.inflate(LayoutInflater.from(context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
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

                binding.imgBookmark.setImageResource(R.drawable.ic_bookmark)

                binding.imgBookmark.setOnClickListener {
                    onItemUnBookMarked(userList[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun addUsers(data: List<BookmarkedUsers>) {
        userList = data
        notifyDataSetChanged()
    }
}