package com.example.submissionawal.ui.detail

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawal.data.local.entity.FavoriteUser
import com.example.submissionawal.data.remote.response.DetailUserResponse
import com.example.submissionawal.data.remote.retrofit.ApiConfig
import com.example.submissionawal.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel(application: Application) : ViewModel() {

    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val userDetail: LiveData<DetailUserResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var lastUsername: String? = null

    private val mfavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getFavoriteUser(username: String): LiveData<FavoriteUser> =
        mfavoriteRepository.getFavoriteUser(username)

    fun insert(favoriteUser: FavoriteUser) {
        CoroutineScope(Dispatchers.IO).launch {
            mfavoriteRepository.insert(favoriteUser)
        }
    }

    fun delete(favoriteUser: FavoriteUser) {
        mfavoriteRepository.delete(favoriteUser)
    }


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
