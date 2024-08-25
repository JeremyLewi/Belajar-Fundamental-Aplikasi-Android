package com.example.submissionawal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawal.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private var position: Int = 0
    private var username: String? = null
    private val followViewModel by viewModels<FollowViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)


        val layoutManager = LinearLayoutManager(context)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        followViewModel.userFollowers.observe(viewLifecycleOwner) {
            binding.rvUsers.adapter = ListUserAdapter(it)

        }

        followViewModel.userFollowing.observe(viewLifecycleOwner) {
            binding.rvUsers.adapter = ListUserAdapter(it)

        }

        followViewModel.isLoadingFollow.observe(viewLifecycleOwner) {
            binding.progressBarFollow.visibility = if (it) View.VISIBLE else View.GONE
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1) {
            username?.let { followViewModel.findUserFollowers(it) }
        } else {
            username?.let { followViewModel.findUserFollowing(it) }
        }

    }

    companion object {

        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }


}
