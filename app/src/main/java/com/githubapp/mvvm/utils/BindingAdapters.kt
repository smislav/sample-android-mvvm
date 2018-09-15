package com.githubapp.mvvm.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.githubapp.data.models.Repo
import com.githubapp.mvvm.ui.main.repos.ReposAdapter

@BindingAdapter("adapter","repos")
fun setMutableRepos(recycler: RecyclerView, adapter: ReposAdapter, repos: LiveData<PagedList<Repo>>) {
    val activity: AppCompatActivity? = recycler.getActivity()
    if(activity != null) {
        repos.observe(activity, Observer { value ->
            adapter.submitList(value)
        })
    }
}