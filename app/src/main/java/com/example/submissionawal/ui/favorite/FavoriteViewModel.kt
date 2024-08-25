package com.example.submissionawal.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionawal.data.local.entity.FavoriteUser
import com.example.submissionawal.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> =
        mFavoriteRepository.getAllFavoriteUser()
}



