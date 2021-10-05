package com.example.githubuserapidemo.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapidemo.viewmodel.ViewModelFactory
import com.example.githubuserapidemo.adapter.BookmarkAdapter
import com.example.githubuserapidemo.databinding.FragmentBookmarkBinding
import com.example.githubuserapidemo.model.BookmarkedUsers
import com.example.githubuserapidemo.room.DatabaseBuilder
import com.example.githubuserapidemo.viewmodel.UserViewModel

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var bookmarkViewModel: UserViewModel
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(layoutInflater)
        setupUI()
        setupViewModel()
        setupObserver()

        return binding.root
    }

    private fun setupViewModel() {
        bookmarkViewModel =
            ViewModelProvider(
                requireActivity(),
                ViewModelFactory(
                    DatabaseBuilder.getDBInstance(requireContext().applicationContext).getUserDao()
                )
            ).get(
                UserViewModel::class.java
            )

        bookmarkViewModel.fetchBookmarkedUsersFromDB()
    }

    private fun setupObserver() {
        bookmarkViewModel.getBookMarkedUsers().observe(requireActivity(), {
            updateProductsList(it)
        })
    }

    private fun setupUI() {

        binding.rvBookmarks.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        binding.rvBookmarks.layoutManager =
            LinearLayoutManager(context)
        bookmarkAdapter = BookmarkAdapter(requireContext(), arrayListOf()) { userToUnBookMark ->
            unBookMarkUser(userToUnBookMark)

        }

        binding.rvBookmarks.adapter = bookmarkAdapter
    }

    private fun unBookMarkUser(userToUnBookMark: BookmarkedUsers) {

        bookmarkViewModel.unBookmarkThisUser(userToUnBookMark)

    }

    private fun updateProductsList(it: List<BookmarkedUsers>) {
        bookmarkAdapter.addUsers(it)
        if (it.isEmpty()) {
            binding.rvBookmarks.visibility = View.GONE
            binding.tvEmptyBookmark.visibility = View.VISIBLE
        } else {
            binding.rvBookmarks.visibility = View.VISIBLE
            binding.tvEmptyBookmark.visibility = View.GONE
        }
    }
}