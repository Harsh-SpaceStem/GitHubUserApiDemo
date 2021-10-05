package com.example.githubuserapidemo.views

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapidemo.viewmodel.ViewModelFactory
import com.example.githubuserapidemo.adapter.UserAdapter
import com.example.githubuserapidemo.databinding.FragmentUserBinding
import com.example.githubuserapidemo.model.BookmarkedUsers
import com.example.githubuserapidemo.model.Users
import com.example.githubuserapidemo.room.DatabaseBuilder
import com.example.githubuserapidemo.viewmodel.UserViewModel


class UserFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: FragmentUserBinding
    private lateinit var userAdapter: UserAdapter

    var isLoading = false
    private var PAGE_START = 1

    override fun onStart() {
        super.onStart()
        if (!isNetworkAvailable()) {
            binding.refreshLayout.visibility = View.GONE
            binding.tvOfflineMessage.visibility = View.VISIBLE
        } else {
            binding.refreshLayout.visibility = View.VISIBLE
            binding.tvOfflineMessage.visibility = View.GONE
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isAvailable && info.isConnected
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(layoutInflater)
        setupUI()
        setupViewModel()
        setupObserver()

        return binding.root
    }

    private fun addOrRemoveUserFromBookmarks(pos: Int) {

        if (!userAdapter.getLatestList()[pos].isBookmarked) {
            userViewModel.bookmarkThisUser(
                BookmarkedUsers(
                    userAdapter.getLatestList()[pos].id,
                    userAdapter.getLatestList()[pos].login,
                    userAdapter.getLatestList()[pos].avatar_url
                )
            )
            userAdapter.getLatestList()[pos].isBookmarked = true
        } else {
            userViewModel.unBookmarkThisUser(
                BookmarkedUsers(
                    userAdapter.getLatestList()[pos].id,
                    userAdapter.getLatestList()[pos].login,
                    userAdapter.getLatestList()[pos].avatar_url
                )
            )
            userAdapter.getLatestList()[pos].isBookmarked = false
        }

        userAdapter.notifyItemChanged(pos)

    }

    private fun setupViewModel() {
        userViewModel =
            ViewModelProvider(
                requireActivity(),
                ViewModelFactory(
                    DatabaseBuilder.getDBInstance(requireContext().applicationContext).getUserDao()
                )
            ).get(UserViewModel::class.java)
    }

    private fun setupObserver() {
        userViewModel.getUsers().observe(requireActivity(), {
            updateProductsList(it)
        })
    }

    private fun updateProductsList(it: List<Users>) {
        userAdapter.addUsers(it)
        userAdapter.notifyItemRangeChanged(0, it.size)
        if (binding.refreshLayout.isRefreshing) {
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun setupUI() {

        binding.refreshLayout.setOnRefreshListener {
            setupObserver()
        }
        binding.refreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        binding.rvUsers.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        binding.rvUsers.layoutManager =
            LinearLayoutManager(context)
        userAdapter = UserAdapter(requireContext(), arrayListOf()) {
            addOrRemoveUserFromBookmarks(it)
        }

        binding.rvUsers.adapter = userAdapter
    }
}