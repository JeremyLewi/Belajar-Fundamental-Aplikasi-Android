package com.example.submissionawal.data.remote.retrofit

import com.example.submissionawal.data.remote.response.DetailUserResponse
import com.example.submissionawal.data.remote.response.User
import com.example.submissionawal.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getUsers(
        @Query("q") username: String? = null,
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<User>>
}
