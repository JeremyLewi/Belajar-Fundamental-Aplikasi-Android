package com.example.submissionawal.ui.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawal.R
import com.example.submissionawal.data.local.datastore.SettingPreferences
import com.example.submissionawal.data.local.entity.FavoriteUser
import com.example.submissionawal.data.remote.response.User
import com.example.submissionawal.databinding.ActivityFavoriteBinding
import com.example.submissionawal.helper.ViewModelFactory
import com.example.submissionawal.ui.adapter.ListUserAdapter
import com.example.submissionawal.ui.detail.DetailActivity


class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(application, SettingPreferences.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle(R.string.favorite_name)


        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)


        favoriteViewModel.getAllFavoriteUser().observe(this) { favoriteUser ->
            val listUser = mapListFavoriteToUser(favoriteUser)
            setFavoriteData(listUser)
        }
    }

    private fun mapListFavoriteToUser(listFavorite: List<FavoriteUser>): List<User> {
        val listUser = ArrayList<User>()
        for (favorite in listFavorite) {
            val user = User(
                login = favorite.username,
                avatarUrl = favorite.avatarUrl
            )
            listUser.add(user)
        }
        return listUser
    }

    private fun setFavoriteData(users: List<User>) {
        emptyFavorite(users.isEmpty())
        val listUserAdapter = ListUserAdapter(users)
        binding.rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intentToDetail = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                startActivity(intentToDetail)
            }

        })
    }

    private fun emptyFavorite(isEmpty: Boolean) {
        if (isEmpty) {
            binding.rvUser.visibility = View.GONE
            binding.emptyFavorite.visibility = View.VISIBLE
        } else {
            binding.rvUser.visibility = View.VISIBLE
            binding.emptyFavorite.visibility = View.GONE
        }
    }

}