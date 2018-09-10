package com.githubapp.mvvm.ui.main.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.githubapp.data.models.Repo
import com.githubapp.data.source.GithubRepository
import com.githubapp.mvvm.enums.Sort
import javax.inject.Inject

class ReposDataSourceFactory : DataSource.Factory<Int, Repo> {
    companion object {
        const val DEFAULT_QUERY = "android"
        val DEFAULT_SORT = Sort.FORKS
    }

    var dataSource: ReposDataSource
    var liveDataSource: MutableLiveData<ReposDataSource>

    var githubRepository: GithubRepository
    var query: String
    var sort: Sort

    @Inject
    constructor(githubRepository: GithubRepository){
        this.githubRepository = githubRepository
        this.query = DEFAULT_QUERY
        this.sort = DEFAULT_SORT

        dataSource = ReposDataSource(githubRepository, query, sort)
        liveDataSource = MutableLiveData()
    }

    override fun create(): DataSource<Int, Repo> {
        dataSource = ReposDataSource(githubRepository, query, sort)

        liveDataSource.postValue(dataSource)

        return dataSource
    }
}