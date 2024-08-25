package com.example.submissionawal.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bumptech.glide.Glide
import com.example.submissionawal.R
import com.example.submissionawal.data.local.datastore.SettingPreferences
import com.example.submissionawal.data.local.entity.FavoriteUser
import com.example.submissionawal.data.remote.response.DetailUserResponse
import com.example.submissionawal.databinding.ActivityDetailBinding
import com.example.submissionawal.helper.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application, SettingPreferences.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle(R.string.detail_name)


        val username = intent.getStringExtra(EXTRA_USERNAME)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        sectionsPagerAdapter.username = username ?: ""

        detailViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        detailViewModel.userDetail.observe(this) { userDetail ->
            setUserDetail(userDetail)

        }

        if (username != null) {
            detailViewModel.findDetailUser(username)

            detailViewModel.getFavoriteUser(username).observe(this) { favoriteUser ->
                if (favoriteUser != null) {
                    binding.fabAdd.setImageResource(R.drawable.ic_favorite_full)
                    binding.fabAdd.setOnClickListener {
                        detailViewModel.delete(favoriteUser)
                    }
                } else {
                    binding.fabAdd.setImageResource(R.drawable.ic_favorite)
                    binding.fabAdd.setOnClickListener {
                        detailViewModel.insert(
                            FavoriteUser(
                                username,
                                avatarUrl = detailViewModel.userDetail.value?.avatarUrl
                            )
                        )
                    }
                }
            }
        }
    }


    private fun setUserDetail(user: DetailUserResponse) {
        binding.apply {
            tvName.text = user.name
            tvTotalFollowers.text = user.followers.toString()
            tvTotalFollowing.text = user.following.toString()
            tvUserName.text = user.login
            Glide.with(this@DetailActivity).load(user.avatarUrl).into(imgUserPhoto)

        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"


        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }
}
