package com.example.submissionawal.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submissionawal.data.local.entity.FavoriteUser
import com.example.submissionawal.data.local.room.FavoriteDao
import com.example.submissionawal.data.local.room.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mfavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mfavoriteDao = db.favoriteDao()
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mfavoriteDao.getAllFavoriteUser()


    fun getFavoriteUser(username: String): LiveData<FavoriteUser> =
        mfavoriteDao.getFavoriteUserByUsername(username)

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mfavoriteDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mfavoriteDao.delete(favoriteUser) }
    }

}


