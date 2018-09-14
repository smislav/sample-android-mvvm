package com.githubapp.mvvm.ui.main.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.githubapp.data.models.Repo
import com.githubapp.data.models.User
import com.githubapp.mvvm.R

class ReposAdapter: PagedListAdapter<Repo, ReposViewHolder>(RepoDiffCallback) {
    var userClick: MutableLiveData<User> = MutableLiveData()
    var repoClick: MutableLiveData<Repo> = MutableLiveData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false))
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bind(getItem(position), userClick, repoClick)
    }

    companion object {
        val RepoDiffCallback = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }
        }
    }
}