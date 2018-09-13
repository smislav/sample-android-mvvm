package com.githubapp.mvvm.ui.main.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.githubapp.data.models.Repo
import javax.inject.Inject

class ReposViewModelFactory: ViewModelProvider.Factory{
    private var dataSourceFactory: ReposDataSourceFactory
    private var repos: LiveData<PagedList<Repo>>

    @Inject
    constructor(dataSourceFactory: ReposDataSourceFactory, repos: LiveData<PagedList<Repo>>){
        this.dataSourceFactory = dataSourceFactory
        this.repos = repos
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ReposViewModel::class.java)){
            return ReposViewModel(dataSourceFactory, repos) as T
        }
        throw RuntimeException("ViewModel not found")
    }
}