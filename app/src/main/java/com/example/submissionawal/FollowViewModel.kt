package com.example.submissionawal

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _userFollowers = MutableLiveData<List<User>>()
    val userFollowers: LiveData<List<User>> = _userFollowers

    private val _userFollowing = MutableLiveData<List<User>>()
    val userFollowing: LiveData<List<User>> = _userFollowing

    private val _isLoadingFollow = MutableLiveData<Boolean>()
    val isLoadingFollow: LiveData<Boolean> = _isLoadingFollow

    private var lastUsernameFollowers: String? = null
    private var lastUsernameFollowing: String? = null


    fun findUserFollowers(username: String) {
        if (username == lastUsernameFollowers) {
            return
        }

        _isLoadingFollow.value = true
        val client = ApiConfig.getApiService().getFollowers(username = username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoadingFollow.value = false
                if (response.isSuccessful) {
                    _userFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Toast.makeText(null, "Failed to load data", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoadingFollow.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                Toast.makeText(null, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun findUserFollowing(username: String) {
        if (username == lastUsernameFollowing) {
            return
        }
        _isLoadingFollow.value = true
        val client = ApiConfig.getApiService().getFollowing(username = username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoadingFollow.value = false
                if (response.isSuccessful) {
                    _userFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Toast.makeText(null, "Failed to load data", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoadingFollow.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                Toast.makeText(null, "Failed to load data", Toast.LENGTH_SHORT).show()

            }
        })
    }

    companion object {
        private const val TAG = "FollowViewModel"
    }


}