package com.githubapp.mvvm.ui.main.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.githubapp.data.models.Repo
import com.githubapp.mvvm.enums.Sort
import javax.inject.Inject

class ReposViewModel : ViewModel {
    var repos: LiveData<PagedList<Repo>>

    private var dataSourceFactory: ReposDataSourceFactory

    @Inject
    constructor(dataSourceFactory: ReposDataSourceFactory, repos: LiveData<PagedList<Repo>>){
        this.dataSourceFactory = dataSourceFactory
        this.repos = repos
    }

    fun getLiveDataSource(): MutableLiveData<ReposDataSource>{
        return dataSourceFactory.liveReposSource
    }

    fun setQuery(query: String){
        dataSourceFactory.query = query
        reloadData()
    }

    fun setSort(sort: Sort){
        dataSourceFactory.sort = sort
        reloadData()
    }

    fun reloadData(){
        dataSourceFactory.reposSource.invalidate()
    }
}