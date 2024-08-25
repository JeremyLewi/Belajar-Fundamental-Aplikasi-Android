package com.example.submissionawal.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionawal.data.remote.response.User
import com.example.submissionawal.databinding.ItemRowUserBinding
import com.example.submissionawal.ui.favorite.FavoriteActivity
import com.example.submissionawal.ui.main.MainActivity

class ListUserAdapter(private val listUser: List<User>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.binding.imgUserPhoto)

        holder.binding.tvUserName.text = user.login

        holder.itemView.setOnClickListener {
            if (holder.itemView.context is MainActivity) {
                onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
            } else if (holder.itemView.context is FavoriteActivity) {
                onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}








