package com.example.submissionawal

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.submissionawal.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
