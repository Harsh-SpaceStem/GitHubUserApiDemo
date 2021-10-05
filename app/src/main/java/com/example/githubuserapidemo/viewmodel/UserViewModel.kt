package com.example.githubuserapidemo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserapidemo.RetrofitBuilder
import com.example.githubuserapidemo.room.UserDao
import com.example.githubuserapidemo.model.BookmarkedUsers
import com.example.githubuserapidemo.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    val users = MutableLiveData<List<Users>>()

    fun getUsers(): LiveData<List<Users>> = users

    init {
        fetchUsersFromApi()
    }

    private fun fetchUsersFromApi() {
        users.postValue(arrayListOf())
        RetrofitBuilder.apiServices.getUsersFromGithubApi(10)
            .enqueue(object : Callback<List<Users>> {
                override fun onResponse(
                    call: Call<List<Users>>,
                    response: Response<List<Users>>
                ) {
                    users.postValue(response.body())
                }

                override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                    users.postValue(arrayListOf())
                    Log.e("TAG", "onFailure: ")
                }
            })
    }

    fun bookmarkThisUser(bookmarkedUsers: BookmarkedUsers) {

        viewModelScope.launch(Dispatchers.IO) {

            userDao.bookmarkThisUser(bookmarkedUsers)

        }

    }

    fun unBookmarkThisUser(bookmarkedUsers: BookmarkedUsers) {

        viewModelScope.launch(Dispatchers.IO) {

            userDao.unbookmarkThisUser(bookmarkedUsers)
            fetchBookmarkedUsersFromDB()
        }

    }

    private val bookMarkedUsers = MutableLiveData<List<BookmarkedUsers>>()

    fun getBookMarkedUsers(): LiveData<List<BookmarkedUsers>> = bookMarkedUsers

    fun fetchBookmarkedUsersFromDB() {

        viewModelScope.launch(Dispatchers.IO) {

            bookMarkedUsers.postValue(userDao.getAllBookmarkedUsers())

        }

    }
}