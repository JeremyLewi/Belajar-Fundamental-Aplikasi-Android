package com.example.submissionawal

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel : ViewModel() {

    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val userDetail: LiveData<DetailUserResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var lastUsername: String? = null


    fun findDetailUser(username: String) {
        if (username == lastUsername) {
            return
        }

        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username = username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                    lastUsername = username
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    Toast.makeText(null, "Failed to load data", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                Toast.makeText(null, "Failed to load data", Toast.LENGTH_SHORT).show()

            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        lastUsername = null
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}
